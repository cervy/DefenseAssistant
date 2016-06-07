package com.szzaq.jointdefence;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 *
 *
 */
public class ChangePwdActivity extends Activity implements OnClickListener {

    private EditText tvOldPassword, tvNewPassword, tvNewPassword1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        App.getInstance().addActivity(this);
        initViews();

    }

    private void initViews() {
        findViewById(R.id.ivBack).setOnClickListener(this);

        tvOldPassword = (EditText) findViewById(R.id.tvOldPassword);
        tvNewPassword = (EditText) findViewById(R.id.tvNewPassword);
        tvNewPassword1 = (EditText) findViewById(R.id.tvNewPassword1);
        findViewById(R.id.modify).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modify:
                String oldPass = tvOldPassword.getText().toString();
                String newPass = tvNewPassword.getText().toString();
                String newPass1 = tvNewPassword1.getText().toString();
                if (oldPass.isEmpty()) {
                    Toast.makeText(this, "请输入原密码！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (newPass.length() <= 5) {
                    Toast.makeText(this, "密码长度须大于6位！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!newPass.equals(newPass1)) {
                    Toast.makeText(this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (newPass.equals(oldPass)) {
                    Toast.makeText(this, "新密码与旧密码一致！", Toast.LENGTH_SHORT).show();
                    break;
                }

                changePwd(App.getInstance().getUser().getId(), oldPass, newPass);

                break;
            case R.id.btnCancel:
                this.finish();
                break;
            case R.id.ivBack:
                finish();
                break;
        }

    }

    private void changePwd(int userId, String oldPass, String newPass) {
        PostRequest request = new PostRequest();
        request.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {
                try {
                    JSONObject json = new JSONObject(s);

                    if ("OK".equals(json.optString("result"))) {
                        Toast.makeText(ChangePwdActivity.this, "修改密码成功！", Toast.LENGTH_SHORT).show();
                        ChangePwdActivity.this.finish();
                    } else {
                        Toast.makeText(ChangePwdActivity.this, "修改密码失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        JSONStringer js = new JSONStringer();
        try {
            js.object().key("user_id").value(userId).key("old_pass")
                    .value(oldPass).key("new_pass").value(newPass).endObject();
            request.execute(Resources.CHANGE_PWD, js.toString(),
                    ChangePwdActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
