package com.example.moreno.places.components.root.list;

/**
 * Created on 10.10.2015.
 */
public class PlaceDataHolder {
    public final String imageUrl;
    public final CharSequence name;
    public final CharSequence distance;
    public final CharSequence address;

    private PlaceDataHolder(Builder builder) {
        imageUrl = builder.imageUrl;
        name = builder.name;
        address = builder.address;
        distance = builder.distance;
    }

    public static class Builder {
        private String imageUrl;
        private CharSequence name;
        private CharSequence distance;
        private CharSequence address;

        public Builder name(CharSequence name) {
            this.name = name;
            return this;
        }

        public Builder address(CharSequence address) {
            this.address = address;
            return this;
        }

        public Builder distance(CharSequence distance) {
            this.distance = distance;
            return this;
        }

        public Builder url(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public PlaceDataHolder build() {
            return new PlaceDataHolder(this);
        }
    }
}
