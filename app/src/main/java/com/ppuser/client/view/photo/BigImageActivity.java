package com.ppuser.client.view.photo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.ppuser.client.R;

/**
 * 大图查看器
 */
public class BigImageActivity extends FragmentActivity {
	private BigPictureViewPager vp_pager;
	private int pagerPosition;
	private ImageView[] indicator;
	private LinearLayout ll_indicator;
	private String[] urls;
	private Context context;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=getApplicationContext();
		/**
		 * 没有标题
		 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/**
		 * 去掉信息栏
		 */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_bigimage);
		pagerPosition = getIntent().getIntExtra("image_index", 0);
		urls = getIntent().getStringArrayExtra("image_urls");
		vp_pager = (BigPictureViewPager) findViewById(R.id.vp_pager);
		vp_pager.setAdapter(new MyPagerAdapter());
		ll_indicator = (LinearLayout) findViewById(R.id.ll_indicator);
		/**
		 * 更新下标
		 */
		vp_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				initIndicator(arg0);
			}

		});
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt("STATE_POSITION");
		}

		vp_pager.setCurrentItem(pagerPosition);
		initIndicator(pagerPosition);
	}

	/**
	 * 加入下标点
	 *
	 * @param postion
	 *            当前显示第几张
	 */
	private void initIndicator(int postion) {
		if (urls.length <= 1) {
			ll_indicator.setVisibility(View.GONE);
			return;
		} else {
			ll_indicator.setVisibility(View.VISIBLE);
		}
		ImageView imgView;
		indicator = new ImageView[urls.length];
		((ViewGroup) ll_indicator).removeAllViews();
		for (int i = 0; i < urls.length; i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
			params.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params);

			indicator[i] = imgView;
			if (i == postion) {
				indicator[i].setBackgroundResource(R.mipmap.timedian_true);
			} else {
				indicator[i].setBackgroundResource(R.mipmap.timedian_false);
			}
			((ViewGroup) ll_indicator).addView(indicator[i]);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("image_index", vp_pager.getCurrentItem());
	}

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return urls.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(context);
			photoView.setAdjustViewBounds(true);
			photoView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			photoView.enable();
			photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			Glide.with(context).load(urls[position].trim()).into(photoView);
			container.addView(photoView);
			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

}