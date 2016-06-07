package com.szzaq.jointdefence.fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.AgentActivity;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.activity.ke.ZhongdianScanningActivity;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.net.UploadUtil;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.ImageCompression;
import com.szzaq.jointdefence.utils.PathUtils;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * 重点单位扫描检查界面 2015/07/16
 *
 * @author jiajiaUser
 */
public class CheckFragment extends Fragment implements OnClickListener {
    // 选择时间，选择协办人
    private TextView tvExpiration, tvAssistant;
    private EditText etAdvice;
    private View view;
    // 回传值是否有选中
    private static int SUCCESS = 2;
    private Calendar calendar; // 通过Calendar获取系统时间
    private int mYear;
    private int mMonth;
    private int mDay;
    private ImageView[] imageViews;
    private int currentIndex = 0;
    // 设备状态选项
    private RadioButton[] radiobuttons;
    // 图片压缩类
    private ImageCompression imageCompression;
    // 照片文件
    private File cameraFile;
    private String photoFileName = null;
    private String inspectMethod = "", inspectType = "", inspectTimeType = "",
            deviceStatus = "1", fixType = "", selectedAssistant = "";
    private JSONArray images = new JSONArray();
    private Intent fromIntent;
    private LinearLayout layoutInspectMethod, layoutFixType, layoutExpiration,
            layoutAssistant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_zhongdian_check, container,
                false);
        initView();
        fromIntent = getActivity().getIntent();
        return view;
    }

    @SuppressLint("NewApi")
    public void initView() {
        // 获取当前时间
        calendar = Calendar.getInstance();
        // 图片压缩类
        imageCompression = new ImageCompression();
        tvExpiration = (TextView) view.findViewById(R.id.tvExpiration);
        tvAssistant = (TextView) view.findViewById(R.id.tvAssistant);
        etAdvice = (EditText) view.findViewById(R.id.etAdvice);

        layoutInspectMethod = (LinearLayout) view
                .findViewById(R.id.layoutInspectMethod);
        layoutFixType = (LinearLayout) view.findViewById(R.id.layoutFixType);
        layoutExpiration = (LinearLayout) view
                .findViewById(R.id.layoutExpiration);
        layoutAssistant = (LinearLayout) view
                .findViewById(R.id.layoutAssistant);
        if ("[ADMIN][LEADER][SUPERVISOR]".contains(App.getInstance().getUser()
                .getRole()[0])) {
            layoutInspectMethod.setVisibility(View.VISIBLE);
            layoutAssistant.setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.btnSubmit).setOnClickListener(this);
        imageViews = new ImageView[]{
                (ImageView) view.findViewById(R.id.imageView1),
                (ImageView) view.findViewById(R.id.imageView2),
                (ImageView) view.findViewById(R.id.imageView3)};

        for (ImageView imageView : imageViews) {
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageView.setOnClickListener(this);
        }

        tvExpiration.setOnClickListener(this);
        tvAssistant.setOnClickListener(this);

        int dp10 = DensityUtil.dip2px(getActivity(), 10);
        int dp100 = DensityUtil.dip2px(getActivity(), 100);
        int dp34 = DensityUtil.dip2px(getActivity(), 34);

        RadioGroup rgInspectMethod = (RadioGroup) view
                .findViewById(R.id.rgInspectMethod);
        for (HashMap<String, String> map : App.getInstance().getConfig().INSPECT_METHOD_CHOICES) {
            final RadioButton radioButton = new RadioButton(this.getActivity());
            radioButton.setLayoutParams(new LayoutParams(dp100, dp34));
            radioButton.setText(map.values().iterator().next());
            radioButton.setTag(map.keySet().iterator().next());
            radioButton.setBackgroundResource(R.drawable.radio_check_bg_l);
            radioButton.setButtonDrawable(R.drawable.nullpic);
            radioButton.setTextColor(Color.parseColor("#B8B8B8"));
            radioButton.setPadding(dp10, dp10 / 2, dp10, dp10 / 2);
            radioButton
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            if (isChecked) {
                                inspectMethod = radioButton.getTag().toString();
                            }

                        }

                    });
            rgInspectMethod.addView(radioButton);
            View diver = new View(getActivity());
            diver.setLayoutParams(new LayoutParams(dp10,
                    LayoutParams.MATCH_PARENT));
            rgInspectMethod.addView(diver);
        }

        RadioGroup rgDeviceStatus = (RadioGroup) view
                .findViewById(R.id.rgDeviceStatus);
        for (HashMap<String, String> map : App.getInstance().getConfig().DEVICE_STATUS_CHOICES) {
            final RadioButton radioButton = new RadioButton(this.getActivity());
            radioButton.setLayoutParams(new LayoutParams(dp100, dp34));
            radioButton.setText(map.values().iterator().next());
            radioButton.setTag(map.keySet().iterator().next());
            radioButton.setBackgroundResource(R.drawable.radio_check_bg_l);
            radioButton.setButtonDrawable(R.drawable.nullpic);
            radioButton.setTextColor(Color.parseColor("#B8B8B8"));
            radioButton.setPadding(dp10, dp10 / 2, dp10, dp10 / 2);
            radioButton
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            if (isChecked) {
                                deviceStatus = radioButton.getTag().toString();
                                if ("1".equals(deviceStatus)) {
                                    layoutExpiration.setVisibility(View.GONE);
                                    layoutFixType.setVisibility(View.GONE);
                                } else {
                                    layoutExpiration
                                            .setVisibility(View.VISIBLE);
                                    layoutFixType.setVisibility(View.VISIBLE);
                                }
                            }

                        }

                    });
            rgDeviceStatus.addView(radioButton);
            View diver = new View(getActivity());
            diver.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    dp10));
            rgDeviceStatus.addView(diver);
            if (rgDeviceStatus.getChildCount() > 0)
                rgDeviceStatus.getChildAt(0).performClick();
        }

        RadioGroup rgFixType = (RadioGroup) view.findViewById(R.id.rgFixType);
        for (HashMap<String, String> map : App.getInstance().getConfig().DANGER_FIX_TYPE_CHOICES) {
            final RadioButton radioButton = new RadioButton(this.getActivity());
            radioButton.setLayoutParams(new LayoutParams(dp100, dp34));
            radioButton.setText(map.values().iterator().next());
            radioButton.setTag(map.keySet().iterator().next());
            radioButton.setBackgroundResource(R.drawable.radio_check_bg_l);
            radioButton.setButtonDrawable(R.drawable.nullpic);
            radioButton.setTextColor(Color.parseColor("#B8B8B8"));
            radioButton.setPadding(dp10, dp10 / 2, dp10, dp10 / 2);
            radioButton
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            if (isChecked) {
                                fixType = radioButton.getTag().toString();
                            }

                        }

                    });
            rgFixType.addView(radioButton);
            View diver = new View(getActivity());
            diver.setLayoutParams(new LayoutParams(dp10,
                    LayoutParams.MATCH_PARENT));
            rgFixType.addView(diver);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvExpiration:
                getDatePickerDialog();
                break;
            case R.id.tvAssistant:
                Intent intent = new Intent(getActivity(), AgentActivity.class);
                intent.putExtra("type", SUCCESS);
                this.startActivityForResult(intent, SUCCESS);
                break;
            case R.id.imageView1:
                currentIndex = 0;
                selectPicFromCamera();

                break;
            case R.id.imageView2:
                currentIndex = 1;
                selectPicFromCamera();

                break;
            case R.id.imageView3:
                currentIndex = 2;
                selectPicFromCamera();
                break;
            case R.id.btnSubmit:
                String inspectType = "1";
                String parentRecord = fromIntent.getStringExtra("parentRecord");
                String role = App.getInstance().getUser().getRole()[0];
                if ("[ADMIN][LEADER][SUPERVISOR]".contains(role)) {
                    if (parentRecord == null)
                        inspectType = "1";
                    else
                        inspectType = "2";

                } else if ("[FPADMIN][INCHARGE]".contains(role)) {
                    inspectType = "3";
                } else if ("[MAINTAINER]".contains(role)) {
                    inspectType = "4";
                }
                JSONStringer js = new JSONStringer();
                try {
                    String loc = App.getInstance().getLocation();
                    loc = "".equals(loc) ? "不详" : loc;
                    js.object()
                            .key("device")
                            .value(((ZhongdianScanningActivity) getActivity())
                                    .getDevice().getUrl()).key("inspect_type")
                            .value(inspectType);

                    if (!"".equals(inspectMethod))
                        js.key("inspect_method").value(inspectMethod);
                    js.key("inspect_time_type").value("1");

                    if (!"".endsWith(tvExpiration.getText().toString()))
                        js.key("expiration").value(
                                tvExpiration.getText().toString() + " 00:00");

                    if (!"".equals(fixType))
                        js.key("fix_type").value(fixType);

                    js.key("inspector").array()
                            .value(App.getInstance().getUser().getUrl()).endArray();
                    if (!"".equals(selectedAssistant))
                        js.key("superviser_assistant").array()
                                .value(Resources.URL_ROOT + selectedAssistant)
                                .endArray();
                    js.key("status").value(deviceStatus);

                    if (!"".equals(etAdvice.getText().toString()))
                        js.key("advice").value(etAdvice.getText().toString());

                    js.key("lat").value(App.getInstance().getLngLat()[1])
                            .key("lng").value(App.getInstance().getLngLat()[0])
                            .key("location").value(loc);

                    if (images.length() > 0) {
                        js.key("img").value(images);
                    }
                    String fromTask = getActivity().getIntent().getStringExtra(
                            "fromTask");
                    if (fromTask != null) {
                        js.key("from_task").value(fromTask);
                    }
                    js.endObject();
                    String params = js.toString().replace("\\", "");
                    //Log.e("jldskjfoisdjflsdkjf", params);
                    PostRequest request = new PostRequest();
                    request.setCallback(new HttpCallback() {

                        @Override
                        public void handleData(String s) {
                            //try {
                            //JSONObject json = new JSONObject(s);
                            /*if (!s.isEmpty()) {

                            }*/
                            App.getInstance().setDataChanged(true);
                            getActivity().finish();
                           /* } catch (JSONException e) {
                                e.printStackTrace();
                            }*/

                        }

                    });
                    request.execute(Resources.KEYENTERPRISE_DEVICE_CHECK, params, getActivity());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }

    }

    public void getDatePickerDialog() {
        new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month,
                                          int day) {
                        mYear = year;
                        mMonth = month;
                        mDay = day;
                        // 更新EditText控件日期 小于10加0
                        tvExpiration.setText(new StringBuilder()
                                .append(mYear)
                                .append("-")
                                .append((mMonth + 1) < 10 ? (mMonth + 1)
                                        : (mMonth + 1)).append("-")
                                .append((mDay < 10) ? mDay : mDay));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 跳转选择协办人
        if (requestCode == SUCCESS && resultCode == SUCCESS) {
            tvAssistant.setText(data.getStringExtra("name"));
            selectedAssistant = data.getStringExtra("resource");
        }
        // 跳转拍照
        if (200 == requestCode) {
            Bitmap bt = BitmapFactory.decodeFile(cameraFile.getAbsolutePath());// 图片地址
            if (bt != null) {
                bt = imageCompression.comp(bt);
                imageViews[currentIndex].setImageBitmap(bt);
                imageCompression.bitmap2file(bt, cameraFile.getAbsolutePath());
                File uploadFile = new File(cameraFile.getAbsolutePath());
                UploadUtil uploader = new UploadUtil();
                uploader.setCallback(new HttpCallback() {

                    @Override
                    public void handleData(String s) {
                        try {
                            JSONObject imageStore = new JSONObject(s);
                            images.put(imageStore.getString("url"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });

                uploader.execute(Resources.IMAGE_STORE,
                        uploadFile,
                        ((ZhongdianScanningActivity) getActivity()).getDevice()
                                .getName()
                                + ":"
                                + etAdvice.getText().toString(),
                        getActivity());


            }
        }
    }

    /**
     * 照相获取图片
     */
    public void selectPicFromCamera() {
        String photoPath = PathUtils.getPicturePath(getActivity());
        String fileName = (new Date()).getTime() + ""
                + (new Random()).nextInt(999999) + ".jpg";
        cameraFile = new File(photoPath, fileName);
        photoFileName = cameraFile.getAbsolutePath();
        Uri uri = Uri.fromFile(cameraFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 200);
    }

}
