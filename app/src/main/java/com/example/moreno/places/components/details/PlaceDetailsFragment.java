package com.example.moreno.places.components.details;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.example.moreno.places.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 09.10.2015.
 */
public class PlaceDetailsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = "place details fragment";
    public static final String PLACE_ID_KEY = "place id";
    private static final String LOG_TAG = "PlaceDetailsFragment";
    private GoogleApiClient mApiClient;
    private List<Bitmap> mPhotos = new ArrayList<>();
    private boolean mAllPhotosLoaded;
    private PlaceDetailsHolder mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addApi(Places.GEO_DATA_API)
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

    public void getPlaceDetails() {
        String placeId = getActivity().getIntent().getStringExtra(PLACE_ID_KEY);
        if (mData == null) {
            getDetails(placeId);
        } else {
            if (getActivity() instanceof OnDetailsReceivedListener) {
                ((OnDetailsReceivedListener) getActivity()).onPlaceReceived(mData);
            }
        }

        if (!mAllPhotosLoaded) {
            getPhotos(placeId);
        } else {
            if (getActivity() instanceof OnDetailsReceivedListener) {
                ((OnDetailsReceivedListener) getActivity()).onAllPhotosLoaded(mPhotos);
            }
        }
    }

    private void getPhotos(String placeId) {
        Log.i(LOG_TAG, "Request photos");
        mPhotos.clear();
        mAllPhotosLoaded = false;
//        final PendingResult<PlacePhotoMetadataResult> photosResult = Places.GeoDataApi.getPlacePhotos(mApiClient, "ChIJN1t_tDeuEmsRUsoyG83frY4");
        final PendingResult<PlacePhotoMetadataResult> photosResult = Places.GeoDataApi.getPlacePhotos(mApiClient, placeId);
        ResultCallback<PlacePhotoMetadataResult> callback = new ResultCallback<PlacePhotoMetadataResult>() {
            @Override
            public void onResult(PlacePhotoMetadataResult placePhotoMetadataResult) {
                final boolean requestSuccess = placePhotoMetadataResult.getStatus().isSuccess();
                Log.i(LOG_TAG, "Response photos success: " + requestSuccess);
                if (requestSuccess) {
                    final PlacePhotoMetadataBuffer photoMetadata = placePhotoMetadataResult.getPhotoMetadata();
                    int photoSize = getResources().getDimensionPixelSize(R.dimen.details_photo_width);
                    for (PlacePhotoMetadata data : photoMetadata) {
                        final PendingResult<PlacePhotoResult> photoResult = data.getScaledPhoto(mApiClient, photoSize, photoSize);
                        ResultCallback<PlacePhotoResult> callback1 = new ResultCallback<PlacePhotoResult>() {
                            @Override
                            public void onResult(PlacePhotoResult placePhotoResult) {
                                if (placePhotoResult.getStatus().isSuccess()) {
                                    final Bitmap photo = placePhotoResult.getBitmap();
                                    mPhotos.add(photo);
                                    if (getActivity() instanceof OnDetailsReceivedListener) {
                                        ((OnDetailsReceivedListener) getActivity()).onPhotoLoaded(photo);
                                    }
                                    Log.d(LOG_TAG, "Photo " + mPhotos.size() + " added");
                                    if (photoMetadata.getCount() == mPhotos.size()) {
                                        Log.d(LOG_TAG, "All photos loaded");
                                        mAllPhotosLoaded = true;
                                    }
                                }
                            }
                        };
                        photoResult.setResultCallback(callback1);
                    }
                    photoMetadata.release();
                }
            }
        };
        photosResult.setResultCallback(callback);
    }

    private void getDetails(String placeId) {
        Log.i(LOG_TAG, "Request details");
        final PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mApiClient, placeId);
        ResultCallback<PlaceBuffer> callback = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                final boolean requestSuccess = places.getStatus().isSuccess();
                Log.i(LOG_TAG, "Response details success: " + requestSuccess);
                if (requestSuccess){
                    mData = new PlaceDetailsHolder(places.get(0));
                }
                if (getActivity() instanceof OnDetailsReceivedListener) {
                    ((OnDetailsReceivedListener) getActivity()).onPlaceReceived(mData);
                }
                places.release();
            }
        };
        placeResult.setResultCallback(callback);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(LOG_TAG, "Api client connected");
        getPlaceDetails();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "Api client suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "Api client failed");
    }


    public interface OnDetailsReceivedListener {
        void onPlaceReceived(PlaceDetailsHolder data);
        void onPhotoLoaded(Bitmap photo);
        void onAllPhotosLoaded(List<Bitmap> photos);
    }
}
