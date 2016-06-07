package com.szzaq.jointdefence;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.szzaq.jointdefence.fragment.F_His;
import com.szzaq.jointdefence.fragment.F_SendAlert;

/**
 * Created by DOS on 2016/5/12 0012.
 */
public class A_AlertEachother extends A_Base {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.adailycheck, R.string.record, 0);
        findViewById(R.id.scan).setVisibility(View.GONE);
        // mMessageDBManager = new MessageDBManager(this);

        toSendAlert = (TextView) findViewById(R.id.addrecord);
        toSendAlert.setText(R.string.tosendalertmsg);
        toHiss1 = (TextView) findViewById(R.id.history);

        initListeners(R.id.history, R.id.addrecord);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        tabFragment1 = new F_SendAlert();
        toSendAlert.setTextColor(Color.WHITE);
        toSendAlert.setBackgroundColor(Color.parseColor("#ff9900"));
        transaction.replace(R.id.containerofdailycheck, tabFragment1);
        transaction.commit();
    }

    private Fragment tabFragment1, tabFragment2;
    private TextView toSendAlert, toHiss1;

    @Override
    public void click(int id) {
        super.click(id);
        FragmentManager fm = getFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        switch (id) {
            case R.id.addrecord:
                if (tabFragment1 == null) {
                    tabFragment1 = new F_SendAlert();
                }
                transaction.replace(R.id.containerofdailycheck, tabFragment1);
                toSendAlert.setTextColor(Color.WHITE);
                toHiss1.setTextColor(Color.BLACK);
                toSendAlert.setBackgroundColor(Color.parseColor("#ff9900"));
                toHiss1.setBackgroundColor(Color.WHITE);
                break;

            case R.id.history:
                if (tabFragment2 == null) {
                    tabFragment2 = new F_His(1);
                }
                transaction.replace(R.id.containerofdailycheck, tabFragment2);
                toSendAlert.setTextColor(Color.BLACK);
                toHiss1.setTextColor(Color.WHITE);
                toHiss1.setBackgroundColor(Color.parseColor("#ff9900"));
                toSendAlert.setBackgroundColor(Color.WHITE);
                break;
            default:
        }
        transaction.commit();
    }

    //public MessageDBManager mMessageDBManager;

}
