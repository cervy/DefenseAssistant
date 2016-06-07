package com.szzaq.jointdefence.net;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.utils.DialogUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class AudioUploadUtil extends AsyncTask<Object, Integer, String> {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000;
    private static final String CHARSET = "utf-8";
    private HttpCallback callback;
    private Context context;

    @Override
    protected String doInBackground(Object... objects) {
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString();
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";
        File file = (File) objects[1];
        String token = App.getInstance().getToken();
        String title = objects.length > 2 ? (String) objects[2] : "";
        if (objects.length >= 4 && (objects[3] instanceof Context)) {
            context = (Context) objects[3];
            DialogUtil.showDialog(context);
        }
        try {
            URL url = new URL((String) objects[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", CHARSET);
            conn.addRequestProperty("Accept", "application/json");
            if (token != null) {
                String basicAuth = "Token " + token;
                conn.setRequestProperty("Authorization", basicAuth);
            }
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            if (file != null) {
                /**
                 *
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                String sb = PREFIX +
                        BOUNDARY +
                        LINE_END +
                        "Content-Disposition: form-data; name=\"title\"" + LINE_END + LINE_END + title + LINE_END +
                        PREFIX +
                        BOUNDARY +
                        LINE_END +
                        "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINE_END +
                        "Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END +
                        LINE_END;

                dos.write(sb.getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 */
                int res = conn.getResponseCode();
                if (res == 200 || res == 201) {
                    Log.e(TAG, "request success");
                    InputStream input = conn.getInputStream();
                    StringBuilder sb1 = new StringBuilder();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                    Log.e(TAG, "result : " + result);
                } else {
                    Log.e(TAG, "request error");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        DialogUtil.closeDialog();
        if (callback != null)

            callback.handleData(s);
        Log.e("AudioUpload", s);// TODO: 2016/6/6 0006  
    }

    public void setCallback(HttpCallback callback) {
        this.callback = callback;
    }

}
