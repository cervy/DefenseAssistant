package com.szzaq.jointdefence.activity.smallsite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.szzaq.jointdefence.AgentActivity;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.ScreeningCommitteeActivity;
import com.szzaq.jointdefence.model.Configuration;
import com.szzaq.jointdefence.model.SmallSite;
import com.szzaq.jointdefence.utils.ImageCompression;
import com.szzaq.jointdefence.utils.PathUtils;

import java.io.File;
import java.util.Date;
import java.util.Random;

/**
 * 三小场所新增和修改界面
 * 0
 *
 * @author jiajiaUser
 * @date 2015/07/13
 */
public class SmallSiteInfoActivity extends Activity implements OnClickListener {
    private File cameraFile;
    //private String photoFileName;
    public static int name_value_4_SUCCESS = 2, name_value_5_SUCCESS = 3, COMMITTEE_SUCCESS = 4;
    private EditText sanxiaoinfo_name, sanxiaoinfo_address, sanxiaoinfo_range,
            sanxiaoinfo_number, sanxiaoinfo_floor, phone_1, phone_2, phone_3,
            phone_4, phone_5;
    // 经营类型
    private TextView sanxiaoinfo_type, name_key_1, name_value_1, name_key_2,
            name_value_2, name_key_3, name_value_3, name_key_4, name_value_4,
            name_key_5, name_value_5, sanxiao_info_title, belonging_community;
    // 单选按钮
    private RadioButton sanxiaoinfo_yes, sanxiaoinfo_no;
    // 上一个界面传过来的基本信息
    //private HashMap<String, Object> map;
    // 界面展示类型,0为修改，1为新增
    //private int type;
    // 返回键
    private ImageView sanxiao_info_back, sanxiaoinfo_photo;
    // 场所联系人列表
    //private ArrayList<HashMap<String, Object>> list;
    // 图片压缩类
    private ImageCompression imageCompression;
    private SmallSite mSmallSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_sanxiao_info);
        App.getInstance().addActivity(this);

        mSmallSite = getIntent().getParcelableExtra("site");
        //type = getIntent().getIntExtra("type", 1);
        //imageCompression = new ImageCompression();
        /*if (type == 0) {
            map = (HashMap<String, Object>) getIntent().getSerializableExtra(
                    "details_map");
            list = (ArrayList<HashMap<String, Object>>) getIntent()
                    .getSerializableExtra("list_person");
        }*/
        initView();
    }

    public void initView() {
        findViewById(R.id.userinfo_edit).setOnClickListener(this);
        sanxiaoinfo_name = (EditText) this.findViewById(R.id.sanxiaoinfo_name);
        sanxiaoinfo_address = (EditText) this
                .findViewById(R.id.sanxiaoinfo_address);
        sanxiaoinfo_range = (EditText) this
                .findViewById(R.id.sanxiaoinfo_range);
        sanxiaoinfo_number = (EditText) this
                .findViewById(R.id.sanxiaoinfo_number);
        sanxiaoinfo_floor = (EditText) this
                .findViewById(R.id.sanxiaoinfo_floor);
        sanxiaoinfo_type = (TextView) this.findViewById(R.id.sanxiaoinfo_type);
        sanxiaoinfo_yes = (RadioButton) this.findViewById(R.id.sanxiaoinfo_yes);
        sanxiaoinfo_no = (RadioButton) this.findViewById(R.id.sanxiaoinfo_no);
        name_key_1 = (TextView) this.findViewById(R.id.name_key_1);
        name_value_1 = (TextView) this.findViewById(R.id.name_value_1);
        name_key_2 = (TextView) this.findViewById(R.id.name_key_2);
        name_value_2 = (TextView) this.findViewById(R.id.name_value_2);
        name_key_3 = (TextView) this.findViewById(R.id.name_key_3);
        name_value_3 = (TextView) this.findViewById(R.id.name_value_3);
        name_key_4 = (TextView) this.findViewById(R.id.name_key_4);
        name_value_4 = (TextView) this.findViewById(R.id.name_value_4);
        //name_key_5 = (TextView) this.findViewById(R.id.name_key_5);
        //name_value_5 = (TextView) this.findViewById(R.id.name_value_5);
        belonging_community = (TextView) this.findViewById(R.id.belonging_community);
        phone_1 = (EditText) this.findViewById(R.id.phone_1);
        phone_2 = (EditText) this.findViewById(R.id.phone_2);
        phone_3 = (EditText) this.findViewById(R.id.phone_3);
        phone_4 = (EditText) this.findViewById(R.id.phone_4);
        //phone_5 = (EditText) this.findViewById(R.id.phone_5);
        sanxiao_info_back = (ImageView) this
                .findViewById(R.id.sanxiao_info_back);
        sanxiao_info_title = (TextView) this
                .findViewById(R.id.sanxiao_info_title);
        sanxiaoinfo_photo = (ImageView) this
                .findViewById(R.id.sanxiaoinfo_photo);
        sanxiao_info_back.setOnClickListener(this);
        sanxiaoinfo_type.setOnClickListener(this);
        name_value_4.setOnClickListener(this);
        //name_value_5.setOnClickListener(this);
        sanxiaoinfo_photo.setOnClickListener(this);
        belonging_community.setOnClickListener(this);
        // 当为修改界面时绑定数据
        /*if (type == 0) {
            //if (map != null) {

                sanxiaoinfo_name.setText(map.get("place_name").toString());
                sanxiaoinfo_address.setText(map.get("place_address").toString());
                sanxiaoinfo_range.setText(map.get("business_range").toString());
                sanxiaoinfo_number.setText(map.get("place_number").toString());
                sanxiaoinfo_floor.setText(map.get("place_floor").toString());
                sanxiaoinfo_type.setText(map.get("place_type").toString());

                if ("有".equals((String) map.get("place_license"))) {
                    sanxiaoinfo_yes.setChecked(true);
                } else {
                    sanxiaoinfo_no.setChecked(true);
                }
            }*/
        Configuration config = App.getInstance().getConfig();

        sanxiaoinfo_name.setText(mSmallSite.getName());
        sanxiaoinfo_address.setText(mSmallSite.getAddress());
        sanxiaoinfo_range.setText(mSmallSite.getScope());
        sanxiaoinfo_number.setText(mSmallSite.getCode());
        sanxiaoinfo_floor.setText(String.valueOf(mSmallSite.getFloors()));
        sanxiaoinfo_type.setText(mSmallSite.getSite_type_name());

        if ("有".equals(config.getTypeName(config.HAS_CERTIFICATION_CHOICES, String.valueOf(mSmallSite.getCertificate())))) {
            sanxiaoinfo_yes.setChecked(true);
        } else {
            sanxiaoinfo_no.setChecked(true);
        }

    /*if (list != null && list.size() > 0) {


                name_key_1.setText(list.get(0).get("name_key").toString());
                name_value_1.setText(list.get(0).get("name_value").toString());
                name_key_2.setText(list.get(1).get("name_key").toString());
                name_value_2.setText(list.get(1).get("name_value").toString());
                name_key_3.setText(list.get(2).get("name_key").toString());
                name_value_3.setText(list.get(2).get("name_value").toString());
                name_key_4.setText(list.get(3).get("name_key").toString());
                name_value_4.setText(list.get(3).get("name_value").toString());
                name_key_5.setText(list.get(4).get("name_key").toString());
                name_value_5.setText(list.get(4).get("name_value").toString());
                phone_1.setText(list.get(0).get("phone_number").toString());
                phone_2.setText(list.get(1).get("phone_number").toString());
                phone_3.setText(list.get(2).get("phone_number").toString());
                phone_4.setText(list.get(3).get("phone_number").toString());
                phone_5.setText(list.get(4).get("phone_number").toString());
            }
            sanxiao_info_title.setText(getResources().getString(
                    R.string.sanxiao_edit));
        }*/
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.userinfo_edit:// TODO: 2016/5/25 0025 修改或添加 接口
                //Toast.makeText(getApplicationContext(), "操作成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.sanxiao_info_back:
                this.finish();
                break;
            case R.id.sanxiaoinfo_type:
                new AlertDialog.Builder(this)
                        .setTitle("场所类型")
                        .setItems(getResources().getStringArray(R.array.date),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        final String[] aryShop = getResources()
                                                .getStringArray(R.array.date); // array
                                        sanxiaoinfo_type.setText(aryShop[which]);
                                    }
                                }).setNegativeButton("取消", null).show();
                break;
            case R.id.name_value_4:
                Intent intent = new Intent(this, AgentActivity.class);
                intent.putExtra("type", name_value_4_SUCCESS);
                this.startActivityForResult(intent, name_value_4_SUCCESS);
                break;
            /*case R.id.name_value_5:
                Intent intent1 = new Intent(this, AgentActivity.class);
                intent1.putExtra("type", name_value_5_SUCCESS);
                this.startActivityForResult(intent1, name_value_5_SUCCESS);
                break;*/
            case R.id.sanxiaoinfo_photo:
                selectPicFromCamera();
                break;
            case R.id.belonging_community:
                Intent intent2 = new Intent(this, ScreeningCommitteeActivity.class);
                this.startActivityForResult(intent2, COMMITTEE_SUCCESS);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (name_value_4_SUCCESS == requestCode
                && name_value_4_SUCCESS == resultCode) {
            name_value_4.setText(data.getStringExtra("name"));
        }
        /*if (name_value_5_SUCCESS == requestCode
                && name_value_5_SUCCESS == resultCode) {
            name_value_5.setText(data.getStringExtra("name"));
        }*/
        if (200 == requestCode) {
            Bitmap bt = BitmapFactory.decodeFile(cameraFile.getAbsolutePath());// 图片地址
            if (bt != null) {
                bt = imageCompression.comp(bt);
                sanxiaoinfo_photo.setImageBitmap(bt);
                imageCompression.bitmap2file(bt, cameraFile.getAbsolutePath());
            }
        }
        if (requestCode == COMMITTEE_SUCCESS && resultCode == COMMITTEE_SUCCESS) {
            belonging_community.setText(data.getStringExtra("name"));
        }
    }

    /**
     * 照相获取图片
     */
    public void selectPicFromCamera() {
        String photoPath = PathUtils.getPicturePath(this);
        String fileName = (new Date()).getTime() + ""
                + (new Random()).nextInt(999999) + ".jpg";
        cameraFile = new File(photoPath, fileName);
        //photoFileName = cameraFile.getAbsolutePath();
        Uri uri = Uri.fromFile(cameraFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // photoPath = file.getPath();
        startActivityForResult(intent, 200);
    }

}
