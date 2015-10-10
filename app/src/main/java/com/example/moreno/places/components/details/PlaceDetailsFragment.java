package com.example.moreno.places.components.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created on 09.10.2015.
 */
public class PlaceDetailsFragment extends Fragment {
    public static final String TAG = "place details fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
