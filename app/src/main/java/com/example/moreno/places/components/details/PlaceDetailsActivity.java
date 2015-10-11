package com.example.moreno.places.components.details;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;

/**
 * Created on 09.10.2015.
 */
public class PlaceDetailsActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PlaceDetailsFragment.OnDetailsReceivedListener{

    private static final String LOG_TAG = "PlaceDetailsActivity";
    private PlaceDetailsFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareFragment();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
    public void onDetailsReceived(Place place) {
        Log.d(LOG_TAG, place.toString());
    }
}
