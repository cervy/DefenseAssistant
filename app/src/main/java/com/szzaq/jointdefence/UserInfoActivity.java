package com.szzaq.jointdefence;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.model.UserInfo;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.net.UploadUtil;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.ImageUtils;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.PathUtils;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 个人资料界面
 *
 * @author jiajiaUser
 * @date 2015/07/07
 */
public class UserInfoActivity extends Activity implements OnClickListener {

    private ImageView userinfo_back, ivPortrait;
    private PopupWindow portraitMenu;
    private String photoPath = "";
    private final static int REQUEST_TAKE_PHOTO = 1;
    private final static int REQUEST_SELECT_PHOTO = 2;
    // 编辑按钮，姓名，手机号码，所在区域，单位名称，状态，代理人
    private TextView userinfo_edit, userinfo_name, userinfo_phone,
            userinfo_map, userinfo_company_name, userinfo_state,
            userinfo_agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_userinfo);
        App.getInstance().addActivity(this);
        initView();
    }

    public void initView() {
        photoPath = new File(PathUtils.getPicturePath(this), App.getInstance()
                .getUser().getUsername()
                + ".jpg").getPath();
        // 获取控件
        userinfo_back = (ImageView) this.findViewById(R.id.userinfo_back);
        userinfo_edit = (TextView) this.findViewById(R.id.userinfo_edit);
        userinfo_name = (TextView) this.findViewById(R.id.userinfo_name);
        userinfo_phone = (TextView) this.findViewById(R.id.userinfo_phone);
        userinfo_map = (TextView) this.findViewById(R.id.userinfo_map);
        userinfo_company_name = (TextView) this
                .findViewById(R.id.userinfo_company_name);
        ivPortrait = (ImageView) this.findViewById(R.id.ivPortrait);
        ivPortrait.setOnClickListener(this);
        String imgUrl = App.getInstance().getUser().getProfile()
                .getPortrait_url();
        if (!"".equals(imgUrl)) {
            if (!imgUrl.contains(Resources.URL_ROOT))
                imgUrl = Resources.URL_ROOT + imgUrl;
            ImageloaderUtils.loaderimage(imgUrl, ivPortrait);
        }
        // userinfo_state = (TextView) this.findViewById(R.id.userinfo_state);
        // userinfo_agent = (TextView) this.findViewById(R.id.userinfo_agent);
        // 绑定监听
        userinfo_back.setOnClickListener(this);
        userinfo_edit.setOnClickListener(this);
        // 登陆后，从全局 变量获取信息
        UserInfo userInfo = App.getInstance().getUser();
        if (userInfo != null) {
            userinfo_name.setText(userInfo.getProfile().getNickname());
            userinfo_phone.setText(userInfo.getProfile().getPhone());
            userinfo_map.setText(App.getInstance().getLocation());
            String workUnit = "";
            if ("[ADMIN][LEADER][SUPERVISOR]".contains(userInfo.getRole()[0])) {
                workUnit = userInfo.getAsFireMan().split(",")[1];
            }
            if ("[FPADMIN][INCHARGE]".contains(userInfo.getRole()[0])) {
                workUnit = userInfo.getAsStaff().split(",")[1];
            }
            if ("[MAINTAINER]".contains(userInfo.getRole()[0])) {
                workUnit = userInfo.getAsMaintainer().split(",")[1];
            }
            userinfo_company_name.setText(workUnit);
            /*
             * int status =App.getInstance().getUser().getStatus();
			 * if(status==0){ userinfo_state.setText("工作中"); }else{
			 * userinfo_state.setText("休假中"); }
			 * userinfo_agent.setText(App.getInstance
			 * ().getUser().getAgent_name()+"");
			 */
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            // 返回
            case R.id.userinfo_back:
                UserInfoActivity.this.finish();
                break;
            // 跳转编辑界面
            case R.id.userinfo_edit:
                Intent intenEdit = new Intent(this, UserInfoEditActivity.class);
                this.startActivity(intenEdit);
                break;
            case R.id.ivPortrait:
                showPortraitMenu(this);//设置头像
                break;
            default:
                break;
        }

    }

    /**
     * 点击用户头像后弹出“拍照”和“从相册选取”两个选项给用户选择
     *
     * @param context
     */
    private void showPortraitMenu(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View display = inflater.inflate(R.layout.layout_modify_portrait, null);
        LinearLayout layout = new LinearLayout(context);
        Button btnPickPhoto = (Button) display.findViewById(R.id.btnPickPhoto);
        btnPickPhoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                portraitMenu.dismiss();
                pickPhoto();

            }

        });
        Button btnTakePhoto = (Button) display.findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                portraitMenu.dismiss();
                takePhoto();

            }

        });

        layout.setGravity(Gravity.CENTER);
        layout.addView(display);

        DisplayMetrics mertrics = context.getResources().getDisplayMetrics();
        portraitMenu = new PopupWindow(layout, mertrics.widthPixels,
                DensityUtil.dip2px(context, 100));
        portraitMenu.setFocusable(true);
        portraitMenu.setOutsideTouchable(true);
        portraitMenu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));

        int[] location = new int[2];
        ivPortrait.getLocationOnScreen(location);
        portraitMenu.showAsDropDown(ivPortrait);
        portraitMenu.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {

            }

        });
    }

    /**
     * 调用相机拍摄图片
     */
    public void takePhoto() {

        File file = new File(photoPath);
        Uri uri = Uri.fromFile(file);
        photoPath = file.getPath();
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 从相册选择图片
     */
    private void pickPhoto() {
        try {
            Intent intent = new Intent("android.intent.action.PICK");
            intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                    "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            startActivityForResult(intent, REQUEST_SELECT_PHOTO);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), R.string.albumerr, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 响应拍照合作选择图片操作
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap portraitBitmap = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO://拍照回调
                    try {
                        portraitBitmap = ImageUtils.decodeFromFile(photoPath, 100,
                                100, true);
                        File file = new File(photoPath);
                        FileOutputStream fos;
                        try {
                            fos = new FileOutputStream(file);
                            assert portraitBitmap != null;
                            portraitBitmap.compress(CompressFormat.JPEG, 80, fos);
                            uploadPortrait(new File(photoPath));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (portraitBitmap != null) {
                        ivPortrait.setImageBitmap(portraitBitmap);
                    }

                    break;
                case REQUEST_SELECT_PHOTO://相册回调
                    portraitBitmap = data.getParcelableExtra("data");
                    if (portraitBitmap != null) {
                        ivPortrait.setImageBitmap(portraitBitmap);
                        File file = new File(photoPath);
                        FileOutputStream fos;
                        try {
                            fos = new FileOutputStream(file);
                            portraitBitmap.compress(CompressFormat.JPEG, 80, fos);
                            uploadPortrait(new File(photoPath));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    break;
            }
        }
    }

    private void uploadPortrait(File file) {//上传头像图片
        UploadUtil uploader = new UploadUtil();
        uploader.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    String imgUrl = json.optString("url");
                    String portraitUrl = json.optString("img");
                    App.getInstance().getUser().getProfile()
                            .setPortrait_url(portraitUrl);
                    if (!"".equals(imgUrl)) {
                        updatePortrait(imgUrl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        uploader.execute(Resources.IMAGE_STORE, file,
                App.getInstance().getUser().getUsername());
    }

    private void updatePortrait(final String imgUrl) {
        PostRequest request = new PostRequest();
        /*request.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    json.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });*/
        UserInfo.Profile profile = App.getInstance().getUser().getProfile();
        Gson gson = new Gson();
        JSONObject profileJson = null;
        try {
            profileJson = new JSONObject(gson.toJson(profile));
            profileJson.put("portrait", imgUrl);
            profileJson.put("_method", "PUT");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (profileJson != null)
            request.execute(profile.getUrl(),
                    profileJson.toString(), UserInfoActivity.this);

    }
}
