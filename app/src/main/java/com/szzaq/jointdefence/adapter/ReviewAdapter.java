package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.activity.smallsite.RecordDetailsActivity;
import com.szzaq.jointdefence.model.SmallSiteCheck;
import com.szzaq.jointdefence.model.SmallSiteRecheck;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 复查记录适配器,重点单位设备隐患列表适配器 adapter_type,0为重点单位设备隐患适配器，1为三小场所复查记录适配器
 *
 * @author jiajiaUser
 * @date 2015/07/20
 */
public class ReviewAdapter extends BaseAdapter {
    // 声明imageloder
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private ArrayList<Object> list;
    private Context context;
    /**
     * 类型判断，0为重点单位设备隐患适配器，1为三小场所复查记录适配器,2为三小场所隐患列表
     */
    private int adapter_type;

    public ReviewAdapter(ArrayList<Object> list, Context context,
                         int adapter_type) {
        super();
        this.list = list;
        this.context = context;
        this.adapter_type = adapter_type;
    }

    @Override
    public int getCount() {
        if (list.size() == 0 && list == null) return 0;
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list  != null ? list.get(arg0) : null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        // 三小场所复查记录
        if (adapter_type == 1) {
            view = inflater.inflate(R.layout.sanxiao_review_record_item, null);
            TextView check_person, document_number, review_date;
            TableRow click_details;
            // 查看详情
            click_details = (TableRow) view.findViewById(R.id.click_details);
            check_person = (TextView) view.findViewById(R.id.check_person);
            document_number = (TextView) view
                    .findViewById(R.id.document_number);
            review_date = (TextView) view.findViewById(R.id.review_date);
            final SmallSiteRecheck recheck = (SmallSiteRecheck) list.get(position);
            check_person.setText(recheck.getInspector_names());
            document_number.setText("整改隐患数:" + recheck.getDangers().length);
            review_date.setText(recheck.getCreated());
            // 跳转三小场所复查记录详情界面
            click_details.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 三小场所复查详情界面
                    Intent intent = new Intent(context, RecordDetailsActivity.class);
                    // 标记界面类型为复查记录界面
                    intent.putExtra("type", 0);
                    intent.putExtra("recheck", recheck);
                    context.startActivity(intent);
                }
            });
        }
        // 重点单位的设备详情列表
        if (adapter_type == 0) {
            view = inflater.inflate(R.layout.zhongdian_task_details_item, null);
            // 设备编号TextView equipment_id;equipment_id = (TextView) view.findViewById(R.id.equipment_id);
            // 设备图片
            ImageView[] imageViews = new ImageView[3];
            imageViews[0] = (ImageView) view.findViewById(R.id.imageView1);
            imageViews[1] = (ImageView) view.findViewById(R.id.imageView2);
            imageViews[2] = (ImageView) view.findViewById(R.id.imageView3);
        /*			equipment_id.setText(list.get(arg0).get("equipment_id") + "");
                    String[] imagestrs = (String[]) list.get(arg0).get("imagestr");
					for (int i = 0; i < imagestrs.length; i++) {
						ImageloaderUtils.loaderimage("", imageViews[i]);
						// imageViews[i].setOnClickListener();
					}*/
        }
        // 三小场所复查记录里面的隐患列表
        if (adapter_type == 2) {
            view = inflater.inflate(R.layout.hidden_trouble_item, null);
            // 设备编号
            final TextView hidden_title;
            hidden_title = (TextView) view.findViewById(R.id.hidden_title);
            // 设备图片
            final ImageView[] imageViews = new ImageView[2];
            imageViews[0] = (ImageView) view.findViewById(R.id.imageView1);
            imageViews[1] = (ImageView) view.findViewById(R.id.imageView2);
            for (ImageView imageView : imageViews) {
                imageView.setScaleType(ScaleType.CENTER_CROP);
            }
            String url = (String) list.get(position);
            GetRequest request = new GetRequest();
            request.setCallback(new HttpCallback() {
                @Override
                public void handleData(String s) {
                    try {
                        JSONObject json = new JSONObject(s);
                        hidden_title.setText(json.optString("desc"));
                        String photoBefore = json.optString("photo_before_url");
                        photoBefore = photoBefore.contains(Resources.URL_ROOT) ? photoBefore : Resources.URL_ROOT + photoBefore;
                        ImageloaderUtils.loaderimage(photoBefore, imageViews[0]);
                        String photoAfter = json.optString("photo_after_url");
                        photoAfter = photoAfter.contains(Resources.URL_ROOT) ? photoAfter : Resources.URL_ROOT + photoAfter;
                        ImageloaderUtils.loaderimage(photoAfter, imageViews[1]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            });
            request.execute(url, "");
/*			hidden_title.setText(list.get(arg0).get("hidden_title") + "");
            String[] imagestrs = (String[]) list.get(arg0).get("imagestr");
			for (int i = 0; i < imagestrs.length; i++) {
				ImageloaderUtils.loaderimage("", imageViews[i]);
			}*/
        }
        // 三小场所复查里面的隐患列表
        if (adapter_type == 3) {
            SmallSiteCheck.Danger danger = (SmallSiteCheck.Danger) list.get(position);
            view = inflater.inflate(R.layout.check_hidden_trouble_item, null);
            //解决按钮
            CheckBox checkbox_button;
            // 隐患标题
            TextView hidden_title;
            hidden_title = (TextView) view.findViewById(R.id.hidden_title);
            checkbox_button = (CheckBox) view.findViewById(R.id.checkbox_button);
            checkbox_button.setTag(position);
            if (danger.getStatus() == 1)
                checkbox_button.setChecked(true);
            checkbox_button.setOnCheckedChangeListener(new OnRemoveHidden());
            // 隐患图片
            ImageView[] imageViews = new ImageView[2];
            imageViews[0] = (ImageView) view.findViewById(R.id.imageView1);
            imageViews[1] = (ImageView) view.findViewById(R.id.imageView2);
            //设置当前索引为tag,在activity中的获取图片是，方便添加进集合中
            imageViews[1].setTag(position);
            hidden_title.setText(danger.getDesc());
            String imagestrs = danger.getPhoto_before_url();
            if (!"".equals(imagestrs)) {
                imageViews[0].setScaleType(ScaleType.CENTER_CROP);
                ImageloaderUtils.loaderimage(Resources.URL_ROOT + imagestrs, imageViews[0]);
            }

            String photo_after_url = danger.getPhoto_after_url();
            if (!"".equals(photo_after_url)) {
                imageViews[1].setScaleType(ScaleType.CENTER_CROP);
                if (!photo_after_url.contains(Resources.URL_ROOT))
                    photo_after_url = Resources.URL_ROOT + photo_after_url;
                ImageloaderUtils.loaderimage(photo_after_url, imageViews[1]);
            }
        }
        return view;
    }


    /**
     * 选择解决的隐患
     */

    class OnRemoveHidden implements OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton checkBox, boolean checked) {
            //点击复选框可改变当前隐患的状态
            PostRequest request = new PostRequest();

            int status = 0;
            String img = null;
            SmallSiteCheck.Danger danger = (SmallSiteCheck.Danger) list.get((Integer) checkBox.getTag());
            if (checked) {
                status = 1;
                img = danger.getPhoto_after();
            }

            danger.setStatus(status);

            try {
                //JSONStringer js = new JSONStringer();
                Gson gson = new Gson();
                //JsonElement je;

                JSONObject json = new JSONObject(gson.toJson(danger));
                json.put("_method", "PUT");
                json.put("status", status);
                json.put("photo_after", img);
                /*request.setCallback(new HttpCallback() {

                    @Override
                    public void handleData(String s) {
                        try {
                            JSONObject json = new JSONObject(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                });*/

                request.execute(danger.getUrl(),
                        json.toString().replace("\\", ""),
                        context);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

}
