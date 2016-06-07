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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.activity.ke.ZhongDianCheckDetailsActivity;
import com.szzaq.jointdefence.activity.smallsite.SmallSiteCheckActivity;
import com.szzaq.jointdefence.adapter.ListviewAdapter;
import com.szzaq.jointdefence.model.DeviceCheck;
import com.szzaq.jointdefence.model.SmallSiteCheck;
import com.szzaq.jointdefence.model.Task;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.Resources;

/**
 * 检查记录界面
 *
 * @author zg
 * @date 2016/02/21
 */
public class TaskActivity extends Activity implements OnClickListener {
    private Button btnMoreRecords;
    //private ImageLoader imageLoader;
    private LinearLayout layoutLoading;
    private boolean isLoading = false;
    private String nextPage = "";
    private int recordsCount = 0;
    private boolean isLastPage = false;
    /*private final String entGroup = "[LEADER][SUPERVISOR][ADMIN][INCHARGE][FPADMIN]";
    private final String gridGroup = "[BIGGRID][MIDGRID]";*/
    private String curUserRole = "";
    private ArrayList<Object> taskList = new ArrayList<Object>();
    private ListviewAdapter adapter;
    private ListView listViewTask;
    private int lastItem = 0, itemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_task);
        App.getInstance().addActivity(this);
        curUserRole = App.getInstance().getUser().getRole()[0];
        // 实例化适配器
        adapter = new ListviewAdapter(taskList, this, 0);
        // 绑定适配器
        listViewTask = (ListView) this.findViewById(R.id.listViewTask);
        layoutLoading = (LinearLayout) findViewById(R.id.progressLoading);
        listViewTask.setAdapter(adapter);
        listViewTask.setOnScrollListener(new ListViewScrollLisenter());
        //listViewTask.setOnItemClickListener(new OnRecordItemClickListener());
        btnMoreRecords = (Button) findViewById(R.id.btnMoreRecords);
        loadTaskList();
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

    private void loadTaskList() {
        showLoadingProgress(true);
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                showLoadingProgress(false);
                if ("".equals(nextPage))
                    taskList.clear();
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
                        btnMoreRecords.setText(R.string.label_more_records);

//					main_gtasks.setText("待办任务(" + orgJson.optString("count")
//							+ ")");
                    JSONArray arr = orgJson.getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject taskJson = arr.getJSONObject(i);
                        Gson gson = new Gson();
                        Task task = gson.fromJson(taskJson.toString(),
                                new TypeToken<Task>() {
                                }.getType());
                        taskList.add(task);
                    }
                    adapter.notifyDataSetChanged();
                    Log.e("tasks", arr.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        String url = "".equals(nextPage) ? Resources.TASK : nextPage;
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
                    loadTaskList();
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
            Object obj = taskList.get(position);

            // 检查对象，并且为重点
            if (obj instanceof DeviceCheck) {
                // 跳转重点单位检查记录界面
                DeviceCheck deviceCheck = (DeviceCheck) obj;
                Intent intent = new Intent(TaskActivity.this,
                        ZhongDianCheckDetailsActivity.class);
                intent.putExtra("deviceCheck", deviceCheck);
                TaskActivity.this.startActivity(intent);
            }
            if (obj instanceof SmallSiteCheck) {
                SmallSiteCheck siteCheck = (SmallSiteCheck) obj;
                Intent intent = new Intent(TaskActivity.this,
                        SmallSiteCheckActivity.class);
                intent.putExtra("check", siteCheck);
                TaskActivity.this.startActivity(intent);

            }

        }
    }
}
