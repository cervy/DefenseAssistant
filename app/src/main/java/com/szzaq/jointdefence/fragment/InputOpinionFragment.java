package com.szzaq.jointdefence.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.AgentActivity;
import com.szzaq.jointdefence.App;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.activity.smallsite.SmallSiteScanningActivity;
import com.szzaq.jointdefence.adapter.SanXiaoChcekAdapter;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.utils.ScrollViewUtils;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * 三小场所扫描后填写意见的fragment
 * 
 * @author jiajiaUser
 * 
 */
public class InputOpinionFragment extends Fragment implements OnClickListener {
	private View view;
	// 隐患列表
	public ListView hidden_listview;
	// 隐患拍摄照片
	private GridView hidden_gridView;
	// 场所基本信息
	private TextView tvInspector, tvExpiration;
	//private EditText check_address;
	// 隐患图片的gridview数据集合
	public static ArrayList<HashMap<String, Object>> list_grid;
	public static ArrayList<HashMap<String, Object>> dangerList;
	// 隐患照片适配器
	public SanXiaoChcekAdapter sanXiaoChcekAdapter,dangerAdapter;
	// 回传值是否有选中
	private static int SUCCESS = 2;
	private Calendar calendar; // 通过Calendar获取系统时间
	private int mYear;
	private int mMonth;
	private int mDay;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate( R.layout.fragment_sanxiao_inputopinion,
				container, false);
		initView();
		return view;
	}

	public void initView() {
		dangerList = (ArrayList<HashMap<String, Object>>) this.getArguments().getSerializable("dangerTypes");
		hidden_listview = (ListView) view.findViewById( R.id.hidden_listview);
		hidden_gridView = (GridView) view.findViewById( R.id.hidden_gridview);
		tvInspector = (TextView) view.findViewById( R.id.tvInspector);
		tvExpiration = (TextView) view
				.findViewById( R.id.tvExpiration);
		Button btnSubmit = (Button) view.findViewById( R.id.btnSubmit);
		//check_address = (EditText) view.findViewById(R.id.check_address);
		// 隐患照片集合
		list_grid = new ArrayList<HashMap<String, Object>>();
		// 实例一个gridview适配器
		sanXiaoChcekAdapter = new SanXiaoChcekAdapter(list_grid, getActivity(),
				1, null, null, null);
		// 隐患照片gridview适配
		hidden_gridView.setAdapter(sanXiaoChcekAdapter);
		
		dangerAdapter = new SanXiaoChcekAdapter(dangerList,
				getActivity(), 0, sanXiaoChcekAdapter, list_grid,
				hidden_gridView);
		// 隐患列表适配
		hidden_listview.setAdapter(dangerAdapter);
		ScrollViewUtils.setScrollViewHeight(hidden_listview, getActivity(), 0);
		// 设置跳转界面选择联系人
		tvInspector.setOnClickListener(this);
		// 弹出时间框
		tvExpiration.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		tvInspector.setText(App.getInstance().getUser().getProfile().getNickname());
		// 获取当前时间
		calendar = Calendar.getInstance();
	}

	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case  R.id.tvInspector:
			Intent intent = new Intent(getActivity(), AgentActivity.class);
			intent.putExtra("type", SUCCESS);
			this.startActivityForResult(intent, SUCCESS);
			break;
		case  R.id.tvExpiration:
			getDatePickerDialog();
			break;
		case  R.id.btnSubmit:
			JSONStringer js = new JSONStringer();
			try {
				js.object()
					.key("site").value(((SmallSiteScanningActivity)getActivity()).site.getUrl())
					.key("dangers").array();
				
				for(HashMap<String,Object>map : list_grid){
					js.object()
						.key("desc").value(map.get("desc").toString())
						.key("status").value(0)
						.key("danger_type").value(Resources.URL_ROOT+map.get("dangerType").toString());
					Object photoUrl = map.get("photo_brefore");
					if(photoUrl!=null)
						js.key("photo_brefore").value(map.get("photo_brefore").toString());
					js.endObject();
				}
				js.endArray();
				js.key("inspectors").array()
					.value(App.getInstance().getUser().getUrl())
					.endArray()
					.key("lng").value(App.getInstance().getLngLat()[0])
					.key("lat").value(App.getInstance().getLngLat()[1])
					.key("location").value(App.getInstance().getLocation());
				String expiration = tvExpiration.getText().toString();
				if (!"".equals(expiration)){
					js.key("expiration").value(expiration+" 00:00");
				}
				js.endObject();
				String params = js.toString().replace("\\", "");
				Log.e("jsonppppppppppppppp", params);
				PostRequest request = new PostRequest();
				request.setCallback(new HttpCallback(){

					@Override
					public void handleData(String s) {
						getActivity().finish();
					}
					
				});
				request.execute(Resources.SMALLSITE_CHECK,
						params,
						getActivity());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 弹框选择时间
	 */
	public void getDatePickerDialog() {
		new DatePickerDialog(getActivity(),
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int month,
							int day) {
						mYear = year;
						mMonth = month;
						mDay = day;
						// 更新EditText控件日期 小于10加0
						tvExpiration.setText(new StringBuilder()
								.append(mYear)
								.append("-")
								.append((mMonth + 1) < 10 ? (mMonth + 1)
										: (mMonth + 1)).append("-")
								.append((mDay < 10) ? mDay : mDay));
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)).show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 跳转选择协办人
		if (requestCode == SUCCESS && resultCode == SUCCESS) {
			String selectNames = data.getStringExtra("name");
			String userName = tvInspector.getText().toString();
			tvInspector.setText(userName+("".equals(selectNames)?"":","+selectNames));
		}
	}

}
