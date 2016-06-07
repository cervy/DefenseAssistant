package com.szzaq.jointdefence.activity.smallsite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.szzaq.jointdefence.R;

import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.model.Configuration;
import com.szzaq.jointdefence.model.SmallSite;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.utils.ScrollViewUtils;

/**
 * 三小场所详情
 *
 * @author jiajiaUser
 * @date 2015/07/13
 */
public class SmallSiteDetailsActivity extends Activity implements OnClickListener {
    // 场所图片
    private ImageView place_image;
    // 三小场所联系人列表
    private ListView sanxiao_listview;
    // 场所基本信息
    private TextView place_name, place_address, place_type, business_range,
            place_license, place_number, place_floor, tvOwner, tvOwnerTel, tvResponsilbe,
            tvResponsilbeTel, tvAdmin, tvAdminTel;
    // 声明imageloder
   /* private DisplayImageOptions options;
    private ImageLoader imageLoader;
    //场所基本信息
    private HashMap<String, Object> map;
    //场所联系人列表
    private ArrayList<HashMap<String, Object>> list;*/
    //srcollview
    private ScrollView list_srcoll;
    //编辑按钮,返回键, 分享
    private ImageView sanxiao_info_edit, sanxiao_details_back;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_sanxiao_details);
        App.getInstance().addActivity(this);
        initView();
    }

    private SmallSite site;

    public void initView() {
        site = getIntent().getParcelableExtra("site");
        Configuration config = App.getInstance().getConfig();
        place_image = (ImageView) this.findViewById(R.id.place_image);
        place_image.setScaleType(ScaleType.CENTER_CROP);
        place_name = (TextView) this.findViewById(R.id.place_name);
        place_address = (TextView) this.findViewById(R.id.place_address);
        place_type = (TextView) this.findViewById(R.id.place_type);
        business_range = (TextView) this.findViewById(R.id.business_range);
        place_license = (TextView) this.findViewById(R.id.place_license);
        place_number = (TextView) this.findViewById(R.id.place_number);
        place_floor = (TextView) this.findViewById(R.id.place_floor);
        tvOwner = (TextView) this.findViewById(R.id.tvOwner);
        tvOwnerTel = (TextView) this.findViewById(R.id.tvOwnerTel);
        tvResponsilbe = (TextView) this.findViewById(R.id.tvResponsible);
        tvResponsilbeTel = (TextView) this.findViewById(R.id.tvResponsibleTel);
        tvAdmin = (TextView) this.findViewById(R.id.tvAdmin);
        tvAdminTel = (TextView) this.findViewById(R.id.tvAdminTel);

        sanxiao_listview = (ListView) this.findViewById(R.id.sanxiao_listview);
        list_srcoll = (ScrollView) this.findViewById(R.id.list_srcoll);
        sanxiao_info_edit = (ImageView) this.findViewById(R.id.sanxiao_info_edit);
        sanxiao_details_back = (ImageView) this.findViewById(R.id.sanxiao_details_back);

        place_name.setText(site.getName());
        place_address.setText(site.getAddress());
        place_type.setText(site.getSite_type_name());
        business_range.setText(site.getScope());
        place_license.setText(config != null ? config.getTypeName(config.HAS_CERTIFICATION_CHOICES, String.valueOf(site.getCertificate())) : "licnse");
        place_number.setText(site.getCode());
        place_floor.setText(String.valueOf(site.getFloors()));
        tvOwner.setText(site.getOwner());
        tvOwnerTel.setText(site.getOwner_phone());
        tvResponsilbe.setText(site.getResponsible());
        tvResponsilbeTel.setText(site.getResponsible_phone());
        tvAdmin.setText(site.getAdministrator());
        tvAdminTel.setText(site.getAdministrator_phone());

        //list = new ArrayList<HashMap<String, Object>>();
        //sanxiao_listview.setAdapter(new ActivitySanxiaoDetailsAdapter(list, this));
        sanxiao_listview.setDivider(new ColorDrawable(Color.parseColor("#F5F5F5")));
        sanxiao_listview.setDividerHeight(DensityUtil.dip2px(this, 20));
//        ScrollViewUtils.setScrollViewHeight(sanxiao_listview, this, 20);

        sanxiao_info_edit.setOnClickListener(this);
        sanxiao_details_back.setOnClickListener(this);
        findViewById(R.id.info_select).setOnClickListener(this);

        //远程获取头像
        ImageLoader.getInstance().displayImage(Resources.URL_ROOT + site.getImgUrl(), place_image);
        //回到顶部
        ScrollViewUtils.setToTop(sanxiao_listview, list_srcoll);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.sanxiao_details_back:
                this.finish();
                break;
            case R.id.sanxiao_info_edit:
                Intent intent = new Intent(SmallSiteDetailsActivity.this, SmallSiteInfoActivity.class);
                //intent.putExtra("type", 0);// TODO:  界面展示类型,0为修改，1为新增
                /* intent.putExtra("details_map", map);
                intent.putExtra("list_person", list);*/
                intent.putExtra("site", site);
                this.startActivity(intent);
                break;
            case R.id.info_select:
                // TODO: 2016/4/28 0028 分享图标
                Toast.makeText(getApplicationContext(), "谢谢，此页面暂不支持分享", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
