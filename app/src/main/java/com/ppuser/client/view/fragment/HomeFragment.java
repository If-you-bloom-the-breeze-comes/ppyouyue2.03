package com.ppuser.client.view.fragment;

import android.view.View;
import com.ppuser.client.R;
import com.ppuser.client.base.BaseFragment;
import com.ppuser.client.common.CommonMethod;
import com.ppuser.client.present.HomeFragmentPersenter;
import com.ppuser.client.view.interfac.IHomeView;
import org.json.JSONObject;

/**
 * Created by mylddw on 2017/12/28.
 *
 * 首页fragment
 */

public  class HomeFragment extends BaseFragment<IHomeView,HomeFragmentPersenter<IHomeView>> implements IHomeView{
    @Override
    protected View initView() {
        final View view = CommonMethod.getView(activity, R.layout.fragment_home1);
        return view;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        mPresent.fectch();
    }

    @Override
    protected HomeFragmentPersenter createPresent() {
        return new HomeFragmentPersenter(this,"");
    }

    @Override
    public void toInitHomeView(JSONObject json) {

    }
}
