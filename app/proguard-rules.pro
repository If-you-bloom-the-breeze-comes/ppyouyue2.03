# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Hfc\AndroidStudioSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#---------------------------------基本指令区----------------------------------
# 指定代码的压缩级别
-optimizationpasses 7

# 混淆时不会产生形形色色的类名
-dontusemixedcaseclassnames

# 指定不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

# 混淆时是否做预校验
-dontpreverify

# 混淆时是否记录日志
-verbose

# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#过滤泛型
-keepattributes Signature

#不优化输入的类文件
-dontoptimize

#指定不去忽略包可见的库类的成员
-dontskipnonpubliclibraryclassmembers
#不输出通知
-dontnote
#-ignorewarnings # 忽略警告，避免打包时某些警告出现 优化打包时使用

#-libraryjars libs/tbs_sdk_v1.5.1.1036_25435_20160107_obfs.jar


# 官方 4大主件 及jar包
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class com.android.vending.licensing.ILicensingService

#------------------  下方是android平台自带的排除项，这里不要动         ----------------

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keep public class * extends android.app.Activity{
	public <fields>;
	public <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepattributes *Annotation*

-keepclasseswithmembernames class *{
	native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class * implements java.io.Serializable {
  *;
}

#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
	... *JRI*(...);
}

-keep class **JNI* {*;}

#---------------------------------webview------------------------------------
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}

#js 交互
-keepclasseswithmembers class com.ppuser.client.activity.CommonWebViewActivity$MyJavaScriptInterface { <methods>; }
-keepclassmembers class * extends android.webkit.WebChromeClient {
            public void openFileChooser(...);
            public void onShowFileChooser(...);

      }


##--------------------------第三方 library 不混淆 start -----------------------------------------

# 银联
-dontwarn com.unionpay.**
-keep class com.unionpay.** { *; }

#qq
-dontwarn com.qq.**
-keep class com.qq.**{*;}
-dontwarn com.tencent.**
-keep class com.tencent.**{*;}

#gson解析不被混淆
-dontwarn com.google.**
-keep class com.google.**{*;}
-keep class com.google.protobuf.** {*;}

#百度地图不混淆
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**


#阿帕奇 第三方库
-dontwarn org.apache.**
-keep class org.apache.**{*;}

-dontwarn internal.org.apache.**
-keep class internal.org.apache.**{*;}

#阿里支付
-dontwarn com.alipay.**
-keep class com.alipay.**{*;}

#阿里云 图片上传
-dontwarn com.alibaba.**
-keep class com.alibaba.**{*;}


#J2SE 禁止发出警告
-dontwarn javax.swing.**
-dontwarn java.awt.**

#友盟
-dontwarn com.umeng.**
-keep class com.umeng.** {*; }

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keep public class com.gridinn.android.R$*{
	public static final int *;
}

# 微信回调
-dontwarn com.ppuser.client.wxapi.**
-keep class com.ppuser.client.wxapi.** {*;}

#okhttp3
-dontwarn okio.**
-keep class okio.** { *; }

-dontwarn okhttp3.**
-keep class okhttp3.** { *; }


# RongCloud SDK
-keep class io.rong.** {*;}
-keep class * implements io.rong.imlib.model.MessageContent {*;}
-dontwarn io.rong.push.**
-dontnote com.xiaomi.**
-dontnote com.google.android.gms.gcm.**
-dontnote io.rong.**

# VoIP
-keep class io.agora.rtc.** {*;}

# Location 高德（融云自带）
-keep class com.amap.api.**{*;}
-keep class com.amap.api.services.**{*;}
-dontwarn com.amap.api.**

# 红包
-keep class com.google.gson.** { *; }
-keep class com.uuhelper.Application.** {*;}
-keep class net.sourceforge.zbar.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.alipay.** {*;}
-keep class com.jrmf360.rylib.** {*;}

#shareSDK
-keep class cn.sharesdk.**{*;}
	-keep class com.sina.**{*;}
	-keep class **.R$* {*;}
	-keep class **.R{*;}
	-keep class com.mob.**{*;}
	-dontwarn com.mob.**
	-dontwarn cn.sharesdk.**
	-dontwarn **.R$*

#Jpush
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

#openinstall
-dontwarn com.fm.openinstall.**
-keep class com.fm.openinstall.** { *; }

#fresco
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**

#glide
 -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
      **[] $VALUES;
      public *;
    }

#eventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#pulltorefresh and other custom view
-dontwarn com.ppuser.client.view.**
-keep class com.ppuser.client.view.** { *; }

# takephoto
-dontwarn com.jph.takephoto.**
-keep class com.jph.takephoto.** { *; }

# timeselector
-dontwarn org.feezu.liuli.**
-keep class org.feezu.liuli.** { *; }

# circleimageview
-dontwarn de.hdodenhof.circleimageview.**
-keep class de.hdodenhof.circleimageview.** { *; }

# pickerview
-dontwarn com.bigkoo.pickerview.**
-keep class com.bigkoo.pickerview.** { *; }

# rxjava
-dontwarn rx.**
-keep class rx.** { *; }

# nineoldandroids
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }

# advancedluban library-1.3.2
-dontwarn me.shaohui.advancedluban.**
-keep class me.shaohui.advancedluban.** { *; }

# soundcloud lib_crop
-dontwarn com.soundcloud.**
-keep class com.soundcloud.** { *; }

# hamcrest
-dontwarn org.hamcrest.**
-keep class org.hamcrest.** { *; }

# umeng
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.ppuser.client.R$*{
public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

##--------------------------第三方 library 不混淆 end -----------------------------------------

##--------------------------业务逻辑类 -----------------------------------------
-keep class com.ppuser.client.bean.** { *; }
#adapter
-keep class com.ppuser.client.adapter.** { *; }