package com.example.moreno.places.components.root.list;

/**
 * Created on 10.10.2015.
 */
public class PlaceDataHolder {
    public final String placeId;
    public final String iconUrl;
    public final CharSequence name;
    public final float distance;
    public final CharSequence address;

    private PlaceDataHolder(Builder builder) {
        placeId = builder.placeId;
        iconUrl = builder.iconUrl;
        name = builder.name;
        address = builder.address;
        distance = builder.distance;
    }

    public static class Builder {
        private String placeId;
        private String iconUrl;
        private CharSequence name;
        private float distance;
        private CharSequence address;

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

        public Builder placeType(String iconUrl) {
            this.iconUrl = iconUrl;
            return this;
        }

        public PlaceDataHolder build() {
            return new PlaceDataHolder(this);
        }
    }
}
