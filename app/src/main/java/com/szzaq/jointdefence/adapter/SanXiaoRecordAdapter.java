package com.szzaq.jointdefence.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.szzaq.jointdefence.ImageActivity;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.activity.smallsite.SmallSiteCheckActivity;
import com.szzaq.jointdefence.model.SmallSiteCheck;
import com.szzaq.jointdefence.utils.Resources;

import java.util.List;

/**
 * 三小场所扫描后的检查记录适配器
 * @author jiajiaUser
 *
 */
public class SanXiaoRecordAdapter extends BaseAdapter {

	private List<Object> list;
	private Context context;
	
	public SanXiaoRecordAdapter(List<Object> list,
			Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int position) {
		return list!=null?list.get(position):null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.sanxiao_check_record_item, null);
			TextView check_person,document_number,check_time,review_date,view_details;
			ImageView[] imageviews = new ImageView[3];
			check_person = (TextView) view.findViewById(R.id.check_person);
			document_number = (TextView) view.findViewById(R.id.document_number);
			check_time = (TextView) view.findViewById(R.id.check_time);
			review_date = (TextView) view.findViewById(R.id.review_date);
			view_details = (TextView) view.findViewById(R.id.view_details);
			imageviews[0] = (ImageView) view.findViewById(R.id.imageview1);
			imageviews[1] = (ImageView) view.findViewById(R.id.imageview2);
			imageviews[2] = (ImageView) view.findViewById(R.id.imageview3);
			final SmallSiteCheck check = (SmallSiteCheck) list.get(position);
			check_person.setText(check.getInspector_names());
			document_number.setText("");
			if (check.getCreated()!=null)
				check_time.setText(check.getCreated().substring(0, 10));
			if (check.getExpiration()!=null)
				review_date.setText(check.getExpiration().substring(0, 10));
			int counter = 0;
			
			String imgNames = null,
					imgUrls = null;
			for(SmallSiteCheck.Danger danger:check.getDangers()){
				if(!"".equals(danger.getPhoto_before_url())){
					ImageView iv = imageviews[counter++];
					iv.setScaleType(ScaleType.CENTER_CROP);
					String imgUrl = Resources.URL_ROOT+danger.getPhoto_before_url();
					ImageLoader.getInstance().displayImage(imgUrl, iv);
					if (imgUrls==null){
						imgUrls = imgUrl;
						imgNames = danger.getDesc();
					}else{
						imgUrls += "--=--"+imgUrl;
						imgNames += "--=--"+danger.getDesc();
					}
					if (counter>=3)
						break;
				}
			}
			
			final String imgUrlsStr = imgUrls;
			final String imgNamesStr = imgNames;
			LinearLayout layoutPhotos = (LinearLayout) view.findViewById(R.id.layoutPhotos);
			if(counter==0){
				layoutPhotos.setVisibility((View.GONE));
			}else{
				layoutPhotos.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context,ImageActivity.class);
						intent.putExtra("imgUrls", imgUrlsStr.split("--=--"));
						intent.putExtra("imgNames", imgNamesStr.split("--=--"));
						context.startActivity(intent);
						
					}
					
				});
			}
			view_details.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,SmallSiteCheckActivity.class);
					intent.putExtra("check", check);
					context.startActivity(intent);
				}
				 
			});
		return view;
	}
	
	
	/**
	 * 跳转相册界面和调整检查记录详情界面
	 * @author jiajiaUser
	 *
	 *//*
	class OnClickStartIntent implements OnClickListener{
		@Override
		public void onClick(View v) {
			int id = v.getId();
			Intent intent;
			switch (id) {
			case R.id.imageview1:
				 intent = new Intent(context,ImageActivity.class);
				 context.startActivity(intent);
				break;
			case R.id.imageview2:
				 intent = new Intent(context,ImageActivity.class);
				 context.startActivity(intent);
				break;
			case R.id.imageview3:
				 intent = new Intent(context,ImageActivity.class);
				 context.startActivity(intent);
				break;
			default:
				break;
			}
			
		}
		
	}*/
	
	

}
