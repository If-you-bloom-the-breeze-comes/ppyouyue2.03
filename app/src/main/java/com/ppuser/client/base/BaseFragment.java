package com.ppuser.client.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ppuser.client.present.BasePersenter;

/**
 * Created by mylddw on 2017/12/21.
 */

public abstract class BaseFragment <V, T extends BasePersenter<V>> extends android.support.v4.app.Fragment{

    protected T mPresent;
    public Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mPresent = createPresent();
        if (mPresent != null && !mPresent.equals(""))
            mPresent.attachView((V) this);
        activity = getActivity();
        View view = initView();
        setListener();
        initData();
        return view;
    }

    protected abstract View initView();

    protected abstract void setListener();

    protected abstract void initData();

    @Override
    public void onDestroy() {
        if (mPresent != null && !mPresent.equals(""))
            mPresent.detach();
        super.onDestroy();

    }

    /**
     * 子类实现具体的构建过程
     *
     * @return
     */
    protected abstract T createPresent();
}
