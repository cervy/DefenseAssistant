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
 * POST
 */
public class JPushHelper extends AsyncTask<Object, Integer, String> {
    private HttpCallback callback;
    private Context context;
    public static final int FIVE_THOUSAND = 5000, TWOHUNDREDONE = 201, TWOHUNDRED = 200;

    @Override
    protected String doInBackground(Object... params) {
        String strResp = "";
        String token = App.getInstance().getToken();
        if (params.length >= 3 && (params[2] instanceof Context)) {
            context = (Context) params[2];
            DialogUtil.showDialog(context);//第三个参数为context时 请求进度提示
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL((String) params[0])
                    .openConnection());
            conn.setConnectTimeout(FIVE_THOUSAND);

            //请求头信息
            conn.addRequestProperty("Content-Type", "application/json");
            conn.addRequestProperty("Accept", "application/json");
            if (token != null) {
                String basicAuth = "Basic MGJhYjY3YmFkNGM1MGQ0NDgwMDQyNDA2OjFmOWIzN2MwYmQ3ZjAzMWY3Yjg4NmI1Yg==";
                conn.setRequestProperty("Authorization", basicAuth);
            }


            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStream outStream = conn.getOutputStream();
            JSONObject requestParams = new JSONObject((String) params[1]);
            outStream.write(requestParams.toString().getBytes());
            outStream.flush();
            if (conn.getResponseCode() == TWOHUNDREDONE || conn.getResponseCode() == TWOHUNDRED) {
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
        DialogUtil.closeDialog();//关闭进度提示
        if (callback != null) {
            callback.handleData(s);
            Log.e("JPushHelper concequence", s); // TODO: 2016/6/6 0006
        }
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
