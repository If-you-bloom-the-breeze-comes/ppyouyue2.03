package com.ppuser.client.utils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.ppuser.client.MyApplication;

import static com.ppuser.client.utils.OssConfig.ACCESS_KEY_ID;
import static com.ppuser.client.utils.OssConfig.ACCESS_KEY_SECRET;
import static com.ppuser.client.utils.OssConfig.BUCKET_NAME;
import static com.ppuser.client.utils.OssConfig.END_POINT;

/**
 * 图片上传utils
 */

public class OssUtils {
    private static final String TAG = "OssUtils : ";
    private static OSSClient ossClient;
    private static OSSCredentialProvider ossCredentialProvider = new OSSPlainTextAKSKCredentialProvider(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    private static ClientConfiguration conf = ClientConfiguration.getDefaultConf();

    /**
     * @param filePath  图片路径
     * @param objectKey 保存到后台的 url xxx.jpg 规则：（base url：https://bk-pp.oss-cn-shanghai.aliyuncs.com/xxx.jpg）
     */
    public static void uploadPic(String filePath, String objectKey, OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {

        synchronized (OssUtils.class) {
            if (ossClient == null) {
                ossClient = new OSSClient(MyApplication.getMyApplication(), END_POINT, ossCredentialProvider, conf);
            }


            PutObjectRequest putRequest = new PutObjectRequest(BUCKET_NAME, objectKey, filePath);

            putRequest.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest putObjectRequest, long currentSize, long totalSize) {
                    LogUtil.t(TAG + "currentSize: " + currentSize + " totalSize: " + totalSize);
                }
            });
            ossClient.asyncPutObject(putRequest, ossCompletedCallback);
        }

    }

    public static void uploadPic(final String filePath, final String objectKey, final OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback, final OnPhotoUploadResult onPhotoUploadResult) {

        synchronized (OssUtils.class) {
            if (ossClient == null) {
                ossClient = new OSSClient(MyApplication.getMyApplication(), END_POINT, ossCredentialProvider, conf);
            }


            PutObjectRequest putRequest = new PutObjectRequest(BUCKET_NAME, objectKey, filePath);

            putRequest.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest putObjectRequest, long currentSize, long totalSize) {
                    LogUtil.t(TAG + "currentSize: " + currentSize + " totalSize: " + totalSize);
                    if (onPhotoUploadResult != null){
                        onPhotoUploadResult.onProgress(currentSize, totalSize);
                    }
                }
            });

            ossClient.asyncPutObject(putRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                    if (ossCompletedCallback != null)
                        ossCompletedCallback.onSuccess(putObjectRequest, putObjectResult);

                    if (onPhotoUploadResult != null)
                        onPhotoUploadResult.onResult(objectKey, filePath);
                }

                @Override
                public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
                    if (ossCompletedCallback != null)
                        ossCompletedCallback.onFailure(putObjectRequest, e, e1);
                }
            });
        }

    }

    public interface OnPhotoUploadResult {
        void onResult(String key, String filePath);

        void onProgress(long currentSize, long totalSize);
    }
}
