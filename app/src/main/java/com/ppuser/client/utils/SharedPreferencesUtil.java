package com.ppuser.client.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具类
 * 
 * @author mayue
 *
 */
public class SharedPreferencesUtil {

	
	public static SharedPreferences getShraredPreference(Context context,
                                                         String preference) {
		return context.getSharedPreferences(preference, Context.MODE_PRIVATE);
	}

	public static String getStringPreference(Context context,
                                             String preference, String key, String defaultValue) {
		return context.getSharedPreferences(preference, Context.MODE_PRIVATE)
				.getString(key, defaultValue);
	}

	public static int getIntPreference(Context context, String preference,
                                       String key, int defaultValue) {
		return context.getSharedPreferences(preference, Context.MODE_PRIVATE)
				.getInt(key, defaultValue);
	}

	public static boolean getBooleanPreference(Context context,
                                               String preference, String key, boolean defaultValue) {
		return context.getSharedPreferences(preference, Context.MODE_PRIVATE)
				.getBoolean(key, defaultValue);

	}

	public static void saveStringPreference(Context context, String preference, String key, String value) {
		context.getSharedPreferences(preference, Context.MODE_PRIVATE).edit()
				.putString(key, value).commit();
	}

	public static void saveIntPreference(Context context, String preference,
                                         String key, int value) {
		context.getSharedPreferences(preference, Context.MODE_PRIVATE).edit()
				.putInt(key, value).commit();
	}

	public static void saveBooleanPreference(Context context,
                                             String preference, String key, boolean value) {
		context.getSharedPreferences(preference, Context.MODE_PRIVATE).edit()
				.putBoolean(key, value).commit();
	}

}
