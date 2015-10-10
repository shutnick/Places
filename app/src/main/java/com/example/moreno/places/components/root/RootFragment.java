package com.example.moreno.places.components.root;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.example.moreno.places.components.root.list.PlaceDataHolder;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 09.10.2015.
 */
public class RootFragment extends Fragment {

    public static final String TAG = "root fragment";
    public static final String LOG_TAG = "RootFragment";
    private GoogleApiClient mApiClient;
    private Activity mActivity;
    private List<PlaceDataHolder> mPlacesList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mActivity = getActivity();
        mApiClient = new GoogleApiClient.Builder(mActivity)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) mActivity)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) mActivity)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mApiClient.connect();
        ((OnDataReceivedListener) mActivity).onDataReceived(mPlacesList);
    }

    @Override
    public void onStop() {
        mApiClient.disconnect();
        super.onStop();
    }

    public void getNearLocations() {
        Log.i(LOG_TAG, "Define near places");
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mApiClient, new PlaceFilter());
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                boolean resultSuccess = placeLikelihoods.getStatus().isSuccess();
                Log.i(LOG_TAG,  "Near places result success: " + resultSuccess);
                if (resultSuccess) {
                    mPlacesList.clear();
                    for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                        final Place place = placeLikelihood.getPlace();
                        Log.d(LOG_TAG, "Near place: " + place.toString());
                        PlaceDataHolder.Builder builder = new PlaceDataHolder.Builder()
                                .url("default")
                                .name(place.getName())
                                .address(place.getAddress())
                                .distance("default");
                        mPlacesList.add(builder.build());
                    }
                    ((OnDataReceivedListener) mActivity).onDataReceived(mPlacesList);
                }

            }
        });
    }

    public void getRequestedLocations(String query) {
        Log.i(LOG_TAG, "Define query places");
        PendingResult<AutocompletePredictionBuffer> predictions = Places.GeoDataApi.getAutocompletePredictions(mApiClient, query, null, null);
        predictions.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
            @Override
            public void onResult(AutocompletePredictionBuffer autocompletePredictions) {
                boolean resultSuccess = autocompletePredictions.getStatus().isSuccess();
                Log.i(LOG_TAG,  "Requested places result success: " + resultSuccess);
                if (resultSuccess) {
                    mPlacesList.clear();
                    for (AutocompletePrediction prediction : autocompletePredictions) {
                        Log.d(LOG_TAG, "Place: " + prediction.toString());
                        PlaceDataHolder.Builder builder = new PlaceDataHolder.Builder()
                                .url("default")
                                .name(prediction.getDescription())
                                .address("default")
                                .distance("default");
                        mPlacesList.add(builder.build());
                    }
                    ((OnDataReceivedListener) mActivity).onDataReceived(mPlacesList);
                }
            }
        });
    }

    public interface OnDataReceivedListener {
        void onDataReceived(List<PlaceDataHolder> places);
    }
}
