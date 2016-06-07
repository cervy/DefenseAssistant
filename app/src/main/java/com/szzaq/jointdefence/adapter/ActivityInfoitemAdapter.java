package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.szzaq.jointdefence.MapActivity;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.model.Enterprise;
import com.szzaq.jointdefence.model.SmallSite;

import java.util.ArrayList;

/**
 * 场所信息列表的适配器，三小场所和重点单位都使用该适配器
 *
 * @author jiajiaUser
 * @date 2015/07/08
 */
public class ActivityInfoitemAdapter extends BaseAdapter {

    private ArrayList<Object> list;
    private Context context;
    private int place_type;

    public ActivityInfoitemAdapter(ArrayList<Object> list, Context context,
                                   int place_type) {
        super();
        this.list = list;
        this.context = context;
        this.place_type = place_type;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.activity_info_item, null);
        // 场所编号一列
        TableRow info_sanxiao;
        TextView item_name, placeAddress, place_responsibility, placetelephone;//placeNumber,
        //placeNumber = (TextView) view.findViewById(R.id.place_number);
        item_name = (TextView) view.findViewById(R.id.info_item_name);
        placeAddress = (TextView) view.findViewById(R.id.PlaceAddress);
        place_responsibility = (TextView) view
                .findViewById(R.id.Place_responsibility);
        placetelephone = (TextView) view.findViewById(R.id.Placetelephone);
        info_sanxiao = (TableRow) view.findViewById(R.id.info_sanxiao);
        /* item_select_name.setText(list.get(position).get("name")+""); */
        // 重点单位
        if (place_type == 0) {
            // 隐藏场所编号这一列
            info_sanxiao.setVisibility(View.GONE);
            final Enterprise enterprise = (Enterprise) list.get(position);
            item_name.setText(enterprise.getName());
            placeAddress.setText(enterprise.getAddress());
            place_responsibility.setText(enterprise.getIncharge_name());
            placetelephone.setText(enterprise.getIncharge_mobile());
        }
        // 三小场所
        if (place_type == 1) {
            final SmallSite site = (SmallSite) list.get(position);
            // 实例化场所编号控件
            // 展示这一列
            info_sanxiao.setVisibility(View.VISIBLE);

            item_name.setText(site.getName());
            // 获取编号数据
            // placeNumber.setText(site.getSite_type());
            // 获取场所地址
            placeAddress.setText(site.getAddress());
            // 获取场所责任人
            place_responsibility.setText(site.getResponsible());
            // 获取场所电话
            placetelephone.setText(site.getResponsible_phone());
            ImageView ivLoc = (ImageView) view.findViewById(R.id.ivLocation);
            ivLoc.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtra("site", site);
                    context.startActivity(intent);

                }

            });
        }

        return view;
    }

}
