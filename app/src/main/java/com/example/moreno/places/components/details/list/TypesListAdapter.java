package com.example.moreno.places.components.details.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moreno.places.R;
import com.example.moreno.places.util.PlaceTypeIconPicker;

import java.util.List;

/**
 * Created on 11.10.2015.
 */
public class TypesListAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private List<Integer> mPlaceTypes;

    public TypesListAdapter(List<Integer> placeTypes) {
        mPlaceTypes = placeTypes;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_holder, parent, false);
        return new ImageViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(PlaceTypeIconPicker.getIconId(mPlaceTypes.get(position)));
    }

    @Override
    public int getItemCount() {
        return mPlaceTypes.size();
    }
}
