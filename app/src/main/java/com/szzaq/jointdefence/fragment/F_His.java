package com.szzaq.jointdefence.fragment;

import android.annotation.SuppressLint;
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
import com.szzaq.jointdefence.R;
import com.szzaq.jointdefence.adapter.ActivityMessageAdapter;
import com.szzaq.jointdefence.model.Hiss;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.sqlite.MessageDBManager;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOS on 2016/5/12 0012. 两种历史记录列表
 */
@SuppressLint("ValidFragment")
public class F_His extends android.app.Fragment {
    private View view;
    private ListView listOfHis;
    private List<Hiss> mList = new ArrayList<Hiss>();
    private int ONE;
    public MyAdapter mMyAdapter;

    public F_His(int isOne) {
        ONE = isOne;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.f_his, container,
                false);
        listOfHis = (ListView) view.findViewById(R.id.listofhis);
        btnMoreRecords = (Button) view.findViewById(R.id.btnMoreRecords);
        /*listOfHis.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                         {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                 if (ONE == 0) {

                                                 }

                                             }
                                         }

        );*/
        if (ONE == 0) {
            mMyAdapter = new MyAdapter();
            listOfHis.setDivider(null);
            listOfHis.setAdapter(mMyAdapter);
            listOfHis.setOnScrollListener(new ListViewScrollLisenter());
            setData();// 巡查历史

        }
        if (ONE == 1) setData1();// 预警互助历史

        return view;
    }

    private Button btnMoreRecords;
    private boolean isLoading = false;
    private String nextPage = "";
    private boolean isLastPage = false;
    private int lastItem, itemCount;


    //public F_His(){}
    public void setData() {


        GetRequest request = new GetRequest();

        request.setCallback(new HttpCallback() {
                                @Override
                                public void handleData(String s) {


                                    Gson gson = new Gson();
                                    try {
                                        JSONObject jsonObject = new JSONObject(s);
                                        JSONArray objects = (jsonObject.getJSONArray(Resources.RESULTS));
                                        if (objects != null && objects.length() > 0) {
                                            nextPage = jsonObject.optString("next", "null");
                                            if ("null".equals(nextPage)) {
                                                isLastPage = true;
                                                btnMoreRecords.setText("已到达最后页");

                                            } else
                                                btnMoreRecords.setText(R.string.label_more_records);
                                            for (int i = 0; i < objects.length(); i++) {
                                                JSONObject obj = objects.getJSONObject(i);
                                                Hiss enterprise = gson.fromJson(obj.toString(), new TypeToken<Hiss>() {
                                                }.getType());
                                                mList.add(enterprise);
                                            }
                                            mMyAdapter.notifyDataSetChanged();

                                        } else {
                                            listOfHis.setVisibility(View.GONE);
                                            TextView textView = (TextView) view.findViewById(R.id.whennodata);
                                            textView.setVisibility(View.VISIBLE);
                                            btnMoreRecords.setVisibility(View.GONE);

                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

        );
        request.execute("".equals(nextPage) ? Resources.DAILYCHECK : nextPage);

    }


    public void setData1() {

        btnMoreRecords.setVisibility(View.GONE);
        m = new MessageDBManager(getActivity());
        if (m.query() != null && m.query().size() > 0) {
            listOfHis.setDivider(null);
            listOfHis.setAdapter(new ActivityMessageAdapter(m.query(), getActivity()));
        } else {
            listOfHis.setVisibility(View.GONE);
            TextView textView = (TextView) view.findViewById(R.id.whennodata);
            textView.setVisibility(View.VISIBLE);


        }
    }

    private MessageDBManager m;

    @Override
    public void onDetach() {
        super.onDetach();
        if (m != null)
            m.closeDB();
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            int size = 0;
            if (mList != null)
                size = mList.size();
            /*if (ONE == 1 && mListt != null) {
                size = mListt.size();
            }*/
            return size;
        }

        @Override
        public Object getItem(int position) {
            Object o = null;
            if (mList != null && mList.get(position) != null) {
                o = mList.get(position);
            }

           /* if (ONE == 1 && mListt != null && mListt.get(position) != null) {
                o = mListt.get(position);
            }*/
            return o;

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;


            if (convertView == null) {
                holder = new ViewHolder();


                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.hisitem, null);
                holder.checker = (TextView) convertView.findViewById(R.id.checker);
                holder.checkerr = (TextView) convertView.findViewById(R.id.checkerr);
                holder.manager = (TextView) convertView.findViewById(R.id.manager);
                holder.checkTimes = (TextView) convertView.findViewById(R.id.checktimes);
                holder.date = (TextView) convertView.findViewById(R.id.date);
                holder.profile = (TextView) convertView.findViewById(R.id.profile);
                holder.remark = (TextView) convertView.findViewById(R.id.remark);
                holder.delete = (TextView) convertView.findViewById(R.id.delete);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //Hiss hiss = mList.get(mList.size() - 1 - position);
            final Hiss hiss = mList.get(position);

            holder.checkerr.setText("巡查员：" + hiss.patrolmen);//巡查人
            holder.checker.setText("检查人：" + hiss.reviewer);//检查人
            holder.manager.setText("主管人：" + hiss.supervisor);//主管人
            holder.checkTimes.setText("检查次数：" + hiss.patrol_times);
            holder.date.setText("检查时间：" + hiss.date);
            holder.profile.setText("总体情况：" + hiss.report);
            holder.remark.setText("备注：" + hiss.remark);


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PostRequest request = new PostRequest();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(new Gson().toJson(hiss));

                        jsonObject.put("_method", "DELETE");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    request.setCallback(new HttpCallback() {
                        @Override
                        public void handleData(String s) {
                            //if ()
                            mList.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    //request.execute(Resources.DAILYCHECK, jsonObject.toString());
                    request.execute(hiss.url, jsonObject.toString());
                }
            });
            //  }
            /* else {
                holder.name.setText(mListt.get(position).name);
                if (!mListt.get(position).headurl.isEmpty())
                    ImageloaderUtils.loaderimage(mListt.get(position).headurl, holder.head);
                holder.time.setText(mListt.get(position).time);
                if (mListt.get(position).fireType.equals("firetype"))
                    holder.fireType.setBackgroundResource(R.drawable.ic_color_lens_black_48dp);
                holder.msg.setText(mListt.get(position).msg);
            }*/

            return convertView;
        }

        final class ViewHolder {
            private TextView checker, checkerr, manager, checkTimes, date, profile, remark, delete;
/*
            private TextView name, msg, fireType, time;
            private CircleImageView head;*/
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
                    setData();
                }
            }
        }

    }

}
