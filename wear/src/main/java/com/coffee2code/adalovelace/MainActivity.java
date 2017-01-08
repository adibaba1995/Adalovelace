package com.coffee2code.adalovelace;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity implements HeartBeatService.OnChangeListener {

    private static final String TAG = "MainActivity";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.heartbeatText);
        bindService(new Intent(MainActivity.this, HeartBeatService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(TAG, "connected to service.");

                ((HeartBeatService.HeartBeatServiceBinder)iBinder).setChangeListener(MainActivity.this);

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        }, Service.BIND_AUTO_CREATE);


        
    }

    @Override
    protected void onResume() {
        super.onResume();

    }




    @Override
    public void onValueChanged(int newValue) {
        mTextView.setText(String.valueOf(newValue));
    }
}
