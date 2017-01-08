package com.coffee2code.adalovelace;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by adityathanekar on 08/01/17.
 */

public class UserInfoFragment extends Fragment {

    private AppCompatSpinner mSpinner;
    private String[] arrayString;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        arrayString = new String[]{"User","Ambulance","Hospital"};
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, arrayString);
        return view;
    }
}
