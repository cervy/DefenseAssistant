package com.szzaq.jointdefence.Adialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.szzaq.jointdefence.R;

import org.json.JSONException;
import org.json.JSONStringer;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.utils.Resources;

public class AwardActivity extends Activity implements OnClickListener{
	private TextView tvCoin;
	private Button btnConfirm;
	String device_id;
	@Override  
	protected void onCreate(Bundle savedInstanceState) {  
	       super.onCreate(savedInstanceState);  
	        setContentView( R.layout.activity_award);
	        
	        Intent intent = getIntent();
	        int count = intent.getIntExtra("count", 0);
	        device_id = intent.getStringExtra("device_id");
	        tvCoin = (TextView) findViewById( R.id.tvCoin);
	        tvCoin.setText(String.valueOf(count));
	        
	        btnConfirm = (Button) findViewById( R.id.btnConfirm);
	        btnConfirm.setOnClickListener(this);
	    }
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case  R.id.btnConfirm:
			acceptCoin();
			break;
		
		}
		
	} 
	
	private void acceptCoin(){
		PostRequest request = new PostRequest();
		request.setCallback(new HttpCallback(){
			@Override
			public void handleData(String s) {
				AwardActivity.this.finish();
			}
			
		});
		String params = "";
		JSONStringer js = new JSONStringer();
		try {
			js.object().key("source_type").value("DEVICE")
					   .key("source_id").value(device_id)
			  .endObject();
			params = js.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		request.execute(Resources.COIN_TRANSACTION,params,AwardActivity.this);
	}
	
}
