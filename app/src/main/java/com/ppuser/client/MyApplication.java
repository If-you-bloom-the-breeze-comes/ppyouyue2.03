package com.ppuser.client;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.ppuser.client.common.CommonData;
import com.ppuser.client.utils.FileUtil;
import com.ppuser.client.utils.LogUtil;
import com.ppuser.client.utils.LoginUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imageloader.cache.disc.impl.UnlimitedDiskCache;
import io.rong.imageloader.cache.disc.naming.HashCodeFileNameGenerator;
import io.rong.imageloader.cache.memory.impl.LruMemoryCache;
import io.rong.imageloader.core.DefaultConfigurationFactory;
import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.ImageLoaderConfiguration;
import io.rong.imageloader.core.assist.ImageScaleType;
import io.rong.imageloader.core.assist.QueueProcessingType;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * Created by mylddw on 2017/12/8.
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;
    public double longitude;
    public double latitude;
    public String city;
    public String cityId;

    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    public final static String mMode = "00";

    public static String jPushRedId;

    ImageLoaderConfiguration configuration;

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        if (isMainProcess()) {
            /**
             * 创建项目文件存储目录
             */
            createFile();


            //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
            UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
            //友盟 开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
            Config.DEBUG = true;
            QueuedWork.isUseThreadPool = false;
            UMShareAPI.get(this);
            //设置LOG开关，默认为false
            UMConfigure.setLogEnabled(true);


            /**
             * 百度地图
             */
            SDKInitializer.initialize(this);

            /**
             * 极光推送
             */
            JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
            JPushInterface.init(this);            // 初始化 JPush
            //打开调试，便于看到Log
            jPushRedId = JPushInterface.getRegistrationID(this);

            setJPushAliasAndTag();
            initImageLoder();
        }

        /**
         *
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
            if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

            }
            getIMToken();
        }

    }


    public boolean hasLocation() {
        if (MyApplication.getMyApplication().longitude == 0 || MyApplication.getMyApplication().latitude == 0 || MyApplication.getMyApplication().city == null) {
            return false;
        }
        return true;
    }


    public void setJPushAliasAndTag() {
        LoginUtils.saveMember_id(this, "603");
        if (!TextUtils.isEmpty(LoginUtils.getMember_id(this))) {
            Set<String> stringSet = new HashSet<>();
            stringSet.add("Android");
            stringSet.add("Client");

            JPushInterface.setAliasAndTags(this, LoginUtils.getMember_id(this), stringSet, mAliasCallback);
        } else {
            JPushInterface.setAliasAndTags(this, "", null, mAliasCallback);
        }
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set tags) {
            String logs;
            switch (code) {
                case 0:

                    logs = "Set tag and alias success";
                    LogUtil.t(logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    LogUtil.t(logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    Map<String, Object> param = new HashMap<>();
                    param.put("alias", alias);
                    param.put("tags", tags);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(1001, param), 1000 * 60);
                    break;
                case 6012:
                    if (JPushInterface.isPushStopped(getApplicationContext()))
                        JPushInterface.resumePush(getApplicationContext());
                    Map<String, Object> param1 = new HashMap<>();
                    param1.put("alias", alias);
                    param1.put("tags", tags);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(1001, param1), 1000);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    LogUtil.e("Jpush", logs);
            }
        }
    };

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    LogUtil.t("Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    Map<String, Object> map = (Map<String, Object>) msg.obj;
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) map.get("alias"), (Set<String>) map.get("tags"), mAliasCallback);
                    break;
                default:
                    LogUtil.t("Unhandled msg - " + msg.what);
            }
        }
    };

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {
        if (getApplicationInfo().packageName.equals(this.getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    getIMToken();
                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    setUpChatListener();
                    Log.d("LoginActivity", "--onSuccess" + userid);
                    LoginUtils.saveIMUserId(MyApplication.this, userid);

                    /**
                     * 设置消息体内是否携带用户信息。
                     * @param state 是否携带用户信息，true 携带，false 不携带。
                     */
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 *                  http://www.rongcloud.cn/docs/android.html#常见错误码
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

    public void getIMToken() {
        if (!LoginUtils.getIMUserId(this).equals("") && !LoginUtils.getIMToken(this).equals("")) {
            connect(LoginUtils.getIMToken(this));
        }
    }

    private void setUpChatListener() {

        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                return false;
            }
        });

        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {
                //   SealUserInfoManager.getInstance().getUserInfo(userId);
                UserInfo userInfo = getUserInfoFromServer(userId);
                return userInfo;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, true);
    }

    private UserInfo getUserInfoFromServer(String userID) {

        return null;
    }

    private void initImageLoder() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()

                /**
                 * 在内存中缓存
                 * 默认：true
                 */
                .cacheInMemory(false)
                .cacheInMemory()
                /**
                 * 在磁盘中缓存
                 * 默认：true
                 */
                .cacheOnDisk(true)
                .cacheOnDisc(true)
                .cacheOnDisc()
                /**
                 * 设置Bitmap.Config
                 * 默认：Bitmap.Config.ARGB_8888
                 */
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                /**
                 * 是否考虑EXIF的信息
                 * 默认：false
                 */
                .considerExifParams(false)
                /**
                 * 设置解析图片参数
                 */
                .decodingOptions(new BitmapFactory.Options())
                /**
                 * 设置加载图片的时间
                 * 默认：0
                 */
                .delayBeforeLoading(0)
                /**
                 * 设置自定义BitmapDisplayer
                 * 默认：SimpleBitmapDisplayer
                 */
                .displayer(DefaultConfigurationFactory.createBitmapDisplayer())
                /**
                 * 设置辅助的传递对象
                 * 默认：null
                 */
                .extraForDownloader(null)
                /**
                 * 设置自定义Hanlder
                 */
                .handler(null)
                /**
                 * 设置解码时图片的缩放
                 * 默认：ImageScaleType.IN_SAMPLE_POWER_OF_2
                 */
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                /**
                 * 在图片加载到内存中前进行处理
                 * 默认：null
                 */
                .preProcessor(null)
                /**
                 * 在显示图片时对图片处理
                 * 默认：null
                 */
                .postProcessor(null)
                /**
                 * 设置是否在加载图片前重置
                 * 默认：true
                 */
                .resetViewBeforeLoading(true)
                .resetViewBeforeLoading()

                .build();

        configuration = new ImageLoaderConfiguration.Builder(myApplication)
                /**
                 * 设置内存保存图片的最大高宽
                 * 默认：屏幕大小
                 */
                .memoryCacheExtraOptions(480, 800)
                /**
                 * 设置内存缓存，会优先用memoryCacheSize设置的值
                 * 默认：LruMemoryCache 1/8的应用缓存
                 */
                .memoryCache(new LruMemoryCache(30))
                /**
                 * 设置最大的内存缓存(如果使用了memoryCache，就尽量不要使用此方法)
                 * 默认：1/8的应用缓存
                 */
                .memoryCacheSize(10)
                /**
                 * 设置可用的内存缓存百分比(1-100)(如果使用了memoryCache，就尽量不要使用此方法)
                 * 默认：1/8的应用缓存
                 */
                .memoryCacheSizePercentage(12)
                /**
                 * 设置磁盘缓存大小
                 * 默认：没有限制
                 */
                .diskCacheSize(10)
                .discCacheSize(10)
                /**
                 * 设置磁盘缓存文件的最大数量
                 * 默认：没有限制
                 */
                .diskCacheFileCount(10)
                .discCacheFileCount(10)
                /**
                 * 设置磁盘文件的命名方式
                 * 默认：HashCodeFileNameGenerator
                 */
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                /**
                 * 设置磁盘缓存
                 * 默认：路径（/Android/data/[app_package_name]/cache），命名（HashCodeFileNameGenerator）,大小（无限）
                 *
                 * tip：默认主路径是/Android/data/[app_package_name]/cache，
                 *      但是如果sdcard处于挂起或无sdcard的情况时会使用/data/data/<application package>/cache
                 *
                 *
                 */
                .diskCache(new UnlimitedDiskCache(new File(CommonData.STORAGE_PICTURE)))
                /**
                 * 设置自定义Executor,用于加载本地无缓存的task
                 * tip：threadPoolSize，threadPriority，tasksProcessingOrder将无效
                 */
                .taskExecutor(null)
                /**
                 * 设置自定义Executor，用于加载本地有缓存的task
                 */
                .taskExecutorForCachedImages(null)
                /**
                 * 设置对队列的处理顺序
                 * 默认：QueueProcessingType.LIFO（FIFO, LIFO）
                 */
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                /**
                 * 设置线程的优先级（MIN_PRIORITY<threadPriority<MAX_PRIORITY）
                 * 默认：Thread.NORM_PRIORITY - 2
                 */
                .threadPriority(Thread.NORM_PRIORITY - 2)
                /**
                 * 设置线程池的大小
                 * 默认：3
                 */
                .threadPoolSize(3)
                /**
                 * 设置图片的解码器
                 * 默认：BaseImageDecoder
                 */
                .imageDecoder(DefaultConfigurationFactory.createImageDecoder(true))
                /**
                 * 设置图片下载器
                 * 默认：BaseImageDownloader
                 */
                .imageDownloader(DefaultConfigurationFactory.createImageDownloader(this.getApplicationContext()))
                /**
                 * 阻止同一个图片不同大小都存放在内存中（如果是同一个都在就去掉）
                 * 默认：flase
                 */
                .denyCacheImageMultipleSizesInMemory()
                /**
                 * 显示图片选项
                 * 默认：{ImageScaleType#IN_SAMPLE_POWER_OF_2}
                 *      {@link Bitmap.Config#ARGB_8888}
                 *      {@link SimpleBitmapDisplayer}
                 */
                .defaultDisplayImageOptions(options)
                /**
                 * 显示详细的log
                 * 默认：false
                 */
                .writeDebugLogs()
                .build();


        ImageLoader.getInstance().init(configuration);

    }

    /**
     * 创建跟目录
     */
    private void createFile() {
        FileUtil.createDirectory(CommonData.STORAGE_CACHE);
        FileUtil.createDirectory(CommonData.STORAGE_PHOTO);
        FileUtil.createDirectory(CommonData.STORAGE_PICTURE);
        FileUtil.createDirectory(CommonData.STORAGE_PHOTO_UPLOAD);

        FileUtil.createDirectory(CommonData.STORAGE_PHOTO_CHAT);
        FileUtil.createDirectory(CommonData.STORAGE_PICTURE_HEAD);
        FileUtil.createDirectory(CommonData.STORAGE_PICTURE_DOWN);

    }

    /**
     * 删除目录
     */
    private void deleteFile() {


        FileUtil.delete(CommonData.STORAGE_PICTURE);


    }

    public boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wx5cd9a5cbef57eed8", "becc1c154d56e8a85dd4ddb83104ca8d");
        PlatformConfig.setSinaWeibo("1966811435", "9113f81da46ee7471c174d4e48944c39", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1106110107", "DT283X5DAZSplZlY");
    }

}
