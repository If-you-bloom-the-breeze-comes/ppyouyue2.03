package com.ppuser.client.utils;

import com.google.gson.Gson;

/**
 * Created by mylddw on 2015/9/24 13 20.
 */
public class GsonUtils {

    private static Gson mGson = null;

    private GsonUtils() {

    }

    public static Gson getGsonInstance() {
        if (mGson == null)
            mGson = new Gson();
        return mGson;
    }

}
