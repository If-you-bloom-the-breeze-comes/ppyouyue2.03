package com.ppuser.client.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.ppuser.client.present.BasePersenter;
import com.ppuser.client.utils.ActivityCollector;
import com.ppuser.client.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylddw on 2017/3/3 0003.
 * V  IGrilView接口
 */

public abstract class BaseActivity<V, T extends BasePersenter<V>> extends FragmentActivity {
    protected T mPresent;
    public Context mContext;
    private static final int SEND_PERMISSION_REQUEST = 1001;
    private static PermissionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        StatusBarUtils.with(this).init();
        //添加到列表中
        ActivityCollector.addActivity(this);
        mPresent = createPresent();
        if (mPresent != null && !mPresent.equals(""))
            mPresent.attachView((V) this);
        initView(savedInstanceState);
        setListener();
        initData();
    }



    @Override
    protected void onDestroy() {
        if (mPresent != null && !mPresent.equals(""))
            mPresent.detach();
        super.onDestroy();
        //从列表移除
        ActivityCollector.removeActivity(this);

    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void setListener();

    protected abstract void initData();


    /**
     * 子类实现具体的构建过程
     *
     * @return
     */
    protected abstract T createPresent();

    public static void onRuntimePermissionRequest(String[] permissions, PermissionListener listener) {
        Activity top = ActivityCollector.getTopActivity();
        List<String> deniedPermissions = new ArrayList<>();//存储被拒绝的权限
        mListener = listener;

        if (top == null) {
            return;
        } else {
            for (String p : permissions) {
                if (ContextCompat.checkSelfPermission(top, p) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(p);
                }
            }
            if (deniedPermissions.isEmpty()) {
                listener.onGranted();
            } else {
                ActivityCompat.requestPermissions(top,
                        deniedPermissions.toArray(new String[deniedPermissions.size()]), SEND_PERMISSION_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_PERMISSION_REQUEST:
                List<String> deniedPermissionList = new ArrayList<>();
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissionList.add(permissions[i]);
                        }
                    }
                    if (deniedPermissionList.isEmpty()) {
                        //权限全部同意处理
                        if (mListener != null)
                            mListener.onGranted();
                    } else {
                        //存在拒绝权限处理
                        if (mListener != null)
                            mListener.onDenied(deniedPermissionList);
                    }
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
