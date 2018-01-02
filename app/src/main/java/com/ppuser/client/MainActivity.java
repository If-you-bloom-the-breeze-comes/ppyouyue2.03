package com.ppuser.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.ppuser.client.adapter.CommonFragmentAdapter;
import com.ppuser.client.base.BaseActivity;
import com.ppuser.client.present.BasePersenter;
import com.ppuser.client.utils.StatusBarUtils;
import com.ppuser.client.view.fragment.HomeFragment;
import com.ppuser.client.view.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static boolean isForeground = false;
    private SpaceNavigationView spaceNavigationView;
    private List<Fragment> fragmentList;
    private ViewPager viewPager;
    private CommonFragmentAdapter commonAdapter;
    /**
     * 再按一次退出时间间隔
     */
    private long temTime = 0;
    private TitleBar mTitleBar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        StatusBarUtils.with(this).init();

        mTitleBar = (TitleBar) findViewById(R.id.titleBar);
        mTitleBar.setActivity(this);
        mTitleBar.setMenuIvVisibility(false);
        mTitleBar.setMenuBackVisibility(false);

        viewPager = (ViewPager) findViewById(R.id.ViewPager);

        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("首页", R.drawable.main_ic_shouye, R.drawable.main_ic_shouye_foused));
        spaceNavigationView.addSpaceItem(new SpaceItem("寻找", R.drawable.main_ic_youdan, R.drawable.main_ic_youdan_foused));
        spaceNavigationView.addSpaceItem(new SpaceItem("发现", R.drawable.main_ic_youwan, R.drawable.main_ic_youwan_foused));
        spaceNavigationView.addSpaceItem(new SpaceItem("我的", R.drawable.main_ic_me, R.drawable.main_ic_me_foused));
        spaceNavigationView.setSpaceBackgroundColor(getResources().getColor(R.color.space_white));
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setActiveSpaceItemColor(getResources().getColor(R.color.app_green));

        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        commonAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), fragmentList, null);
        viewPager.setAdapter(commonAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 || position == 1 || position == 3) {
                    mTitleBar.setVisibility(View.GONE);
                } else {
                    mTitleBar.setVisibility(View.VISIBLE);
                }
                spaceNavigationView.changeCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePersenter createPresent() {
        return null;
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }
}
