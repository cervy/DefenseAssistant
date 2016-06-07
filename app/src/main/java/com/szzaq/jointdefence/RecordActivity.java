package com.szzaq.jointdefence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.activity.ke.ZhongDianCheckDetailsActivity;
import com.szzaq.jointdefence.activity.smallsite.SmallSiteCheckActivity;
import com.szzaq.jointdefence.adapter.ListviewAdapter;
import com.szzaq.jointdefence.model.DeviceCheck;
import com.szzaq.jointdefence.model.SmallSiteCheck;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 检查记录界面
 *
 * @author zg
 * @date 2016/02/21
 */
public class RecordActivity extends Activity implements OnClickListener {
    private Button btnMoreRecords;
    private ImageLoader imageLoader;
    private LinearLayout layoutLoading;
    private boolean isLoading = false;
    private String nextPage = "";
    private int recordsCount = 0;
    private boolean isLastPage = false;
    private final String entGroup = "[LEADER][SUPERVISOR][ADMIN][INCHARGE][FPADMIN]";
    private final String gridGroup = "[BIGGRID][MIDGRID]";
    private String curUserRole = "";
    private ArrayList<Object> checkList = new ArrayList<Object>();
    private ListviewAdapter adapter;
    private ListView listViewRecord;
    private int lastItem = 0, itemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_record);
        App.getInstance().addActivity(this);
        curUserRole = App.getInstance().getUser().getRole()[0];
        // 实例化适配器
        adapter = new ListviewAdapter(checkList, this, 0);
        // 绑定适配器
        listViewRecord = (ListView) this.findViewById(R.id.listViewRecord);
        layoutLoading = (LinearLayout) findViewById(R.id.progressLoading);
        listViewRecord.setAdapter(adapter);
        listViewRecord.setOnScrollListener(new ListViewScrollLisenter());
        listViewRecord.setOnItemClickListener(new OnRecordItemClickListener());
        btnMoreRecords = (Button) findViewById(R.id.btnMoreRecords);
        loadCheckList();
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

    private void loadCheckList() {
        showLoadingProgress(true);
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {
                showLoadingProgress(false);
                isLoading = false;
                if ("".equals(nextPage))
                    checkList.clear();
                try {
                    JSONObject orgJson = new JSONObject(s);
                    recordsCount = orgJson.optInt("count", 0);
                    nextPage = orgJson.optString("next", "null");
                    if ("null".equals(nextPage))
                        isLastPage = true;
                    if (isLastPage)
                        btnMoreRecords.setText("已到达最后页");
                    else
                        btnMoreRecords.setText(R.string.label_more_records);

                    JSONArray arr = orgJson.getJSONArray("results");
                    Object check;
                    Type type = null;
                    if (entGroup.contains(curUserRole)) {
                        type = new TypeToken<DeviceCheck>() {
                        }.getType();
                    } else if (gridGroup.contains(curUserRole)) {
                        type = new TypeToken<SmallSiteCheck>() {
                        }.getType();
                    }
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject deviceCheckJson = arr.getJSONObject(i);
                        Gson gson = new Gson();
                        check = gson.fromJson(
                                deviceCheckJson.toString(),
                                type);
                        checkList.add(check);
                        Log.e("check", deviceCheckJson.toString());

                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
        String dist = "";

        if (entGroup.contains(curUserRole)) {
            dist = Resources.KEYENTERPRISE_DEVICE_CHECK;
        } else if (gridGroup.contains(curUserRole)) {
            dist = Resources.SMALLSITE_CHECK;
        }

        String url = "".equals(nextPage) ? dist : nextPage;
        request.execute(url, "");
    }

    private void showLoadingProgress(boolean show) {
        int showProgress = show ? View.VISIBLE : View.GONE;
        int showButtonMore = show ? View.GONE : View.VISIBLE;
        layoutLoading.setVisibility(showProgress);
        btnMoreRecords.setVisibility(showButtonMore);
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
                    //Toast.makeText(RecordActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                    loadCheckList();
                }
            }
        }

    }

    class OnRecordItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            if (arg3 == -1)
                return;
            int position = (int) arg3;
            Object obj = checkList.get(position);

            // 检查对象，并且为重点
            if (obj instanceof DeviceCheck) {
                // 跳转重点单位检查记录界面
                DeviceCheck deviceCheck = (DeviceCheck) obj;
                Intent intent = new Intent(RecordActivity.this,
                        ZhongDianCheckDetailsActivity.class);
                intent.putExtra("deviceCheck", deviceCheck);
                RecordActivity.this.startActivity(intent);
            } else if (obj instanceof SmallSiteCheck) {
                SmallSiteCheck siteCheck = (SmallSiteCheck) obj;
                Intent intent = new Intent(RecordActivity.this,
                        SmallSiteCheckActivity.class);
                intent.putExtra("check", siteCheck);
                RecordActivity.this.startActivity(intent);

            }

        }
    }
}
