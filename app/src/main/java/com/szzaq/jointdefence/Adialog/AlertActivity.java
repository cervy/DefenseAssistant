package com.szzaq.jointdefence.Adialog;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.adapter.AlertAdapter;
import com.szzaq.jointdefence.model.Message;
import com.szzaq.jointdefence.net.AudioUploadUtil;
import com.szzaq.jointdefence.net.JPushHelper;
import com.szzaq.jointdefence.sqlite.MessageDBManager;
import com.szzaq.jointdefence.utils.CommonUtils;
import com.szzaq.jointdefence.utils.PathUtils;
import com.szzaq.jointdefence.utils.Resources;
import com.zxing.utils.BeepManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import cn.jpush.android.api.JPushInterface;

public class AlertActivity extends Activity implements OnClickListener {
    private ArrayList<HashMap<String, Object>> list;
    private AlertAdapter adapter;
    private AlertReceiver alertReceiver;
    private PowerManager.WakeLock wl;
    private ImageView micImage;
    private TextView btnRecordVoice;
    //private Drawable[] micImages;
    //private Handler micImageHandler;
    public String audioFileName;
    private MediaRecorder mRecorder;
    private boolean isRecording = false;
    private BeepManager alertManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        mgr = new MessageDBManager(this);

        alertManager = new BeepManager(this, R.raw.bell);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        initView();
        regReceiver();
        updateMsg(getIntent());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:

                break;

        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        alertManager.close();
        /*if (micImageHandler != null)
            micImageHandler.removeCallbacksAndMessages(null);*/
        unregisterReceiver(alertReceiver);
        super.onDestroy();
        mgr.closeDB();
    }

    private void initView() {
        recordstate = ((TextView) findViewById(R.id.recoedstate));

        ListView listViewAlert = (ListView) findViewById(R.id.listViewAlert);
        list = new ArrayList<HashMap<String, Object>>();
        adapter = new AlertAdapter(list, AlertActivity.this);
        listViewAlert.setAdapter(adapter);
        alertReceiver = new AlertReceiver();
        micImage = (ImageView) findViewById(R.id.mic_image);
        btnRecordVoice = (TextView) findViewById(R.id.btnRecordVoice);
        btnRecordVoice.setOnTouchListener(new PressToSpeakListen());
       /* micImages = new Drawable[]{
                getResources().getDrawable(R.drawable.record_animate_01),
                getResources().getDrawable(R.drawable.record_animate_02),
                getResources().getDrawable(R.drawable.record_animate_03),
                getResources().getDrawable(R.drawable.record_animate_04),
                getResources().getDrawable(R.drawable.record_animate_05),
                getResources().getDrawable(R.drawable.record_animate_06),
                getResources().getDrawable(R.drawable.record_animate_07),
                getResources().getDrawable(R.drawable.record_animate_08),
                getResources().getDrawable(R.drawable.record_animate_09),
                getResources().getDrawable(R.drawable.record_animate_10),
                getResources().getDrawable(R.drawable.record_animate_11),
                getResources().getDrawable(R.drawable.record_animate_12),
                getResources().getDrawable(R.drawable.record_animate_13),
                getResources().getDrawable(R.drawable.record_animate_14),};*/
        /*micImageHandler = new Handler() {// 录音 动画
            @Override
            public void handleMessage(android.os.Message msg) {
                micImage.setImageDrawable(micImages[msg.what]);
            }
        };*/
        wakeUp(AlertActivity.this);
        initAudioFileName();
    }

    private void regReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ALERT_MSG"); // 为BroadcastReceiver指定action，即要监听的消息名字。
        registerReceiver(alertReceiver, intentFilter); // 注册监听
    }

    private class AlertReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("ALERT_MSG".equals(action)) {
                updateMsg(intent);
            }
            wakeUp(AlertActivity.this);
        }

    }

    public void updateMsg(Intent intent) {
        Bundle bundle = intent.getExtras();
        String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String contentType = bundle
                .getString(JPushInterface.EXTRA_CONTENT_TYPE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        HashMap<String, Object> map = new HashMap<String, Object>();
        //map.put("user", "");
        map.put("msg", msg);
        map.put("contentType", contentType);
        map.put("extras", extras);
        list.add(map);//消息数据


        adapter.notifyDataSetChanged();
        if ("text".equals(contentType) || "alert".equals(contentType)) {
            alertManager.playBeepSoundAndVibrate();
        }

        try {
            JSONObject extrasJson = new JSONObject(extras);
            Message person1 = new Message(extrasJson.optString(TIME, ""), extrasJson.optString(USERNAME), msg, extrasJson.optString(PORTRAIT, null), contentType, audioFileName, extrasJson.optString(TYPE));
            mgr.add(person1);//sqlite持久化消息
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static final String TIME = "time", USERNAME = "userName", PORTRAIT = "portrait", URL = "url", TYPE = "type";
    private MessageDBManager mgr;

    public void wakeUp(Context context) {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        // 点亮屏幕
        wl.acquire();
        wl.release();
    }

    private TextView recordstate;

    /**
     * 按住说话listener
     */
    class PressToSpeakListen implements View.OnTouchListener {//录音

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!CommonUtils.isExitsSdcard()) {
                        String st4 = "抱歉，您没有内存卡，无法使用语音功能。";
                        Toast.makeText(getApplicationContext(), st4, Toast.LENGTH_SHORT)
                                .show();
                        return true;
                    }
                    try {
                        v.setPressed(true);
                        wl.acquire();
                        recordstate.setText("向上滑动取消");
                        micImage.setVisibility(View.VISIBLE);
                        recordstate.setVisibility(View.VISIBLE);
                        btnRecordVoice.setBackgroundColor(Color
                                .parseColor("#666666"));

                        startRecord();
                    } catch (Exception e) {

                        e.printStackTrace();
                        v.setPressed(false);
                        if (wl.isHeld())
                            wl.release();

                        // recordingContainer.setVisibility(View.INVISIBLE);
                        Toast.makeText(AlertActivity.this, "语音发送失败",
                                Toast.LENGTH_SHORT).show();
                    }

                    return true;
                case MotionEvent.ACTION_MOVE: {
                    if (event.getY() < 0) {
                        recordstate.setText("松开手取消");
                    }
                    return true;
                }
                case MotionEvent.ACTION_UP:
                    v.setPressed(false);
                    recordstate.setVisibility(View.GONE);
                    micImage.setVisibility(View.GONE);
                    btnRecordVoice
                            .setBackgroundResource(R.drawable.button_send_default);
                    if (wl.isHeld())
                        wl.release();
                    if (event.getY() >= 0) {
                        stopRecord();
                        uploadVoice();
                    }
                    return true;
                default:
                    // recordingContainer.setVisibility(View.INVISIBLE);

                    return false;
            }
        }
    }

    private void initAudioFileName() {
        audioFileName = PathUtils.getAudioPath(this) + "/";
        String fn = String.valueOf((new Date()).getTime());
        fn += String.valueOf((new Random()).nextInt(sixNines));
        audioFileName += fn + ".3gp";
    }

    public static final int sixNines = 999999;

    private void startRecord() {
        if (isRecording)
            return;
        isRecording = true;
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(audioFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("MediaRecord", "prepare() failed");
        }
        mRecorder.start();
    }

    // 停止录音

    private void stopRecord() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        isRecording = false;
    }

    // 上传录音至服务器
    private void uploadVoice() {
        AudioUploadUtil request = new AudioUploadUtil();
        request.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {
                JSONObject json;

                try {
                    json = new JSONObject(s);
                    // TODO: 2016/6/6 0006  if (!json.getString("file").isEmpty())
                    sendAudio(json.getString("file"));//再发送至极光
                    //Log.e("yuyinfuwuqi", json.getString("file"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
        try {
            File audioFile = new File(audioFileName);
            //mgr.add(audioFileName);

            request.execute(Resources.AUDIO, audioFile, "audio",
                    AlertActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendAudio(String url) {
        JPushHelper helper = new JPushHelper();
        /*helper.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {

                try {
                    JSONObject json = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });*/
        JSONStringer js = new JSONStringer();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        try {
            js.object()
                    .key("platform")
                    .array()
                    .value("android")
                    .endArray()
                    .key("audience")
                    .value("all")
                    .key("message")
                    .object()
                    .key("msg_content")
                    .value("")
                    .key("content_type")
                    .value("audio")
                    .key("title")
                    .value("msg")
                    .key("extras")
                    .object()
                    .key("url")
                    .value(url)
                    .key("userName")
                    .value(App.getInstance().getUser().getProfile()
                            .getNickname())
                    .key("portrait")
                    .value(App.getInstance().getUser().getProfile()
                            .getPortrait_url()).key("time")
                    .value(df.format(new Date())).key("lat")
                    .value(App.getInstance().getLngLat()[1]).key("lng")
                    .value(App.getInstance().getLngLat()[0]).endObject()
                    .endObject().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        helper.execute("https://api.jpush.cn/v3/push",
                js.toString());
    }

}
