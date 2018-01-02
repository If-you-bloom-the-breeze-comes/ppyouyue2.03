package com.ppuser.client.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Kuky on 2017-02-13.
 */
public class CommonFragmentAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private List<T> mList;
    private String[] titles;

    public CommonFragmentAdapter(FragmentManager fm, List<T> list, String[] titles) {
        super(fm);
        this.mList = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * 绑定标题
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? super.getPageTitle(position) : titles[position];
    }
}
