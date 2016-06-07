package com.szzaq.jointdefence;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.activity.ke.ZhongdianDetailsActivity;
import com.szzaq.jointdefence.activity.smallsite.SmallSiteDetailsActivity;
import com.szzaq.jointdefence.activity.smallsite.SmallSiteInfoActivity;
import com.szzaq.jointdefence.adapter.ActivityInfoitemAdapter;
import com.szzaq.jointdefence.adapter.ActivityinfoitemSelectAdapter;
import com.szzaq.jointdefence.model.Enterprise;
import com.szzaq.jointdefence.model.SmallSite;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * place_type  1三小信息列表和0重点单位信息列表界面
 *
 * @author jiajiaUser
 * @date 2015/07/08
 */
public class InfoActivity extends Activity implements OnClickListener {
    //场所类型，默认为重点,从上一个界面获取
    private int place_type;
    //选项框
    private TextView info_select, info_title;
    //搜索框
    private EditText info_edsearch;
    //列表和地区选项卡
    private ListView info_listview, info_listview_2;
    private ImageView info_back, info_add;
    private ActivityInfoitemAdapter adapter;
    private ArrayList<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_info);
        App.getInstance().addActivity(this);
        //获取场所类型,0为重点单位，默认重点
        place_type = getIntent().getIntExtra("place_type", 0);
        //初始化控件
        initView();
        info_title.setText(place_type == 0 ? "重点单位" : "三小场所");
        getData(place_type);
    }

    /**
     * 初始化控件
     */
    public void initView() {
        info_select = (TextView) this.findViewById(R.id.info_select);
        info_edsearch = (EditText) this.findViewById(R.id.info_edsearch);
        info_listview = (ListView) this.findViewById(R.id.info_listview);
        info_back = (ImageView) this.findViewById(R.id.info_back);
        info_listview_2 = (ListView) this.findViewById(R.id.info_listview_2);
        info_add = (ImageView) this.findViewById(R.id.info_add);
        info_title = (TextView) this.findViewById(R.id.info_title);
        //获取向下箭头
        Drawable dra = getResources().getDrawable(R.drawable.bottom_arrow);
        assert dra != null;
        dra.setBounds(0, 0, DensityUtil.dip2px(this, 12), DensityUtil.dip2px(this, 8));
        info_select.setCompoundDrawables(null, null, dra, null);
        Drawable dra2 = getResources().getDrawable(R.drawable.search_gray);
        assert dra2 != null;
        dra2.setBounds(0, 0, DensityUtil.dip2px(this, 24), DensityUtil.dip2px(this, 24));
        info_edsearch.setCompoundDrawables(dra2, null, null, null);
        //设置透明效果
        info_listview_2.getBackground().setAlpha(120);
        info_select.setOnClickListener(this);
        info_back.setOnClickListener(this);
        info_listview_2.setFocusable(true);
        info_add.setOnClickListener(this);
        //场所信息列表
        list = new ArrayList<Object>();
        adapter = new ActivityInfoitemAdapter(list, this, place_type);
        info_listview.setAdapter(adapter);
        info_listview.setOnItemClickListener(new Place_ListOnItemClick());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //地区或者大队选择键
            case R.id.info_select:
                //判断选项卡状态
                if (info_listview_2.getVisibility() == View.GONE) {
                    info_listview_2.setVisibility(View.VISIBLE);
                    //展示选项
                    info_listview_2.setAdapter(new ActivityinfoitemSelectAdapter((List<String>) getdata(1), this));
                } else {
                    info_listview_2.setVisibility(View.GONE);
                }
                break;
            //返回键
            case R.id.info_back:
                this.finish();
                break;
            case R.id.info_add:
                if (place_type == 1) {
                    Intent intent = new Intent(this, SmallSiteInfoActivity.class);
                    intent.putExtra("type", 1);
                    this.startActivity(intent);
                }
                break;
            default:
                break;
        }
    }


    class Place_ListOnItemClick implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            int pos = (int) arg3;
            if (place_type == 0) {

                Intent intent = new Intent(InfoActivity.this, ZhongdianDetailsActivity.class);

                intent.putExtra("list", (Enterprise) list.get(pos));
                //intent.putExtra("type", 0);
                InfoActivity.this.startActivity(intent);
            }
            if (place_type == 1) {

                Intent intent = new Intent(InfoActivity.this, SmallSiteDetailsActivity.class);
                SmallSite site = (SmallSite) list.get(pos);
                intent.putExtra("site", site);
                InfoActivity.this.startActivity(intent);
            }

        }

    }

    /**
     * 获取场所数据
     */
    public Object getdata(int type) {
        if (type == 0) {
            return new ArrayList<HashMap<String, Object>>();
        } else {
            ArrayList<String> list = new ArrayList<String>();
            list.add("大冲社区");
            list.add("前海社区");
            list.add("后海社区");
            list.add("福田社区");
            return list;
        }
    }


    public void getData(final int dataType) {
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                Log.e("infoActivity", s);
                Gson gson = new Gson();
                try {
                    JSONArray objects = (new JSONObject(s)).getJSONArray("results");
                    for (int i = 0; i < objects.length(); i++) {
                        JSONObject obj = objects.getJSONObject(i);
                        if (dataType == 0) {
                            Enterprise enterprise = gson.fromJson(obj.toString(), new TypeToken<Enterprise>() {
                            }.getType());
                            list.add(enterprise);

                        } else {

                            SmallSite site = gson.fromJson(obj.toString(), new TypeToken<SmallSite>() {
                            }.getType());
                            list.add(site);

                        }
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        String url = dataType == 0 ? Resources.KEYENTERPRISE : Resources.SMALLSITE;
        request.execute(url, "");
    }


}
