package com.ppuser.client.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ppuser.client.R;



/**
 * 自定义Toast
 * 
 * @author huangfucai
 *
 */
public class ToastUtil {

	private static Toast toast = null;
	/**
	 * 可自定义布局／时间／位置／偏移量的Toast
	 * 
	 * @param context 上下文
	 * @param layout 布局
	 * @param time 显示的时间
	 * @param gravity 位置
	 * @param xOffset x轴偏移量
	 * @param yOffset y轴偏移量
	 * @return
	 */
	public static Toast showToast(Context context, View layout, int time, int gravity, int xOffset, int yOffset) {
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setGravity(gravity, xOffset, yOffset);
		toast.setView(layout);
		toast.setDuration(time);
		toast.show();
		return toast;
	}
	/**
	 * 可自定义时间／文字／位置／图片／偏移量的Toast
	 * 
	 * @param context
	 * @param text
	 * @param time
	 * @param backGround
	 * @param gravity
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	@SuppressLint("InflateParams")
	public static Toast showToast(Context context, String text, int time, int backGround, int gravity, int xOffset, int yOffset) {
		View layout = getView(context, R.layout.layout_mytoast);
		TextView tv = (TextView) layout.findViewById(R.id.tv);
		ImageView mImageView = (ImageView) layout.findViewById(R.id.iv);
		mImageView.setBackgroundResource(backGround);
		tv.setText(text);
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setGravity(gravity, xOffset, yOffset);
		toast.setView(layout);
		toast.setDuration(time);
		toast.show();
		return toast;
	}
	/**
	 * 可自定义文字／时间／位置／图标的Toast
	 * 
	 * @param context
	 * @param text
	 * @param time
	 * @param backGround
	 * @param gravity
	 * @return
	 */
	@SuppressLint("InflateParams")
	public static Toast showToast(Context context, String text, int time, int backGround, int gravity) {
		View layout = getView(context, R.layout.layout_mytoast);
		TextView tv = (TextView) layout.findViewById(R.id.tv);
		ImageView mImageView = (ImageView) layout.findViewById(R.id.iv);
		mImageView.setBackgroundResource(backGround);
		tv.setText(text);
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setGravity(gravity, 0, 0);
		toast.setView(layout);
		toast.setDuration(time);
		toast.show();
		return toast;
	}
	/**
	 * 可自定义文字／时间／位置并有固定图标的Toast
	 * 
	 * @param context 上下文
	 * @param text 要显示的文字
	 * @param time 显示时间
	 * @param gravity 显示的位置
	 * @return
	 */
	@SuppressLint("InflateParams")
	public static Toast showToast(Context context, String text, int time, int gravity) {
		View layout = getView(context, R.layout.layout_mytoast);
		TextView tv = (TextView) layout.findViewById(R.id.tv);
		ImageView mImageView = (ImageView) layout.findViewById(R.id.iv);
		mImageView.setBackgroundResource(R.mipmap.ic_launcher);
		tv.setText(text);
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setGravity(gravity, 0, 0);
		toast.setView(layout);
		toast.setDuration(time);
		toast.show();
		return toast;
	}
	/**
	 * 可自定义文字／时间／位置的Toast
	 * 
	 * @param context 上下文
	 * @param text 要显示的文字
	 * @param time 显示时间
	 * @param gravity 显示的位置
	 * @return
	 */
	@SuppressLint("InflateParams")
	public static Toast showToast(Context context, ViewGroup root, String text, int time, int gravity) {
		View layout = getView(context, R.layout.layout_mytoast);
		TextView tv = (TextView) layout.findViewById(R.id.tv);
		tv.setText(text);
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setGravity(gravity, 0, 0);
		toast.setView(layout);
		toast.setDuration(time);
		toast.show();
		return toast;
	}
	/**
	 * 可自定义文字和时间并有固定图标的Toast
	 * 
	 * @param context 上下文
	 * @param root 布局的根
	 * @param text 要显示的文字
	 * @param time 显示时间
	 * @return
	 */
	public static Toast showToast(Context context, ViewGroup root, String text, int time) {
		View layout = getView(context, R.layout.layout_mytoast);
		TextView tv = (TextView) layout.findViewById(R.id.tv);
		ImageView mImageView = (ImageView) layout.findViewById(R.id.iv);
		mImageView.setBackgroundResource(R.mipmap.ic_launcher);
		tv.setText(text);
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setView(layout);
		toast.setDuration(time);
		toast.show();
		return toast;
	}
	/**
	 * 可自定义文字和时间的Toast
	 * 
	 * @param context 上下文
	 * @param text 要显示的文字
	 * @param time 显示时间
	 * @return
	 */
	@SuppressLint("InflateParams")
	public static Toast showToast(Context context, String text, int time) {
		View layout = getView(context, R.layout.layout_mytoast);
		TextView tv = (TextView) layout.findViewById(R.id.tv);
		tv.setText(text);
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setView(layout);
		toast.setDuration(time);
		toast.show();
		return toast;
	}

	/**
	 * 可自定义文字和时间的Toast
	 *
	 * @param context 上下文
	 * @param text 要显示的文字
	 * @return
	 */
	@SuppressLint("InflateParams")
	public static Toast showToast(Context context, String text) {
		View layout = getView(context, R.layout.layout_mytoast);
		TextView tv = (TextView) layout.findViewById(R.id.tv);
		ImageView iv= (ImageView) layout.findViewById(R.id.iv);
		iv.setVisibility(View.GONE);
		tv.setText(text);
		if (toast == null) {
			toast = new Toast(context);
		}
		toast.setView(layout);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
		return toast;
	}
	/**
	 * 将Toast置空
	 * 
	 */
	public static void cancelToast() {
		if (toast != null) {
			toast.cancel();
			toast=null;
		}
	}

	public static View getView(Context context, int layout) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(layout, null);
	}

}
