package com.ppuser.client.base;

import java.util.List;

/**
 * Created by mylddw on 2017-02-28.
 */

public interface PermissionListener {
    void onGranted();

    void onDenied(List<String> deniedPermissions);
}
