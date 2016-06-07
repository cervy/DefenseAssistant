package com.szzaq.jointdefence;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import com.szzaq.jointdefence.adapter.ReviewAdapter;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.ScrollViewUtils;

/**
 * 任务详情界面
 *
 * @author jiajiaUser
 * @date 2015/07/14
 */
public class TaskActivityDetails extends Activity implements OnClickListener {

    private ImageView task_details_back, task_captrue, imageView1, imageView2, imageView3;
    private TextView task_name, task_type, designated_person, last_task_time, last_task_trouble, textView1, textView2, textView3;
    //声明imageloder
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private LinearLayout image_click;
    //复查记录，重点单位没有改项
    private ListView review_record_listview;
    private Button check_id;
    //任务数据
    private HashMap<String, Object> map;
    //场所类型,0为重点单位，1为三小
    private int place_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_task_details);
        App.getInstance().addActivity(this);
        place_type = getIntent().getIntExtra("place_type", 0);
        initView();

    }

    //初始化控件
    public void initView() {
        //检查按钮，只在三小出现
        check_id = (Button) this.findViewById(R.id.check_id);
        //复查记录
        review_record_listview = (ListView) this.findViewById(R.id.review_record_listview);
        //相册
        image_click = (LinearLayout) this.findViewById(R.id.image_click);
        //返回键
        task_details_back = (ImageView) this.findViewById(R.id.task_details_back);
        //扫一扫按钮
        task_captrue = (ImageView) this.findViewById(R.id.task_captrue);
        //隐患第一张图片
        imageView1 = (ImageView) this.findViewById(R.id.imageView1);
        //第二张
        imageView2 = (ImageView) this.findViewById(R.id.imageView2);
        //第三张
        imageView3 = (ImageView) this.findViewById(R.id.imageView3);
        //任务名称
        task_name = (TextView) this.findViewById(R.id.task_name);
        //类型
        task_type = (TextView) this.findViewById(R.id.task_type);
        //指派人
        designated_person = (TextView) this.findViewById(R.id.designated_person);
        //上次检查时间
        last_task_time = (TextView) this.findViewById(R.id.last_task_time);
        //存在隐患
        last_task_trouble = (TextView) this.findViewById(R.id.last_task_trouble);
        //条目1
        textView1 = (TextView) this.findViewById(R.id.textView1);
        //条目2
        textView2 = (TextView) this.findViewById(R.id.textView2);
        //条目3
        textView3 = (TextView) this.findViewById(R.id.textView3);
        //返回键
        task_details_back.setOnClickListener(this);
        //相册
        image_click.setOnClickListener(this);
        //扫描
        task_captrue.setOnClickListener(this);
        task_name.setText("深圳蛇口大酒店");
        task_type.setText("日常检查");
        designated_person.setText("系统");
        last_task_time.setText("2015-06-09");
        last_task_trouble.setText("设备运行故障");
        textView1.setText("9.经营场所内没有贴安全标识");
        textView2.setText("7.经营场所内没有贴安全标识");
        textView3.setText("8.经营场所内没有贴安全标识");
        if (place_type == 0) {
            //this.findViewById(R.id.sanxiao_hidden_trouble).setVisibility(View.GONE);
            this.findViewById(R.id.record_title).setVisibility(View.GONE);
            this.findViewById(R.id.record_title_1).setVisibility(View.VISIBLE);
            this.findViewById(R.id.record_title_line).setVisibility(View.VISIBLE);
            check_id.setVisibility(View.GONE);
        } else {
            ImageloaderUtils.loaderimage("", imageView1);
            ImageloaderUtils.loaderimage("", imageView2);
            ImageloaderUtils.loaderimage("", imageView3);
            review_record_listview.setPadding(DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 10));
            review_record_listview.setDivider(new ColorDrawable(Color.parseColor("#F5F5F5")));
            review_record_listview.setDividerHeight(DensityUtil.dip2px(this, 15));

        }
        //模拟数据绑定
        review_record_listview.setAdapter(new ReviewAdapter(getdata(place_type), this, place_type));
        ScrollViewUtils.setScrollViewHeight(review_record_listview, this, 40);
        //默认回到顶部
        ScrollViewUtils.setToTop(review_record_listview, (ScrollView) this.findViewById(R.id.task_scrollview));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();
        switch (id) {
            case R.id.task_details_back:
                this.finish();
                break;
            case R.id.task_captrue:
                intent = new Intent(this, CaptureActivity.class);
                this.startActivity(intent);
                break;
            case R.id.image_click:
                intent = new Intent(this, ImageActivity.class);
                this.startActivity(intent);
                break;
            default:
                break;
        }

    }

    /**
     * 模拟数据
     */
    public ArrayList<Object> getdata(int type) {
        ArrayList<Object> list = new ArrayList<Object>();
        if (type == 1) {
            for (int i = 0; i < 3; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("check_person", "王五");
                map.put("document_number", "szsn10100011");
                map.put("review_date", "2015-07-05");
                list.add(map);
            }
        }
        if (type == 0) {
            for (int i = 0; i < 3; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("equipment_id", "SZCN1020121");
                String str = "http://img4.duitang.com/uploads/item/201302/08/20130208100856_52frh.thumb.600_0.jpeg";
                map.put("imagestr", new String[]{str, str, str});
                list.add(map);
            }
        }
        return list;
    }


}
