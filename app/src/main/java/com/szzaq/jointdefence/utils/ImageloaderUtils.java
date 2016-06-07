package com.szzaq.jointdefence.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;

public class ImageloaderUtils {
    //声明imageloder
    private static DisplayImageOptions options;
    private static ImageLoader imageLoader;

    /**
     * 使用imageloader获取头像
     */
    public static void loaderimage(String url, ImageView imageview) {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()

                .showImageOnLoading(null)//加载过程中显示的图片

                .showImageForEmptyUri(null)//加载内容为空显示的图片

                .showImageOnFail(null)//加载失败显示的图片

                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)

                .bitmapConfig(Bitmap.Config.RGB_565).displayer(new FadeInBitmapDisplayer(388)).build();
        imageLoader.displayImage(url, imageview, options);
    }

    public static String takePhoto(Activity context, String photoPath, int REQUEST_TAKE_PHOTO) {

        File file = new File(photoPath);
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        return file.getPath();

    }
}
