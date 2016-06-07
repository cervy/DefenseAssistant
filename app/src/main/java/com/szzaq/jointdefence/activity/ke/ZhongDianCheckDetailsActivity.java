package com.szzaq.jointdefence.activity.ke;

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

import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.ImageActivity;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.adapter.ZhongDianTabAdapter;
import com.szzaq.jointdefence.model.DeviceCheck;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.utils.ScrollViewUtils;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 重点单位检查详情
 *
 * @author jiajiaUser
 * @date 2015/07/20
 */
public class ZhongDianCheckDetailsActivity extends MapActivity implements
        OnClickListener {

    // 整改计划列表
    private ListView listview;
    // scrollview
    private ScrollView check_scrollview;
    // 返回键
    private ImageView check_back;
    private MapView mapview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.zhongdian_check_record);
        App.getInstance().addActivity(this);
        initView();
    }

    public void initView() {
        final DeviceCheck check = getIntent().getParcelableExtra(
                "deviceCheck");
        check_scrollview = (ScrollView) this
                .findViewById(R.id.check_scrollview);
        check_back = (ImageView) this.findViewById(R.id.check_back);
        listview = (ListView) this.findViewById(R.id.listview);
        listview.setDivider(new ColorDrawable(Color.parseColor("#FFFFFF")));
        listview.setDividerHeight(DensityUtil.dip2px(this, 15));
        // 返回按钮监听
        check_back.setOnClickListener(this);

        ArrayList<Object> list = new ArrayList<Object>();
        DeviceCheck.Task[] tasks = check.getRelated_tasks();
        if (tasks != null) {
            Collections.addAll(list, tasks);
            listview.setAdapter(new ZhongDianTabAdapter(list, this, 1));

            // 如果不是监督检查和复查，隐藏协办人
            // 设置scrollview高度
            ScrollViewUtils.setScrollViewHeight(listview, this, 20);
            // 默认回到顶部
            ScrollViewUtils.setToTop(listview, check_scrollview);

        }
        if (!"1,2".contains(check.getInspect_type())) {
            findViewById(R.id.tableRowSAssistant).setVisibility(View.GONE);
        }
        // 如果没有隐患，隐藏整改时间肯整改类型
        if ("1".equals(check.getStatus())) {
            findViewById(R.id.tableRowExpiration).setVisibility(View.GONE);
            findViewById(R.id.tableRowFixType).setVisibility(View.GONE);
        }

        HashMap<Integer, String> map = new HashMap<Integer, String>() {
            {
                put(R.id.tvAdvice, check.getAdvice());
                put(R.id.tvDeviceName, check.getDevice_name());
                put(R.id.tvDeviceStatus,
                        App.getInstance()
                                .getConfig()
                                .getTypeName(
                                        App.getInstance().getConfig().DEVICE_STATUS_CHOICES,
                                        check.getStatus()));
                put(R.id.tvEnterprise, check.getEnterprise_name());
                put(R.id.tvExpiration, check.getExpiration());
                put(R.id.tvFixType,
                        App.getInstance()
                                .getConfig()
                                .getTypeName(
                                        App.getInstance().getConfig().DANGER_FIX_TYPE_CHOICES,
                                        check.getFix_type()));
                put(R.id.tvSupervisor, check.getInspector_name());
                put(R.id.tvSupervisorAssistant, check.getAssistant_name());
                put(R.id.tvAddress, check.getLocation());
            }
        };
        for (int i : map.keySet()) {
            try {
                ((TextView) findViewById(i)).setText(map.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int[] imageViewIds = new int[]{R.id.imageView1, R.id.imageView2,
                R.id.imageView3};
        //HashMap<Integer, String> imageMap = new HashMap<Integer, String>();
        final String[] urls = check.getImg_urls();
        final String[] imgNames = check.getImg_names();
        for (int i = 0; i < urls.length; i++) {
            urls[i] = Resources.URL_ROOT + urls[i];
        }
        for (int i = 0; i < Math.min(3, urls.length); i++) {
            ImageView iv = (ImageView) findViewById(imageViewIds[i]);
            iv.setScaleType(ScaleType.CENTER_CROP);
            iv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(
                            ZhongDianCheckDetailsActivity.this,
                            ImageActivity.class);
                    intent.putExtra("imgUrls", urls);
                    intent.putExtra("imgNames", imgNames);
                    startActivity(intent);

                }

            });
            ImageloaderUtils.loaderimage(urls[i], iv);
        }

        mapview = (MapView) findViewById(R.id.mapview);
        TencentMap tencentMap = mapview.getMap();
        LatLng latLng = new LatLng(Double.parseDouble(check.getLat()),
                Double.parseDouble(check.getLng()));
        tencentMap.setCenter(latLng);
        tencentMap.setZoom(16);
        Marker markerFix = tencentMap.addMarker(new MarkerOptions()
                .position(latLng).title(check.getInspector_name())
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker()).draggable(true));
        markerFix.showInfoWindow();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.check_back:
                this.finish();
                break;

            default:
                break;
        }

    }

}
