package com.szzaq.jointdefence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.szzaq.jointdefence.activity.ke.EquipmentEditActivity;
import com.szzaq.jointdefence.adapter.ActivityinfoitemSelectAdapter;
import com.szzaq.jointdefence.utils.ScrollViewUtils;

public class SearchMaintenanceActivity extends Activity implements OnClickListener {

	private ImageView search_equipment_back,search_clear,search,search_clear_Two;
	//点击选择维保单位，提交信息
	private TextView search_commit;
	//填写维保单位控件
	private EditText search_maintenance,put_maintenance;
	//搜索结果列表
	private ListView search_listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_serach_maintenance);
		App.getInstance().addActivity(this);
		initView();
	}
	
	public void initView(){
		//返回键
		search_equipment_back = (ImageView) this.findViewById(R.id.search_equipment_back);
		//清空输入框按钮
		search_clear = (ImageView) this.findViewById(R.id.search_clear);
		//清空搜索框按钮
		search_clear_Two = (ImageView) this.findViewById(R.id.search_clear_Two);
		
		//搜查图标
		search = (ImageView) this.findViewById(R.id.search);
		//搜索结果列表
		search_listview = (ListView) this.findViewById(R.id.search_listview);
		//完成按钮
		search_commit = (TextView) this.findViewById(R.id.search_commit);
		//搜查框
		search_maintenance = (EditText) this.findViewById(R.id.search_maintenance);
		//输入框
		put_maintenance = (EditText) this.findViewById(R.id.put_maintenance);
		search_clear_Two.setOnClickListener(this);
		search_equipment_back.setOnClickListener(this);
		search_clear.setOnClickListener(this);
		search_commit.setOnClickListener(this);
		search.setOnClickListener(this);
		search_maintenance.addTextChangedListener(new OnEditTextTextWatcher(R.id.search_maintenance));
		put_maintenance.addTextChangedListener(new OnEditTextTextWatcher(R.id.put_maintenance));
		search_listview.setAdapter(new ActivityinfoitemSelectAdapter(getdata(), this));
		search_listview.setOnItemClickListener(new MaintenanceItemOnClick());
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.search_equipment_back:
			this.finish();
			break;
			//清空输入框内容
		case R.id.search_clear:
			put_maintenance.setText("");
			break;
			//清空搜索框内容
		case R.id.search_clear_Two:
			search_maintenance.setText("");
			break;
			//查询按钮点击事件
		case R.id.search:
			this.findViewById(R.id.search_result).setVisibility(View.VISIBLE);
			//调节高度
			ScrollViewUtils.setScrollViewHeight(search_listview, this, 0);
			break;
		case R.id.search_commit:
			Intent intent = new Intent();
			intent.putExtra("name",put_maintenance.getText()+"");
			 this.setResult(EquipmentEditActivity.RESULT_SUCCESS, intent);
			this.finish();
			break;
		default:
			break;
		}
	}
	
	
	
	
	/**
	 * 获取模拟数据
	 */
	public List<String> getdata(){
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 20;i++) {
			list.add("深圳维保单位"+i+"号");
		}
		return list;
	}
	
	/**
	 * 输入框和搜索框字符变化监听，实现清空按钮的展示与否
	 * @author jiajiaUser
	 *
	 */
	class OnEditTextTextWatcher implements TextWatcher{
		private int id;
		public  OnEditTextTextWatcher(int id){
			this.id = id;
		}
		@Override
		public void afterTextChanged(Editable arg0) {
		}
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			switch (id) {
			case R.id.search_maintenance:
				if(arg3>0){
					SearchMaintenanceActivity.this.findViewById(R.id.search_clear_Two).setVisibility(View.VISIBLE);
				}else{
					SearchMaintenanceActivity.this.findViewById(R.id.search_clear_Two).setVisibility(View.GONE);
				}
				
				break;
			case R.id.put_maintenance:
				if(arg3>0){
					SearchMaintenanceActivity.this.findViewById(R.id.search_clear).setVisibility(View.VISIBLE);
				}else{
					SearchMaintenanceActivity.this.findViewById(R.id.search_clear).setVisibility(View.GONE);
				}
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * 维保单位列表选择事件
	 */
	class MaintenanceItemOnClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			put_maintenance.setText(getdata().get(arg2));
		}
		
	}
	
	
}
