package com.szzaq.jointdefence;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.net.HttpHelper;

/**
 * 提交数据时的弹窗
 * @author jiajiaUser
 *
 */
public class CommitActivity extends Activity {

	private String url;
	private String param;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		this.setContentView(R.layout.activity_commit);
		App.getInstance().addActivity(this);
		url= getIntent().getStringExtra("url");
		param= getIntent().getStringExtra("param");
		commitData();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return event.getAction() == MotionEvent.ACTION_DOWN && isOutOfBounds(this, event) || super.onTouchEvent(event);
	}

	private boolean isOutOfBounds(Activity context, MotionEvent event) {
		final int x = (int) event.getX();
		final int y = (int) event.getY();
		final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
		final View decorView = context.getWindow().getDecorView();
		return (x < -slop) || (y < -slop)|| (x > (decorView.getWidth() + slop))|| (y > (decorView.getHeight() + slop));
	}
	
	
	public void commitData(){
		HttpHelper helper = new HttpHelper();
		helper.setCallback(new HttpCallback() {
			@Override
			public void handleData(String s) {
				try {
					if(s.contains("success")){
						CommitActivity.this.setResult(UserInfoEditActivity.COMMITSUCCESS);
						Toast.makeText(CommitActivity.this,"修改成功！",Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(CommitActivity.this,"修改失败，请检查网络！！！",Toast.LENGTH_LONG).show();
					}
					CommitActivity.this.finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		helper.execute(url,param);
	}
}
