package com.ppuser.client.utils;

import android.util.Log;


/**
 * 日志工具类
 * 
 * @author huangfucai
 *
 */
public class LogUtil {
	/**
	 *  Debug模式才会打印日志
	 */
	public static boolean isDebug = true;

	public static void d(String tag, String value) {
		if (isDebug) {
			Log.i(tag, value);
		}
	}

	public static void i(String tag, String value) {
		if (isDebug) {
			Log.d(tag, value);
		}

	}

	public static void e(String tag, String value) {
		if (isDebug) {
			Log.e(tag, value);
		}

	}

	public static void t(String value) {
		if (isDebug) {
			Log.e("测试", value);
		}

	}
}
