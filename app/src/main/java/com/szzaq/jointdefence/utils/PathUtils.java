package com.szzaq.jointdefence.utils;

import android.content.Context;

import java.io.File;

public class PathUtils {
	
	public static String getAudioPath(Context context){
			File audioDir;
	        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
	        	audioDir=new File(android.os.Environment.getExternalStorageDirectory(),"fpplus/audio");
	        else
	        	audioDir=context.getCacheDir();
	        if(!audioDir.exists())
	        	audioDir.mkdirs();
	        
	        return audioDir.getAbsolutePath();
	}
	
	public static String getPicturePath(Context context){
		File picDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        	picDir=new File(android.os.Environment.getExternalStorageDirectory(),"fpplus/pictures");
        else
        	picDir=context.getCacheDir();
        if(!picDir.exists())
        	picDir.mkdirs();
        
        return picDir.getAbsolutePath();
}	
}
