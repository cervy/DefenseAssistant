package com.szzaq.jointdefence.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtils {

    public static Bitmap getBitmap(String fileName,int requireSize,boolean corp){
    	File f = new File(fileName);
        try {
            //decode image size
           BitmapFactory.Options o = new BitmapFactory.Options();
            
           o.inJustDecodeBounds = true;
           BitmapFactory.decodeStream(new FileInputStream(f),null,o);
            //Find the correct scale value. It should be the power of 2.
            //int requireSize=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<requireSize || height_tmp/2<requireSize)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
            
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            Bitmap ret = BitmapFactory.decodeStream(new FileInputStream(f),null, o2);
            int x,y,width,height,offset;
            width = ret.getWidth();
            height = ret.getHeight();
            offset = Math.abs(width-height)/2;
            x = width > height ? offset : 0;
            y = x==0?offset:0;
            width = width>height?height:width;
            height = width;
            return corp?Bitmap.createBitmap(ret, x, y, width, height):ret;
        } catch (FileNotFoundException ignored) {}
        catch(Exception ignored){}
        return null;
    }  
    
	public static int calculateInSampleSize(BitmapFactory.Options options,  
	        int reqWidth, int reqHeight) {  
	    // ԴͼƬ�ĸ߶ȺͿ��  
	    final int height = options.outHeight;  
	    final int width = options.outWidth;  
	    int inSampleSize = 1;  
	    if (height > reqHeight || width > reqWidth) {  
	        // �����ʵ�ʿ�ߺ�Ŀ���ߵı���  
	        final int heightRatio = Math.round((float) height / (float) reqHeight);  
	        final int widthRatio = Math.round((float) width / (float) reqWidth);  
	        // ѡ���͸�����С�ı�����ΪinSampleSize��ֵ��������Ա�֤����ͼƬ�Ŀ�͸�  
	        // һ��������ڵ���Ŀ��Ŀ�͸ߡ�  
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;  
	    }  
	    return inSampleSize; 
	}
	
	public static Bitmap zoomImage(Bitmap bitmap, int newWidth, int newHeight){
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scaleW = (float)newWidth/width;
		newHeight = newWidth * height / width;
		float sacleH = (float)newHeight/height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleW, sacleH);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}
	
	public static Bitmap decodeFromFile(String fileName,int reqWidth, int reqHeight,boolean corp) {  
	    try {
			// ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С  
			final BitmapFactory.Options options = new BitmapFactory.Options();  
			options.inJustDecodeBounds = true;  
			BitmapFactory.decodeFile(fileName, options);
			// �������涨��ķ�������inSampleSizeֵ  
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);  
			// ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ  
			options.inJustDecodeBounds = false;  
			Bitmap ret = BitmapFactory.decodeFile(fileName, options); 
			int x,y,width,height,offset;
			width = ret.getWidth();
			height = ret.getHeight();
			offset = Math.abs(width-height)/2;
			x = width > height ? offset : 0;
			y = x==0?offset:0;
			width = width>height?height:width;
			height = width;
			return corp?Bitmap.createBitmap(ret, x, y, width, height):ret;
		} catch (Exception e) {
			return null;
		}

	}
	
    public static Bitmap combineImages(Bitmap bgd,float x1,float y1, Bitmap fg ,float x2, float y2) {
        Bitmap bmp;
        /*
        int width = bgd.getWidth() > fg.getWidth() ? 
            bgd.getWidth() : fg.getWidth();
        int height = bgd.getHeight() > fg.getHeight() ? 
            bgd.getHeight() : fg.getHeight();
        */
        int width = bgd.getWidth();
        int height = bgd.getHeight();
        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bgd, -x1, -y1, null);
        canvas.drawBitmap(fg, -x2, -y2, paint);
        return bmp;
    }
    
    public static Bitmap downloadBitmap(String imageUrl) {  
        Bitmap bitmap = null;  
        HttpURLConnection con = null;  
        try {  
            URL url = new URL(imageUrl);  
            con = (HttpURLConnection) url.openConnection();  
            con.setConnectTimeout(5 * 1000);  
            con.setReadTimeout(10 * 1000);  
            con.setDoInput(true);  
            con.setDoOutput(true);  
            bitmap = BitmapFactory.decodeStream(con.getInputStream());  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (con != null) {  
                con.disconnect();  
            }  
        }  
        return bitmap;  
    }  
    
}
