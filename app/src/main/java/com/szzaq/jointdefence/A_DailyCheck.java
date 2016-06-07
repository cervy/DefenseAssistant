package com.szzaq.jointdefence;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.szzaq.jointdefence.fragment.F_AddRecord;
import com.szzaq.jointdefence.fragment.F_His;
import com.szzaq.jointdefence.fragment.F_Scan;

/**
 * Created by DOS on 2016/5/11 0011.
 */
public class A_DailyCheck extends A_Base implements F_AddRecord.CommunicateWithA {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.adailycheck, R.string.messages, 0);
        right.setOnClickListener(this);
        addRecord = (TextView) findViewById(R.id.addrecord);
        scan = (TextView) findViewById(R.id.scan);
        history = (TextView) findViewById(R.id.history);
        initListeners(addRecord, scan, history);
        setDefaultFragment();
    }

    public void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        tabFragment1 = new F_AddRecord(getIntent().getStringExtra("enterprise"));
        addRecord.setTextColor(Color.WHITE);
        addRecord.setBackgroundColor(Color.parseColor("#ff9900"));
        transaction.replace(R.id.containerofdailycheck, tabFragment1);
        transaction.commit();
    }

    public TextView addRecord, scan, history;
    private Fragment tabFragment1, tabFragment2, tabFragment3;

    @Override
    public void click(int id) {
        super.click(id);
        FragmentManager fm = getFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        switch (id) {
            case R.id.addrecord:
                if (tabFragment1 == null) {
                    tabFragment1 = new F_AddRecord(getIntent().getStringExtra("enterprise"));
                }
                transaction.replace(R.id.containerofdailycheck, tabFragment1);
                addRecord.setTextColor(Color.WHITE);
                history.setTextColor(Color.BLACK);

                scan.setTextColor(Color.BLACK);
                addRecord.setBackgroundColor(Color.parseColor("#ff9900"));
                history.setBackgroundColor(Color.WHITE);
                scan.setBackgroundColor(Color.WHITE);
                break;
            case R.id.scan:
                if (tabFragment2 == null) {
                    tabFragment2 = new F_Scan();
                }
                transaction.replace(R.id.containerofdailycheck, tabFragment2);
                addRecord.setTextColor(Color.BLACK);
                history.setTextColor(Color.BLACK);
                scan.setTextColor(Color.WHITE);
                scan.setBackgroundColor(Color.parseColor("#ff9900"));
                history.setBackgroundColor(Color.WHITE);
                addRecord.setBackgroundColor(Color.WHITE);


                break;
            case R.id.history:
                  if (tabFragment3 == null) {
                tabFragment3 = new F_His(0);
                 }
                transaction.replace(R.id.containerofdailycheck, tabFragment3);
                addRecord.setTextColor(Color.BLACK);

                history.setTextColor(Color.WHITE);
                scan.setTextColor(Color.BLACK);
                history.setBackgroundColor(Color.parseColor("#ff9900"));
                addRecord.setBackgroundColor(Color.WHITE);
                scan.setBackgroundColor(Color.WHITE);

                break;
            default:
        }
        transaction.commit();

    }


    @Override
    public void change() {
        FragmentManager fm = getFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();

        tabFragment3 = new F_His(0);

        transaction.replace(R.id.containerofdailycheck, tabFragment3);
        addRecord.setTextColor(Color.BLACK);

        history.setTextColor(Color.WHITE);
        scan.setTextColor(Color.BLACK);
        history.setBackgroundColor(Color.parseColor("#ff9900"));
        addRecord.setBackgroundColor(Color.WHITE);
        scan.setBackgroundColor(Color.WHITE);
        transaction.commit();

    }
}
