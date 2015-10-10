package com.example.moreno.places.components.root.list;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

    public void setLayoutManager(Context context) {
        final int deviceOrientation = context.getResources().getConfiguration().orientation;
        LayoutManager layoutManager;
        if (deviceOrientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(context, Configuration.ORIENTATION_PORTRAIT, false);
        } else {
            layoutManager = new StaggeredGridLayoutManager(2, Configuration.ORIENTATION_PORTRAIT);
        }
        setLayoutManager(layoutManager);
    }
}
