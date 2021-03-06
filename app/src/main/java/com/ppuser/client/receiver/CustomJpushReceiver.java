package com.ppuser.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;


import com.ppuser.client.MainActivity;
import com.ppuser.client.ProjectGlobalVar;
import com.ppuser.client.utils.SystemUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class CustomJpushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Log.d(TAG, "[CustomJpushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {//// TODO: 2017/5/16 现阶段只用设备id
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[CustomJpushReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[CustomJpushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[CustomJpushReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[CustomJpushReceiver] 用户点击打开了通知");

                //判断app进程是否存活
                if (SystemUtils.isAppAlive(context, "com.ppuser.client")) {
                    Intent mainIntent = new Intent(context, MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //    Intent detailIntent = new Intent(context, MessageActivity.class);
                    Intent detailIntent = new Intent(context, MainActivity.class);
                    detailIntent.putExtra("type", 1);

                    Intent[] intents = {mainIntent, detailIntent};
                    context.startActivities(intents);
                } else {
                    Intent launchIntent = context.getPackageManager().
                            getLaunchIntentForPackage("com.ppuser.client");
                    launchIntent.setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    Bundle args = new Bundle();
                    args.putString(ProjectGlobalVar.NOTICATION_JUMP_ACTION, "message");
                    launchIntent.putExtra(ProjectGlobalVar.EXTRA_BUNDLE, args);
                    context.startActivity(launchIntent);
                }
                /*//打开自定义的Activity
				Intent i = new Intent(context, MainActivity.class);
				i.putExtras(bundle);
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(i);*/

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            } else {
                Log.d(TAG, "[CustomJpushReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent("com.ppuser.client.MESSAGE_RECEIVED_ACTION");
            msgIntent.putExtra("message", message);
            if (!TextUtils.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra("extras", extras);
                    }
                } catch (JSONException e) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }
}
