package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szzaq.jointdefence.R;

import java.util.List;
/**
 * 场所信息中选择地区的item
 * @date 2015/07/08
 * @author jiajiaUser
 *
 */
public class ActivityinfoitemSelectAdapter extends BaseAdapter {

	private List<String> list;
	private Context context;
	
	
	public ActivityinfoitemSelectAdapter(List<String> list, Context context) {
		super();
		this.list = list;
		this.context = context;
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view;
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate( R.layout.activity_info_item_select, null);
		TextView item_select_name = (TextView) view.findViewById( R.id.info_item_select_name);
		item_select_name.setText(list.get(arg0));
		return view;
	}

}
