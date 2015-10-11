package com.example.moreno.places.components.details.list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created on 11.10.2015.
 */
public class ImagesListView extends RecyclerView {
    public ImagesListView(Context context) {
        this(context, null);
    }

    public ImagesListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImagesListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
    }

    @Override
    public void scrollTo(int x, int y) {
        //Override unsupported operation
    }
}
