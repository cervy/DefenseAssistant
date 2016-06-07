package com.szzaq.jointdefence.activity.ke;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.Adialog.AwardActivity;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.CaptureActivity;
import com.szzaq.jointdefence.MainActivity;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.fragment.CheckFragment;
import com.szzaq.jointdefence.fragment.EquipHistoryRecordFragment;
import com.szzaq.jointdefence.model.Device;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 重点单位扫描详情，
 */
public class ZhongdianScanningActivity extends FragmentActivity implements
        OnClickListener {
    /**
     * 代表选项卡下的下划线的imageview
     */
    private ImageView cursor = null, scannig_back;
    /**
     * 选项卡下划线长度
     */
    private static int lineWidth = 0;

    /**
     * 偏移量 （手机屏幕宽度/3-选项卡长度）/2
     */
    private static int offset = 0;

    /**
     * 选项卡总数
     */
    private static final int TAB_COUNT = 4;
    /**
     * 当前显示的选项卡位置
     */
    private int current_index = 0;
    /**
     * 选项卡按钮
     */
    private final static int MSG_DEVICE_DATA_SUCCESS = 1;

    //private final static int MSG_DEVICE_DATA_FAIL = 0;

    private RadioButton write_comments, main_record, inspection_records,
            maintenance_records;
    /**
     * 选项卡tab
     */
    private EquipHistoryRecordFragment tabFragment1, tabFragment2, tabFragment3;
    // 检查界面fragment
    private CheckFragment checkFragment;
    // 查看设备详情
    private LinearLayout scanning_details;
    // 扫码
    private TextView scanning_captrue, tvDeviceLoc, tvDeviceName;
    private Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView( R.layout.activity_scanning_zhongdian);
        App.getInstance().addActivity(this);
        initView();
        loadDeviceData();
    }

    public void initView() {
        write_comments = (RadioButton) this.findViewById(R.id.write_comments);
        main_record = (RadioButton) this.findViewById( R.id.main_record);
        inspection_records = (RadioButton) this
                .findViewById( R.id.inspection_records);
        maintenance_records = (RadioButton) this
                .findViewById( R.id.maintenance_records);
        scanning_details = (LinearLayout) this
                .findViewById( R.id.scanning_details);
        scanning_captrue = (TextView) this.findViewById( R.id.scanning_captrue);
        scannig_back = (ImageView) this.findViewById( R.id.scannig_back);
        write_comments.setOnClickListener(this);
        main_record.setOnClickListener(this);
        inspection_records.setOnClickListener(this);
        maintenance_records.setOnClickListener(this);
        scanning_details.setOnClickListener(this);
        scanning_captrue.setOnClickListener(this);
        scannig_back.setOnClickListener(this);
        tvDeviceLoc = (TextView) findViewById( R.id.tvDeviceLoc);
        tvDeviceName = (TextView) findViewById( R.id.tvDeviceName);

        initImageView();
        // setDefaultFragment();
    }

    private void loadDeviceData() {
        Bundle bundle = this.getIntent().getExtras();
        String code = bundle.getString("result");
        if (code != null) {
            if (code.length() < 2) {
                Toast.makeText(ZhongdianScanningActivity.this, "您没有权限访问该数据！",
                        Toast.LENGTH_LONG).show();
                return;
            }
            code = code.split(",")[1];
        }
        String url = getIntent().getStringExtra("deviceUrl");
        url = url == null ? Resources.KEYENTERPRISE_DEVICE + code + "/" : url;
        try {

            GetRequest request = new GetRequest();
            request.setCallback(new HttpCallback() {
                @Override
                public void handleData(String s) {
                    setDefaultFragment();
                    try {
                        JSONObject json = new JSONObject(s);
                        Gson gson = new Gson();
                        device = gson.fromJson(json.toString(),
                                new TypeToken<Device>() {
                                }.getType());
                        Message msg = new Message();
                        msg.what = ZhongdianScanningActivity.MSG_DEVICE_DATA_SUCCESS;
                        handler.dispatchMessage(msg);
                        int award = json.optInt("coin", 0);
                        if (award > 0)
                            showAward(award, device != null ? device.getId() : "");
                    } catch (JSONException e) {
                        Toast.makeText(ZhongdianScanningActivity.this,
                                "您没有权限访问该数据！", Toast.LENGTH_LONG).show();
                        ZhongdianScanningActivity.this.finish();
                        e.printStackTrace();
                    }

                }

            });

            request.execute(url, "");
        } catch (Exception ignored) {

        }

    }

    private void showAward(int count, String device_id) {

        Intent awardIntent = new Intent(ZhongdianScanningActivity.this, AwardActivity.class);
        awardIntent.putExtra("count", count);
        awardIntent.putExtra("device_id", device_id);
        startActivity(awardIntent);
    }

    /**
     * 初始化红线所在的位置
     */
    private void initImageView() {
        cursor = (ImageView) findViewById( R.id.main_red_line);
        // 获取图片宽度
        lineWidth = BitmapFactory.decodeResource(getResources(),
                 R.drawable.line).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 获取屏幕宽度
        int screenWidth = dm.widthPixels;
        Matrix matrix = new Matrix();
        offset = (int) ((screenWidth / (float) TAB_COUNT - lineWidth));
        matrix.postTranslate(offset, 0);
        // 设置初始位置
        cursor.setImageMatrix(matrix);
    }

    /**
     * 红色下划线移动效果
     *
     * @param index
     */
    public void move(int index) {
        int one = offset + lineWidth;// 页卡1 -> 页卡2 偏移量
        Animation animation = new TranslateAnimation(one * current_index, one
                * index, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(300);
        cursor.startAnimation(animation);
        current_index = index;
    }

    /**
     * 设置默认的fragment为检查界面
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        checkFragment = new CheckFragment();
        transaction.replace( R.id.zhongdian_content, checkFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        Bundle bundle;
        Intent intent;
        int id = v.getId();
        switch (id) {
            case  R.id.write_comments:
                move(0);
                if (checkFragment == null) {
                    checkFragment = new CheckFragment();
                }
                transaction.replace( R.id.zhongdian_content, checkFragment);
                break;
            case  R.id.main_record:
                move(1);
                if (tabFragment1 == null) {
                    tabFragment1 = new EquipHistoryRecordFragment();
                    bundle = new Bundle();
                    bundle.putString("checkType", 1 + "");
                    bundle.putString("deviceId", device != null ? device.getId() : "");
                    bundle.putParcelable("device", device);
                    tabFragment1.setArguments(bundle);
                }
                transaction.replace( R.id.zhongdian_content, tabFragment1);
                break;
            case  R.id.inspection_records:
                move(2);
                if (tabFragment2 == null) {
                    tabFragment2 = new EquipHistoryRecordFragment();
                    bundle = new Bundle();
                    bundle.putString("checkType", 3 + "");
                    bundle.putParcelable("device", device);
                    bundle.putString("deviceId", device != null ? device.getId() : "");
                    tabFragment2.setArguments(bundle);
                }

                transaction.replace( R.id.zhongdian_content, tabFragment2);
                break;
            case  R.id.maintenance_records:
                move(3);
                if (tabFragment3 == null) {
                    tabFragment3 = new EquipHistoryRecordFragment();
                    bundle = new Bundle();
                    bundle.putString("checkType", 4 + "");
                    bundle.putParcelable("device", device);
                    bundle.putString("deviceId", device != null ? device.getId() : "");
                    tabFragment3.setArguments(bundle);
                }
                transaction.replace( R.id.zhongdian_content, tabFragment3);
                break;
            case  R.id.scanning_captrue:
                intent = new Intent(this, CaptureActivity.class);
                this.startActivity(intent);
                break;
            case  R.id.scannig_back:
            /*
             * intent = new Intent(this,MainActivity.class);
			 * this.startActivity(intent);
			 */
                this.finish();
                break;
            case  R.id.scanning_details:
                intent = new Intent(this, EquipmentDetailsActivity.class);
                intent.putExtra("device", device);
                this.startActivity(intent);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DEVICE_DATA_SUCCESS:
                    tvDeviceLoc.setText(device != null ? device.getLocation() : "");
                    tvDeviceName.setText(device != null ? device.getName() : "");
                    break;
            }
        }
    };

    /**
     * 处理返回键不是返回扫描页
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    public Device getDevice() {
        return this.device;
    }

    @Override
    protected void onDestroy() {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
