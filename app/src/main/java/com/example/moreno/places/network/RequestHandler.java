package com.example.moreno.places.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created on 14.10.2015.
 */
public class RequestHandler {

    private com.android.volley.RequestQueue mQueue;
    private ImageLoader  mImageLoader;
    private static RequestHandler sInstance;

    private RequestHandler(Context context) {
        mQueue = Volley.newRequestQueue(context);
        final ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            private final int MAX_IMAGE_AMOUNT = 10;
            private final LruCache<String, Bitmap> cache = new LruCache<>(MAX_IMAGE_AMOUNT);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        };
        mImageLoader = new ImageLoader(mQueue, imageCache);
    }

    public static RequestHandler getInstance(Context appContext) {
        if (sInstance == null) {
            synchronized (RequestHandler.class) {
                if (sInstance == null) {
                    sInstance = new RequestHandler(appContext);
                }
            }
        }
        return sInstance;
    }


    public com.android.volley.RequestQueue getRequestQueue() {
        return mQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
