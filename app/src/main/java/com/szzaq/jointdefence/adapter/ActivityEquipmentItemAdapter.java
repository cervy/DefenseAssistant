package com.szzaq.jointdefence.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.activity.ke.EquipmentEditActivity;

import java.util.HashMap;
import java.util.List;

public class ActivityEquipmentItemAdapter extends BaseAdapter implements OnClickListener {

    private List<HashMap<String, Object>> list;
    private Context context;

    public ActivityEquipmentItemAdapter(List<HashMap<String, Object>> list,
                                        Context context) {
        super();
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {

        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int arg0) {
        return list != null && list.get(arg0) != null ? list.get(arg0) : null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        //加载布局文件
        view = inflater.inflate(R.layout.activity_equipment_list_item, null);
        //获取控件
        TextView equipment_name, equipment_number, equipment_location, maintenance, maintenance_person;
        //button
        Button equipment_edit = (Button) view.findViewById(R.id.equipment_edit);
        equipment_edit.setTag(arg0);
        equipment_edit.setOnClickListener(this);
        equipment_name = (TextView) view.findViewById(R.id.equipment_name);
        equipment_number = (TextView) view.findViewById(R.id.equipment_number);
        equipment_location = (TextView) view.findViewById(R.id.equipment_location);
        maintenance = (TextView) view.findViewById(R.id.maintenance);
        maintenance_person = (TextView) view.findViewById(R.id.maintenance_person);
        //获取值绑定到控件
        equipment_name.setText(list.get(arg0).get("equipment_name").toString());
        equipment_number.setText(list.get(arg0).get("equipment_number").toString());
        equipment_location.setText(list.get(arg0).get("equipment_location").toString());
        maintenance.setText(list.get(arg0).get("maintenance").toString());
        maintenance_person.setText(list.get(arg0).get("maintenance_person").toString());
        //返回
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.equipment_edit:
                HashMap<String, Object> map = list.get(Integer.parseInt(v.getTag() + ""));
                Intent intent = new Intent(context, EquipmentEditActivity.class);
                intent.putExtra("map", map);
                intent.putExtra("equipment_type", 0);
                context.startActivity(intent);
                break;

            default:
                break;
        }

    }


}
