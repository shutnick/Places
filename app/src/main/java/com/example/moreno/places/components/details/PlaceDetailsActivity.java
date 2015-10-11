package com.example.moreno.places.components.details;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moreno.places.R;
import com.example.moreno.places.components.details.list.ImagesListView;
import com.example.moreno.places.components.details.list.PhotosListAdapter;
import com.example.moreno.places.components.details.list.TypesListAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

/**
 * Created on 09.10.2015.
 */
public class PlaceDetailsActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PlaceDetailsFragment.OnDetailsReceivedListener{

    private static final String LOG_TAG = "PlaceDetailsActivity";
    private PlaceDetailsFragment mFragment;
    private TextView mNameView;
    private ImagesListView mTypesListView;
    private RatingBar mRatingView;
    private TextView mAddressView;
    private TextView mPhoneView;
    private TextView mWebSiteView;
    private TextView mPriceView;
    private ImagesListView mPhotosListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        prepareFragment();
        saveItems();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void saveItems() {
        mNameView = (TextView) findViewById(R.id.place_details_name);
        mTypesListView = (ImagesListView) findViewById(R.id.place_details_types);
        mRatingView = (RatingBar) findViewById(R.id.place_details_rating);
        mAddressView = (TextView) findViewById(R.id.place_details_address);
        mPhoneView = (TextView) findViewById(R.id.place_details_phone_number);
        mWebSiteView = (TextView) findViewById(R.id.place_details_website);
        mPriceView = (TextView) findViewById(R.id.place_details_price);
        mPhotosListView = (ImagesListView) findViewById(R.id.place_details_photos);
    }

    private void prepareFragment() {
        FragmentManager supportFragmentManager = getFragmentManager();
        mFragment = (PlaceDetailsFragment) supportFragmentManager.findFragmentByTag(PlaceDetailsFragment.TAG);
        if (mFragment == null) {
            mFragment = new PlaceDetailsFragment();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.add(mFragment, PlaceDetailsFragment.TAG);
            transaction.commit();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mFragment.getPlaceDetails();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "Api client suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "Api client failed");
    }

    @Override
    public void onPlaceReceived(PlaceDetailsHolder data) {
        mNameView.setText(data.name);
        mTypesListView.setAdapter(new TypesListAdapter(data.types));
        mRatingView.setRating(data.rating);
        mAddressView.setText(data.address);
        mPhoneView.setText(data.phone);
        mWebSiteView.setText(data.website);
        mPriceView.setText(data.priceLevel + "");
    }

    @Override
    public void onPhotosReceived(List<Bitmap> photos) {
        mPhotosListView.setAdapter(new PhotosListAdapter(photos));
    }
}
