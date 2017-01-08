package com.coffee2code.adalovelace;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import java.util.List;

/**
 * Created by adityathanekar on 08/01/17.
 */

public class HeartBeatService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private int currentValue;
    private static final String LOG_TAG = "MyHeart";
    private IBinder binder = new HeartBeatServiceBinder();
    private OnChangeListener onChangeListener;
    private GoogleApiClient mGoogleApiClient;

    public interface OnChangeListener {
        void onValueChanged(int newValue);
    }

    public class HeartBeatServiceBinder extends Binder {

        public void setChangeListener(OnChangeListener listener) {
            onChangeListener = listener;
            //return known value
            listener.onValueChanged(currentValue);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE && sensorEvent.values.length > 0) {
            int newValue = Math.round(sensorEvent.values[0]);
            //Log.d(LOG_TAG,sensorEvent.sensor.getName() + " changed to: " + newValue);
            // only do something if the value differs from the value before and the value is not 0.
            if (currentValue != newValue && newValue != 0) {
                // save the new value
                currentValue = newValue;
                // send the value to the listener
                if (onChangeListener != null) {
                    Log.d(LOG_TAG, "sending new value to listener: " + newValue);
                    onChangeListener.onValueChanged(newValue);
                    sendMessageToHandheld(Integer.toString(newValue));
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensorEvent, int i) {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        boolean res = mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        mGoogleApiClient.connect();



    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    private void sendMessageToHandheld(final String message) {

        if (mGoogleApiClient == null)
            return;

        Log.d(LOG_TAG, "sending a message to handheld: " + message);

        // use the api client to send the heartbeat value to our handheld
        final PendingResult<NodeApi.GetConnectedNodesResult> nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient);
        nodes.setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult result) {
                final List<Node> nodes = result.getNodes();
                if (nodes != null) {
                    for (int i = 0; i < nodes.size(); i++) {
                        final Node node = nodes.get(i);
                        Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), message, null);
                    }
                }
            }
        });


    }
}