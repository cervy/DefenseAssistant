package com.szzaq.jointdefence.net;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.utils.DialogUtil;
import com.szzaq.jointdefence.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * restful 框架 POST类
 */
public class PostRequest extends AsyncTask<Object, Integer, String> {
    private HttpCallback callback;
    private Context context;
    private static final int FIVETHOUSANDS = 5000;

    @Override
    protected String doInBackground(Object... params) {
        String strResp = "";
        String token = App.getInstance().getToken();
        if (params.length >= 3 && (params[2] instanceof Context)) {
            context = (Context) params[2];
            DialogUtil.showDialog(context);
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL((String) params[0])
                    .openConnection());
            conn.setConnectTimeout(FIVETHOUSANDS);
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.addRequestProperty("Accept", "application/json");
            if (token != null) {
                //String basicAuth = "Token "+ token;
                String basicAuth = "Token " + token;
                conn.setRequestProperty("Authorization", basicAuth);
            }
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStream outStream = conn.getOutputStream();
            JSONObject requestParams = new JSONObject((String) params[1]);
            JSONObject json = new JSONObject("{}");
            if (!"".equals(requestParams.optString("_method", ""))) {
                json.put("_method", requestParams.optString("_method", ""));
            }
            json.put("_content", requestParams.toString());
            json.put("_content_type", "application/json");
            outStream.write(jsonToParams(json.toString()).getBytes());
            outStream.flush();
            if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200) {
                strResp = StringUtils.inputStream2String(conn.getInputStream());
            } else {
                strResp = conn.getResponseMessage();
                strResp += StringUtils.inputStream2String(conn.getErrorStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResp;
    }

    @Override
    protected void onPostExecute(String s) {
        DialogUtil.closeDialog();
        if (callback != null)

            callback.handleData(s);
        Log.e("Post数据", s); // TODO: 2016/5/17 0017
    }

    public void setCallback(HttpCallback callback) {
        this.callback = callback;
    }

    private static String jsonToParams(String jsonStr) {
        String ret = "";
        try {
            JSONObject json = new JSONObject(jsonStr);
            Iterator<String> it = json.keys();
            while (it.hasNext()) {
                String key = it.next();
                Object obj = json.get(key);
                if (obj instanceof JSONArray) {
                    JSONArray jarr = (JSONArray) obj;
                    if (jarr.length() == 0)
                        ret += "&" + key + "=null";
                    for (int i = 0; i < jarr.length(); i++) {
                        ret += "&" + key + "=" + jarr.getString(i);
                    }
                } else {
                    ret += "&" + key + "=" + json.optString(key);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret.startsWith("&") ? ret.substring(1, ret.length()) : ret;
    }


}
