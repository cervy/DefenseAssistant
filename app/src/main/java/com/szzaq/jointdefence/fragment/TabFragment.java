package com.szzaq.jointdefence.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.adapter.ZhongDianTabAdapter;
import com.szzaq.jointdefence.model.Device;
import com.szzaq.jointdefence.model.DeviceCheck;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.utils.ScrollViewUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 重点单位扫描结果，检查记录和自查记录，维保记录选项界面
 *
 * @author jiajiaUser
 * @date 2015/07/16
 */
public class TabFragment extends Fragment implements OnClickListener {
    private ListView tab_listview;
    private ArrayList<Object> list;
    private ZhongDianTabAdapter adapter;
    private String nextPage = "";
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int recordsCount = 0;
    private Button btnMoreRecords;
    private LinearLayout layoutLoading;
    private Device device;
    int checkType;
    Bundle bundle;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate( R.layout.zhongdiantab, container, false);
        btnMoreRecords = (Button) view.findViewById( R.id.btnMoreRecords);
        layoutLoading = (LinearLayout) view.findViewById( R.id.progressLoading);
        if (isLastPage)
            btnMoreRecords.setText( R.string.label_is_lastpage);
        else
            btnMoreRecords.setText( R.string.label_more_records);

        if (bundle == null) {
            bundle = getArguments();
            list = new ArrayList<Object>();
            bundle.putSerializable("list", list);
            checkType = bundle.getInt("checkType");
            device = bundle.getParcelable("device");
            loadData(checkType);
        } else {
            list = (ArrayList<Object>) bundle.getSerializable("list");
        }
        adapter = new ZhongDianTabAdapter(list, this.getActivity(), 0);
        tab_listview = (ListView) view.findViewById(R.id.tab_listview);
        tab_listview.setAdapter(adapter);
        ScrollViewUtils.setScrollViewHeight(tab_listview,
                getActivity(), 0);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.btnMoreRecords:
                if (!isLastPage) {
                    loadData(checkType);
                }
                break;
        }
    }

    private void loadData(int checkType) {
        isLoading = true;
        showLoadingProgress(true);
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                showLoadingProgress(false);
                if ("".equals(nextPage))
                    list.clear();
                isLoading = false;
                try {
                    JSONObject orgJson = new JSONObject(s);
                    nextPage = orgJson.optString("next", "null");
                    recordsCount = orgJson.optInt("count", 0);
                    if ("null".equals(nextPage))
                        isLastPage = true;

                    if (isLastPage)
                        btnMoreRecords.setText(R.string.label_is_lastpage);
                    else
                        btnMoreRecords.setText( R.string.label_more_records);

                    //main_gtasks.setText("待办任务(" + orgJson.optString("count")
                    //		+ ")");
                    JSONArray arr = orgJson.getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject checkJson = arr.getJSONObject(i);
                        Gson gson = new Gson();
                        DeviceCheck check = gson.fromJson(checkJson.toString(),
                                new TypeToken<DeviceCheck>() {
                                }.getType());
                        list.add(check);
                    }
                    adapter.notifyDataSetChanged();
                    // 调节srcollview高度
                    ScrollViewUtils.setScrollViewHeight(tab_listview,
                            TabFragment.this.getActivity(), 0);
                    // 加载时就回到顶部
                    // ScrollViewUtils.setToTop(main_listview, main_scrollview);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        String params = "device__id=" + device.getDeviceId() + "&inspect_type=" + checkType;
        String url = "".equals(nextPage) || "null".equals(nextPage) ? Resources.KEYENTERPRISE_DEVICE_CHECK : nextPage;
        url = url.contains("?") ? url + "&" + params : url + "?" + params;
        //Log.e("s", url);
        request.execute(url, "");

    }

    private void showLoadingProgress(boolean show) {
        int showProgress = show ? View.VISIBLE : View.GONE;
        int showButtonMore = show ? View.GONE : View.VISIBLE;
        layoutLoading.setVisibility(showProgress);
        btnMoreRecords.setVisibility(showButtonMore);
    }


}
