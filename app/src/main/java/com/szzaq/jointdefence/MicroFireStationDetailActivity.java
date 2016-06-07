package com.szzaq.jointdefence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.activity.smallsite.RecordDetailsActivity;
import com.szzaq.jointdefence.adapter.FireStationEquipmentAdapter;
import com.szzaq.jointdefence.adapter.FireStationMemberAdapter;
import com.szzaq.jointdefence.model.SmallSiteCheck;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.utils.ScrollViewUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 微型消防站详情
 *
 * @author zg
 * @date 2016/02/02
 */
public class MicroFireStationDetailActivity extends Activity implements OnClickListener {
    //场所基本信息
    private TextView tvName, tvEnterprise, tvAddress;
    //复查记录列表
    private ListView listViewMembers, listViewEquipment;
    //返回按钮
    private ImageView back_button, ivPortal;
    private SmallSiteCheck check;
    private LinearLayout layoutPictures;
    private ArrayList<HashMap<String, Object>> memberList;
    private FireStationMemberAdapter memberAdapter;
    private ArrayList<HashMap<String, Object>> equipList;
    private FireStationEquipmentAdapter equipAdapter;
    private int[] icons = new int[]{
            R.drawable.toukui,
            R.drawable.fhf,
            R.drawable.shoutao,
            R.drawable.yaodai,
            R.drawable.xuezi,
            R.drawable.diantong,
            R.drawable.hujiuqi,
            R.drawable.shengzi,
            R.drawable.mianju,
            R.drawable.shuiqiang,
            R.drawable.shuidai,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_micro_fire_station_detail);
        App.getInstance().addActivity(this);
        initView();

    }

    private void initView() {
        findViewById(R.id.change).setOnClickListener(this);
        String url = getIntent().getStringExtra("url");
        tvName = (TextView) findViewById(R.id.tvName);
        tvEnterprise = (TextView) findViewById(R.id.tvEnterprise);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        ivPortal = (ImageView) findViewById(R.id.ivPortal);
        layoutPictures = (LinearLayout) findViewById(R.id.layoutPictures);
        getStationData(url);
    }

    private void getFireStationMembers(String stationId) {
        listViewMembers = (ListView) findViewById(R.id.listViewMembers);
        memberList = new ArrayList<HashMap<String, Object>>();
        memberAdapter = new FireStationMemberAdapter(memberList, this);
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                try {
                    JSONArray arr = new JSONObject(s).getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject json = arr.getJSONObject(i);
                        String name = json.optString("name");
                        String role = json.optString("role");
                        String phone = json.optString("phone");
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("userName", name + "[" + role + "]");
                        map.put("phone", phone);
                        memberList.add(map);
                    }
                    listViewMembers.setAdapter(memberAdapter);
                    ScrollViewUtils.setScrollViewHeight(listViewMembers, MicroFireStationDetailActivity.this, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        request.execute(Resources.MICRO_FIRE_STATION_MEMBERS + "?rows=100&station_id=" + stationId);

    }

    private void getEquipment(String stationId) {
        listViewEquipment = (ListView) findViewById(R.id.listEquipment);
        equipList = new ArrayList<HashMap<String, Object>>();
        equipAdapter = new FireStationEquipmentAdapter(equipList, this);
        listViewEquipment.setAdapter(equipAdapter);
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                try {
                    JSONArray arr = new JSONObject(s).getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject json = arr.getJSONObject(i);
                        String name = json.optString("name");
                        String quantity = json.optString("quantity");
                        int type = json.optInt("type", 0);
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("equipmentName", name);
                        map.put("quantity", quantity);
                        if (type > 0) {
                            map.put("icon", icons[type - 1]);
                        }
                        equipList.add(map);
                    }
                    equipAdapter.notifyDataSetChanged();
                    ScrollViewUtils.setScrollViewHeight(listViewEquipment, MicroFireStationDetailActivity.this, 0);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        request.execute(Resources.MICRO_FIRE_STATION_EQUIP + "?rows=100&station_id=" + stationId);
    }


    /**
     * 获取微型消防站数据
     *
     * @return
     */
    private void getStationData(String url) {
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    getFireStationMembers(json.getString("id"));
                    getEquipment(json.getString("id"));
                    tvName.setText(json.getString("name"));
                    tvAddress.setText(json.getString("location"));
                    tvEnterprise.setText(json.optString("enterprise", "无").replace("null", "无"));
                    ivPortal.setScaleType(ScaleType.CENTER_CROP);
                    ImageloaderUtils.loaderimage(Resources.URL_ROOT + json.getString("portal_photo_url"), ivPortal);
                    JSONArray photoArr = json.getJSONArray("photo_urls");
                    int dp120 = DensityUtil.dip2px(MicroFireStationDetailActivity.this, 120);
                    String imgUrls = null;
                    String imgNames = null;

                    if (photoArr.length() == 0) {
                        LinearLayout layout = new LinearLayout(MicroFireStationDetailActivity.this);
                        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 0, 20, 10);
                        layout.setLayoutParams(params);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        TextView tvNoPic = new TextView(MicroFireStationDetailActivity.this);
                        tvNoPic.setText("暂无图片");
                        layout.addView(tvNoPic);
                        layoutPictures.addView(layout);
                    }
                    for (int i = 0; i < photoArr.length(); i++) {
                        String url = photoArr.getString(i);
                        LinearLayout layout = new LinearLayout(MicroFireStationDetailActivity.this);
                        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 0, 20, 10);
                        layout.setLayoutParams(params);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        ImageView iv = new ImageView(MicroFireStationDetailActivity.this);
                        params = new LayoutParams(dp120, dp120);
                        iv.setLayoutParams(params);
                        iv.setScaleType(ScaleType.CENTER_CROP);
                        if (!"".equals(url)) {
                            url = !url.contains(Resources.URL_ROOT) ? Resources.URL_ROOT + url : url;
                            if (imgUrls == null) {
                                imgUrls = url;
                                imgNames = "";
                            } else {
                                imgUrls += "--=--" + url;
                                imgNames += "--=--" + json.getString("name");
                            }
                            ImageloaderUtils.loaderimage(url, iv);
                        } else
                            iv.setImageResource(R.drawable.ic_launcher);
                        layout.addView(iv);
                        params = new LayoutParams(LayoutParams.MATCH_PARENT, 100);
                        layoutPictures.addView(layout);
                    }
                    if (imgUrls != null) {
                        final String[] urlArr = imgUrls.split("--=--");
                        final String[] nameArr = imgNames.split("--=--");
                        layoutPictures.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MicroFireStationDetailActivity.this, ImageActivity.class);
                                intent.putExtra("imgUrls", urlArr);
                                intent.putExtra("imgNames", nameArr);
                                startActivity(intent);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        request.execute(url, "");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.change:
                startActivityForResult(new Intent(this, A_ChangeMicroStationDetail.class), REQUESTCODE);
                break;
            case R.id.back_button:
                this.finish();
                break;
            case R.id.review_button:
                Intent intent = new Intent(this, RecordDetailsActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("check", check);
                this.startActivity(intent);
                break;
            default:
                break;
        }

    }

    public static final int REQUESTCODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUESTCODE) {

        }
    }
}
