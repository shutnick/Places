package com.example.moreno.places.components.root.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moreno.places.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10.10.2015.
 */
public class PlacesListAdapter extends RecyclerView.Adapter<PlaceViewHolder> {
    private List<PlaceDataHolder> mPlacesList = new ArrayList<>();

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_view, parent, false);
        return new PlaceViewHolder(item);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        PlaceDataHolder data = mPlacesList.get(position);
        holder.address.setText(data.address);
        holder.distance.setText(data.distance);
        holder.name.setText(data.name);
//        holder.icon.set
    }


    @Override
    public int getItemCount() {
        return mPlacesList.size();
    }

    public void setPlacesList(List<PlaceDataHolder> places) {
        mPlacesList = places;
        notifyDataSetChanged();
    }


}
