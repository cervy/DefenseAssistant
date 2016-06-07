package com.szzaq.jointdefence;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.utils.Resources;

/**
 * 检查记录界面
 *
 * @author zg
 * @date 2016/02/21
 */
public class HjhConfigActivity extends Activity implements OnClickListener {
    private EditText edUser, edPwd, edJcr, edShr;
    private String objUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_hjhconfig);
        App.getInstance().addActivity(this);
        edUser = (EditText) findViewById(R.id.edUser);
        edPwd = (EditText) findViewById(R.id.edPwd);
        edJcr = (EditText) findViewById(R.id.edTbr);
        edShr = (EditText) findViewById(R.id.edShr);
        findViewById(R.id.btnConfirm).setOnClickListener(this);
        loadCheckList();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnConfirm:
                createOrUpdateConfig();
                break;
            default:
                break;
        }

    }

    private void loadCheckList() {

        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {

                try {
                    JSONObject results = new JSONObject(s);
                    JSONArray arr = results.getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject json = arr.getJSONObject(i);
                        edUser.setText(json.optString("user"));
                        edPwd.setText(json.optString("pwd"));
                        edJcr.setText(json.optString("jcr"));
                        edShr.setText(json.optString("shr"));
                        objUrl = json.optString("url");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        try {
            JSONStringer js = new JSONStringer();
            js.object()
                    .key("enterprise")
                    .value(App.getInstance().getUser().getAsStaff().split(",")[1])
                    .endObject();
            request.execute(Resources.HJH_CONFIG, js.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createOrUpdateConfig() {
        String user = edUser.getText().toString();
        String pwd = edPwd.getText().toString();
        String jcr = edJcr.getText().toString();
        String shr = edShr.getText().toString();

        try {
            JSONStringer js = new JSONStringer();
            if (objUrl == null) {
                js.object()
                        .key("enterprise")
                        .value(App.getInstance().getUser().getAsStaff()
                                .split(",")[1]).key("user").value(user)
                        .key("pwd").value(pwd).key("jcr").value(jcr).key("shr")
                        .value(shr).endObject();
            } else {
                js.object()
                        .key("_method")
                        .value("PUT")
                        .key("enterprise")
                        .value(App.getInstance().getUser().getAsStaff()
                                .split(",")[1]).key("user").value(user)
                        .key("pwd").value(pwd).key("jcr").value(jcr).key("shr")
                        .value(shr).endObject();
            }
            PostRequest request = new PostRequest();
            request.setCallback(new HttpCallback() {

                @Override
                public void handleData(String s) {
                    try {
                        JSONObject json = new JSONObject(s);
                        if (json.optString("url") != null) {
                            Toast.makeText(HjhConfigActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }

            });
            String url = objUrl == null ? Resources.HJH_CONFIG : objUrl;
            request.execute(url, js.toString(), HjhConfigActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
