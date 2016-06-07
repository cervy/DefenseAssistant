package com.szzaq.jointdefence.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.szzaq.jointdefence.fragment.LoadingDataFragment;

public class DialogUtil {
	private static LoadingDataFragment loading;
	@SuppressLint("NewApi") 
	public static void showDialog(final Context context){//fragment
		loading = new LoadingDataFragment();  
		try {
			loading.show(((Activity)context).getFragmentManager(), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressLint("NewApi") 
	public static void closeDialog(){
		if (loading!=null){
			try {
				loading.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
