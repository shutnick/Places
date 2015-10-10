package com.example.moreno.places.components.root;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created on 09.10.2015.
 */
public class RootFragment extends Fragment {

    public static final String TAG = "root fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void defineLocation() {

    }
}
