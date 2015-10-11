package com.example.moreno.places.components.root.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moreno.places.R;
import com.example.moreno.places.components.root.RootActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10.10.2015.
 */
public class PlacesListAdapter extends RecyclerView.Adapter<PlaceViewHolder> {
    private final RootActivity mActivity;
    private List<PlaceDataHolder> mPlacesList = new ArrayList<>();

    public PlacesListAdapter(final RootActivity activity) {
        mActivity = activity;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_view, parent, false);
        return new PlaceViewHolder(item);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        final PlaceDataHolder data = mPlacesList.get(position);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.openPlaceDetails(data.placeId);
            }
        });
        holder.address.setText(data.address);
        holder.distance.setText((int)data.distance + " m");
        holder.name.setText(data.name);
        holder.icon.setImageResource(data.iconResId);
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
