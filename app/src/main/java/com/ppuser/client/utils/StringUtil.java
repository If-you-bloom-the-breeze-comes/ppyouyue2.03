package com.ppuser.client.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 检查str是否为空或者空白，如果不为空返回str.trim(),否则返回""
     *
     * @param str
     * @return
     */
    public static String checkNull(String str) {
        if (str == null || str.length() == 0 || "".equals(str.trim())) {
            return "";
        }
        return str.trim();
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || "".equals(str.trim())) {
            return true;
        }

        return false;
    }

    public static boolean isNumber(String str) {
        Pattern pattern = Pattern
                .compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isChinese(String str) {
        Pattern pattern = Pattern
                .compile("[\\u4e00-\\u9fa5]+");
        Matcher match = pattern.matcher(str);
        if (match.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkAppVersion(String version1, String version2) {
        int ver1 = -1, ver2 = -1;
        try {
            String regEx = "[^0-9]";

            String [] stringArr1= version1.split("\\.");
            String [] stringArr2= version2.split("\\.");

            if (!TextUtils.isEmpty(version1)) {
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(version1);
                ver1 = Integer.parseInt(m.replaceAll(""));
            }

            if (!TextUtils.isEmpty(version2)) {
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(version2);
                ver2 = Integer.parseInt(m.replaceAll(""));
            }
            if (ver1 != -1 && ver2 != -1 ){
                int j = stringArr1.length>stringArr2.length?stringArr2.length:stringArr1.length;

               for(int i=0;i<j;i++){
                   if(stringArr1[i].substring(0,1).equals("0")&&stringArr1[i].length()>1){
                       stringArr1[i] = stringArr1[i].substring(1);
                   }

                   if(stringArr2[i].substring(0,1).equals("0")&&stringArr2[i].length()>1){

                       stringArr2[i] = stringArr2[i].substring(1);
                   }
                   Log.e("mayue",i+"====="+stringArr1[i]+"==="+stringArr2[i]);
                   if(Integer.parseInt(stringArr1[i])<Integer.parseInt(stringArr2[i])){

                        return true;
                   }else if(Integer.parseInt(stringArr1[i])>Integer.parseInt(stringArr2[i])){
                       return false;
                   }
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 检查str字符串为中文，英文，数字
     *
     * @param str
     * @return
     */
    public static boolean isEnglishChineseNumber(String str) {
        Pattern pattern = Pattern
                .compile("^[\\u4E00-\\u9FA5A-Za-z0-9]+$");
        Matcher match = pattern.matcher(str);
        if (match.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
