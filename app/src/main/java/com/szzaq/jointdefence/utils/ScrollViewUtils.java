package com.szzaq.jointdefence.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * scrollview调节高度类
 *
 * @author jiajiaUser
 */
public class ScrollViewUtils {
    /**
     * scrollview调节高度
     *
     * @param context
     */
    public static void setScrollViewHeight(ListView listview, Context context, int addheight) {
        ListAdapter adapter = listview.getAdapter();
        int count = adapter.getCount();
        float hangao = 0;
        for (int i = 0; i < count; i++) {
            View view = adapter.getView(i, null, listview);
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            hangao += view.getMeasuredHeight();
            float jiangefu = listview.getDividerHeight();
            hangao += jiangefu;
        }
        LayoutParams params = listview.getLayoutParams();
        params.height = (int) hangao + DensityUtil.dip2px(context, addheight);
        listview.setLayoutParams(params);
    }

    /**
     * 由于srcollview中有其他的控件，所以导致加载的时候无法在顶部，所以写此方法解决该问题，使一开始就在顶部
     */
    public static void setToTop(ListView listview, final ScrollView scrollView) {
        listview.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        });
    }

}
