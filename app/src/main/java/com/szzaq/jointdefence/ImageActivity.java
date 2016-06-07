package com.szzaq.jointdefence;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.szzaq.jointdefence.widget.ImageTouchView;

public class ImageActivity extends Activity implements OnPageChangeListener {
    private ViewPager pager;
    private ImageTouchView[] mImageViews;
    private String[] imgIdArray;
    private MyAdapter my;
    private TextView page;
    // 声明imageloder
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    String[] imgNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_image);
        App.getInstance().addActivity(this);
        page = (TextView) this.findViewById(R.id.page);
        initPagers();
    }

    private void initPagers() {
        String[] imgUrls = getIntent().getStringArrayExtra("imgUrls");
        imgNames = getIntent().getStringArrayExtra("imgNames");
        imgIdArray = new String[imgUrls.length];
        page.setText(getString(R.string.viewpager_indicator, 1,
                imgIdArray.length)
                + "\n\r"
                + imgNames[0]);
        pager = (ViewPager) this.findViewById(R.id.pager);
        // 将图片装载到数组中
        mImageViews = new ImageTouchView[imgIdArray.length];
        for (int i = 0; i < mImageViews.length; i++) {
            String url = imgUrls[i];
            ImageTouchView imageView = new ImageTouchView(this);
            mImageViews[i] = imageView;
            loaderimage(url, imageView);
        }
        pager.setOnPageChangeListener(this);
        my = new MyAdapter();
        pager.setAdapter(my);
    }

    /**
     * @author
     */
    public class MyAdapter extends PagerAdapter {

        @Override
        // 获取当前窗体界面数
        public int getCount() {
            return mImageViews.length;
        }

        @Override
        // 断是否由对象生成界面
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        // 是从ViewGroup中移出当前View
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mImageViews[arg1]);
        }

        // 返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mImageViews[arg1]);
            return mImageViews[arg1];
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        try {
            page.setText(getString(R.string.viewpager_indicator, arg0 + 1,
                    imgIdArray.length)
                    + "\n\r"
                    + imgNames[arg0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用imageloader获取头像
     */
    public void loaderimage(String url, ImageView view) {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()

                .showImageOnLoading(null)
                // 加载过程中显示的图片

                .showImageForEmptyUri(null)
                // 加载内容为空显示的图片

                .showImageOnFail(null)
                // 加载失败显示的图片

                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)

                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(388)).build();
        imageLoader
                .displayImage(
                        url,
                        view, options);
    }

}
