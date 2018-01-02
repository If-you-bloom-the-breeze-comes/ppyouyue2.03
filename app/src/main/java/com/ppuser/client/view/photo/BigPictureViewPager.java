package com.ppuser.client.view.photo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ppuser.client.utils.LogUtil;

public class BigPictureViewPager extends ViewPager {
	public BigPictureViewPager(Context context) {
		super(context);
	}
	public BigPictureViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			LogUtil.t("BigPictureViewPager："+e.getMessage());
			return false;
		}catch(ArrayIndexOutOfBoundsException e ){
			LogUtil.t("BigPictureViewPager："+e.getMessage());
			return false;
		}
	}

}
