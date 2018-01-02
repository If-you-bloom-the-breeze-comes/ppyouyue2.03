package com.ppuser.client.http;

import android.content.Context;

import com.ppuser.client.MyApplication;
import com.ppuser.client.utils.LoginUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 网络请求对象
 * context ： 上下文环境，用于获取httpclient;
 * url : 请求地址，不带参数
 * withCookie : 是否带上cookie， true
 * requestParams : 请求参数
 * callbackParam : 回调参数，默认为空
 * updateCookie : 是否要更新本地缓存的cookie，默认更新
 * type : 请求类型 0 : get, 1 : post
 * timeout : 超时时间
 */
public class RequestObject {
    /**
     * 上下文
     */
    private Context context;

    /**
     * 请求路径
     */
    private String url;

    private String urlParams;

    /**
     * 是否带上cookie,默认true
     */
    private boolean withCookie = true;

    /**
     * 简单参数,以后会过时，建议使用extraParams
     * 请求参数,如果get请求已经把参数写在了url上，可以传null或者空map
     */
    private Map<String, String> simpleParams;
    /**
     * headers
     */
    private Map<String, String> headerParams;

    /**
     * 额外参数
     */
    private List<String[]> extraParams;

    /**
     * 回调参数名
     */
    private String callbackParam;

    /**
     * 成功请求后，是否保存cookie,默认true
     */
    private boolean updateCookie = true;

    /**
     * 请求类型：0 get,1 post
     */
    private int type;

    /**
     * 超时
     */
    private int timeout = 60000;

    /**
     * 是否返回json 默认为是
     */
    private boolean isJson = true;

    public RequestObject(String url, Map<String, String> requestParams) {
        this.url = url;
        this.simpleParams = getMapWithTokenMemberId(requestParams);//默认带token与memberid
    }

    public Context getContext() {
        return context;
    }

    /**
     * 此方法自带token member_id
     * @param requestParams
     * @return
     */

    public Map<String ,String> getMapWithTokenMemberId(Map<String, String> requestParams){

        requestParams.put("token", LoginUtils.getToken(MyApplication.getMyApplication()));
        requestParams.put("member_id",LoginUtils.getMember_id(MyApplication.getMyApplication()));
        return requestParams;
    }

    /**
     * 此方法自带sessionID
     *
     * @param context
     * @return
     */
    public static Map<String, String> getParamsMap(Context context) {
        //得到一个Map 可以进行排序的，是对字符串进行排序
//        HashMap通过hashcode对其内容进行快速查找，而 TreeMap中所有的元素都保持着某种固定的顺序，
//        如果你需要得到一个有序的结果你就应该使用TreeMap（HashMap中元素的排列顺序是不固定的）。
        Map<String, String> map = UrlConstructor.getParamsMap();
//        map.put("sessionId", SessionUtils.extractSession(context));
//        map.put("user_id", SessionUtils.extractData(context, "user_id"));
        return map;
    }

    public static Map<String, String> getParamsMapNoSession(Context context) {
        Map<String, String> map = UrlConstructor.getParamsMap();
//        map.put("debug", "true");
//        map.put("platform", "android-" + GlobalVar.version);
//        map.put("resolution", GlobalVar.screenWidth + "x" + GlobalVar.screenHeight);
//        map.put("device", "android-" + GlobalVar.device);
        return map;
    }

    public static JSONObject getParamsMapJson(Context context) {
        JSONObject object = new JSONObject();
        return object;
    }

//	public static Map<String, String> getParamsMap() {
//		return UrlConstructor.getParamsMap();
//	}

//	public String getKey() {
//		return key;
//	}
//
//	public void setKey(String key) {
//		this.key = key;
//	}

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(String urlParams) {
        this.urlParams = urlParams;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isWithCookie() {
        return withCookie;
    }

    public void setWithCookie(boolean withCookie) {
        this.withCookie = withCookie;
    }

    public boolean isUpdateCookie() {
        return updateCookie;
    }

    public void setUpdateCookie(boolean updateCookie) {
        this.updateCookie = updateCookie;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getCallbackParam() {
        return callbackParam;
    }

    public void setCallbackParam(String callbackParam) {
        this.callbackParam = callbackParam;
    }

    public boolean isJson() {
        return isJson;
    }

    /**
     * @param isJson，false直接解析为String
     */
    public void setJson(boolean isJson) {
        this.isJson = isJson;
    }

    public Map<String, String> getSimpleParams() {
        return simpleParams;
    }

    public void setSimpleParams(Map<String, String> simpleParams) {
        this.simpleParams = simpleParams;
    }

    public List<String[]> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(List<String[]> extraParams) {
        this.extraParams = extraParams;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
    }
}
