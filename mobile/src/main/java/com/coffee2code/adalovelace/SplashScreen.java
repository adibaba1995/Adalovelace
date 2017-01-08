package com.coffee2code.adalovelace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

/**
 * Created by adityathanekar on 08/01/17.
 */

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    public static final String SHARED_PREFERENCES = "ADALOVELACE_SHARED";
    private static final String ACTIVITY_LAUNCHED_PREVIOUSLY = "ACTIVITY_LAUNCHED";
    private boolean condition;

    static final int RC_SIGN_IN = 100;
    private FirebaseAuth mAuth;

    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
    }


    @Override
    protected void onResume() {
        super.onResume();


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth auth = FirebaseAuth.getInstance();


                if (auth.getCurrentUser() != null) {

// already signed in
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                                    .setLogo(R.mipmap.ic_launcher)
                                    .setTheme(R.style.AppTheme)
                                    .setIsSmartLockEnabled(false)
                                    .build(),
                            RC_SIGN_IN);

                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
            condition = preferences.getBoolean(ACTIVITY_LAUNCHED_PREVIOUSLY, false);
            if (!condition) {
                condition = true;
                preferences.edit().putBoolean(ACTIVITY_LAUNCHED_PREVIOUSLY, condition).apply();
                startActivity(new Intent(this, IntroActivity.class));
            }
            startActivity(new Intent(this, IntroActivity.class));
            finish();


        }

// Sign in canceled
        if (resultCode == RESULT_CANCELED) {
//            showSnackbar(R.string.sign_in_cancelled);
            return;
        }

// No network
        if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
//            showSnackbar(R.string.no_internet_connection);
            return;
        }

// User is not signed in. Maybe just wait for the user to press
// "sign in" again, or show a message.
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}