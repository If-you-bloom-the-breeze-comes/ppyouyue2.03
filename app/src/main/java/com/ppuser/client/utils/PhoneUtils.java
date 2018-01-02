package com.ppuser.client.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Peter mylddw on 2015/9/11 13 10.
 */
public class PhoneUtils {

    private PhoneUtils() {

    }

    public static String getDeviceId(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (SecurityException e) {
            return "";
        }
    }

    public static boolean matchTel(String tel) {
        Pattern p = Pattern
                .compile("^(0|86|17951)?(13[0-9]|15[0-9]|166|19[89]|17[0-9]|18[0-9]|14[57])[0-9]{8}$");
        Matcher m = p.matcher(tel);
        return m.matches();
    }

}
