package com.szzaq.jointdefence.activity.smallsite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.ImageActivity;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.adapter.ReviewAdapter;
import com.szzaq.jointdefence.model.SmallSiteCheck;
import com.szzaq.jointdefence.model.SmallSiteRecheck;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.utils.ScrollViewUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 三小场所检查记录详情
 *
 * @author jiajiaUser
 * @date 2015/07/21
 */
public class SmallSiteCheckActivity extends Activity implements OnClickListener {
    //场所基本信息
    private TextView place_name, document_number, check_person, check_time,
            rectification_date, check_address, review_button, tvDangerCount;
    //复查记录列表
    private ListView review_record_listview;
    //返回按钮
    private ImageView back_button, place_image;
    private SmallSiteCheck check;
    private LinearLayout layoutDangers;
    private ArrayList<Object> recheckList;
    private ReviewAdapter recheckAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView( R.layout.check_record_sanxiao);
        App.getInstance().addActivity(this);
        initView();
    }

    public void initView() {
        check = getIntent().getParcelableExtra("check");

        layoutDangers = (LinearLayout) findViewById(R.id.layoutDangers);
        place_image = (ImageView) this.findViewById( R.id.place_image);
        //复查按钮
        review_button = (TextView) this.findViewById( R.id.review_button);
        //场所名称
        place_name = (TextView) this.findViewById( R.id.place_name);
        //文书编号
        document_number = (TextView) this.findViewById( R.id.document_number);
        //检查人
        check_person = (TextView) this.findViewById( R.id.check_person);
        //检查时间
        check_time = (TextView) this.findViewById( R.id.check_time);
        //限期整改
        rectification_date = (TextView) this.findViewById( R.id.rectification_date);
        //检查地址
        check_address = (TextView) this.findViewById( R.id.check_address);
        //返回按钮
        back_button = (ImageView) this.findViewById( R.id.back_button);
        tvDangerCount = (TextView) this.findViewById( R.id.tvDangerCount);

        //复查记录列表
        review_record_listview = (ListView) this.findViewById( R.id.review_record_listview);
        review_record_listview.setPadding(DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 10));
        review_record_listview.setDivider(new ColorDrawable(Color.parseColor("#F5F5F5")));
        review_record_listview.setDividerHeight(DensityUtil.dip2px(this, 15));
        recheckList = new ArrayList<Object>();
        recheckAdapter = new ReviewAdapter(recheckList, this, 1);
        review_record_listview.setAdapter(recheckAdapter);
        getRecheckData();


        place_name.setText(check.getSite_name());
        document_number.setText("");
        check_person.setText(check.getInspector_names());
        check_time.setText(check.getCreated());
        rectification_date.setText(check.getExpiration());
        check_address.setText(check.getLocation());
        place_image.setScaleType(ScaleType.CENTER_CROP);
        ImageloaderUtils.loaderimage(Resources.URL_ROOT + check.getSite_img_url(), place_image);
        int dp120 = DensityUtil.dip2px(this, 120);
        int makeUpCount = 0;
        SmallSiteCheck.Danger[] dangers = check.getDangers();
        if (dangers != null) {
            String imgUrls = null;
            String imgNames = null;
            for (SmallSiteCheck.Danger danger : dangers) {
                if (danger.getStatus() == 1)
                    makeUpCount++;
                LinearLayout layout = new LinearLayout(this);
                LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 20, 10);
                layout.setLayoutParams(params);
                layout.setOrientation(LinearLayout.VERTICAL);
                ImageView iv = new ImageView(this);
                params = new LayoutParams(dp120, dp120);
                iv.setLayoutParams(params);
                iv.setScaleType(ScaleType.CENTER_CROP);
                String url = danger.getPhoto_before_url();
                if (!"".equals(url)) {
                    url = !url.contains(Resources.URL_ROOT) ? Resources.URL_ROOT + url : url;
                    if (imgUrls == null) {
                        imgUrls = url;
                        imgNames = danger.getDesc();
                    } else {
                        imgUrls += "--=--" + url;
                        imgNames += "--=--" + danger.getDesc();
                    }
                    ImageloaderUtils.loaderimage(url, iv);
                } else
                    iv.setImageResource( R.drawable.ic_launcher);
                layout.addView(iv);
                TextView tv = new TextView(this);
                tv.setText(danger.getDesc());
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                params = new LayoutParams(LayoutParams.MATCH_PARENT, 100);
                tv.setLayoutParams(params);
                layout.addView(tv);
                layoutDangers.addView(layout);
            }
            if (imgUrls != null) {
                final String[] urlArr = imgUrls.split("--=--");
                final String[] nameArr = imgNames.split("--=--");
                layoutDangers.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SmallSiteCheckActivity.this, ImageActivity.class);
                        intent.putExtra("imgUrls", urlArr);
                        intent.putExtra("imgNames", nameArr);
                        startActivity(intent);
                    }
                });
            }
            tvDangerCount.setText("存在隐患" + check.getDangers().length + "条，已整改" + makeUpCount + "条");
        }

        //调节高度
        ScrollViewUtils.setScrollViewHeight(review_record_listview, this, 0);
        //ScrollViewUtils.setToTop(review_record_listview, (ScrollView)this.findViewById(R.id.scrollview));


        back_button.setOnClickListener(this);
        review_button.setOnClickListener(this);
    }

    /**
     * 获取复查记录
     *
     * @return
     */
    private void getRecheckData() {
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {
                try {
                    JSONObject orgJson = new JSONObject(s);
                    JSONArray results = orgJson.optJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject json = results.getJSONObject(i);
                        Gson gson = new Gson();
                        SmallSiteRecheck recheck = gson.fromJson(json.toString(),
                                new TypeToken<SmallSiteRecheck>() {
                                }.getType());
                        recheckList.add(recheck);
                    }
                    recheckAdapter.notifyDataSetChanged();
                    ScrollViewUtils.setScrollViewHeight(review_record_listview, SmallSiteCheckActivity.this, 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        String url = Resources.SMALLSITE_RECHECK;
        url = url + "?parent__id=" + check.getId();
        request.execute(url, "");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case  R.id.back_button:
                this.finish();
                break;
            case  R.id.review_button://复查right-up corner
                Intent intent = new Intent(this, RecordDetailsActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("check", check);
                this.startActivity(intent);
                break;
            default:
                break;
        }

    }


}
