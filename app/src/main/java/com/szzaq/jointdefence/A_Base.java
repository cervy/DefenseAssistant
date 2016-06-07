package com.szzaq.jointdefence;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DOS on 2016/4/7.

 */
public class A_Base extends Activity implements View.OnClickListener {

    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.a_base);
        App.getInstance().addActivity(this);

        container = (FrameLayout) findViewById(R.id.container);
        initTitlebar();

    }

    private void initTitlebar() {
        title = (TextView) findViewById(R.id.title);
        findViewById(R.id.back).setOnClickListener(this);
        right = (TextView) findViewById(R.id.right);

    }

    protected final void addView(int layoutId, String title, int rightBackground) {
        setTitlebar(title, rightBackground);
        View child = LayoutInflater.from(this).inflate(layoutId, null);
        container.addView(child);
    }

    protected final void addView(int layoutId, int title, int rightBackground) {
        setTitlebar(title, rightBackground);
        View child = LayoutInflater.from(this).inflate(layoutId, null);
        container.addView(child);
    }

    protected void setTitlebar(String title, int rightBackground) {
        this.title.setText(title);
        this.right.setBackgroundResource(rightBackground);
    }

    protected void setTitlebar(int title, int rightBackground) {
        this.title.setText(title);
        this.right.setBackgroundResource(rightBackground);
    }

    protected TextView title, right;

    protected void initListeners(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    protected void initListeners(int... views) {//批量注册点击事件监听
        for (int view : views) {
            findViewById(view).setOnClickListener(this);
        }
    }


    protected void showToast(String text) {
        Toast.makeText(this, text,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;

        }
        click(id);
    }

    public void click(int id) {

    }
}
