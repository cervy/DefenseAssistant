package com.szzaq.jointdefence;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.model.Configuration;
import com.szzaq.jointdefence.model.UserInfo;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.utils.Resources;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends Activity implements OnClickListener, TencentLocationListener {

    private EditText username, password;
    private LinearLayout layoutLogin, layoutLoading;
    private SharedPreferences preferences;

    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        preferences = this.getSharedPreferences("fpplus", MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                checkToken();
            }

        }, 1500);//检查token获取登陆状态

        App.getInstance().addActivity(this);

        registerLocListener();//初始化腾讯地图

        initView();

    }

    @Override
    public void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    public void initView() {
        //findViewById(R.id.btnCancel).setOnClickListener(this);
        layoutLogin = (LinearLayout) findViewById(R.id.layoutLogin);
        layoutLoading = (LinearLayout) findViewById(R.id.layoutLoading);
        username = (EditText) this.findViewById(R.id.username);
        password = (EditText) this.findViewById(R.id.password);
        Button login = (Button) this.findViewById(R.id.login);
        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                this.findViewById(R.id.layoutWelcome).setVisibility(View.GONE);
                layoutLoading.setVisibility(View.VISIBLE);

                login();
                break;
            /*case R.id.btnCancel:// 取消按钮

                break;*/
            default:
        }

    }

    private void checkToken() {

        String token = preferences.getString("token", "");
        if ("".equals(token)) {
            layoutLogin.setVisibility(View.VISIBLE);//token空显示登陆部分
        } else {
            App.getInstance().setToken(token);
            getConfig();//获取（并缓存Configuration和UserInfo）并跳到主页
        }
    }

    private void login() {
        PostRequest request = new PostRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                JSONObject json;
                try {
                    json = new JSONObject(s);
                    String token = json.optString("token", "");
                    //String token = username.getText().toString() +":"+ password.getText().toString();
                    //token = Base64.encodeToString(token.getBytes(), Base64.DEFAULT);
                    if (!"".equals(token)) {
                        Editor editor = preferences.edit();
                        editor.putString("token", token);
                        editor.apply();
                        App.getInstance().setToken(token);
                    }
                    getConfig();
                } catch (JSONException e) {


                    LoginActivity.this.findViewById(R.id.layoutWelcome)
                            .setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, R.string.wronginfo,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        JSONStringer js;
        String params = "";
        try {
            js = new JSONStringer();
            js.object().key("username").value(username.getText().toString())
                    .key("password").value(password.getText().toString());
            js.endObject();
            params = js.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //String token = username.getText().toString() +":"+ password.getText().toString();
        //token = Base64.encodeToString(token.getBytes(), Base64.DEFAULT);
        App.getInstance().setToken(null);//
        //   Log.e("params", params);
        request.execute(Resources.LOGIN, params, this);//url及请求参数封装
    }

    public void getPersonInfo() {
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                JSONArray jarr;
                try {
                    JSONObject orgJson = new JSONObject(s);
                    jarr = orgJson.getJSONArray(Resources.RESULTS);
                    JSONObject json = jarr.optJSONObject(0);
                    Gson gson = new Gson();
                    UserInfo user = gson.fromJson(json.toString(),
                            new TypeToken<UserInfo>() {
                            }.getType());
                    App.getInstance().setUser(user);

                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    finish();
                } catch (JSONException ignored) {
                    ignored.printStackTrace();
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

    public void getConfig() {
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                JSONObject json;
                try {
                    json = new JSONObject(s);
                    Gson gson = new Gson();
                    Configuration config = gson.fromJson(json.toString(),
                            new TypeToken<Configuration>() {
                            }.getType());
                    App.getInstance().setConfig(config);

                    getPersonInfo();//获取账户信息

                } catch (JSONException e) {
                    layoutLogin.setVisibility(View.VISIBLE);
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
        request.execute(Resources.CONFIGURATION, params);
    }

    private void registerLocListener() {
        Context context = getApplicationContext();

        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);//包含经纬度, 位置名称, 位置地址
        request.setInterval(60000);
        locationManager = TencentLocationManager.getInstance(context);
        @SuppressWarnings("UnusedAssignment") int error = locationManager.requestLocationUpdates(request, this);
        /*String[] err = new String[]{"注册定位成功", "设备缺少使用腾讯定位SDK需要的基本条件", "配置的 key 不正确", "自动加载libtencentloc.so失败"};
        Log.e("ditu", err[error]);//  */

    }

    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason) {/*
error	错误码
reason	错误描述*/
        if (TencentLocation.ERROR_OK == error) {
            App.getInstance().setLngLat(new double[]{location.getLongitude(), location.getLatitude()});
            App.getInstance().setLocation(location.getAddress() + location.getName());
            //  Log.e("ding", location.getAddress() + location.getName());// TODO: 2016/5/15 0015
        } else {
            Toast.makeText(this, R.string.faillocated, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStatusUpdate(String arg0, int arg1, String arg2) {/*name	GPS，Wi-Fi等
status	启用或禁用
desc	状态描述*/

    }

    public TencentLocationManager locationManager;

    @Override
    protected void onDestroy() {
        //locationManager.removeUpdates(this);

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
