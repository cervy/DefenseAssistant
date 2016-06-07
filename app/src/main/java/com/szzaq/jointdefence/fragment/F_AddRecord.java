package com.szzaq.jointdefence.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.Calendar;

/**
 * Created by DOS on 2016/5/12 0012.
 */
@SuppressLint("ValidFragment")

public class F_AddRecord extends android.app.Fragment {
    private View view;
    private Button checkDate;
    private EditText checkerr, checkTimes, profile, remark, checker, manager;
    private Calendar calendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String mHousehold;
    private CommunicateWithA mCommunicateWithA;

    public interface CommunicateWithA {
        void change();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCommunicateWithA = (CommunicateWithA) activity;
    }

    public F_AddRecord(String o) {
        mHousehold = o;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_addrecord, container,
                false);
        calendar = Calendar.getInstance();
        checkDate = (Button) view.findViewById(R.id.checkdate);
        checkerr = (EditText) view.findViewById(R.id.checkerr);
        checkTimes = (EditText) view.findViewById(R.id.checktimes);
        profile = (EditText) view.findViewById(R.id.profile);
        remark = (EditText) view.findViewById(R.id.remark);
        checker = (EditText) view.findViewById(R.id.checker);
        manager = (EditText) view.findViewById(R.id.manager);
        checkDate.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                                 @Override
                                                 public void onDateSet(DatePicker view, int year, int month, int day) {
                                                     mYear = year;
                                                     mMonth = month;
                                                     mDay = day;
                                                     checkDate.setText(new StringBuilder().append(mYear).append("-").append((mMonth + 1) < 10 ? (mMonth + 1) : (mMonth + 1)).append("-").append((mDay < 10) ? mDay : mDay));
                                                 }
                                             }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                                         }
                                     }

        );
        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if (checkDate.getText().toString().isEmpty() || checkerr.getText().toString().isEmpty() || checker.getText().toString().isEmpty() || manager.getText().toString().isEmpty() || checkTimes.getText().toString().isEmpty() || profile.getText().toString().isEmpty() || remark.getText().toString().isEmpty()) {
                                                                    Toast.makeText(getActivity(), "请将表单填写完整！", Toast.LENGTH_SHORT).show();
                                                                    return;
                                                                }
                                                                PostRequest request = new PostRequest();
                                                                request.setCallback(new HttpCallback() {
                                                                    @Override
                                                                    public void handleData(String s) {
                                                                        Toast.makeText(getActivity(), "数据保存成功", Toast.LENGTH_SHORT).show();
                                                                        //F_AddRecord.this.getActivity().findViewById(R.id.history).performClick();
                                                                        mCommunicateWithA.change();

                                                                    }
                                                                });


                                                                String params = "";
                                                                JSONStringer js = new JSONStringer();
                                                                try

                                                                {
                                                                    js.object().key("date").value(checkDate.getText().toString())
                                                                            .key("patrolmen").value(checkerr.getText().toString()).key("reviewer").value(checker.getText().toString()).key("supervisor").value(manager.getText().toString()).key("patrol_times").value(Integer.valueOf(checkTimes.getText().toString())).key("report").value(profile.getText().toString()).key("remark").value(remark.getText().toString()).key("account").value(mHousehold).key("updated").value(1)

                                                                            .endObject();
                                                                    params = js.toString();
                                                                } catch (
                                                                        JSONException e
                                                                        )

                                                                {
                                                                    e.printStackTrace();
                                                                }

                                                                request.execute(Resources.DAILYCHECK, params, this);

                                                            }
                                                        }

        );
        return view;
    }
}
