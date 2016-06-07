package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.szzaq.jointdefence.R;

import java.util.HashMap;
import java.util.List;

import com.szzaq.jointdefence.utils.DensityUtil;

/**
 * 三小场所检查界面中的隐患列表和拍摄照片gridview适配器
 * 
 * @date 2015/07/22
 * @author jiajiaUser
 * 
 */
public class SanXiaoChcekAdapter extends BaseAdapter {

	private List<HashMap<String, Object>> list;
	//
	private Context context;
	/**
	 * 0为隐患列表，1为gridview
	 */
	private int type;
	/**
	 * gridviewadapter
	 * 
	 * @param list
	 * @param context
	 * @param type
	 */
	private SanXiaoChcekAdapter sanXiaoChcekAdapter;
	// gridview数据集，方便添加数据
	private List<HashMap<String, Object>> grid_list;
	//为了能够动态设置scrollview的高度，需要将listview传进来
	private GridView hidden_gridview;
	public SanXiaoChcekAdapter(List<HashMap<String, Object>> list,
			Context context, int type, SanXiaoChcekAdapter sanXiaoChcekAdapter,
			List<HashMap<String, Object>> grid_list,GridView hidden_gridview) {
		super();
		this.list = list;
		this.context = context;
		this.type = type;
		this.grid_list = grid_list;
		this.sanXiaoChcekAdapter = sanXiaoChcekAdapter;
		this.hidden_gridview = hidden_gridview;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = null;
		LayoutInflater inflater = LayoutInflater.from(context);
		if (type == 0) {
			view = inflater.inflate(R.layout.sanxiao_hidden_item, null);
			TextView hidden_title;
			final CheckBox hidden_checkBox;
			hidden_title = (TextView) view.findViewById(R.id.hidden_title);
			hidden_checkBox = (CheckBox) view
					.findViewById(R.id.hidden_checkBox);
			hidden_checkBox.setTag(arg0);
			hidden_checkBox
					.setOnCheckedChangeListener(new CheckHiddenOnClick());
			hidden_title.setText(list.get(arg0).get("desc").toString());
			view.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					hidden_checkBox.performClick();
					
				}
				
			});			
		}
		if (type == 1) {
			view = inflater
					.inflate(R.layout.sanxiao_hidden_gridview_item, null);
			TextView hidden_title;
			ImageView hidden_image;
			hidden_title = (TextView) view.findViewById(R.id.hidden_title);
			hidden_image = (ImageView) view.findViewById(R.id.hidden_image);
			hidden_image.setScaleType(ScaleType.CENTER_CROP);
			hidden_image.setTag(arg0);
			hidden_title.setText(list.get(arg0).get("desc").toString());
			Bitmap bitmap = (Bitmap) list.get(arg0).get("bitmap");
			if(bitmap!=null){
				hidden_image.setImageBitmap(bitmap);
			}
			
		}
		return view;
	}

	/**
	 * listview中选择隐患的checkbox按钮事件
	 * @author jiajiaUser
	 *
	 */
	class CheckHiddenOnClick implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("desc", list.get((Integer) buttonView.getTag())
					.get("desc"));
			map.put("dangerType", list.get((Integer) buttonView.getTag())
					.get("url"));
			if (isChecked) {
				grid_list.add(map);
				//Log.i("ss", grid_list.size() + "");
				sanXiaoChcekAdapter.notifyDataSetChanged();
				LayoutParams params = hidden_gridview.getLayoutParams();
				int hang = (grid_list.size()%3);
				if(hang!=0){
					hang = (grid_list.size()/3)+1;
				}else{
					hang = grid_list.size()/3;
				}
				params.height = hang* DensityUtil.dip2px(context,140);
				hidden_gridview.setLayoutParams(params);
			}else{
				grid_list.remove(map);
				sanXiaoChcekAdapter.notifyDataSetChanged();
			}
		}
	}

}
