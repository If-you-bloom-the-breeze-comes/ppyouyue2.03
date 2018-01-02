package com.ppuser.client.utils;

import android.content.Context;

/**
 * Created by UPC on 2017/4/18.
 */

public class AdressShareUtil {
    public static void saveLongitude(Context context, String longitude){
        SharedPreferencesUtil.saveStringPreference(context,"BDLocation","longitude",longitude);
    }

    public static String getLongitude(Context context){
        return SharedPreferencesUtil.getStringPreference(context,"BDLocation","longitude","");
    }

    public static void saveLatitude(Context context,String latitude){
        SharedPreferencesUtil.saveStringPreference(context,"BDLocation","latitude",latitude);
    }

    public static String getLatitude(Context context){
        return SharedPreferencesUtil.getStringPreference(context,"BDLocation","latitude","");
    }

    public static void saveCity(Context context,String city){
        SharedPreferencesUtil.saveStringPreference(context,"BDLocation","city",city);
    }

    public static String getCity(Context context){
        return SharedPreferencesUtil.getStringPreference(context,"BDLocation","city","北京市");
    }

    public static void saveAdress(Context context,String adress){
        SharedPreferencesUtil.saveStringPreference(context,"BDLocation","adress",adress);
    }

    public static String getAdress(Context context){
        return SharedPreferencesUtil.getStringPreference(context,"BDLocation","adress","");
    }

    public static void saveCityId(Context context,String cityId){
        SharedPreferencesUtil.saveStringPreference(context,"BDLocation","cityId",cityId);
    }

    public static String getCityId(Context context){
        if(context==null)
            return "";
        return SharedPreferencesUtil.getStringPreference(context,"BDLocation","cityId","36");
    }

}
