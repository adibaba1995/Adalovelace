package com.coffee2code.adalovelace;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;




/**
 * Created by adityathanekar on 08/01/17.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment newInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = newInstance();
            fragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }

}
