package com.example.moreno.places.components.details;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

/**
 * Created on 09.10.2015.
 */
public class PlaceDetailsFragment extends Fragment {
    public static final String TAG = "place details fragment";
    public static final String PLACE_ID_KEY = "place id";
    private GoogleApiClient mApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        final Activity activity = getActivity();
        mApiClient = new GoogleApiClient.Builder(activity)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) activity)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) activity)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiClient.connect();
    }

    @Override
    public void onStop() {
        mApiClient.disconnect();
        super.onStop();
    }

    public void getPlaceDetails() {
        String placeId = getActivity().getIntent().getStringExtra(PLACE_ID_KEY);
        final PendingResult<PlaceBuffer> place = Places.GeoDataApi.getPlaceById(mApiClient, placeId);
        ResultCallback<PlaceBuffer> callback = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (places.getStatus().isSuccess()){
                    ((OnDetailsReceivedListener) getActivity()).onDetailsReceived(places.get(0));
                }
            }
        };
        place.setResultCallback(callback);
    }

    public interface OnDetailsReceivedListener {
        void onDetailsReceived(Place place);
    }
}
