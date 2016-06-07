package com.szzaq.jointdefence;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.szzaq.jointdefence.utils.DensityUtil;
/**
 * 消息详细界面
 * @author jiajiaUser
 *
 */
public class MessageDetailsActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_message_details);
		App.getInstance().addActivity(this);
		String title = getIntent().getStringExtra("title");
		ListView listview = (ListView) this.findViewById(R.id.listview);
		findViewById(R.id.message_back).setOnClickListener(this);
		TextView message_details_title = (TextView) this.findViewById(R.id.message_details_title);
		message_details_title.setText(title);
		//listview.setAdapter(new ActivityMessageAdapter(getdata(),this,1));
		listview.setDivider(new ColorDrawable(Color.parseColor("#F5F5F5")));
		listview.setDividerHeight(DensityUtil.dip2px(this,15));
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.message_back:
			this.finish();
			break;

		default:
			break;
		}
	
	}
	
	/**
	 * 模拟数据
	 */
	public List<HashMap<String,Object>> getdata(){
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < 3; i++) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("message_details_date","2015-07-10 16:50");
			map.put("message_details_conten","【消防监督平台】提醒您有一条待办事项，对象为三小场所（深圳南山蛇口小档口）。任务类型：日常检查，指派人：系统，到期时间：7-30");
			list.add(map);
		}
	return list;
	}
}
