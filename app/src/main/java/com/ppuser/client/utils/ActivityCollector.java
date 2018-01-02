package com.ppuser.client.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mylddw on 2017-12-12.
 *
 * 活动管理器
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
        if (!activity.isFinishing()) {
            activity.finish();
        }
    }

    //销毁所有
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    //获取最上活动窗口
    public static Activity getTopActivity() {
        if (activities.isEmpty()) {
            return null;
        } else {
            return activities.get(activities.size() - 1);
        }
    }
}
