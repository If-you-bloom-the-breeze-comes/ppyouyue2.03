package com.ppuser.client.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.ppuser.client.utils.LogUtil;
import com.ppuser.client.utils.LoginUtils;
import com.ppuser.client.utils.SecurityUtil;
import com.ppuser.client.utils.StringUtil;
import com.ppuser.client.utils.VersionInfoUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    public final static int CONNECT_TIMEOUT =5;
    public final static int READ_TIMEOUT=5;
    public final static int WRITE_TIMEOUT=5;
    private static HttpUtil util = null;
    private OkHttpClient okHttpClient;
    private Handler handler;

    public HttpUtil() {
        okHttpClient = new OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS).build();
        handler = new Handler(Looper.getMainLooper());
    }

    public static HttpUtil getInstance() {
        if (util == null) {
            synchronized (HttpUtil.class) {
                util = new HttpUtil();
            }
        }
        return util;
    }

    public void doGet(Context context, RequestObject object, String dialogMsg, boolean isShowDialog, GetTaskCallBack back) {
        start(context,dialogMsg, isShowDialog, getRequestDoGet(object), back);
    }

    public void doPost(Context context, RequestObject object, String dialogMsg, boolean isShowDialog, GetTaskCallBack back) {
        start(context,dialogMsg, isShowDialog, getRequestDoPost(object), back);
    }

    public void doPut(Context context, RequestObject object, String dialogMsg, boolean isShowDialog, GetTaskCallBack back) {
        start(context,dialogMsg, isShowDialog, getRequestDoPut(object,context), back);
    }

    public void doPostFileForMap(Context context, Map<String, File> fileMap, RequestObject object, String dialogMsg, boolean isShowDialog, GetTaskCallBack back) {
        start(context,dialogMsg, isShowDialog, getRequestDoPostFileForMap(fileMap, object), back);
    }

    public void doPostFile(Context context, List<File> fileList, RequestObject object, String dialogMsg, boolean isShowDialog, GetTaskCallBack back) {
        start(context,dialogMsg, isShowDialog, getRequestDoPostFile(fileList, object), back);
    }

    private void start(final Context context, String dialogMsg, boolean isShowDialog, final Request request, final GetTaskCallBack back) {
        LogUtil.t("start");
        if (isShowDialog && context != null) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage(dialogMsg);
            dialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doBackground(context,dialog, request, back);
                }
            }).start();
        } else
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doBackground(context,null, request, back);
                }
            }).start();
    }

    /**
     * get请求的request
     *
     * @param object
     * @return
     */
    private Request getRequestDoGet(RequestObject object) {
        String url = UrlConstructor.obtainUrlParam(object);
        LogUtil.t("访问链接getRequestDoGet："+url);
        if (StringUtil.isEmpty(url))
            return null;
        Request request = new Request.Builder()
                .url(url)
                .build();
        return request;
    }

    /**
     * post请求的request
     *
     * @param object
     * @return
     */
    private Request getRequestDoPost(RequestObject object) {
        String url = object.getUrl();
        LogUtil.t("访问链接getRequestDoPost："+url);
        if (StringUtil.isEmpty(url))
            return null;
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Map<String, String> params = object.getSimpleParams();
        if (params != null && params.size() != 0)
            builder.post(getPostBody(object));
        Map<String, String> params1 = object.getHeaderParams();
        if (params1 != null && params1.size() != 0)
           addHeaders(object,builder);

        Request request = builder.build();
        return request;
    }

    private void addHeaders(RequestObject object, Request.Builder builder){
        String key;
        String value;
        Map<String, String> params = object.getHeaderParams();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (StringUtil.isEmpty(value))
                value = "";
            builder.addHeader(key, value);
            LogUtil.t("addHeaders："+key+","+value);
        }
    }

    /**
     * put请求的request
     *
     * @param object
     * @return
     */
    private Request getRequestDoPut(RequestObject object, Context context) {
        String url = object.getUrl();

        if (StringUtil.isEmpty(url))
            return null;
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        LogUtil.t("不加密的访问链接getRequestDoPut："+url);

        Map<String, String> params = object.getSimpleParams();

        if (params != null && params.size() != 0)
            builder.post(getPutBody(params,context));
        Request request = builder.build();
        return request;
    }

    /**
     * post请求的request 带Map<String, File>文件
     *
     * @param fileMap
     * @param object
     * @return
     */
    private Request getRequestDoPostFileForMap(Map<String, File> fileMap, RequestObject object) {
        String url = object.getUrl();
        LogUtil.t("getRequestDoPostFile："+url);
        if (StringUtil.isEmpty(url))
            return null;

        Map<String, String> params = object.getSimpleParams();
        MultipartBody.Builder builder = new MultipartBody.Builder();

        if (params != null)
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.addFormDataPart(key, value);
                LogUtil.t("getRequestDoPostFile："+key+","+value);
            }

        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            File file = entry.getValue();
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\"; filename=\"" + file.getName() + "\""),
                    RequestBody.create(MediaType.parse("image/png"), file));
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return request;
    }

    /**
     * post请求的request 带List<File>文件
     *
     * @param fileList
     * @param object
     * @return
     */
    private Request getRequestDoPostFile(List<File> fileList, RequestObject object) {
        String url = object.getUrl();
        LogUtil.t("getRequestDoPostFile："+url);
        if (StringUtil.isEmpty(url)){
            return null;
        }
        Map<String, String> params = object.getSimpleParams();
        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(params!=null){
            for (String key : params.keySet()) {
//                LogUtil.t("参数："+key+","+params.get(key));
                builder.addFormDataPart(key, params.get(key));
            }

        }
        for (int i=0;i<fileList.size();i++){
            builder.addPart( Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""),
                    RequestBody.create(MediaType.parse("image/png"),fileList.get(i)));
        }

        RequestBody requestBody=builder.build();
        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        return  request;
    }

    /**
     * 获取post请求的body
     *
     * @param object
     */
    private RequestBody getPostBody(RequestObject object) {
        LogUtil.t("getPostBody");
        Map<String, String> params = object.getSimpleParams();

        if (params == null || params.size() == 0)
            return null;

        FormBody.Builder builder = new FormBody.Builder();
        String key;
        String value;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (StringUtil.isEmpty(value))
                value = "";
            builder.add(key, value);
            LogUtil.t("getRequestDoPostFile："+key+","+value);
        }
        RequestBody requestBody = builder.build();

        return requestBody;
    }

    /**
     * 获取post请求的body
     *
     */
    private RequestBody getPutBody(Map<String, String> params, Context context) {
        if (params == null || params.size() == 0)
            return null;

        String content="{";
        content+="\"APP_SYSTEM\":\"1\",";
        content+="\"APP_VER\":\""+ VersionInfoUtil.getAppVersionName(context)+"\",";
        content+="\"member_id\":\""+ LoginUtils.getMember_id(context)+"\",";
//        content+="\"member_id\":\""+ "1"+"\",";
        content+="\"token\":\""+LoginUtils.getToken(context)+"\",";
//        content+="\"token\":\""+ "aaa"+"\",";
        String key;
        String value;
        int size=0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            size++;
            key = entry.getKey();
            value = entry.getValue();
            if (StringUtil.isEmpty(value))
                value = "";
            if (size!=params.size()){
                content+="\""+key+"\":\""+value+"\",";
            }else {
                content+="\""+key+"\":\""+value+"\"";
            }
        }
        content+="}";
        LogUtil.t("未加密："+content);
        String contentStr= SecurityUtil.encrypt(content,"1234567891234567");
        LogUtil.t("加密后："+contentStr);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("data",contentStr);
        RequestBody requestBody = builder.build();

        return requestBody;
    }

    /**
     * 网络请求
     *
     * @param request
     */
    private void doBackground(final Context context, final ProgressDialog dialog, Request request, final GetTaskCallBack back) {
        LogUtil.t("doBackground");
        if (request == null)
            return;

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (dialog != null)
                    dialog.dismiss();
                if (context != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.t("请求失败："+e.toString());
                            back.onTaskPostExecuteFailure("亲，网络有点问题哦！");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (dialog != null)
                    dialog.dismiss();
                final String msg = response.body().string();
                LogUtil.t("请求成功："+msg);
                if (back != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            back.onTaskPostExecute(msg);
                        }
                    });
                }
            }
        });
    }

    /**
     * 网络请求回调
     */
    public interface GetTaskCallBack {
        public void onTaskPostExecute(String callBackStr);
        public void onTaskPostExecuteFailure(String callBackStr);
    }
}
