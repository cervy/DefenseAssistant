package com.szzaq.jointdefence.net;

import android.os.AsyncTask;
import android.util.Log;

import com.szzaq.jointdefence.App;
import com.szzaq.interfaces.HttpCallback;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * 接口连接类
 *
 * @author jiajiaUser
 * @date 2015/07/08
 */
public class HttpHelper extends AsyncTask<String, Integer, String> {
    private HttpCallback callback;

    @Override
    protected String doInBackground(String... params) {
        String strResp = "";
        HttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter("charset", HTTP.UTF_8);
        HttpConnectionParams.setConnectionTimeout(httpParams, 8 * 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 8 * 1000);
        HttpPost request = new HttpPost(params[0]);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("charset", HTTP.UTF_8);
        if (null != App.getInstance().getSessionId()) {
            request.setHeader("Cookie", "sessionid=" + App.getInstance().getSessionId());
        }
        try {
            StringEntity entity = new StringEntity(URLEncoder.encode(params[1], "UTF-8"));
            if (!"".equals(params[1])) {
                request.setEntity(entity);
            }
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            Log.i("ss", response.getStatusLine().getStatusCode() + "");
            if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
                strResp = EntityUtils.toString(response.getEntity());
                CookieStore cookieStore = httpClient.getCookieStore();
                List<Cookie> cookies = cookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
                    if ("sessionid".equals(cookies.get(i).getName()) && App.getInstance().getSessionId() == null) {
                        App.getInstance().setSessionId(cookies.get(i).getValue());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("ss", strResp);
        return strResp;
    }

    @Override
    protected void onPostExecute(String s) {
        if (callback != null)
            callback.handleData(s);
    }

    public void setCallback(HttpCallback callback) {
        this.callback = callback;
    }


}


