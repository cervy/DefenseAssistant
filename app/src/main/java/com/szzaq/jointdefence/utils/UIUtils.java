package com.szzaq.jointdefence.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.szzaq.jointdefence.App;


public class UIUtils{
	public static void quit(final Context context){
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("您确认退出系统吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",new OnClickListener(){
			@Override
				public void onClick(DialogInterface dialog,int which){
					dialog.dismiss();
					App.getInstance().exit();
			}
		});
		builder.setNegativeButton("取消",new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog,int which){
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
}
