package com.szzaq.jointdefence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONStringer;

/**
 * 编辑个人资料界面
 * @date 2015/07/07
 * @author jiajiaUser
 *
 */
public class UserInfoEditActivity extends Activity implements OnClickListener {

	//声明控件
	private TextView userinfo_edit_agent,userinfo_edit_cencel,userinfo_edit_commit;
	//private EditText userinfo_phone;
	//选择代理人
	public static final int RESULTSUCCESS=1;
	//提交数据
	public static final int COMMITSUCCESS = 2;
	private RadioButton radioReal,radioSpy;
	//是否在岗
	//private int status=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_userinfo_edit);
		App.getInstance().addActivity(this);
		radioReal = (RadioButton) this.findViewById(R.id.radioReal);
		radioSpy = (RadioButton) this.findViewById(R.id.radioSpy);
		//userinfo_phone = (EditText) this.findViewById(R.id.userinfo_phone);
		userinfo_edit_agent = (TextView) this.findViewById(R.id.userinfo_edit_agent);
		userinfo_edit_cencel = (TextView) this.findViewById(R.id.userinfo_edit_cencel);
		userinfo_edit_commit = (TextView) this.findViewById(R.id.userinfo_edit_commit);
		userinfo_edit_agent.setOnClickListener(this);
		userinfo_edit_cencel.setOnClickListener(this);
		userinfo_edit_commit.setOnClickListener(this);
		radioReal.setOnClickListener(this);
		radioSpy.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		//取消按钮
		case R.id.userinfo_edit_cencel:
			UserInfoEditActivity.this.finish();
			break;
			//选择代理人跳转y页面
		case R.id.userinfo_edit_agent:
			Intent intent = new Intent(this,AgentActivity.class);
			intent.putExtra("type", RESULTSUCCESS);
			this.startActivityForResult(intent, RESULTSUCCESS);
			break;
			//在岗状态
		case R.id.radioReal:
			//status = Integer.parseInt(v.getTag()+"");
			break;
			//休假
		case R.id.radioSpy:
			//status = Integer.parseInt(v.getTag()+"");
			break;
			//提交
		case R.id.userinfo_edit_commit:
			Intent intent1 = new Intent(this,CommitActivity.class);
			JSONStringer js;
			String params = "";
			try {
				js = new JSONStringer();
/*				js.object().key("id").value(App.getInstance().getUser().getId())
				.key("phone").value(userinfo_phone.getText())
				.key("status").value(status)
				.key("agent").value((Integer)userinfo_edit_agent.getTag());
				js.endObject();*/
				params = js.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			intent1.putExtra("url",getString(R.string.http_url)+"personal/edit/");
			intent1.putExtra("param",params);
			startActivityForResult(intent1,COMMITSUCCESS);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 接收选择代理人界面的值
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//判断是否有选择
		if(requestCode==RESULTSUCCESS&&resultCode==RESULTSUCCESS){
			String agent_name = data.getStringExtra("name");
			int agent_id = data.getIntExtra("id",0);
			userinfo_edit_agent.setText(agent_name);
			userinfo_edit_agent.setTag(agent_id);
		}
		if(requestCode==COMMITSUCCESS&&resultCode==COMMITSUCCESS){
			this.finish();
		}
	}
	
	
}
