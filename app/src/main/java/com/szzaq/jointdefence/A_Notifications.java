package com.szzaq.jointdefence;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.model.Notii;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.net.PostRequest;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOS on 2016/5/12 0012.
 */
public class A_Notifications extends A_Base {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.f_his, R.string.helppls, 0);
        listOfHis = (ListView) findViewById(R.id.listofhis);
        aAdapter = new AAdapter();
        listOfHis.setAdapter(aAdapter);
        btnMoreRecords = (Button) findViewById(R.id.btnMoreRecords);
        listOfHis.setOnScrollListener(new ListViewScrollLisenter());
        getData();


       /* listOfHis.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                         {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                             }
                                         }

        );*/
    }

    AAdapter aAdapter;
    private Button btnMoreRecords;
    private boolean isLoading = false;
    private String nextPage = "";
    private boolean isLastPage = false;
    private int lastItem, itemCount;

    public void getData() {
        //mList = new ArrayList<Notii>();
        oo = new ArrayList<JSONObject>();
        GetRequest request = new GetRequest();

        request.setCallback(new HttpCallback() {
                                @Override
                                public void handleData(String s) {


                                    Gson gson = new Gson();
                                    try {
                                        JSONObject object = new JSONObject(s);
                                        JSONArray objects = (object.getJSONArray(Resources.RESULTS));

                                        if (objects != null && objects.length() > 0) {
                                            nextPage = object.optString("next", "null");
                                            if ("null".equals(nextPage)) {
                                                isLastPage = true;
                                                btnMoreRecords.setText("已到达最后页");

                                            } else
                                                btnMoreRecords.setText(R.string.label_more_records);

                                            for (int i = 0; i < objects.length(); i++) {
                                                JSONObject obj = objects.getJSONObject(i);
                                                JSONObject o = obj.optJSONObject("read_log");
                                                Notii enterprise = gson.fromJson(obj.toString(), new TypeToken<Notii>() {
                                                }.getType());
                                                mList.add(enterprise);
                                                oo.add(o);

                                            }
                                            aAdapter.notifyDataSetChanged();
                                        } else {
                                            listOfHis.setVisibility(View.GONE);
                                            findViewById(R.id.whennodata).setVisibility(View.VISIBLE);
                                            btnMoreRecords.setVisibility(View.GONE);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

        );
        request.execute(Resources.NOTIFICATIONS, "");
    }

    private ListView listOfHis;
    private List<Notii> mList = new ArrayList<Notii>();

    class AAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            int size = 0;
            if (mList != null)
                size = mList.size();

            return size;
        }

        @Override
        public Object getItem(int position) {

            return mList != null && mList.get(position) != null ? mList.get(position) : null;

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;


            if (convertView == null) {
                holder = new ViewHolder();


                convertView = LayoutInflater.from(A_Notifications.this).inflate(R.layout.notiitem, null);
                holder.name = (TextView) convertView.findViewById(R.id.time);
                holder.msg = (TextView) convertView.findViewById(R.id.msg);

                holder.head = new ImageView[3];
                holder.head[0] = (ImageView) convertView.findViewById(R.id.imgattachment);
                holder.head[1] = (ImageView) convertView.findViewById(R.id.imgattachment1);
                holder.head[2] = (ImageView) convertView.findViewById(R.id.imgattachment2);
                holder.fireType = (CheckBox) convertView.findViewById(R.id.foldor);
                holder.time = (TextView) convertView.findViewById(R.id.receiveor);
                holder.linearlayoutofattachments = (LinearLayout) convertView.findViewById(R.id.linearlayoutofattachments);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Notii notii = mList.get(position);

            holder.msg.setText(notii.content);
            holder.name.setText(notii.created);//时间
            String[] imgUrls = notii.attachment_urls;

            String imgURls = null;
            String imgNames = null;
            if (imgUrls != null && imgUrls.length > 0) {
                for (int i = 0; i < imgUrls.length && i < 3; i++) {
                    if (!imgUrls[i].equals(""))

                        ImageloaderUtils.loaderimage(Resources.URL_ROOT + imgUrls[i], holder.head[i]);


                    if (imgURls == null) {
                        imgURls = Resources.URL_ROOT + imgUrls[i];
                        imgNames = " ";

                    } else {
                        imgURls += "--=--" + Resources.URL_ROOT + imgUrls[i];
                        imgNames += "--=--" + " ";
                    }

                }
                final String imgUrlsStr = imgURls;
                final String imgNamesStr = imgNames;
                holder.linearlayoutofattachments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(A_Notifications.this, ImageActivity.class);
                        intent.putExtra("imgUrls", imgUrlsStr.split("--=--"));
                        intent.putExtra("imgNames", imgNamesStr.split("--=--"));
                        startActivity(intent);
                    }
                });


            }

            JSONObject o = oo.get(position);

            if (o != null && !o.isNull(String.valueOf(App.getInstance().getUser().getId()))) {
                holder.time.setText(o.optString(String.valueOf(App.getInstance().getUser().getId())));
                holder.time.setEnabled(false);

            } else
                holder.time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PostRequest request = new PostRequest();
                        request.setCallback(new HttpCallback() {
                            @Override
                            public void handleData(String s) {
                                Gson gson = new Gson();
                                try {
                                    //JSONObject objects = (new JSONObject(s)).getJSONObject(Resources.RESULTS);
                               /* for (int i = 0; i < objects.length(); i++) {
                                    JSONObject obj = objects.getJSONObject(i); */
                                    Notii enterprise = gson.fromJson(s, new TypeToken<Notii>() {
                                    }.getType());
                                    holder.time.setText(enterprise.created + "签收");
                                    holder.time.setEnabled(false);
                                    //}
                                    aAdapter.notifyDataSetChanged();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        String params = "";
                        JSONStringer js = new JSONStringer();
                        try {
                            js.object().key("user").value(App.getInstance().getUser().getUrl())
                                    .key("notice").value(notii.url)
                                    .endObject();
                            params = js.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast(getString(R.string.dataerr));
                        }

                        request.execute(Resources.NOTIFICATIONSREADLOG, params, this);


                    }
                });
            holder.fireType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.fireType.isChecked()) {
                        holder.fireType.setText("展开");
                        //holder.head.setVisibility(View.GONE);
                        holder.linearlayoutofattachments.setVisibility(View.GONE);
                        holder.msg.setMaxLines(3);
                        //holder.name.setVisibility(View.GONE);
                        //holder.time.setText(mList.get(position).created + "签收");

                    } else {
                        holder.fireType.setText(R.string.fold);
                        //holder.head.setVisibility(View.VISIBLE);
                        holder.linearlayoutofattachments.setVisibility(View.VISIBLE);
                        holder.msg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        //holder.name.setVisibility(View.VISIBLE);
                        //holder.time.setText(R.string.receive);
                    }
                }
            });


            return convertView;
        }

        final class ViewHolder {


            private TextView name, msg, time;
            private CheckBox fireType;
            private ImageView[] head;
            private LinearLayout linearlayoutofattachments;
        }

    }

    // private JSONObject o;
    private List<JSONObject> oo;

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

                    getData();
                }
            }
        }

    }

}
