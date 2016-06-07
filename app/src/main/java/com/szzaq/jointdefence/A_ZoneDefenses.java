package com.szzaq.jointdefence;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.model.ZoneDefense;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOS on 2016/5/13 0013.
 */
public class A_ZoneDefenses extends A_Base {

    private Button btnMoreRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.f_his, R.string.task, 0);
        //setContentView(R.layout.f_his);
        mListView = (ListView) findViewById(R.id.listofhis);
        myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);
        getData();
        btnMoreRecords = (Button) findViewById(R.id.btnMoreRecords);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = (int) id;
                if (mlist.get(pos) != null) {
                    Intent intent = new Intent(A_ZoneDefenses.this, A_ZoneDefense.class);
                    intent.putExtra("list", mlist.get(pos));
                    startActivity(intent);
                    finish();
                }

            }
        });
        mListView.setOnScrollListener(new ListViewScrollLisenter());
    }

    private void getData() {
        //mlist


        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                /*if ("".equals(nextPage))
                    mlist.clear();*/
                Gson gson = new Gson();

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    JSONArray objects = (jsonObject.getJSONArray(Resources.RESULTS));

                    if (objects.length() < 1) {
                        mListView.setVisibility(View.GONE);
                        TextView textView = (TextView) findViewById(R.id.whennodata);
                        textView.setVisibility(View.VISIBLE);
                        btnMoreRecords.setVisibility(View.GONE);

                    } else {

                        nextPage = jsonObject.optString("next", "null");
                        if ("null".equals(nextPage)) {
                            isLastPage = true;
                            btnMoreRecords.setText("已到达最后页");


                        } else
                            btnMoreRecords.setText(R.string.label_more_records);

                        for (int i = 0; i < objects.length(); i++) {
                            JSONObject obj = objects.getJSONObject(i);
                            ZoneDefense enterprise = gson.fromJson(obj.toString(), new TypeToken<ZoneDefense>() {
                            }.getType());
                            mlist.add(enterprise);

                        }
                        myAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showToast(getString(R.string.dataerr));

                }
            }
        });
        //request.execute(Resources.ZONEDEFENSE);
        request.execute("".equals(nextPage) ? Resources.ZONEDEFENSE : nextPage);
    }

    private MyAdapter myAdapter;

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mlist != null ? mlist.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return mlist != null && mlist.get(position) != null ? mlist.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {


                convertView = LayoutInflater.from(A_ZoneDefenses.this).inflate(R.layout.zonedefense, null);
                holder = new Holder();

                holder.taskType = (TextView) convertView.findViewById(R.id.tasktype);
                holder.daedline = (TextView) convertView.findViewById(R.id.deadline);
                //holder.finishState = (TextView) convertView.findViewById(R.id.finishstate);
                holder.zerenren = (TextView) convertView.findViewById(R.id.zerenren);

                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
            }
            ZoneDefense zoneDefense = mlist.get(position);
            holder.zerenren.setText("责任人：" + zoneDefense.person_incharge_name);
            String type = zoneDefense.task_type.equals(String.valueOf(1)) ? "联合演练" : "交叉检查";
            holder.taskType.setText("任务类型：" + type);
            //holder.finishState.setText(mlist.get(position).finishstate);
            holder.daedline.setText("完成期限：" + zoneDefense.expired_date);
            return convertView;
        }


        class Holder {
            TextView taskType, daedline, zerenren;//, finishState
        }

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
                    btnMoreRecords.setText(R.string.load_date);

                    getData();
                }
            }
        }

    }

    private boolean isLoading = false;
    private String nextPage = "";
    private boolean isLastPage = false;
    private int lastItem, itemCount;
    private List<ZoneDefense> mlist = new ArrayList<ZoneDefense>();
    private ListView mListView;
}
