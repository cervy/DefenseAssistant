package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CrossCheckAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, Object>> list;
    private Context context;

    public CrossCheckAdapter(ArrayList<HashMap<String, Object>> list, Context context) {
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
        return
                list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        final HashMap<String, Object> map = list.get(position);
        view = inflater.inflate(R.layout.item_cross_check, null);
        final TextView tvJCDW, tvBCDW, tvJLS, tvYHS;
        tvJCDW = (TextView) view.findViewById(R.id.tvJCDW);
        tvBCDW = (TextView) view.findViewById(R.id.tvBCDW);
        tvJLS = (TextView) view.findViewById(R.id.tvJLS);
        tvYHS = (TextView) view.findViewById(R.id.tvYHS);
        //,tvZGS tvZGS = (TextView) view.findViewById(R.id.tvZGS);
        tvJCDW.setText(map.get("jcdw").toString());
        if (!map.containsKey("entNames")) {
            GetRequest request = new GetRequest();
            request.setCallback(new HttpCallback() {
                @Override
                public void handleData(String s) {
                    try {
                        JSONObject json = new JSONObject(s);
                        tvJLS.setText(json.optString("times", "0"));
                        tvYHS.setText(json.optString("dangers", "0"));
                        JSONArray ents = json.optJSONArray("ent_name");
                        String entNames = "";
                        if (ents.length() == 0)
                            entNames = "无";
                        for (int i = 0; i < ents.length(); i++) {

                            entNames += ents.getString(i) + "\n";
                        }
                        if (entNames.length() > 1)
                            entNames = entNames.substring(0, entNames.length() - 1);
                        tvBCDW.append(entNames);
                        list.get(pos).put("jls", json.optString("times", "0"));
                        list.get(pos).put("yhs", json.optString("dangers", "0"));
                        list.get(pos).put("entNames", entNames);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                }

            });
            request.execute(Resources.CROSS_CHECK + "?ent_id=" + map.get("ent_id").toString(), "");
        } else {
            tvJLS.setText(list.get(pos).get("jls").toString());
            tvYHS.setText(list.get(pos).get("yhs").toString());
            tvBCDW.setText(list.get(pos).get("entNames").toString());
        }

        return view;
    }

}
