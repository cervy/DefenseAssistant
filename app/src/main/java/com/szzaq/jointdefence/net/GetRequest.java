package com.szzaq.jointdefence.net;

import android.os.AsyncTask;
import android.util.Log;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.utils.StringUtils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * restful 框架 GET类
 */
public class GetRequest extends AsyncTask<String, Integer, String> {
    private HttpCallback callback;

    @Override
    protected String doInBackground(String... params) {
        String strResp = "";
        String token = App.getInstance().getToken();
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(params[0])
                    .openConnection());
            conn.setRequestProperty("User-Agent", "fpplus");
            conn.setConnectTimeout(5000);
            conn.addRequestProperty("Content-Type", "application/json");
            conn.addRequestProperty("Accept", "application/json");
            if (token != null) {
                String basicAuth = "Token " + token;
                conn.setRequestProperty("Authorization", basicAuth);
            }
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                strResp = StringUtils.inputStream2String(conn.getInputStream());
            } /*else {
                //strResp = conn.getResponseCode() + ":" + conn.getResponseMessage();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResp;
    }

    @Override
    protected void onPostExecute(String s) {
        if (callback != null) {
            callback.handleData(s);
            Log.e("get数据", s);    // TODO: 2016/5/16 0016
        }
    }

    public void setCallback(HttpCallback callback) {
        this.callback = callback;
    }


}
