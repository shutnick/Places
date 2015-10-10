package com.example.moreno.places.components.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.moreno.places.R;

/**
 * Created on 09.10.2015.
 */
public class PlaceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_layout);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment rootFragment = supportFragmentManager.findFragmentByTag(PlaceDetailsFragment.TAG);
        if (rootFragment == null) {
            rootFragment = new PlaceDetailsFragment();
        }
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(rootFragment, PlaceDetailsFragment.TAG);
        transaction.commit();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Ciklum");
        actionBar.setIcon(R.drawable.app_icon);
    }

}
