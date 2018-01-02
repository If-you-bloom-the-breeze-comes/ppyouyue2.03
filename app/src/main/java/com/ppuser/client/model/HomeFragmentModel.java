package com.ppuser.client.model;

import android.text.TextUtils;

import com.ppuser.client.MyApplication;
import com.ppuser.client.common.CommonRequest;
import com.ppuser.client.common.CommonUrl;
import com.ppuser.client.utils.AdressShareUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mylddw on 2018/1/2.
 */

public class HomeFragmentModel implements IHomeFragmentModel {
    @Override
    public void getIndexData(String cityID, final IHomeFragmentListener onHomeFragmentlitener) {


        Map<String, String> mapRegister = new HashMap<>();
        mapRegister.put("service", CommonUrl.GET_INDEX_new);
        mapRegister.put("city_id", AdressShareUtil.getCityId(MyApplication.getMyApplication()));

        CommonRequest.getJsonObjectDatas(MyApplication.getMyApplication(), false, mapRegister, new CommonRequest.JsonObjectCallBack() {
            @Override
            public void doSomeThing(JSONObject jsonObject) throws Exception {
                onHomeFragmentlitener.onComplete(jsonObject);
            }
        });

    }
}
