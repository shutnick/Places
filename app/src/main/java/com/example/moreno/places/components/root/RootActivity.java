package com.example.moreno.places.components.root;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.moreno.places.R;
import com.example.moreno.places.components.details.PlaceDetailsActivity;

/**
 * Created on 09.10.2015.
 */
public class RootActivity extends AppCompatActivity{

    private RootFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_layout);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        mFragment = (RootFragment) supportFragmentManager.findFragmentByTag(RootFragment.TAG);
        if (mFragment == null) {
            mFragment = new RootFragment();
        }
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(mFragment, RootFragment.TAG);
        transaction.commit();
    }



    private void showSearch() {
        findViewById(R.id.search_panel).setVisibility(View.VISIBLE);
        findViewById(R.id.search_tip_text).setVisibility(View.GONE);
    }

    private void openPlaceDetails() {
        Intent detailsIntent = new Intent(this, PlaceDetailsActivity.class);
        startActivity(detailsIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.root_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.current_location_places:
                mFragment.defineLocation();
                break;
            case R.id.search_place:
                showSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
