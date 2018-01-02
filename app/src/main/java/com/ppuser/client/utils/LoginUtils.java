package com.ppuser.client.utils;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by mylddw on 2017/12/13.
 * <p>
 * 登录信息保存
 */

public class LoginUtils {

    public static void saveMember_id(Context context, String member_id) {
        SharedPreferencesUtil.saveStringPreference(context, "LoginDatas", "member_id", member_id);
    }

    public static String getMember_id(Context context) {
        return SharedPreferencesUtil.getStringPreference(context, "LoginDatas", "member_id", "");
    }

    public static void saveMember_nickname(Context context, String member_nickname) {
        SharedPreferencesUtil.saveStringPreference(context, "LoginDatas", "member_nickname", member_nickname);
    }

    public static String getMember_nickname(Context context) {
        return SharedPreferencesUtil.getStringPreference(context, "LoginDatas", "member_nickname", "");
    }

    public static void saveMember_phone(Context context, String member_phone) {
        SharedPreferencesUtil.saveStringPreference(context, "LoginDatas", "member_phone", member_phone);
    }

    public static String getMember_phone(Context context) {
        return SharedPreferencesUtil.getStringPreference(context, "LoginDatas", "member_phone", "");
    }

    public static void saveMember_avatar(Context context, String member_avatar) {
        SharedPreferencesUtil.saveStringPreference(context, "LoginDatas", "member_avatar", member_avatar);
    }

    public static String getMember_avatar(Context context) {
        return SharedPreferencesUtil.getStringPreference(context, "LoginDatas", "member_avatar", "");
    }

    public static void saveToken(Context context, String token) {
        SharedPreferencesUtil.saveStringPreference(context, "LoginDatas", "token", token);
    }

    public static String getToken(Context context) {
        return SharedPreferencesUtil.getStringPreference(context, "LoginDatas", "token", "");
    }

    public static void saveIMToken(Context context, String token) {
        SharedPreferencesUtil.saveStringPreference(context, "IMDatas", "token", token);
    }

    public static String getIMToken(Context context) {
        return SharedPreferencesUtil.getStringPreference(context, "IMDatas", "token", "");
    }

    public static void saveIMUserId(Context context, String token) {
        SharedPreferencesUtil.saveStringPreference(context, "IMDatas", "userId", token);
    }

    public static String getIMUserId(Context context) {
        return SharedPreferencesUtil.getStringPreference(context, "IMDatas", "userId", "");
    }

    public static boolean isLogin(Context context) {
        return !TextUtils.isEmpty(getToken(context)) && !TextUtils.isEmpty(getMember_id(context));
    }

    public static void saveLoginDatas(Context context, String member_id, String member_nickname, String member_phone, String member_avatar, String token) {
        saveMember_id(context, member_id);
        saveMember_nickname(context, member_nickname);
        saveMember_phone(context, member_phone);
        saveMember_avatar(context, member_avatar);
        saveToken(context, token);
    }

    public static void clearLoginDatas(Context context) {
        saveMember_id(context, "");
        saveMember_nickname(context, "");
        saveMember_phone(context, "");
        saveMember_avatar(context, "");
        saveToken(context, "");
    }
}
