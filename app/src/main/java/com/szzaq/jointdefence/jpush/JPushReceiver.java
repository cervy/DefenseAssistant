package com.szzaq.jointdefence.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.Adialog.AlertActivity;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.MainActivity;
import com.szzaq.jointdefence.model.UserInfo;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[JPushReceiver] onReceive - " + intent.getAction()
                + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            /*Log.d(TAG,
                    "[JPushReceiver] 接收到推送下来的自定义消息: "
                            + bundle.getString(JPushInterface.EXTRA_MESSAGE));*/
            processCustomMessage(context, bundle);


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            Log.d(TAG, "[JPushReceiver] 用户点击打开了通知");

            // 打开自定义的Activity
            Intent i = new Intent(context, MainActivity.class);
            i.putExtras(bundle);
            // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);

        } else if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle
                    .getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
            // send the Registration Id to your server...

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            //Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle
                    .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
                .getAction())) {
            Log.d(TAG,
                    "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: "
                            + bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
            // 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
                .getAction())) {
            boolean connected = intent.getBooleanExtra(
                    JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[JPushReceiver]" + intent.getAction()
                    + " connected state change to " + connected);
        } else {
            Log.d(TAG,
                    "[JPushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    //  所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(
                            bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:").append(key).append(", value: [").append(myKey).append(" - ").append(json.optString(myKey)).append("]");
                    }
                } catch (JSONException ignored) {
                }

            } else {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void processCustomMessage(Context context, Bundle bundle) {
        // String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String contentType = bundle
                .getString(JPushInterface.EXTRA_CONTENT_TYPE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if ("text".equals(contentType) || "audio".equals(contentType)) {
            Location myPoint = new Location("");
            try {
                myPoint.setLongitude(App.getInstance().getLngLat()[0]);
                myPoint.setLatitude(App.getInstance().getLngLat()[1]);
                // HashMap<String, Object> map = new HashMap<String, Object>();
                JSONObject json = new JSONObject(extras);
                float lat = (float) json.optDouble("lat", 0.1f);
                float lng = (float) json.optDouble("lng", 0.1f);
                Location alertPoint = new Location("");
                alertPoint.setLatitude(lat);
                alertPoint.setLongitude(lng);
                if (myPoint.distanceTo(alertPoint) < 3000) {
                    sendMsg(context, bundle);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("alert".equals(contentType)) {

            try {
                JSONObject json = new JSONObject(extras);
                JSONArray users = json.optJSONArray("users");
                if (App.getInstance().getUser() == null) {
                    if (checkToken(context))
                        checkUser(context, bundle, users);

                } else {
                    for (int i = 0; i < users.length(); i++) {
                        if (App.getInstance().getUser().getUsername()
                                .equals(users.optString(i))) {
                            sendMsg(context, bundle);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void sendMsg(Context context, Bundle bundle) {
        Intent intent = new Intent(context, AlertActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        context.startActivity(intent);
        Intent bc = new Intent("ALERT_MSG");
        bc.putExtras(bundle);
        context.sendBroadcast(bc);
    }

    private boolean checkToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("fpplus",
                android.content.Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");

        if (!"".equals(token)) {
            App.getInstance().setToken(token);
            return true;
        }
        return false;
    }

    public void checkUser(final Context context, final Bundle bundle,
                          final JSONArray users) {//核对用户
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                JSONArray jarr;
                try {
                    JSONObject orgJson = new JSONObject(s);
                    jarr = orgJson.getJSONArray("results");
                    JSONObject json = jarr.optJSONObject(0);
                    Gson gson = new Gson();
                    UserInfo user = gson.fromJson(json.toString(),
                            new TypeToken<UserInfo>() {
                            }.getType());
                    App.getInstance().setUser(user);
                    for (int i = 0; i < users.length(); i++) {
                        if (App.getInstance().getUser().getUsername()
                                .equals(users.optString(i))) {
                            sendMsg(context, bundle);
                        }
                    }
                } catch (JSONException ignored) {

                }
            }
        });
        JSONStringer js;
        String params = "";
        try {
            js = new JSONStringer();
            params = js.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.execute(Resources.PERSONINFO, params);
    }
}
