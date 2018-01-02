package com.ppuser.client.model;

import com.ppuser.client.MyApplication;
import com.ppuser.client.common.CommonRequest;
import com.ppuser.client.utils.LogUtil;

/**
 * Created by mylddw on 17/12/20.
 */
public class LoginModel implements ILoginModel{


    @Override
    public void login(String phone, String passwod, OnLoginlitener onLoginlitener) {

        //请求登录信息是否成功
        LogUtil.t("onclick---login");
        onLoginlitener.onComplete(true);

//        CommonRequest.getJsonObjectDatas(MyApplication.getMyApplication(),false,);

    }
}
