package com.szzaq.jointdefence.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.activity.smallsite.SmallSiteScanningActivity;
import com.szzaq.jointdefence.adapter.SanXiaoRecordAdapter;
import com.szzaq.jointdefence.model.SmallSiteCheck;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.utils.ScrollViewUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 扫描后三小场所检查记录fragment
 *
 * @author jiajiaUser
 */
public class CheckRecordFragment extends Fragment {
    private View view;
    //检查记录集合
    private ListView checkrecord_listview;
    private ArrayList<Object> checkList;
    private SanXiaoRecordAdapter adapter;
    private boolean isLoading = false;
    private String nextPage = "";
    private boolean isLastPage = false;
    private int lastItem, itemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sanxiao_check_record, container,
                false);
        initView();
        return view;
    }

    public void initView() {
        //检查记录集合
        checkList = new ArrayList<Object>();
        checkrecord_listview = (ListView) view.findViewById(R.id.checkrecord_listview);
        checkrecord_listview.setDivider(new ColorDrawable(Color.parseColor("#F5F5F5")));
        checkrecord_listview.setDividerHeight(DensityUtil.dip2px(getActivity(), 15));
        adapter = new SanXiaoRecordAdapter(checkList, getActivity());
        checkrecord_listview.setAdapter(adapter);
        checkrecord_listview.setOnScrollListener(new ListViewScrollLisenter());
        getCheckRecords();

    }

    public void getCheckRecords() {
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {

                try {
                    JSONObject orgJson = new JSONObject(s);

                    JSONArray jarr = orgJson.getJSONArray("results");
                    if (orgJson != null && orgJson.length() > 0) {
                        nextPage = orgJson.optString("next", "null");
                        if ("null".equals(nextPage)) {

                            isLastPage = true;

                        }
                        Gson gson = new Gson();
                        for (int i = 0; i < jarr.length(); i++) {
                            SmallSiteCheck check = gson.fromJson(jarr.getJSONObject(i).toString(),
                                    new TypeToken<SmallSiteCheck>() {
                                    }.getType());
                            checkList.add(check);
                        }
                        adapter.notifyDataSetChanged();
                        ScrollViewUtils.setScrollViewHeight(checkrecord_listview, getActivity(), orgJson.optInt("count"));
                    } else {
                        checkrecord_listview.setVisibility(View.GONE);
                        TextView textView = (TextView) view.findViewById(R.id.whennodata);
                        textView.setVisibility(View.VISIBLE);


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        request.execute("".equals(nextPage) ? Resources.SMALLSITE_CHECK
                + "?site__id="
                + ((SmallSiteScanningActivity) getActivity()).site.getId() : Resources.SMALLSITE_CHECK
                + "?" + nextPage + "site__id="
                + ((SmallSiteScanningActivity) getActivity()).site.getId());//http://120.24.220.29/api/smallsitecheck/?page=2&site__id=1
    }

    class ListViewScrollLisenter implements AbsListView.OnScrollListener {

        @Override
        public void onScroll(AbsListView arg0, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            lastItem = firstVisibleItem + visibleItemCount;
            itemCount = totalItemCount;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (lastItem == itemCount && scrollState == SCROLL_STATE_IDLE) {

                if (!isLastPage & !isLoading) {
                    isLoading = true;
                    getCheckRecords();
                }
            }
        }

    }
}
