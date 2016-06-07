package com.szzaq.jointdefence.fragment;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.szzaq.jointdefence.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
public class LoadingDataFragment  extends DialogFragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
	        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);  
	        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	        getDialog().setCancelable(false);
			return inflater.inflate( R.layout.fragment_loading, container);
		}

	}
