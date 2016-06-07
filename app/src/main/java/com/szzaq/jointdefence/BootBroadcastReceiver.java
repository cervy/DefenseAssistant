package com.szzaq.jointdefence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver { 
    static final String ACTION="android.intent.action.BOOT_COMPLETED";  
  
    @Override
    public void onReceive(Context context, Intent intent) { 
    	Log.d("BootBroadcastReceiver", "Received!");
        if (intent.getAction().equals(ACTION)){  
            Intent bootStartIntent=new Intent("cn.jpush.android.service.PushService");  
            //bootStartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	
            context.startService(bootStartIntent);
        } 
    } 


	
} 