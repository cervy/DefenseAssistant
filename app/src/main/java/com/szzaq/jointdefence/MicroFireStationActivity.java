package com.szzaq.jointdefence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.Resources;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.tencent.tencentmap.mapsdk.map.TencentMap.OnMarkerClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * 微型消防站列表
 *
 * @author zg
 * @date 2016/02/22
 */
public class MicroFireStationActivity extends MapActivity implements
        OnClickListener {

    private MapView mapview;
    private TencentMap tencentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_micro_fire_station);
        App.getInstance().addActivity(this);
        initView();
        loadMicroStations();
    }

    private void initView() {
        mapview = (MapView) findViewById(R.id.mapview);
        tencentMap = mapview.getMap();
        LatLng latLng = new LatLng(App.getInstance().getLngLat()[1],
                App.getInstance().getLngLat()[0]);
        tencentMap.setCenter(latLng);
        tencentMap.setZoom(12);
        tencentMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(MicroFireStationActivity.this, MicroFireStationDetailActivity.class);
                intent.putExtra("url", marker.getSnippet());
                startActivity(intent);
                return false;
            }

        });
        findViewById(R.id.btnBack).setOnClickListener(this);

    }

    private void loadMicroStations() {
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                try {
                    JSONArray jarr = new JSONObject(s).getJSONArray("results");
                    Log.e("微站位置", jarr.toString());
                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = jarr.getJSONObject(i);
                        LatLng latLng = new LatLng(json.getDouble("lat"), json.getDouble("lng"));
                        String title = json.getString("name");
                        title += "\n" + json.getString("location");
                        Marker marker = tencentMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(title)
                                .anchor(0.5f, 0.5f)
                                .icon(BitmapDescriptorFactory.defaultMarker())
                                .draggable(false));
                        marker.setSnippet(json.getString("url"));

                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        });

        JSONStringer js = new JSONStringer();
        try {
            js.object().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.execute(Resources.MICRO_FIRE_STATION + "?rows=100", js.toString());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnBack:
                this.finish();
                break;
            default:
                break;
        }

    }

}
