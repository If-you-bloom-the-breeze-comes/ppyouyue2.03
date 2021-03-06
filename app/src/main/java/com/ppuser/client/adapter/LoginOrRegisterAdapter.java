package com.ppuser.client.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by mylddw on 2017/12/22.
 */

public class LoginOrRegisterAdapter extends PagerAdapter {

    private  ArrayList<View> viewLists;

    public LoginOrRegisterAdapter() {

    }

    public LoginOrRegisterAdapter(ArrayList<View> viewLists) {
        super();
        this.viewLists = viewLists;
    }


    @Override
    public int getCount() {
        return viewLists.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
}
