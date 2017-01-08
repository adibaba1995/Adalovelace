package com.coffee2code.adalovelace;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by adityathanekar on 08/01/17.
 */

public class IntroActivity extends AppIntro2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("THIS IS TEST  ", "HELLLO SOME SMALL DESCRIPTION", R.mipmap.ic_launcher, getResources().getColor(R.color.colorAccent)));
    }
}
