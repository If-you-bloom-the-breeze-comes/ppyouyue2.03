package com.ppuser.client.common;

import android.content.Context;
import android.os.Environment;

import com.ppuser.client.utils.LoginUtils;

/**
 * Created by mylddw on 2017/12/17.
 *
 * 存储文件名字
 */

public class CommonData {

    public final static String STORAGE_ROOT = Environment
            .getExternalStorageDirectory() + "/com.ppuser.client";

    public final static String STORAGE_CACHE = STORAGE_ROOT + "/cache";
    public final static String STORAGE_PHOTO = STORAGE_ROOT + "/photo";
    public final static String STORAGE_PICTURE = STORAGE_ROOT + "/picture";

    public final static String STORAGE_PHOTO_CHAT = STORAGE_PHOTO + "/chat";
    public final static String STORAGE_PHOTO_UPLOAD = STORAGE_PHOTO + "/upload";
    public final static String STORAGE_PICTURE_HEAD = STORAGE_PICTURE + "/head";
    public final static String STORAGE_PICTURE_DOWN = STORAGE_PICTURE + "/down";



}
