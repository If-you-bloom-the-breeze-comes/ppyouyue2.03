package com.ppuser.client.common;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.ppuser.client.MyApplication;
import com.ppuser.client.bean.JsonBean;
import com.ppuser.client.http.HttpUtil;
import com.ppuser.client.http.RequestObject;
import com.ppuser.client.utils.LogUtil;
import com.ppuser.client.utils.SecurityUtil;
import com.ppuser.client.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;


/**
 * Created by mylddw on 2017/3/29.
 */

public class CommonRequest {

    public static void getJsonStringDatas(final Context context,boolean showDialog,Map<String, String> mapParam,final JsonStringCallBack jsonStringCallBack) {
        RequestObject object = new RequestObject(CommonUrl.SERVER_ROOT, mapParam);
        HttpUtil.getInstance().doPut(context,object, "正在请求数据，请稍后...", showDialog, new HttpUtil.GetTaskCallBack() {
            @Override
            public void onTaskPostExecute(String callBackStr) {
                try {
                    String content= SecurityUtil.decrypt(callBackStr,"1234567891234567");
                    LogUtil.t("解密之后："+content);
                    JsonBean jsonBean= JsonBean.getJsonBean(content);
                    if (jsonBean.getRet()==200){
                        String msg=jsonBean.getMsg();
                        jsonStringCallBack.doSomeThing(msg);
                    }else {
                        LogUtil.t("请求失败："+jsonBean.getMsg());
                        ToastUtil.showToast(context,jsonBean.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onTaskPostExecuteFailure(String callBackStr){
                LogUtil.t("请求失败："+callBackStr);
                ToastUtil.showToast(context, callBackStr);
            };
        });
    }

    /**
     * 带 error 回调函数
     */
    public static void getJsonStringDatasDatas(final Context context,boolean showDialog,Map<String, String> mapParam,final JsonStringWholeCallBack jsonStringCallBack) {
        RequestObject object = new RequestObject(CommonUrl.SERVER_ROOT, mapParam);
        HttpUtil.getInstance().doPut(context,object, "正在请求数据，请稍后...", showDialog, new HttpUtil.GetTaskCallBack() {
            @Override
            public void onTaskPostExecute(String callBackStr) {
                try {
                    String content= SecurityUtil.decrypt(callBackStr,"1234567891234567");
                    LogUtil.t("解密之后："+content);

                    JsonBean jsonBean= JsonBean.getJsonBean(content);
                    if (jsonBean.getRet()==200){
                        String jsonString=jsonBean.getJsonString();
                        jsonStringCallBack.doSomeThing(jsonString);
                    }else if (jsonBean.getRet() == 499){

                    }else {
                        String value = jsonBean.getMsg();
                        LogUtil.t("请求失败："+value);

                        if (TextUtils.isEmpty(value))
                            value = "没有更多数据！";
                        jsonStringCallBack.doError(value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onTaskPostExecuteFailure(String callBackStr){
                LogUtil.t("请求失败："+callBackStr);
                ToastUtil.showToast(context, callBackStr);
            };
        });
    }

    public static void getJsonStringData(final Context context, boolean showDialog, final Map<String, String> mapParam, final OnResponseCallBack onResponseCallBack) {
        RequestObject object = new RequestObject(CommonUrl.SERVER_ROOT, mapParam);
        HttpUtil.getInstance().doPut(context, object, "正在请求数据，请稍后...", showDialog, new HttpUtil.GetTaskCallBack() {
            @Override
            public void onTaskPostExecute(String callBackStr) {
                try {
                    String content = SecurityUtil.decrypt(callBackStr, "1234567891234567");
                    LogUtil.t("解密之后：" + content);
                    JsonBean jsonBean = JsonBean.getJsonBean(content);
                    if (jsonBean.getRet() == 200) {
                        String jsonString = jsonBean.getJsonString();
                        onResponseCallBack.doSuccess(jsonString);
                    } else if (jsonBean.getRet() == 499) {

                    } else if(jsonBean.getRet() == 402){

                    }else {
                        LogUtil.t("mayue3==：" + content);
                        String value = jsonBean.getMsg();
                        LogUtil.t("请求失败：" + value);

                        if (TextUtils.isEmpty(value))
                            value = "系统繁忙！";
                        onResponseCallBack.doFailure(value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onTaskPostExecuteFailure(String callBackStr) {
                LogUtil.t("请求失败：" + callBackStr);
                onResponseCallBack.doError(callBackStr);
            }
        });
    }

    public static void getJsonStringDatasDatas(final Context context, boolean showDialog, Map<String, String> mapParam, final JsonStringCallBack jsonStringCallBack) {
        RequestObject object = new RequestObject(CommonUrl.SERVER_ROOT, mapParam);
        HttpUtil.getInstance().doPut(context, object, "正在请求数据，请稍后...", showDialog, new HttpUtil.GetTaskCallBack() {
            @Override
            public void onTaskPostExecute(String callBackStr) {
                try {
                    String content = SecurityUtil.decrypt(callBackStr, "1234567891234567");
                    LogUtil.t("解密之后：" + content);
                    JsonBean jsonBean = JsonBean.getJsonBean(content);
                    if (jsonBean.getRet() == 200) {
                        String jsonString = jsonBean.getJsonString();
                        jsonStringCallBack.doSomeThing(jsonString);
                    } else {
                        LogUtil.t("请求失败：" + jsonBean.getMsg());
//                    ToastUtil.showToast(context,jsonBean.getMsg());
                        Toast.makeText(context, jsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTaskPostExecuteFailure(String callBackStr) {
                LogUtil.t("请求失败：" + callBackStr);
                ToastUtil.showToast(context, callBackStr);
            }

            ;
        });
    }

    public static void getJsonObjectDatas(final Context context,boolean showDialog,Map<String, String> mapParam,final JsonObjectCallBack jsonObjectCallBack) {
        RequestObject object = new RequestObject(CommonUrl.SERVER_ROOT, mapParam);
        HttpUtil.getInstance().doPut(context,object, "正在请求数据，请稍后...", showDialog, new HttpUtil.GetTaskCallBack() {
            @Override
            public void onTaskPostExecute(String callBackStr) {
                try {
                    String content= SecurityUtil.decrypt(callBackStr,"1234567891234567");
                    LogUtil.t("解密之后："+content);
                    JsonBean jsonBean= JsonBean.getJsonBean(content,1);
                    if (jsonBean.getRet()==200){
                        JSONObject jsonObject=jsonBean.getJsonObject();
                        jsonObjectCallBack.doSomeThing(jsonObject);
                    }else if (jsonBean.getRet() == 499){

                        MyApplication.getMyApplication().setJPushAliasAndTag();
                    }else {
                        LogUtil.t("请求失败："+jsonBean.getMsg());
                        ToastUtil.showToast(context,jsonBean.getMsg());
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onTaskPostExecuteFailure(String callBackStr){
                LogUtil.t("请求失败："+callBackStr);
                ToastUtil.showToast(context, callBackStr);
            };
        });
    }

    public static void getJsonArrayDatas(final Context context,boolean showDialog,Map<String, String> mapParam,final JsonArrayCallBack jsonArrayCallBack) {
        RequestObject object = new RequestObject(CommonUrl.SERVER_ROOT, mapParam);
        HttpUtil.getInstance().doPut(context,object, "正在请求数据，请稍后...", showDialog, new HttpUtil.GetTaskCallBack() {

            @Override
            public void onTaskPostExecute(String callBackStr) {
                try {
                    String content= SecurityUtil.decrypt(callBackStr,"1234567891234567");
                    LogUtil.t("解密之后："+content);
                    JsonBean jsonBean= JsonBean.getJsonBean(content,2);
                    if (jsonBean.getRet()==200){
                        JSONArray jsonArray=jsonBean.getJsonArray();
                        jsonArrayCallBack.doSomeThing(jsonArray);
                    }else if (jsonBean.getRet() == 499){

                    }else {
                        LogUtil.t("请求失败："+jsonBean.getMsg());
                        ToastUtil.showToast(context,jsonBean.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTaskPostExecuteFailure(String callBackStr){
                LogUtil.t("请求失败："+callBackStr);
                ToastUtil.showToast(context, callBackStr);
            };

        });
    }

    public interface JsonStringCallBack {
        void doSomeThing(String jsonString) throws Exception;
    }

    public interface JsonStringWholeCallBack {
        void doSomeThing(String jsonString) throws Exception;
        void doError(String error);
    }

    public interface OnResponseCallBack {
        void doSuccess(String jsonString) throws Exception;
        void doFailure(String error);
        void doError(String error);
    }

    public interface JsonObjectCallBack {
        void doSomeThing(JSONObject jsonObject) throws Exception;
    }

    public interface JsonArrayCallBack {
        void doSomeThing(JSONArray jsonArray) throws Exception;
    }

}
