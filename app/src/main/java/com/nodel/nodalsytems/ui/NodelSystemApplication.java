package com.nodel.nodalsytems.ui;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by yugandhar on 1/6/2018.
 */

public class NodelSystemApplication extends Application
{
    private static NodelSystemApplication instance=null;
    private RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
    public static NodelSystemApplication getInstance()
    {
        if(instance==null)
            instance=new NodelSystemApplication();
        return instance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }



    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
