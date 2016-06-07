package com.szzaq.jointdefence.activity.ke;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.adapter.ActivityEquipmentItemAdapter;
import com.szzaq.jointdefence.utils.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 设备清单界面
 * @date 2015/07/09
 * @author jiajiaUser
 *
 */
public class EquipmentListActivity extends Activity implements OnClickListener{

	private ListView equipment_listview;
	//返回
	private ImageView equipment_back,equipment_add;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView( R.layout.activity_equipment_list);
		App.getInstance().addActivity(this);
		equipment_listview = (ListView) this.findViewById( R.id.equipment_listview);
		equipment_back = (ImageView) this.findViewById( R.id.equipment_back);
		equipment_add = (ImageView) this.findViewById( R.id.equipment_add);
		equipment_back.setOnClickListener(this);
		equipment_add.setOnClickListener(this);
		//设置listview分割线颜色
		equipment_listview.setDivider(new ColorDrawable(Color.parseColor("#F5F5F5"))); 
		//分割线高度
		equipment_listview.setDividerHeight(DensityUtil.dip2px(this,30));
		//绑定adapter
		equipment_listview.setAdapter(new ActivityEquipmentItemAdapter(getdata(), this));
	}
	
	//返回键
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.equipment_back:
			this.finish();
			break;
		case  R.id.equipment_add:
			Intent intent = new Intent(this,EquipmentEditActivity.class);
			intent.putExtra("equipment_type",1);
			this.startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 获取模拟数据
	 * @return
	 */
	public List<HashMap<String,Object>> getdata(){
		 List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		 for (int i = 0; i < 10; i++) {
			 HashMap<String,Object> map = new HashMap<String, Object>();
			 map.put("equipment_name","火警控制器"+i);
			 map.put("equipment_number","SZN23454334"+i);
			 map.put("equipment_location","首层控制室"+i);
			 map.put("maintenance","广东省消防工程有限公司"+i);
			 map.put("maintenance_person","孙浩"+i);
			 map.put("id",i);
			 list.add(map);
		}
		 return list;
	}
	
	
}
