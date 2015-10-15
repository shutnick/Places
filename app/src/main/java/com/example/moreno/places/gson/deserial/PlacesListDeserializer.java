package com.example.moreno.places.gson.deserial;

import com.example.moreno.places.gson.holder.PlaceDataHolder;
import com.example.moreno.places.gson.holder.PlaceLocationHolder;
import com.example.moreno.places.gson.holder.Places;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created on 15.10.2015.
 */
public class PlacesListDeserializer implements JsonDeserializer<Places> {

    @Override
    public Places deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Places places = new Places();
        JsonArray jArray = json.getAsJsonObject().get("results").getAsJsonArray();
        for (JsonElement jElement : jArray) {
            JsonObject jObject = jElement.getAsJsonObject();
            PlaceDataHolder.Builder placeBuilder = new PlaceDataHolder.Builder();
            String id = jObject.get("place_id").getAsString();
            String iconUrl = jObject.get("icon").getAsString();
            String name = jObject.get("name").getAsString();
            String address = jObject.get("vicinity").getAsString();

            final JsonObject jLocation = jObject.getAsJsonObject("geometry").getAsJsonObject("location");
            PlaceLocationHolder locationHolder = context.deserialize(jLocation, PlaceLocationHolder.class);
            placeBuilder
                    .placeId(id)
                    .iconUrl(iconUrl)
                    .name(name)
                    .address(address)
                    .location(locationHolder);

            places.addPlace(placeBuilder.build());
        }

        return places;
    }
}
