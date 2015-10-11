package com.example.moreno.places.components.details;

import com.google.android.gms.location.places.Place;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 11.10.2015.
 */
public class PlaceDetailsHolder {
    public final CharSequence name;
    public final List<Integer> types;
    public final float rating;
    public final CharSequence address;
    public final CharSequence phone;
    public final String website;
    public final int priceLevel;

    public PlaceDetailsHolder(Place place) {
        name = place.getName();
        types = new LinkedList<>(place.getPlaceTypes());
        rating = place.getRating();
        address = place.getAddress();
        phone = place.getPhoneNumber() == null ? "Undefined" : place.getPhoneNumber();
        website = place.getWebsiteUri() == null ? "Undefined" : place.getWebsiteUri().toString();
        priceLevel = place.getPriceLevel();
    }
}
