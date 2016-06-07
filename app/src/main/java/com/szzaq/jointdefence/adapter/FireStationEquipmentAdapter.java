package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.szzaq.jointdefence.R;

import java.util.HashMap;
import java.util.List;

public class FireStationEquipmentAdapter extends BaseAdapter {

    private List<HashMap<String, Object>> list;
    private Context context;

    public FireStationEquipmentAdapter(List<HashMap<String, Object>> list, Context context) {
        super();
        this.list = list;
        this.context = context;
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
        HashMap<String, Object> map = list.get(position);
        view = inflater.inflate(R.layout.item_microstation_equipment, null);
        TextView tvName, tvQuantity;
        ImageView ivIcon;
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvName.setText(map.get("equipmentName").toString());
        tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
        tvQuantity.setText(map.get("quantity").toString());
        ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        try {
            ivIcon.setImageResource(Integer.parseInt(map.get("icon").toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}
