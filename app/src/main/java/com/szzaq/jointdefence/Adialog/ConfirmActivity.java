package com.szzaq.jointdefence.Adialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.szzaq.jointdefence.R;


public class ConfirmActivity extends Activity implements OnClickListener {
    public static final int OK = 1;
    //public static final int CANCEL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        /*Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent.setText(content);*/
        findViewById(R.id.btnConfirm).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                this.setResult(OK);//发送求援
                break;
            case R.id.btnCancel:
                //this.setResult(CANCEL);
                break;
        }
        finish();


    }
}
