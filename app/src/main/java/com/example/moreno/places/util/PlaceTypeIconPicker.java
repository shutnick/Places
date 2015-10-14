package com.example.moreno.places.util;

import com.example.moreno.places.R;
import com.google.android.gms.location.places.Place;

/**
 * Created on 11.10.2015.
 */
public class PlaceTypeIconPicker {
    public static int getIconId(int placeType) {
        int id;
        switch (placeType) {
            case Place.TYPE_AIRPORT:
                id = R.drawable.airport_icon;
                break;
            case Place.TYPE_AQUARIUM:
                id = R.drawable.aquarium_icon;
                break;
            case Place.TYPE_ART_GALLERY:
                id = R.drawable.art_gallery_icon;
                break;
            case Place.TYPE_BAR:
                id = R.drawable.bar_icon;
                break;
            case Place.TYPE_BOWLING_ALLEY:
                id = R.drawable.bowling_icon;
                break;
            case Place.TYPE_CAFE:
                id = R.drawable.cafe_icon;
                break;
            case Place.TYPE_CAMPGROUND:
                id = R.drawable.camping_icon;
                break;
            case Place.TYPE_CASINO:
                id = R.drawable.casino_icon;
                break;
            case Place.TYPE_SCHOOL:
                id = R.drawable.civic_building_icon;
                break;
            case Place.TYPE_DENTIST:
                id = R.drawable.dentist_icon;
                break;
            case Place.TYPE_DOCTOR:
                id = R.drawable.doctor_icon;
                break;
            case Place.TYPE_ELECTRONICS_STORE:
                id = R.drawable.electronics_icon;
                break;
            case Place.TYPE_GYM:
                id = R.drawable.fitness_icon;
                break;
            case Place.TYPE_GAS_STATION:
                id = R.drawable.gas_station_icon;
                break;
            case Place.TYPE_RESTAURANT:
                id = R.drawable.restaurant_icon;
                break;
            case Place.TYPE_ESTABLISHMENT:
                id = R.drawable.worship_general_icon;
                break;
            case Place.TYPE_BUS_STATION:
                id = R.drawable.car_dealer_icon;
                break;
            case Place.TYPE_GROCERY_OR_SUPERMARKET:
                id = R.drawable.shopping_icon;
                break;
            default:
                id = R.drawable.generic_recreational_icon;
                break;
        }
        return id;
    }

    public static int getIconId(String placeType) {
        int resId;
        switch (placeType) {
            default:
                resId = R.drawable.generic_recreational_icon;
                break;
        }
        return resId;
    }
}
