package com.coffee2code.adalovelace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by adityathanekar on 08/01/17.
 */

public class UserInfoFragment extends Fragment {

    private AppCompatSpinner mSpinner;
    private String[] arrayString;
    private AppCompatImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        mSpinner = (AppCompatSpinner) view.findViewById(R.id.user_spinner);
        arrayString = new String[]{"User", "Ambulance", "Hospital"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayString);

        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (arrayString[position].equalsIgnoreCase("Ambulance")) {
                    mImageView.setImageResource(R.drawable.ic_local_shipping_black_24dp);
                    mImageView.setScaleType(ImageView.ScaleType.CENTER);
                } else if (arrayString[position].equalsIgnoreCase("Hospital")) {
                    mImageView.setImageResource(R.drawable.ic_local_hospital_black_24dp);
                    mImageView.setScaleType(ImageView.ScaleType.CENTER);
                } else {
                    mImageView.setImageResource(R.drawable.ic_person_outline_black_24dp);
                    mImageView.setScaleType(ImageView.ScaleType.CENTER);
                }
            }
        });
        return view;
    }
}
