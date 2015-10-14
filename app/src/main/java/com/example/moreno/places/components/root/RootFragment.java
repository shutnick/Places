package com.example.moreno.places.components.root;

import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moreno.places.components.details.PlaceDetailsFragment;
import com.example.moreno.places.components.root.list.PlaceDataHolder;
import com.example.moreno.places.network.RequestHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        if (mUserLocation == null) {
            //TODO show warning that location didn't find yet
            return;
        }
        mNearLocationRequested = true;
        Log.i(LOG_TAG, "Define near places");
        //TODO Google recommends to use Android Places API or Places Library of the Google Maps Javascript API rather than Google Places API Web Service
        //Google Places API Web Service recommended for web based applications.
        final String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "key=AIzaSyCvrXZgAyUziaCAbt7zg9eQovlG2nRa8wk&" +
                "location=" + mUserLocation.getLatitude() + "," + mUserLocation.getLongitude() + "&" +
                "radius=500&" +
                "rankby=prominence";
        final Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonPlaces = response.getJSONArray("results");
                    int arraySize = jsonPlaces.length();
                    for (int i = 0; i < arraySize; i++) {
                        JSONObject jPlace = jsonPlaces.getJSONObject(i);

                        JSONObject jPlaceLocation = jPlace.getJSONObject("geometry").getJSONObject("location");
                        Location placeLocation = new Location("Place location");
                        placeLocation.setLongitude(jPlaceLocation.getDouble("lng"));
                        placeLocation.setLatitude(jPlaceLocation.getDouble("lat"));

                        String placeType = jPlace.getJSONArray("types").getString(0);
                        PlaceDataHolder data = new PlaceDataHolder.Builder()
                                .placeId(jPlace.getString("place_id"))
                                //TODO save String place type
                                .placeType(0)
                                .address(jPlace.getString("vicinity"))
                                .name(jPlace.getString("name"))
                                .distance(mUserLocation.distanceTo(placeLocation))
                                .build();
                        mPlacesList.add(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (getActivity() instanceof OnDataReceivedListener) {
                    ((OnDataReceivedListener) getActivity()).onDataReceived(mPlacesList);
                }
                Log.d(LOG_TAG, response.toString());
                mNearLocationRequested = false;
            }
        };
        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "Error");
            }
        };
        RequestHandler.getInstance(getActivity().getApplicationContext()).getRequestQueue().
                add(new JsonObjectRequest(Request.Method.GET, url, null, successListener, errorListener));


/*        final PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mApiClient, new PlaceFilter());
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
        });*/
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
                            ((OnDataReceivedListener) getActivity()).onDataReceived(mPlacesList);
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
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria locationCriteria = new Criteria();
        locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        locationManager.requestSingleUpdate(locationCriteria, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mUserLocation = location;
                Log.d(LOG_TAG, "Current location: " + mUserLocation.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }, null);

//        mUserLocation = LocationServices.FusedLocationApi.getLastLocation(mApiClient);
    }

    public void updatePlaceList() {
        if (mNearLocationRequested) {
            getNearLocations();
        } else if (mQueryLocationRequested && mRequest != null) {
            getRequestedLocations(mRequest);
        } else if (!mPlacesList.isEmpty()) {
            ((OnDataReceivedListener) getActivity()).onDataReceived(mPlacesList);
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
