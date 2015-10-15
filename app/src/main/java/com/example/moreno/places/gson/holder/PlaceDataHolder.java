package com.example.moreno.places.gson.holder;

/**
 * Created on 10.10.2015.
 */
public class PlaceDataHolder {
    public final String placeId;
    public final String iconUrl;
    public final CharSequence name;
    /**
     * Use placeLocation instead;
     */
    @Deprecated
    public final float distance;
    public final PlaceLocationHolder placeLocation;
    public final CharSequence address;

    private PlaceDataHolder(Builder builder) {
        placeId = builder.placeId;
        iconUrl = builder.iconUrl;
        name = builder.name;
        address = builder.address;
        distance = builder.distance;
        placeLocation = builder.placeLocation;
    }

    public static class Builder {
        private String placeId;
        private String iconUrl;
        private CharSequence name;
        private float distance;
        private CharSequence address;
        private PlaceLocationHolder placeLocation;

        public Builder placeId(String placeId) {
            this.placeId = placeId;
            return this;
        }
        public Builder name(CharSequence name) {
            this.name = name;
            return this;
        }

        public Builder address(CharSequence address) {
            this.address = address;
            return this;
        }

        public Builder distance(float distance) {
            this.distance = distance;
            return this;
        }

        public Builder iconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
            return this;
        }

        public Builder location(PlaceLocationHolder data) {
            placeLocation = data;
            return this;
        }

        public PlaceDataHolder build() {
            return new PlaceDataHolder(this);
        }
    }
}
