package com.example.moreno.places.components.root;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.moreno.places.components.details.PlaceDetailsFragment;
import com.example.moreno.places.components.root.list.PlaceDataHolder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 09.10.2015.
 */
public class RootFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "root fragment";
    public static final String LOG_TAG = "RootFragment";
    private GoogleApiClient mApiClient;
    private List<PlaceDataHolder> mPlacesList = new ArrayList<>();
    private Location mUserLocation;
    private boolean mNearLocationRequested;
    private boolean mQueryLocationRequested;
    private String mRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
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

    public void getNearLocations() {
        mNearLocationRequested = true;
        Log.i(LOG_TAG, "Define near places");
        final PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mApiClient, new PlaceFilter());
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                boolean resultSuccess = placeLikelihoods.getStatus().isSuccess();
                Log.i(LOG_TAG, "Near places result success: " + resultSuccess);
                if (resultSuccess) {
                    mPlacesList.clear();
                    for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                        final Place place = placeLikelihood.getPlace();
                        addPlaceData(place);
                    }
                    if (getActivity() instanceof PlaceDetailsFragment.OnDetailsReceivedListener) {
                        ((OnDataReceivedListener) getActivity()).onDataReceived(mPlacesList);
                    }
                }
                mNearLocationRequested = false;
                placeLikelihoods.release();
            }
        });
    }

    public void getRequestedLocations(String query) {
        mQueryLocationRequested = true;
        mRequest = query;
        Log.i(LOG_TAG, "Define query places");
        final PendingResult<AutocompletePredictionBuffer> predictions = Places.GeoDataApi.getAutocompletePredictions(mApiClient, query, null, null);
        predictions.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
            @Override
            public void onResult(AutocompletePredictionBuffer autocompletePredictions) {
                boolean resultSuccess = autocompletePredictions.getStatus().isSuccess();
                Log.i(LOG_TAG, "Requested places result success: " + resultSuccess);
                if (resultSuccess) {
                    mPlacesList.clear();

                    List<String> placesIdList = new ArrayList<>();
                    for (AutocompletePrediction prediction : autocompletePredictions) {
                        Log.d(LOG_TAG, "Place: " + prediction.toString());
                        placesIdList.add(prediction.getPlaceId());
                    }
                    String[] placesIdArray = new String[placesIdList.size()];
                    PendingResult<PlaceBuffer> place = Places.GeoDataApi.getPlaceById(mApiClient, placesIdList.toArray(placesIdArray));
                    ResultCallback<PlaceBuffer> callback = new ResultCallback<PlaceBuffer>() {
                        @Override
                        public void onResult(PlaceBuffer places) {
                            for (Place place : places) {
                                addPlaceData(place);
                            }

                            if (getActivity() instanceof PlaceDetailsFragment.OnDetailsReceivedListener) {
                                ((OnDataReceivedListener) getActivity()).onDataReceived(mPlacesList);
                            }
                        }
                    };
                    place.setResultCallback(callback);
                }
                mQueryLocationRequested = false;
                mRequest = null;
                autocompletePredictions.release();
            }
        });
    }

    private void addPlaceData(Place place) {
        final List<Integer> placeTypes = place.getPlaceTypes();
        final LatLng latLng = place.getLatLng();
        Location placeLocation = new Location("Place location");
        placeLocation.setLatitude(latLng.latitude);
        placeLocation.setLongitude(latLng.longitude);
        PlaceDataHolder.Builder builder = new PlaceDataHolder.Builder()
                .placeId(place.getId())
                .placeType(placeTypes.isEmpty() ? 0 : placeTypes.get(0))
                .name(place.getName())
                .address(place.getAddress())
                .distance(mUserLocation.distanceTo(placeLocation));

        mPlacesList.add(builder.build());
    }

    public void getUserLocation() {
        mUserLocation = LocationServices.FusedLocationApi.getLastLocation(mApiClient);
    }

    public void updatePlaceList() {
        if (mNearLocationRequested) {
            getNearLocations();
        } else if (mQueryLocationRequested && mRequest != null) {
            getRequestedLocations(mRequest);
        } else if (!mPlacesList.isEmpty()) {
            if (getActivity() instanceof PlaceDetailsFragment.OnDetailsReceivedListener) {
                ((OnDataReceivedListener) getActivity()).onDataReceived(mPlacesList);
            }

        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(LOG_TAG, "Api client connected");
        getUserLocation();
        updatePlaceList();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "Api client suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "Api client failed");
    }

    public interface OnDataReceivedListener {
        void onDataReceived(List<PlaceDataHolder> places);
    }
}
