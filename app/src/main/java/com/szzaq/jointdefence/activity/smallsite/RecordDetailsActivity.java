package com.szzaq.jointdefence.activity.smallsite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.adapter.ReviewAdapter;
import com.szzaq.jointdefence.model.SmallSiteCheck;
import com.szzaq.jointdefence.model.SmallSiteRecheck;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.net.UploadUtil;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.ImageCompression;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.PathUtils;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.utils.ScrollViewUtils;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 0为复查记录界面，1为复查界面
 *
 * @author jiajiaUser
 * @date 2015/07/21
 */
public class RecordDetailsActivity extends Activity implements OnClickListener {
    private File cameraFile;

    // 当前拍照的控件
    private View currentView;
    // 图片压缩类
    private ImageCompression imageCompression;
    // 场所基本信息
    private TextView place_name, document_number, check_person, check_time,
            check_address;
    // 隐患列表
    private ListView details_itemlistview;
    // 返回键
    private ImageView back_button, place_image;
    /**
     * 界面类型，0为复查记录界面，1为复查界面
     */
    private int type;
    // 数据集合
    private ArrayList<Object> list;
    // 适配器
    private ReviewAdapter reviewAdapter;
    private SmallSiteCheck check;
    private SmallSiteRecheck recheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sanxiao_review_record_details);
        App.getInstance().addActivity(this);
        type = getIntent().getIntExtra("type", 0);
        initView();
        findViewById(R.id.locating).setOnClickListener(this);
    }

    public void initView() {
        place_image = (ImageView) this.findViewById(R.id.place_image);
        place_name = (TextView) this.findViewById(R.id.place_name);
        document_number = (TextView) this.findViewById(R.id.document_number);
        check_person = (TextView) this.findViewById(R.id.check_person);
        check_time = (TextView) this.findViewById(R.id.check_time);
        check_address = (TextView) this.findViewById(R.id.check_address);
        details_itemlistview = (ListView) this
                .findViewById(R.id.details_itemlistview);
        back_button = (ImageView) this.findViewById(R.id.back_button);
        // 返回键
        back_button.setOnClickListener(this);
        if (type == 1) {
            check = getIntent().getParcelableExtra("check");
            place_name.setText(check.getSite_name());
            document_number.setText("");
            check_person.setText(check.getInspector_names());
            check_time.setText(check.getCreated());
            check_address.setText(check.getLocation());
            place_image.setScaleType(ScaleType.CENTER_CROP);
            ImageloaderUtils.loaderimage(
                    Resources.URL_ROOT + check.getSite_img_url(), place_image);

            // 隐藏"整改后"几个字
            this.findViewById(R.id.review_rectification_after_title)
                    .setVisibility(View.GONE);
            // 设置复查按钮为展示
            this.findViewById(R.id.complete_button).setVisibility(View.VISIBLE);
            this.findViewById(R.id.complete_button).setOnClickListener(this);
            // 设置标题为复查
            ((TextView) this.findViewById(R.id.review_title))
                    .setText(getString(R.string.review));
            this.findViewById(R.id.review_title).setPadding(
                    DensityUtil.dip2px(this, 110), 0, 0, 0);
        } else if (type == 0) {

            recheck = getIntent().getParcelableExtra("recheck");
            place_name.setText(recheck.getSite_name());
            document_number.setText("");
            check_person.setText(recheck.getInspector_names());
            check_time.setText(recheck.getCreated());
            check_address.setText(recheck.getLocation());
            place_image.setScaleType(ScaleType.CENTER_CROP);
            ImageloaderUtils.loaderimage(
                    Resources.URL_ROOT + recheck.getSite_img_url(), place_image);

            // 隐藏"整改后"几个字
            this.findViewById(R.id.review_rectification_after_title)
                    .setVisibility(View.GONE);
        }
        // 获取相关数据
        getdata(type);
        reviewAdapter = new ReviewAdapter(list, this, type + 2);
        details_itemlistview.setAdapter(reviewAdapter);
        // 使用scrollview帮助类调节高度
        ScrollViewUtils.setScrollViewHeight(details_itemlistview, this, 300);
        // 使用scrollview帮助类回到顶部
        ScrollViewUtils.setToTop(details_itemlistview,
                (ScrollView) this.findViewById(R.id.scrollview));
        // 实例化图片压缩类
        imageCompression = new ImageCompression();
    }

    public List<Object> getdata(int type) {
        list = new ArrayList<Object>();
        if (type == 1) {
            SmallSiteCheck.Danger[] dangers = check.getDangers();
            Collections.addAll(list, dangers);
        } else {
            String[] dangers = recheck.getDangers();
            Collections.addAll(list, dangers);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {//定位图标
            case R.id.locating:
                break;
            case R.id.back_button:
                this.finish();
                break;
            case R.id.complete_button://完成
                PostRequest request = new PostRequest();

                try {
                    JSONStringer js = new JSONStringer();
                    js.object().key("dangers")
                            .array();
                    for (Object obj : list) {
                        SmallSiteCheck.Danger danger = (SmallSiteCheck.Danger) obj;
                        if (danger.getStatus() == 1) {
                            js.value(danger.getUrl());
                        }
                    }
                    js.endArray()
                            .key("inspectors").array()
                            .value(App.getInstance().getUser().getUrl())
                            .endArray()
                            .key("parent")
                            .value(check.getUrl())
                            .key("lng").value(App.getInstance().getLngLat()[0])
                            .key("lat").value(App.getInstance().getLngLat()[1])
                            .key("location").value(App.getInstance().getLocation())
                            .endObject();
                /*request.setCallback(new HttpCallback(){

					@Override
					public void handleData(String s) {
						Toast.makeText(getApplicationContext(), "完成请求成功", Toast.LENGTH_SHORT).show();
					}
					
				});*/

                    request.execute(Resources.SMALLSITE_RECHECK, js.toString(), RecordDetailsActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (200 == requestCode) {
            Bitmap bt = BitmapFactory.decodeFile(cameraFile.getAbsolutePath());// 图片地址
            if (bt != null) {
                bt = imageCompression.comp(bt);
                // 将拍照获取的图片名称放入集合中，在adapter的checkbox选中事件中讲图片名称获取出来
                /*
				 * list.get((Integer) currentView.getTag()).put("ok_hidden",
				 * cameraFile.getName());
				 */
                SmallSiteCheck.Danger danger = (SmallSiteCheck.Danger) list
                        .get((Integer) currentView.getTag());

                ((ImageView) currentView).setImageBitmap(bt);
                // 压缩图片
                imageCompression.bitmap2file(bt, cameraFile.getAbsolutePath());
                uploadPhoto(cameraFile, danger.getDesc() + "(整改后)");
            }

        }
    }

    private void uploadPhoto(File file, String msg) {
        UploadUtil request = new UploadUtil();
        request.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    String photo_after = json.optString("url");
                    String photo_after_url = json.optString("img");//图片
                    if (!"".equals(photo_after)) {
                        SmallSiteCheck.Danger danger = ((SmallSiteCheck.Danger) list
                                .get((Integer) currentView.getTag()));
                        danger.setPhoto_after(photo_after);
                        danger.setPhoto_after_url(photo_after_url);
                        reviewAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        request.execute(Resources.IMAGE_STORE, file, msg, RecordDetailsActivity.this);
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
}
