package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.activity.ke.ZhongdianScanningActivity;
import com.szzaq.jointdefence.model.DeviceCheck;
import com.szzaq.jointdefence.model.SmallSiteCheck;
import com.szzaq.jointdefence.model.Task;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.Resources;

import java.util.List;

/**
 * 主界面list适配器,待办任务以及检查对象
 * @date 2015/07/07
 * @author jiajiaUser
 *
 */
public class ListviewAdapter extends BaseAdapter {

	private List<Object> list;
	private Context context;
	//列表展示类型，0为待办任务，1为检查对象
	private int listViewType;
	
	public ListviewAdapter(List<Object> list, Context context, int listViewType) {
		super();
		this.list = list;
		this.context = context;
		this.listViewType = listViewType;
	}

	@Override
	public int getCount() {
		return list!=null&&list.size()!=0?list.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		return list!=null&&list.get(arg0)!=null?list.get(arg0):null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int location, View arg1, ViewGroup arg2) {//3种
		View view = null;
		LayoutInflater inflater = LayoutInflater.from(context);
 		Object obj = list.get(location);
 		if (obj instanceof DeviceCheck){
 		 	view = inflater.inflate( R.layout.activity_main_listview_item, null);
 			TextView item_name,item_place_type,item_assigned_or_check_person,item_date,item_hidden_trouble,tvNo; 
 		 	item_name  = (TextView) view.findViewById( R.id.item_name);
 		 	item_place_type  = (TextView) view.findViewById( R.id.item_place_type);
 		 	item_assigned_or_check_person  = (TextView) view.findViewById(R.id.item_assigned_or_check_person);
 		 	item_date  = (TextView) view.findViewById( R.id.item_date);
 		 	item_hidden_trouble = (TextView) view.findViewById( R.id.item_hidden_trouble);
 	 		item_hidden_trouble.setVisibility(View.VISIBLE); 	
 	 		tvNo = (TextView) view.findViewById( R.id.tvNo);
 	 		tvNo.setText(String.valueOf(location+1));
	 		DeviceCheck deviceCheck = (DeviceCheck) list.get(location);
			item_place_type.setText(deviceCheck.getEnterprise_name());
		 	//名称
			item_name.setText(deviceCheck.getDevice_name());
			//这里表示为指派人
		 	item_assigned_or_check_person.setText("检查人："+deviceCheck.getInspector_name());
		 	//到期时间
		 	String created = deviceCheck.getCreated();
		 	created = created.length()>16?created.substring(0, 16):created;
		 	created = created.replace("T", " ");
		 	item_date.setText("检查时间:"+created);
		 	int trouble = Integer.parseInt(deviceCheck.getStatus());
		 	//trouble为0时无隐患，1为有隐患
		 	if(trouble==1){
		 		item_hidden_trouble.setTextColor(Color.parseColor("#20B446"));
		 		item_hidden_trouble.setText("无隐患");
		 	}else{
		 		item_hidden_trouble.setTextColor(Color.parseColor("#B42020"));
		 		item_hidden_trouble.setText("有隐患");
		 	}
		 	
		 	String inspectorPortrait = deviceCheck.getInspector_portrait();
		 	if (!"".equals(inspectorPortrait)){
		 		ImageView ivPortrait = (ImageView) view.findViewById( R.id.ivPortrait);
		 		ImageloaderUtils.loaderimage(Resources.URL_ROOT+inspectorPortrait, ivPortrait);
		 	}
 		}if (obj instanceof SmallSiteCheck){
 		 	view = inflater.inflate( R.layout.activity_main_listview_item, null);
 			TextView item_name,item_place_type,item_assigned_or_check_person,item_date,item_hidden_trouble,tvNo; 
 		 	item_name  = (TextView) view.findViewById( R.id.item_name);
 		 	item_place_type  = (TextView) view.findViewById( R.id.item_place_type);
 		 	item_assigned_or_check_person  = (TextView) view.findViewById( R.id.item_assigned_or_check_person);
 		 	item_date  = (TextView) view.findViewById( R.id.item_date);
 		 	item_hidden_trouble = (TextView) view.findViewById( R.id.item_hidden_trouble);
 	 		item_hidden_trouble.setVisibility(View.VISIBLE); 	
 	 		 tvNo = (TextView) view.findViewById( R.id.tvNo);
 	 		 tvNo.setText(String.valueOf(location+1));
	 		SmallSiteCheck check = (SmallSiteCheck) list.get(location);
			//item_place_type.setText(check.getSite_name());
	 		item_place_type.setVisibility(View.GONE);
		 	//名称
			item_name.setText(check.getSite_name());
			//这里表示为指派人
		 	item_assigned_or_check_person.setText("检查人："+check.getInspector_names());
		 	//到期时间
		 	String created = check.getCreated();
		 	created = created.length()>16?created.substring(0, 16):created;
		 	created = created.replace("T", " ");
		 	item_date.setText("检查时间:"+created);
		 	int trouble = check.getDangers().length;
		 	//trouble为0时无隐患，1为有隐患
		 	if(trouble==0){
		 		item_hidden_trouble.setTextColor(Color.parseColor("#20B446"));
		 		item_hidden_trouble.setText("无隐患");
		 	}else{
		 		item_hidden_trouble.setTextColor(Color.parseColor("#B42020"));
		 		item_hidden_trouble.setText("有隐患");
		 	}
		 	
		 	String inspectorPortrait = check.getInspector_portrait();
		 	if (!"".equals(inspectorPortrait)){
		 		ImageView ivPortrait = (ImageView) view.findViewById( R.id.ivPortrait);
		 		ImageloaderUtils.loaderimage(Resources.URL_ROOT+inspectorPortrait, ivPortrait);
		 	} 			
 		}
 		else if(obj instanceof Task){
 			Task task = (Task) list.get(location);
 		 	view = inflater.inflate( R.layout.activity_main_listview_item_task, null);
 			TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
 			TextView tvContent = (TextView) view.findViewById( R.id.tvContent);
 			TextView tvStatus = (TextView) view.findViewById( R.id.tvStatus);
 			TextView tvExpiration = (TextView) view.findViewById( R.id.tvExpiration);
 			TextView tvNo = (TextView) view.findViewById( R.id.tvNo);
 	 		tvNo.setText(String.valueOf(location+1));
 			Button btnExecute = (Button) view.findViewById( R.id.btnExecute);
 			final String deviceUrl = task.getRelated_device();
 			final String fromTask = task.getUrl();
	 		
	 		tvContent.setText(task.getContent());
		 	//名称
			tvTitle.setText(task.getTitle());
			//这里表示为指派人
			boolean isComplete = !"0".equals(task.getStatus());
			tvStatus.setText("状态："+ (isComplete?"已完成":"未完成"));
			tvStatus.setTextColor(isComplete?Color.parseColor("#20B446"):Color.parseColor("#B42020"));
		 	//到期时间
			String expiration = task.getExpiration();
			expiration = expiration.length()>10?expiration.substring(0, 10):expiration;
			tvExpiration.setText("截止时间:"+expiration);
			if(isComplete || "1".equals(task.getTask_type())){
				btnExecute.setVisibility(View.GONE);
			}else{
	 			btnExecute.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(context,ZhongdianScanningActivity.class);
						intent.putExtra("deviceUrl", deviceUrl);
						intent.putExtra("fromTask", fromTask);
						context.startActivity(intent);
					}
	 			});
 			}
 		}
		return view;
	}

}
