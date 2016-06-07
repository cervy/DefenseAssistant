package com.szzaq.jointdefence;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import com.szzaq.jointdefence.model.SmallSite;

/**
 * 场所地图界面
 *
 * @date 2015/07/07
 */
public class MapActivity extends Activity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_map);
        App.getInstance().addActivity(this);
        initView();


    }

    public void initView() {
        SmallSite site = getIntent().getParcelableExtra("site");
        mapView = (MapView) findViewById(R.id.mapview);
        TencentMap tencentMap = mapView.getMap();
        LatLng latLng = new LatLng(site.getLat(), site.getLng());
        tencentMap.setCenter(latLng);
        tencentMap.setZoom(16);
        Marker markerFix = tencentMap.addMarker(new MarkerOptions()//
                .position(latLng)
                .title(site.getName())
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker()).draggable(true));
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(site.getName());
    }

}
