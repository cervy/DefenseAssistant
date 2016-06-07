package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.model.Configuration;
import com.szzaq.jointdefence.model.DeviceCheck;

import java.util.List;

/**
 * 维保记录，检查记录，自查记录，以及整个计划适配器
 *
 * @author jiajiaUser
 */
public class ZhongDianTabAdapter extends BaseAdapter {
    private List<Object> list;
    private Context context;
    private int type;

    public ZhongDianTabAdapter(List<Object> list,
                               Context context, int type) {
        super();
        this.list = list;
        this.context = context;
        this.type = type;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int arg0) {
        return list!=null?list.get(arg0):null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        //检查记录，维保记录，自查记录适配
        if (type == 0) {
            view = inflater.inflate(R.layout.activity_tabfragment_item, null);
            DeviceCheck check = (DeviceCheck) list.get(position);
            TextView tab_title, tab_person, tab_date;
            tab_title = (TextView) view.findViewById(R.id.tab_title);

            tab_person = (TextView) view.findViewById(R.id.tab_person);
            tab_date = (TextView) view.findViewById(R.id.tab_date);
            tab_title.setText(check.getInspector_name());
            tab_person.setText(check.getCreated());
            Configuration config = App.getInstance().getConfig();
            tab_date.setText(config.getTypeName(config.DEVICE_STATUS_CHOICES, check.getStatus()));
        } else {
            //整改计划适配
            ViewHolder holder;
            Configuration config = App.getInstance().getConfig();
            if (convertView == null) {
                DeviceCheck.Task task = (DeviceCheck.Task) list.get(position);
                convertView = inflater.inflate(R.layout.zhongdian_check_details, null);
                holder = new ViewHolder();
                holder.check_item_title = (TextView) convertView.findViewById(R.id.check_item_title);
                holder.person_name = (TextView) convertView.findViewById(R.id.person_name);
                holder.processing_time = (TextView) convertView.findViewById(R.id.processing_time);
                holder.processing_results = (TextView) convertView.findViewById(R.id.processing_results);
                holder.check_item_title.setText((position + 1) + "." + config.getTypeName(config.TASK_TYPE_CHOICES, task.getTask_type() + ""));
                holder.person_name.setText(task.getUser_name());
                holder.processing_time.setText(task.getExpiration().substring(0, 10));
                holder.processing_results.setText(config.getTypeName(config.TASK_STATUS_CHOICES, task.getStatus() + ""));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

        return view;
    }


    public static class ViewHolder {
        public TextView check_item_title, person_name, processing_time, processing_results;
    }
}
