package com.example.moreno.places.components.root.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created on 10.10.2015.
 */
public class PlacesListView extends RecyclerView {
    public PlacesListView(Context context) {
        super(context);
    }

    public PlacesListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlacesListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void scrollTo(int x, int y) {
        //Override unsupported operation
    }
}
