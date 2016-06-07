package com.szzaq.jointdefence.activity.smallsite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
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
import android.widget.ImageView.ScaleType;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.MainActivity;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.fragment.CheckRecordFragment;
import com.szzaq.jointdefence.fragment.InputOpinionFragment;
import com.szzaq.jointdefence.model.SmallSite;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.net.UploadUtil;
import com.szzaq.jointdefence.utils.ImageCompression;
import com.szzaq.jointdefence.utils.PathUtils;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * 三小场所扫描详情，
 *
 * @author jiajiaUser
 * @date 2015/07/16
 */
public class SmallSiteScanningActivity extends FragmentActivity implements
        OnClickListener {
    /**
     * 代表选项卡下的下划线的imageview
     */
    private ImageView cursor = null, scannig_back, place_image;
    /**
     * 选项卡下划线长度
     */
    private static int lineWidth = 0;

    private final static int MSG_SITE_DATA_SUCCESS = 1;

    /**
     * 偏移量 （手机屏幕宽度/3-选项卡长度）/2
     */
    private static int offset = 0;

    /**
     * 选项卡总数
     */
    private static final int TAB_COUNT = 2;
    /**
     * 当前显示的选项卡位置
     */
    private int current_index = 0;
    /**
     * 选项卡按钮
     */
    private RadioButton input_opinion, main_record;
    /**
     * 选项卡tab
     */
    private CheckRecordFragment checkRecordFragment;

    // 检查界面fragment
    private InputOpinionFragment inputOpinionFragment;

    // 当前拍照的控件
    private View currentView;
    // 图片文件
    private File cameraFile;
    // 图片压缩类
    private ImageCompression imageCompression;
    public SmallSite site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_scanning_sanxiao);
        App.getInstance().addActivity(this);
        initView();
        loadSiteData();
    }

    public void initView() {
        tvOwnerName = (TextView) findViewById(R.id.tvOwnerName);
        tvSiteName = (TextView) findViewById(R.id.tvSiteName);
        tvSiteType = (TextView) findViewById(R.id.tvSiteType);
        ivSite = (ImageView) findViewById(R.id.ivSitePic);
        // 填写意见选项卡
        input_opinion = (RadioButton) this.findViewById(R.id.input_opinion);
        // 检查记录选项卡
        main_record = (RadioButton) this.findViewById(R.id.main_record);
        // 返回键
        scannig_back = (ImageView) this.findViewById(R.id.scannig_back);
        // 场所照片
        place_image = (ImageView) this.findViewById(R.id.place_image);
        input_opinion.setOnClickListener(this);
        main_record.setOnClickListener(this);
        scannig_back.setOnClickListener(this);
        this.findViewById(R.id.layoutSiteBrief).setOnClickListener(this);
        initImageView();
        // 实例化图片压缩类
        imageCompression = new ImageCompression();


    }

    private void loadSiteData() {
        if (inputOpinionFragment != null) {
            return;
        }
        Bundle bundle = this.getIntent().getExtras();
        String code = bundle.getString("result");
        if (code != null) {
            code = code.split(",")[1];
        }
        String url = getIntent().getStringExtra("deviceUrl");
        url = url == null ? Resources.SMALLSITE + code + "/" : url;
        try {

            GetRequest request = new GetRequest();
            request.setCallback(new HttpCallback() {

                @Override
                public void handleData(String s) {
                    try {
                        if (!s.isEmpty()) {

                            JSONObject json = new JSONObject(s);

                            Gson gson = new Gson();
                            site = gson.fromJson(json.toString(),
                                    new TypeToken<SmallSite>() {
                                    }.getType());
                            ArrayList<HashMap<String, Object>> tmpList = App
                                    .getInstance()
                                    .getConfig()
                                    .getDangerTypes(
                                            site.getSite_type().replace(
                                                    Resources.URL_ROOT, ""));
                        /*
                        for (HashMap<String, Object> map : tmpList) {
							inputOpinionFragment.dangerList.add(map);
						}
						inputOpinionFragment.dangerAdapter
								.notifyDataSetChanged();
						ScrollViewUtils.setScrollViewHeight(
								inputOpinionFragment.hidden_listview,
								SmallSiteScanningActivity.this, 0);
						*/
                            Message msg = new Message();
                            msg.what = MSG_SITE_DATA_SUCCESS;
                            msg.obj = tmpList;
                            handler.dispatchMessage(msg);// handle system msg

                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }

            });

            request.execute(url, "");
        } catch (Exception ignored) {

        }

    }

    /**
     * 初始化红线所在的位置
     */
    private void initImageView() {
        cursor = (ImageView) findViewById(R.id.main_red_line);
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
        transaction.replace(R.id.zhongdian_content, inputOpinionFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        Intent intent = null;
        int id = v.getId();
        switch (id) {
            case R.id.input_opinion:
                move(0);
                transaction.replace(R.id.zhongdian_content, inputOpinionFragment);
                break;
            case R.id.main_record:
                move(1);
                if (checkRecordFragment == null) {
                    checkRecordFragment = new CheckRecordFragment();
                }
                transaction.replace(R.id.zhongdian_content, checkRecordFragment);
                break;
            case R.id.scannig_back:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.layoutSiteBrief:
                intent = new Intent(this, SmallSiteDetailsActivity.class);
                intent.putExtra("site", site);
                break;
            default:
                break;
        }
        transaction.commit();

        if (intent != null) this.startActivity(intent);

    }

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

    /**
     * 照相获取图片
     */
    public void PicFromCamera(View view) {
        currentView = view;
        String photoPath = PathUtils.getPicturePath(this);
        String fileName = (new Date()).getTime() + ""
                + (new Random()).nextInt(999999) + ".jpg";
        cameraFile = new File(photoPath, fileName);
        Uri uri = Uri.fromFile(cameraFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // photoPath = file.getPath();
        startActivityForResult(intent, 200);
    }

    private TextView tvSiteName, tvOwnerName, tvSiteType;
    private ImageView ivSite;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (200 == requestCode) {
            Bitmap bt = BitmapFactory.decodeFile(cameraFile.getAbsolutePath());// 图片地址
            if (bt != null) {
                bt = imageCompression.comp(bt);
                // 将拍照获取的图片名称放入集合中，在adapter的checkbox选中事件中讲图片名称获取出来
                InputOpinionFragment.list_grid.get(
                        (Integer) currentView.getTag()).put("ok_hidden",
                        cameraFile.getName());
                InputOpinionFragment.list_grid.get(
                        (Integer) currentView.getTag()).put("bitmap", bt);
                ((ImageView) currentView).setImageBitmap(bt);
                // 压缩图片
                String fileName = cameraFile.getAbsolutePath();
                imageCompression.bitmap2file(bt, fileName);
                // 上传图片
                File uploadFile = new File(fileName);
                UploadUtil uploader = new UploadUtil();
                uploader.setCallback(new HttpCallback() {
                    @Override
                    public void handleData(String s) {
                        try {
                            JSONObject imageStore = new JSONObject(s);
                            InputOpinionFragment.list_grid.get(
                                    (Integer) currentView.getTag()).put(
                                    "photo_brefore", imageStore.getString("url"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });
                uploader.execute(Resources.IMAGE_STORE,
                        uploadFile,
                        InputOpinionFragment.list_grid.get(
                                (Integer) currentView.getTag()).get(
                                "desc"),
                        SmallSiteScanningActivity.this);
            }

        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SITE_DATA_SUCCESS:
                /*TextView tvSiteName = (TextView) findViewById(R.id.tvSiteName);
				TextView tvOwnerName = (TextView) findViewById(R.id.tvOwnerName);
				TextView tvSiteType = (TextView) findViewById(R.id.tvSiteType);
				ImageView ivSite = (ImageView) findViewById(R.id.ivSitePic);*/
                    ivSite.setScaleType(ScaleType.CENTER_CROP);
                    tvSiteName.setText(site.getName());
                    tvOwnerName.setText(site.getOwner());
                    tvSiteType.setText(site.getSite_type_name());
                    ImageLoader.getInstance().displayImage(
                            Resources.URL_ROOT + site.getImgUrl(), ivSite);

                    inputOpinionFragment = new InputOpinionFragment();
                    Bundle b = new Bundle();
                    b.putSerializable("dangerTypes", (ArrayList<HashMap<String, Object>>) msg.obj);
                    inputOpinionFragment.setArguments(b);
                    setDefaultFragment();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
