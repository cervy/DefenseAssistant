package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szzaq.jointdefence.utils.DensityUtil;

import java.util.HashMap;
import java.util.List;

public class CommitteeAdapter extends BaseAdapter {

    private List<HashMap<String, Object>> list;
    private Context context;

    public CommitteeAdapter(List<HashMap<String, Object>> list, Context context) {
        super();
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list!=null?list.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textview = new TextView(context);
        textview.setPadding(DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 10));
        textview.setText(list.get(position).get("name").toString());
        return textview;
    }

}
