package com.szzaq.jointdefence;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.model.TODEAL;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOS on 2016/5/16 0016.TODEAL
 */
public class A_ToDeal extends A_Base {
    private ListView mListView;
    private List<TODEAL> mTODEALs = new ArrayList<TODEAL>();
    private Button btnMoreRecords;
    private boolean isLoading = false;
    private String nextPage = "";
    private boolean isLastPage = false;
    private int lastItem, itemCount;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.f_his, R.string.eachothercheck, 0);
        mListView = (ListView) findViewById(R.id.listofhis);
        btnMoreRecords = (Button) findViewById(R.id.btnMoreRecords);
        mListView.setOnScrollListener(new ListViewScrollLisenter());
        setData();
    }

    private JSONObject json;

    private void setData() {
        final GetRequest re = new GetRequest();

        //mTODEALs = new ArrayList<TODEAL>();
        re.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                final Gson gson = new Gson();
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray objects = (object.getJSONArray(Resources.RESULTS));
                    if (objects != null && objects.length() > 0) {
                        nextPage = object.optString("next", "null");
                        if ("null".equals(nextPage)) {
                            btnMoreRecords.setText("已到达最后页");
                            isLastPage = true;

                        } else
                            btnMoreRecords.setText(R.string.label_more_records);


                        for (int i = 0; i < objects.length(); i++) {
                            JSONObject obj = objects.getJSONObject(i);
                            TODEAL enterprise = gson.fromJson(obj.toString(), new TypeToken<TODEAL>() {
                            }.getType());
                            mTODEALs.add(enterprise);

                        }


                        mListView.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return mTODEALs != null ? mTODEALs.size() : 0;
                            }

                            @Override
                            public Object getItem(int position) {
                                return mTODEALs != null && mTODEALs.get(position) != null ? mTODEALs.get(position) : null;

                            }

                            @Override
                            public long getItemId(int position) {
                                return 0;
                            }

                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {
                                final Holder holder;


                                if (convertView == null) {
                                    holder = new Holder();


                                    convertView = LayoutInflater.from(A_ToDeal.this).inflate(R.layout.todoitem, null);
                                    holder.taskType = (TextView) convertView.findViewById(R.id.tasktype);//0
                                    holder.daedline = (TextView) convertView.findViewById(R.id.deadline);//2
                                    holder.finishState = (TextView) convertView.findViewById(R.id.finishstate);
                                    holder.zerenren = (TextView) convertView.findViewById(R.id.zerenren);//1

                                    convertView.setTag(holder);

                                } else holder = (Holder) convertView.getTag();


                                if (mTODEALs.get(position).status.equals(String.valueOf(0))) {
                                    holder.zerenren.setText("未完成");
                                    holder.finishState.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            PostRequest request = new PostRequest();
                                            try {
                                                json = new JSONObject(gson.toJson(mTODEALs.get(position)));
                                                json.put("_method", "PUT");
                                                json.put("status", String.valueOf(1));

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                            request.execute(mTODEALs.get(position).url, json.toString());

                                            request.setCallback(new HttpCallback() {
                                                @Override
                                                public void handleData(String s) {
                                                    holder.zerenren.setText("已完成");
                                                    holder.finishState.setVisibility(View.GONE);

                                                }
                                            });
                                        }
                                    });
                                } else {
                                    holder.zerenren.setText("已完成");
                                    holder.finishState.setVisibility(View.GONE);
                                }
                                holder.taskType.setText(mTODEALs.get(position).title);

                                holder.daedline.setText(mTODEALs.get(position).content);
                                return convertView;
                            }


                            final class Holder {
                                private TextView taskType, daedline, zerenren, finishState;
                            }

                        });

                    } else {
                        mListView.setVisibility(View.GONE);
                        TextView textView = (TextView) findViewById(R.id.whennodata);
                        textView.setVisibility(View.VISIBLE);
                        btnMoreRecords.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    showToast(getString(R.string.dataerr));

                }
            }
        });
        re.execute(Resources.TODEAL);


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
                    isLoading = true;                    btnMoreRecords.setText(R.string.load_date);

                    setData();
                }
            }
        }

    }

}
