package com.ppuser.client.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 屏幕相关工具类
 * 
 * @author huangfucai
 *
 */
public class ScreenUtil {
	/**
	 * 获取屏幕宽
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕高
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int[] getScreenSize(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return new int[] { dm.widthPixels, dm.heightPixels };
	}

	public static int getDpixel(Context activity, int value) {
		float density = activity.getResources().getDisplayMetrics().density;
		return (int) (density * value);
	}

	/**
	 * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
	 *
	 * @return 返回状态栏高度的像素值。
	 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 设置状态栏背景色
	 * 
	 * @param activity
	 *            当前activity
	 * @param color
	 *            想要设置的颜色
	 */
	public static void setStatusColor(Activity activity, View view, String color) {
		int statusBarHeight = ScreenUtil.getStatusBarHeight(activity.getBaseContext());
		view.setPadding(0, statusBarHeight, 0, 0);
		setStatusColor(activity, color);
	}

	/**
	 * 设置状态了颜色
	 * 
	 * @param activity
	 * @param color
	 */
	public static void setStatusColor(Activity activity, String color) {
		View statusBarView = new View(activity);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ScreenUtil.getStatusBarHeight(activity));
		statusBarView.setBackgroundColor(Color.parseColor(color));
		ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
		contentView.addView(statusBarView, lp);
	}

	/**
	 * 是否隐藏状态栏
	 * 
	 * @param window
	 * @param isHide
	 */
	public static void isHideStatus(Window window, boolean isHide) {
		if (isHide) {
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			window.setAttributes(lp);
			window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			WindowManager.LayoutParams attr = window.getAttributes();
			attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			window.setAttributes(attr);
			window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}

	public static void setViewWHForRelativeLayout(Context context, int width, int heigth, View v) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, heigth);
		v.setLayoutParams(params);
	}
	/**
	 * 设置宽高并指定在某一控件的某个方向上
	 * 
	 * @param context
	 * @param width
	 * @param heigth
	 * @param myView
	 * @param direction
	 * @param toDirectionView
	 */
	public static void setViewWHForRelativeLayoutToDirection(Context context, int width, int heigth, View myView,int direction,int toDirectionView) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, heigth);
		params.addRule(direction,toDirectionView);
		myView.setLayoutParams(params);
	}

	public static void setViewDirectionForRelativeLayout(Context context, int width, int heigth, View myView,int direction,int toDirectionView) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, heigth);
		params.addRule(direction,toDirectionView);
		myView.setLayoutParams(params);
	}

	/**
	 * 设置view的margins
	 *
	 * @param context
	 * @param view
	 * @param left
	 * @param top
	 * @param right
     * @param bottom
     */
	public static void setViewWHForRelativeLayout(Context context, View view,int left, int top, int right, int bottom) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(left, top, right, bottom);
		view.setLayoutParams(params);
	}

	public static void setViewWHForFrameLayout(Context context, View view,int left, int top, int right, int bottom) {
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(left, top, right, bottom);
		view.setLayoutParams(params);
	}
	
	public static void setViewWHForFrameLayout(Context context, int width, int heigth, View v) {
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, heigth);
		v.setLayoutParams(params);
	}

	public static void setViewWHForLinnearLayout(Context context, int width, int heigth, View v) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, heigth);
		v.setLayoutParams(params);
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}

	public static int getTextViewLength(TextView textView, String text) {
		TextPaint paint = textView.getPaint();
		int textLength = (int) paint.measureText(text);
		return textLength;
	}

	public static void setViewHeight(Context context,int width,int heigth,View v) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
				heigth);
		v.setLayoutParams(params);
	}

	public static void setRelativeLayoutMagin(Context context,int left, int top, int right, int bottom,View v) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		params.setMargins(left, top, right, bottom);
		v.setLayoutParams(params);
	}

	public static int getActionBarSize(Context context){
		TypedValue tv = new TypedValue();
		if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
		}else {
			return 0;
		}
	}
}
