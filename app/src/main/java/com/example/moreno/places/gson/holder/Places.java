package com.example.moreno.places.gson.holder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 15.10.2015.
 */
public class Places {
    private final List<PlaceDataHolder> mPlaces;

    public Places() {
        mPlaces = new ArrayList<>();
    }

    public void addPlace(PlaceDataHolder place) {
        mPlaces.add(place);
    }
    public List<PlaceDataHolder> places() {
        return mPlaces;
    }
}
