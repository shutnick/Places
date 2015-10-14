package com.example.moreno.places.components.details;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moreno.places.R;
import com.example.moreno.places.components.details.list.ImagesListView;
import com.example.moreno.places.components.details.list.PhotosListAdapter;
import com.example.moreno.places.components.details.list.TypesListAdapter;

import java.util.List;

/**
 * Created on 09.10.2015.
 */
public class PlaceDetailsActivity extends AppCompatActivity
        implements
        PlaceDetailsFragment.OnDetailsReceivedListener{

    private static final String LOG_TAG = "PlaceDetailsActivity";
    private PlaceDetailsFragment mFragment;
    private TextView mNameView;
    private ImagesListView mTypesListView;
    private RatingBar mRatingView;
    private TextView mAddressView;
    private TextView mPhoneView;
    private TextView mWebSiteView;
    private PriceLevelTextView mPriceView;
    private ImagesListView mPhotosListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        prepareFragment();
        init();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        mNameView = (TextView) findViewById(R.id.place_details_name);
        mTypesListView = (ImagesListView) findViewById(R.id.place_details_types);
        mTypesListView.setAdapter(new TypesListAdapter());
        mRatingView = (RatingBar) findViewById(R.id.place_details_rating);
        mAddressView = (TextView) findViewById(R.id.place_details_address);
        mPhoneView = (TextView) findViewById(R.id.place_details_phone_number);
        mWebSiteView = (TextView) findViewById(R.id.place_details_website);
        mPriceView = (PriceLevelTextView) findViewById(R.id.place_details_price);
        mPhotosListView = (ImagesListView) findViewById(R.id.place_details_photos);
        mPhotosListView.setAdapter(new PhotosListAdapter());
    }

    private void prepareFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        mFragment = (PlaceDetailsFragment) fragmentManager.findFragmentByTag(PlaceDetailsFragment.TAG);
        if (mFragment == null) {
            mFragment = new PlaceDetailsFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(mFragment, PlaceDetailsFragment.TAG);
            transaction.commit();
        }
    }


    @Override
    public void onPlaceReceived(PlaceDetailsHolder data) {
        TypesListAdapter adapter = (TypesListAdapter) mTypesListView.getAdapter();
        adapter.addTypes(data.types);
        mNameView.setText(data.name);
        mRatingView.setRating(data.rating);
        mAddressView.setText(data.address);
        mPhoneView.setText("Phone: " + data.phone);
        mWebSiteView.setText("Website: " + data.website);
        mPriceView.setText(data.priceLevel, new Object());
    }

    @Override
    public void onPhotoLoaded(Bitmap photo) {
        final PhotosListAdapter adapter = (PhotosListAdapter) mPhotosListView.getAdapter();
        adapter.addPhoto(photo);
    }

    @Override
    public void onAllPhotosLoaded(List<Bitmap> photos) {
        final PhotosListAdapter adapter = (PhotosListAdapter) mPhotosListView.getAdapter();
        adapter.addPhotos(photos);
    }
}
