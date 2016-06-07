package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szzaq.jointdefence.R;

import java.util.HashMap;
import java.util.List;

public class FireStationMemberAdapter extends BaseAdapter {

    private List<HashMap<String, Object>> list;
    private Context context;

    public FireStationMemberAdapter(List<HashMap<String, Object>> list, Context context) {
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
        return list != null && list.get(position) != null ? list.get(position) : null;
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
        view = inflater.inflate(R.layout.item_contact, null);
        TextView tvName;//   名和号码
        // ImageView ivCallPhone;
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvName.setText(map.get("userName").toString() + "  :  " + map.get("phone").toString());
        //ivCallPhone = (ImageView) view.findViewById(R.id.ivCallPhone);
        //final String phone = map.get("phone").toString();
        /*ivCallPhone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        });*/
        return view;
    }

}
