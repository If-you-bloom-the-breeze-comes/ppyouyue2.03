package com.ppuser.client.model;

import org.json.JSONObject;

/**
 * Created by mylddw on 2018/1/2.
 */

public interface IHomeFragmentModel {
    interface  IHomeFragmentListener
    {
        void  onComplete(JSONObject json);
    }

    /**
     * 登录
     *
     * @param cityID
     * @return
     */
    void getIndexData(String cityID ,IHomeFragmentListener onHomeFragmentlitener);

}
