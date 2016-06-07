package com.szzaq.jointdefence.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.adapter.ListviewAdapter;
import com.szzaq.jointdefence.model.DeviceCheck;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.DensityUtil;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.utils.ScrollViewUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author zg
 */
public class EquipHistoryRecordFragment extends Fragment {
    private View view;
    //检查记录集合
    private ListView listView;
    private ArrayList<Object> checkList;
    private ListviewAdapter adapter;
    private TextView space;
    private Button more;
    String checkType;
    String deviceId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history_record, container,
                false);
        initView();
        return view;
    }

    public void initView() {
        //检查记录集合
        checkList = new ArrayList<Object>();
        space = (TextView) view.findViewById(R.id.space);
        more = (Button) view.findViewById(R.id.btnMoreRecords);
        listView = (ListView) view.findViewById(R.id.listViewRecord);
        listView.setDivider(new ColorDrawable(Color.parseColor("#F5F5F5")));
        listView.setDividerHeight(DensityUtil.dip2px(getActivity(), 15));
        adapter = new ListviewAdapter(checkList, this.getActivity(), 0);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new ListViewScrollLisenter());
        Bundle bundle = this.getArguments();
        checkType = bundle.getString("checkType");
        deviceId = bundle.getString("deviceId");
        getCheckRecords(checkType, deviceId);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void getCheckRecords(String checkType, String deviceId) {
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                if (!s.isEmpty()) {

                    try {
                        JSONObject orgJson = new JSONObject(s);
                        JSONArray jarr = orgJson.getJSONArray("results");
                        if (jarr != null && jarr.length() > 0) {
                            nextPage = orgJson.optString("next", "null");
                            if ("null".equals(nextPage))
                                more.setText("已到达最后页");


                            else
                                more.setText(R.string.label_more_records);
                            Gson gson = new Gson();
                            for (int i = 0; i < jarr.length(); i++) {
                                DeviceCheck check = gson.fromJson(jarr.getJSONObject(i).toString(),
                                        new TypeToken<DeviceCheck>() {
                                        }.getType());
                                checkList.add(check);
                            }
                            adapter.notifyDataSetChanged();
                            ScrollViewUtils.setScrollViewHeight(listView, getActivity(), 10);
                        } else {
                            space.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);

                            more.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        request.execute(Resources.KEYENTERPRISE_DEVICE_CHECK + "?inspect_type="
                + checkType + "&device__id=" + deviceId, "");

        Log.e("记录，，，，", Resources.KEYENTERPRISE_DEVICE_CHECK + "?inspect_type="
                + checkType + "&device__id=" + deviceId);
    }

    private String nextPage = "";
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int lastItem, itemCount;

    class ListViewScrollLisenter implements AbsListView.OnScrollListener {

        @Override
        public void onScroll(AbsListView arg0, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            lastItem = firstVisibleItem + visibleItemCount;
            itemCount = totalItemCount;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (lastItem == itemCount - 1 && scrollState == SCROLL_STATE_IDLE) {

                if (!isLastPage & !isLoading) {
                    isLoading = true;
                    getCheckRecords(checkType, deviceId);

                }
            }
        }

    }
}
