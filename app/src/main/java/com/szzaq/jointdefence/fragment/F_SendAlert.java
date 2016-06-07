package com.szzaq.jointdefence.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.net.JPushHelper;

import org.json.JSONException;
import org.json.JSONStringer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DOS on 2016/5/12 0012.
 */
public class F_SendAlert extends android.app.Fragment implements View.OnClickListener {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_sendalert, container,
                false);
        view.findViewById(R.id.nearfire).setOnClickListener(this);
        view.findViewById(R.id.thisunitfire).setOnClickListener(this);
        view.findViewById(R.id.blowup).setOnClickListener(this);
        view.findViewById(R.id.collapse).setOnClickListener(this);
        view.findViewById(R.id.leak).setOnClickListener(this);
        return view;
    }

    public static final String alertType = "aletType";
    public static final String nearFire = "附近火灾";
    public static final String thisUnitFire = "本单位火灾";
    public static final String leak = "泄漏";
    public static final String collapse = "垮塌";
    public static final String blowup = "爆炸";

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.nearfire:
                new AlertDialog.Builder(getActivity()).setTitle("发送预警")

                        .setMessage(nearFire)

                        .setNegativeButton(" 取 消 ", new DialogInterface.OnClickListener() {


                            @Override

                            public void onClick(DialogInterface dialog, int which) {//响应事件

                            }

                        }).setPositiveButton(" 确 定 ", new DialogInterface.OnClickListener() {//添加确定按钮


                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        sendSOS(nearFire, "] 附近发生火灾警情，请求前往增援！", "fire");

                    }

                }).show();
                break;
            case R.id.thisunitfire:
                new AlertDialog.Builder(getActivity()).setTitle("发送预警")

                        .setMessage(thisUnitFire)

                        .setNegativeButton(" 取 消 ", new DialogInterface.OnClickListener() {


                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).setPositiveButton(" 确 定 ", new DialogInterface.OnClickListener() {//添加确定按钮


                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        sendSOS(thisUnitFire, "] 发生火灾警情，请求前往增援！", "fire");


                    }

                }).show();

                break;
            case R.id.leak:
                new AlertDialog.Builder(getActivity()).setTitle("发送预警")

                        .setMessage(leak)

                        .setNegativeButton(" 取 消 ", new DialogInterface.OnClickListener() {


                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).setPositiveButton(" 确 定 ", new DialogInterface.OnClickListener() {//添加确定按钮


                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        sendSOS(leak, "] 附近发生泄漏警情，请密切注意！", "leak");


                    }

                }).show();

                break;
            case R.id.collapse:
                new AlertDialog.Builder(getActivity()).setTitle("发送预警")

                        .setMessage(collapse)

                        .setNegativeButton(" 取 消 ", new DialogInterface.OnClickListener() {


                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).setPositiveButton(" 确 定 ", new DialogInterface.OnClickListener() {//添加确定按钮


                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        sendSOS(collapse, "] 附近发生垮塌警情，请密切注意！", "collapse");


                    }

                }).show();

                break;
            case R.id.blowup:
                new AlertDialog.Builder(getActivity()).setTitle("发送预警")

                        .setMessage(blowup)

                        .setNegativeButton(" 取 消 ", new DialogInterface.OnClickListener() {


                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).setPositiveButton(" 确 定 ", new DialogInterface.OnClickListener() {//添加确定按钮


                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        sendSOS(blowup, "] 附近发生爆炸警情，请密切注意！", "bomb");


                    }

                }).show();
                break;

        }
    }

    private void sendSOS(String type, String msg, String typeImg) {
        JPushHelper helper = new JPushHelper();
        helper.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {
/*
                try {
                    @SuppressWarnings("unused")
                    JSONObject json = new JSONObject(s);
                    msgId = json.optString("msg_id");// TODO:   判断成功否
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                Toast.makeText(mActivity, "发送成功", Toast.LENGTH_SHORT).show();
            }

        });
        JSONStringer js = new JSONStringer();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        try {


            js.object()

                    .key("platform").array().value("android").value("ios").endArray()
                    .key("audience").value("all")
                    .key("notification").object().key("ios").object().key("extras").object().key("typeImg").value(typeImg).key("imageUrl").value(App.getInstance().getUser() != null && null != App.getInstance().getUser().getProfile() ? App.getInstance().getUser().getProfile().getPortrait_url() : "").key("type").value("[" + App.getInstance().getLocation() + msg).key("name").value(App.getInstance().getUser() != null && null != App.getInstance().getUser().getProfile() ? App.getInstance().getUser().getProfile().getNickname() : "").key("time").value(df.format(new Date())).endObject().key("badge").value("+1").key("alert").value("附近发生警情，请速援！").endObject().endObject()
                    .key("message").object().key("msg_content").value("[" + App.getInstance().getLocation() + msg)
                    .key("content_type").value("text")
                    .key("title").value("msg")
                    .key("extras").object()
                    .key("userName").value(App.getInstance().getUser() != null && null != App.getInstance().getUser().getProfile() ? App.getInstance().getUser().getProfile().getNickname() : "")
                    .key("portrait").value(App.getInstance().getUser() != null && null != App.getInstance().getUser().getProfile() ? App.getInstance().getUser().getProfile().getPortrait_url() : "")
                    .key("time").value(df.format(new Date())).key("type").value(type)
                    .key("lat").value(App.getInstance().getLngLat()[1])
                    .key("lng").value(App.getInstance().getLngLat()[0])
                    .endObject()
                    .endObject().key("options").object().key("apns_production").value(false).endObject()
                    .endObject();
            //Log.e("dingweile", App.getInstance().getLocation());//

        } catch (JSONException e) {
            e.printStackTrace();
        }
        helper.execute("https://api.jpush.cn/v3/push", js.toString());
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        mActivity = activity;
    }

    Activity mActivity;

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;

    }

   // private String msgId;
}
