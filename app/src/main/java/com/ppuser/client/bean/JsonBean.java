package com.ppuser.client.bean;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.ppuser.client.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by mylddw on 2017/3/29.
 */

public class JsonBean implements IPickerViewData{
    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */
    private String name;
    private List<CityBean> city;

    private int ret=0;
    private String msg="";
    private int type=0;
    private String jsonString="";
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public static JsonBean getJsonBean(String content) {
        JsonBean baseBean = new JsonBean();
        baseBean.setContent(content);
        return baseBean;
    }

    public static JsonBean getJsonBean(String content, int type) {
        JsonBean baseBean = new JsonBean(type);
        baseBean.setContent(content);
        return baseBean;
    }

    public void setContent(String jsonContent) {
        try {
            if (jsonContent==null){
                return;
            }
            JSONObject jsonObject = new JSONObject(jsonContent);
            switch (type) {
                case 1:
                    initJSONObject(jsonObject);
                    break;
                case 2:
                    initJSONArray(jsonObject);
                    break;

                default:
                    initJSONString(jsonObject);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.t("setContent json解析失败："+e.getMessage());
        }
    }

    protected void initJSONString(JSONObject json) {
        try {
            ret = json.optInt("ret", -1);
            msg = json.optString("msg", "");
            jsonString= json.optString("data","");
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.t("initJSONObject json解析失败："+e.getMessage());
        }
    }

    protected void initJSONObject(JSONObject json) {
        try {
            ret = json.optInt("ret", -1);
            msg = json.optString("msg", "");
            if (ret!=500){
                jsonObject = json.getJSONObject("data");
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.t("initJSONObject json解析失败："+e.getMessage());
        }
    }

    protected void initJSONArray(JSONObject jsonObject) {
        try {
            ret = jsonObject.optInt("ret", -1);
            msg = jsonObject.optString("msg", "");
            if (ret!=500){
                jsonArray = jsonObject.getJSONArray("data");
            }

        }catch (Exception e){
            e.printStackTrace();
            LogUtil.t("initJSONArray json解析失败："+e.getMessage());
        }
    }

    public JsonBean() {
    }

    public JsonBean(int dataType) {
        this.type = dataType;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCityList() {
        return city;
    }

    public void setCityList(List<CityBean> city) {
        this.city = city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }



    public static class CityBean {
        /**
         * name : 城市
         * area : ["东城区","西城区","崇文区","昌平区"]
         */

        private String name;
        private List<String> area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}
