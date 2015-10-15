package com.example.moreno.places.gson.deserial;

import com.example.moreno.places.gson.holder.PlaceLocationHolder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created on 15.10.2015.
 */
public class PlaceLocationDeserializer implements JsonDeserializer<PlaceLocationHolder> {
    @Override
    public PlaceLocationHolder deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();
        double latitude = jObject.get("lat").getAsDouble();
        double longitude = jObject.get("lng").getAsDouble();
        return new PlaceLocationHolder(latitude, longitude);
    }
}
