package com.example.moreno.places.components.details;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.moreno.places.R;
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
public class PlaceDetailsFragment extends Fragment {
    public static final String TAG = "place details fragment";
    public static final String PLACE_ID_KEY = "place id";
    private GoogleApiClient mApiClient;
    private List<Bitmap> mPhotos = new ArrayList<>();
    private PlaceDetailsHolder mData;

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
        if (mData == null) {
            String placeId = getActivity().getIntent().getStringExtra(PLACE_ID_KEY);
            getDetails(placeId);
            getPhotos(placeId);
        } else {
            ((OnDetailsReceivedListener) getActivity()).onPlaceReceived(mData);
        }
    }

    private void getPhotos(String placeId) {
        mPhotos.clear();
        final PendingResult<PlacePhotoMetadataResult> photosResult = Places.GeoDataApi.getPlacePhotos(mApiClient, placeId);
        ResultCallback<PlacePhotoMetadataResult> callback = new ResultCallback<PlacePhotoMetadataResult>() {
            @Override
            public void onResult(PlacePhotoMetadataResult placePhotoMetadataResult) {
                if (placePhotoMetadataResult.getStatus().isSuccess()) {
                    final PlacePhotoMetadataBuffer photoMetadata = placePhotoMetadataResult.getPhotoMetadata();
                    int photoSize = getResources().getDimensionPixelSize(R.dimen.details_photo_width);
                    photoMetadata.getCount();
                    for (PlacePhotoMetadata data : photoMetadata) {
                        final PendingResult<PlacePhotoResult> photoResult = data.getScaledPhoto(mApiClient, photoSize, photoSize);
                        ResultCallback<PlacePhotoResult> callback1 = new ResultCallback<PlacePhotoResult>() {
                            @Override
                            public void onResult(PlacePhotoResult placePhotoResult) {
                                if (placePhotoResult.getStatus().isSuccess()) {
                                    final Bitmap photo = placePhotoResult.getBitmap();
                                    mPhotos.add(photo);
                                }
                            }
                        };
                        photoResult.setResultCallback(callback1);
                    }
                    ((OnDetailsReceivedListener) getActivity()).onPhotosReceived(mPhotos);
                    photoMetadata.release();
                }
            }
        };
        photosResult.setResultCallback(callback);
    }

    private void getDetails(String placeId) {
        final PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mApiClient, placeId);
        ResultCallback<PlaceBuffer> callback = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (places.getStatus().isSuccess()){
                    mData = new PlaceDetailsHolder(places.get(0));
                }
                ((OnDetailsReceivedListener) getActivity()).onPlaceReceived(mData);
                places.release();
            }
        };
        placeResult.setResultCallback(callback);
    }

    public interface OnDetailsReceivedListener {
        void onPlaceReceived(PlaceDetailsHolder data);
        void onPhotosReceived(List<Bitmap> photos);
    }
}
