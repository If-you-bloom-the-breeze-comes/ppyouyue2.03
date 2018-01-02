package com.ppuser.client.http;

import android.util.Log;

import com.ppuser.client.utils.DateUtil;
import com.ppuser.client.utils.EncryptionUtil;
import com.ppuser.client.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简单的url构造器
 */
public class UrlConstructor {
    private static boolean isSignature = false; // 用于判断是否签名参数
    private static String secret_key = "094e3e9a6abd4813b8730f2dc5d0d504db934854";

    public static void configurationSecretKey(String msg) {
        secret_key = msg;
    }

    public static void configurationIsSignature(boolean isSign) {
        isSignature = isSign;
    }

    public static String obtainUrl(RequestObject requestObject) {
        StringBuilder sb = new StringBuilder();
        String key, value;
        Map<String, String> params = requestObject.getSimpleParams();
        int i = 0;
//        Map类提供了一个称为entrySet()的方法，这个方法返回一个Map.
//                Entry实例化后的对象集。接着，Map.Entry类提供了一个getKey()方法和一个getValue()方法，
//        因此，上面的代码可以被组织得更符合逻辑。举例如下：
        for (Map.Entry<String, String> entry : params.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (i == 0)
                sb.append("?").append(key).append("=").append(value);
            else
                sb.append("&").append(key).append("=").append(value);
            ++i;
        }
        String urlPs = sb.toString();
        if (StringUtil.isEmpty(urlPs))
            return "";
        requestObject.setUrlParams(urlPs);
        return requestObject.getUrl() + urlPs;
    }

    public static String obtainUrlParam(RequestObject requestObject) {
        String url = requestObject.getUrl();
        if (StringUtil.isEmpty(url))
            return "";

        Map<String, String> params = requestObject.getSimpleParams();
        if (params == null || params.size() == 0)
            return url;

        StringBuilder sb = new StringBuilder();
        String key, value;
        int i = 0;
//        Map类提供了一个称为entrySet()的方法，这个方法返回一个Map.
//                Entry实例化后的对象集。接着，Map.Entry类提供了一个getKey()方法和一个getValue()方法，
//        因此，上面的代码可以被组织得更符合逻辑。举例如下：
        for (Map.Entry<String, String> entry : params.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (i == 0)
                sb.append("?").append(key).append("=").append(value);
            else
                sb.append("&").append(key).append("=").append(value);
            ++i;
        }
        String urlPs = sb.toString();
        if (StringUtil.isEmpty(urlPs))
            return url;
        requestObject.setUrlParams(urlPs);
        return requestObject.getUrl()+ urlPs;
    }

    public static Map<String, String> getParamsMap() {
        Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String key1, String key2) {
//	            int intKey1 = 0, intKey2 = 0;
//	            try {
//	                intKey1 = getInt(key1);
//	                intKey2 = getInt(key2);
//	            } catch (Exception e) {
//	                intKey1 = 0;
//	                intKey2 = 0;
//	            }
//	            return intKey1 - intKey2;
                //如果有空值，直接返回0

//                Collator 类执行区分语言环境的 String 比较。
// 使用此类可为自然语言文本构建搜索和排序例程。
//
//                Collator 是一个抽象基类。
// 其子类实现具体的整理策略。Java 平台目前提供了 RuleBasedCollator 子类，
// 它适用于很多种语言。还可以创建其他子类，以处理更多的专门需要。
//
//                与其他区分语言环境的类一样，可以使用静态工厂方法
// getInstance 来为给定的语言环境获得适当的 Collator 对象。
// 如果需要理解特定整理策略的细节或者需要修改策略，只需查看 Collator 的子类即可。

                Collator collator = Collator.getInstance();//  获取当前默认语言环境的 Collator。
                if (key1 == null || key2 == null)
                    return 0;
                CollationKey ck1 = collator.getCollationKey(String.valueOf(key1));
                // 将该 String 转换为一系列可以和其他 CollationKey 按位进行比较的位。
                CollationKey ck2 = collator.getCollationKey(String.valueOf(key2));
                // ck1.compareTo(ck2) 比较此 CollationKey 与目标 CollationKey。
                // CollationKey.compareTo(CollationKey target)
//                一个整数值。如果此 CollationKey 小于目标 CollationKey，则值小于零；
// 如果此 CollationKey 与目标 CollationKey 相等，则值等于零；
// 如果此 CollationKey 大于目标 CollationKey，则值大于零。

                return ck1.compareTo(ck2);
            }
        });
        return sortedMap;
    }

    public static String SignToGet(String UrlHost, String UrlParams) throws UnsupportedEncodingException {
        if (isSignature) {
            // 获取系统当前时间戳
            Date curDate = DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());
            long curT = curDate.getTime() / 1000;

            // 获取签名窜
            String newUrl = UrlParams.substring(1, UrlParams.length());
            StringBuilder sb = new StringBuilder(newUrl);
            sb.append("&timestamp=").append(curT)
                    .append("&secret=").append(secret_key);

            String ret = URLEncoder.encode(sb.toString(), "utf-8");

            // 构造签名后的url
            StringBuilder lastUrl = new StringBuilder();
            lastUrl.append(UrlHost).append(UrlParams)
                    .append("&timestamp=").append(curT)
                    .append("&sign=").append(EncryptionUtil.SHA1(ret));
            String url = lastUrl.toString();
            Log.d("url", url);
            return url;
        }
        // 因为格子微注释了
        if (UrlParams == null) {
            String url = UrlHost;
            Log.d("url", url);
            return url;
        } else {
            String url = UrlHost + UrlParams;
            Log.d("url", url);
            return url;
        }
    }

    public static void SignToPost(RequestObject requestObject) throws UnsupportedEncodingException {
        if (isSignature) {
//			String UrlHost = requestObject.getUrl();
            obtainUrl(requestObject);
            String UrlParams = requestObject.getUrlParams();

            // 获取系统当前时间戳
            Date curDate = DateUtil.parseDateTime(DateUtil.getCurrentDateTimeStr());
            long curT = curDate.getTime() / 1000;

            // 获取签名窜
            String newUrl = UrlParams.substring(1, UrlParams.length());
            StringBuilder sb = new StringBuilder(newUrl);
            sb.append("&timestamp=").append(curT)
                    .append("&secret=").append(secret_key);

            String ret = URLEncoder.encode(sb.toString(), "utf-8");

            Map<String, String> maps = requestObject.getSimpleParams();
            maps.put("timestamp", String.valueOf(curT));
            maps.put("sign", EncryptionUtil.SHA1(ret));
            // 构造签名后的url
//			StringBuilder lastUrl = new StringBuilder();
//			lastUrl.append(UrlHost).append(UrlParams)
//			.append("&timestamp=").append(curT)
//			.append("&sign=").append();
//			String url = lastUrl.toString();
//			Log.e("url", url);
//			return url;
        }
    }

    private static int getInt(String str) {
        int i = 0;
        try {
            Pattern p = Pattern.compile("^\\d+");
            Matcher m = p.matcher(str);
            if (m.find()) {
                i = Integer.valueOf(m.group());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }
}
