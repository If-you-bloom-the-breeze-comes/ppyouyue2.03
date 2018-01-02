package com.ppuser.client.utils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ppuser.client.MyApplication;

/**
 * Created by mylddw on 2017/5/29.
 */

public class LocationHelper {
    private static LocationHelper mInstance;
    private LocationClient locationClient;
    private BaiDuLocationCallBack baiDuLocationCallBack;

    public void setBaiDuLocationCallBack(BaiDuLocationCallBack baiDuLocationCallBack) {
        this.baiDuLocationCallBack = baiDuLocationCallBack;
    }

    private LocationHelper(Context context) {
        locationClient = new LocationClient(context, getLocOption());
        locationClient.registerLocationListener(new MyBDLocationListener());
    }

    public static synchronized void init(Context context) {
        if (mInstance == null) {
            mInstance = new LocationHelper(context);
        }
    }

    public static LocationHelper getInstance() {
        if (mInstance == null)
            throw new RuntimeException("place in application init!");
        return mInstance;
    }

    public void start() {
        if (isStarted())
            locationClient.stop();
        locationClient.start();
    }

    public boolean isStarted() {
        return locationClient.isStarted();
    }

    public void stop() {
        if (locationClient.isStarted())
            locationClient.stop();
    }

    private LocationClientOption getLocOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setLocationNotify(false);
        option.setOpenGps(true);
        option.setIsNeedLocationPoiList(true);
        option.setAddrType("all");
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedAddress(true);
        return option;
    }

    private class MyBDLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation
                    || bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {

                MyApplication.getMyApplication().latitude = bdLocation.getLatitude();
                MyApplication.getMyApplication().longitude = bdLocation.getLongitude();
                MyApplication.getMyApplication().city = bdLocation.getCity();

                if (baiDuLocationCallBack != null)
                    baiDuLocationCallBack.onSuccess(bdLocation);
            } else if (bdLocation.getLocType() == BDLocation.TypeServerError
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkException
                    || bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                if (baiDuLocationCallBack != null)
                    baiDuLocationCallBack.onFailure(bdLocation);
            }else{
                if (baiDuLocationCallBack != null)
                    baiDuLocationCallBack.onError(bdLocation);
            }
            stop();
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    public interface BaiDuLocationCallBack {
        void onSuccess(BDLocation bdLocation);
        void onFailure(BDLocation bdLocation);
        void onError(BDLocation bdLocation);
    }
}
