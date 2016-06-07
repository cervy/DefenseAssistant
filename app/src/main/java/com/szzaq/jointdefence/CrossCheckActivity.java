package com.szzaq.jointdefence;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.adapter.CrossCheckAdapter;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 交叉检查界面
 *
 * @author zg
 * @date 2016/02/21
 */
public class CrossCheckActivity extends Activity implements OnClickListener {

    private ArrayList<HashMap<String, Object>> crossCheckList = new ArrayList<HashMap<String, Object>>();
    private CrossCheckAdapter adapter;
    private ListView listViewCrossCheck;
    private String nextPage = "";
    private LinearLayout layoutLoading;
    private Button btnMoreRecords;
    private int recordsCount;
    private int lastItem = 0, itemCount = 0;
    private boolean isLastPage = false, isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_cross_check);
        App.getInstance().addActivity(this);

        layoutLoading = (LinearLayout) findViewById(R.id.progressLoading);
        btnMoreRecords = (Button) findViewById(R.id.btnMoreRecords);
        // 实例化适配器
        adapter = new CrossCheckAdapter(crossCheckList, this);
        // 绑定适配器
        listViewCrossCheck = (ListView) this.findViewById(R.id.listViewCrossCheck);
        listViewCrossCheck.setAdapter(adapter);
        listViewCrossCheck.setOnScrollListener(new ListViewScrollLisenter());
        listViewCrossCheck.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//  交叉检查列表

            }
        });
        loadCrossCheck();
        findViewById(R.id.btnBack).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnBack:
                this.finish();
                break;
            default:
                break;
        }

    }


    private void loadCrossCheck() {
        showLoadingProgress(true);
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                showLoadingProgress(false);
                try {
                    JSONObject json = new JSONObject(s);
                    nextPage = json.optString("next", "null");
                    recordsCount = json.optInt("count", 0);
                    if ("null".equals(nextPage))
                        isLastPage = true;

                    if (isLastPage)
                        btnMoreRecords.setText(R.string.label_is_lastpage);
                    else
                        btnMoreRecords.setText(R.string.label_more_records);

                    JSONArray arr = json.getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject j = arr.getJSONObject(i);
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("jcdw", j.opt("name"));
                        map.put("ent_id", j.opt("id"));
                        crossCheckList.add(map);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();


            }

        });
        String url = "".equals(nextPage) ? Resources.KEYENTERPRISE : nextPage;
        request.execute(url, "");

    }

    class ListViewScrollLisenter implements OnScrollListener {

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
                    loadCrossCheck();
                }
            }
        }

    }

    private void showLoadingProgress(boolean show) {
        int showProgress = show ? View.VISIBLE : View.GONE;
        int showButtonMore = show ? View.GONE : View.VISIBLE;
        layoutLoading.setVisibility(showProgress);
        btnMoreRecords.setVisibility(showButtonMore);
    }


}
