package com.ppuser.client.present;

import com.google.gson.JsonObject;
import com.ppuser.client.model.HomeFragmentModel;
import com.ppuser.client.model.IHomeFragmentModel;
import com.ppuser.client.view.interfac.IHomeView;

import org.json.JSONObject;

/**
 * Created by mylddw on 2018/1/2.
 */

public class HomeFragmentPersenter<T> extends BasePersenter<IHomeView> {

    String mCityId;

    /**
     * 持有视图层 UI接口的引用  此时的视图层Activity
     */
    IHomeView mHomeView;

    public HomeFragmentPersenter(IHomeView homeView,String cityId){
        this.mHomeView = homeView;
        this.mCityId =cityId;
    }

    /**
     * 持有模型Model的引用
     */
    IHomeFragmentModel mHomeFragmentModel = new HomeFragmentModel();


    @Override
    public void fectch() {
        mHomeFragmentModel.getIndexData(mCityId, new IHomeFragmentModel.IHomeFragmentListener() {
            @Override
            public void onComplete(JSONObject json) {
                mHomeView.toInitHomeView(json);
            }
        });
    }
}
