package com.example.moreno.places.components.details.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.moreno.places.R;

/**
 * Created on 11.10.2015.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {
    public final ImageView imageView;
    public ImageViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image_view);
    }

}
