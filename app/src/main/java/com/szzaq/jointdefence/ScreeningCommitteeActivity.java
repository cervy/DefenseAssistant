package com.szzaq.jointdefence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.szzaq.jointdefence.activity.smallsite.SmallSiteInfoActivity;
import com.szzaq.jointdefence.adapter.CommitteeAdapter;

/**
 * 三小场所筛选界面
 * @author jiajiaUser
 *
 */
public class ScreeningCommitteeActivity extends Activity {

	private ListView samll_placelistviewl;
	private List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_screening_committee);
		App.getInstance().addActivity(this);
		initView();
	}
	
	public void initView(){
		samll_placelistviewl = (ListView) this.findViewById(R.id.samll_placelistview);
		for (int i = 0; i < 15; i++) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("id",i+1);
			map.put("name","罗湖居委会"+i);
			list.add(map);
		}
		samll_placelistviewl.setAdapter(new CommitteeAdapter(list,this));
		samll_placelistviewl.setOnItemClickListener(new OnItemListView());
	}
	
	/**
	 * 
	 * @author jiajiaUser
	 *
	 */
	class OnItemListView implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent();
			intent.putExtra("name",list.get(arg2).get("name")+"");
			intent.putExtra("id",list.get(arg2).get("id")+"");
			setResult(SmallSiteInfoActivity.COMMITTEE_SUCCESS, intent);
			ScreeningCommitteeActivity.this.finish();
		}
		
	}
	
}
