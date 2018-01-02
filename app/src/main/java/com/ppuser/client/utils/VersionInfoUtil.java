package com.ppuser.client.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 版本控制工具类
 * 
 * @author huangfucai
 *
 */
public class VersionInfoUtil {

	public static String getAppVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(),0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Integer getAppVersinoCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
