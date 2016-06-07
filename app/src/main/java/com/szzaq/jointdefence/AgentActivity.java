package com.szzaq.jointdefence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.adapter.SortAdapter;
import com.szzaq.jointdefence.model.Agent;
import com.szzaq.jointdefence.model.SortModel;
import com.szzaq.jointdefence.net.HttpHelper;
import com.szzaq.jointdefence.utils.CharacterParser;
import com.szzaq.jointdefence.utils.PinyinComparator;
import com.szzaq.jointdefence.utils.SideBar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 选择代理人界面
 *
 * @author jiajiaUser
 * @date 2015/07/07
 */
public class AgentActivity extends Activity {
    private ListView sortListView;
    private SideBar sideBar;
    //屏幕中间字母
    private TextView dialog;
    private ImageView agent_back;
    private SortAdapter adapter;
    private View noservice;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        App.getInstance().addActivity(this);
        //获取返回键
        agent_back = (ImageView) findViewById(R.id.agent_back);
        //获取无法连接服务器时的view
        noservice = this.findViewById(R.id.noservice);
        //结束当前界面
        agent_back.setOnClickListener(new Agent_Back_OnClick());
        //绑定事件
        noservice.setOnClickListener(new Agent_Back_OnClick());
        initViews();
/*		if(App.getInstance().getAgentsList()!=null&&App.getInstance().getAgentsList().size()>=1){
            initViews();
		}else{
			getdata();
		}*/

    }

    private void initViews() {
        ArrayList<Agent> agentList = new ArrayList<Agent>();

        for (HashMap<String, String> map : App.getInstance().config.COWORKERS) {
            Agent agent = new Agent();
            agent.setName(map.get("username"));
            agent.setResource(map.get("resource"));
            agentList.add(agent);
        }
        App.getInstance().setAgentsList(agentList);
        this.findViewById(R.id.agent_listview).setVisibility(View.VISIBLE);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);

        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });
        /**
         * 列表点击事件
         */
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("name", SourceDateList.get(position).getName());
                intent.putExtra("id", SourceDateList.get(position).getId());
                intent.putExtra("role_name", SourceDateList.get(position).getRole_name());
                intent.putExtra("resource", SourceDateList.get(position).getResource());
                AgentActivity.this.setResult(getIntent().getIntExtra("type", 0), intent);
                AgentActivity.this.finish();
            }
        });
        SourceDateList = filledData(App.getInstance().getAgentsList());
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);


    }


    /**
     * 为ListView填充数据
     *
     * @return
     */
    private List<SortModel> filledData(List<Agent> list) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < list.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(list.get(i).getName());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(list.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            sortModel.setId(list.get(i).getId());
            sortModel.setRole_id(list.get(i).getRole_id());
            sortModel.setRole_name(list.get(i).getRole_name());
            sortModel.setResource(list.get(i).getResource());
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    /**
     * 界面本界面
     *
     * @author jiajiaUser
     */
    class Agent_Back_OnClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.agent_back:
                    AgentActivity.this.finish();
                    break;
                case R.id.noservice:
                    //隐藏加载图片和文字
                    AgentActivity.this.findViewById(R.id.loading_text).setVisibility(View.VISIBLE);
                    AgentActivity.this.findViewById(R.id.loading).setVisibility(View.VISIBLE);
                    getdata();
                    break;
                default:
                    break;
            }
        }
    }

    public void getdata() {
        HttpHelper helper = new HttpHelper();
        helper.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                try {
                    if ("".equals(s)) {
                        //没有数据则表示无法连接服务器
                        AgentActivity.this.findViewById(R.id.noservice).setVisibility(View.VISIBLE);
                    } else {
                        AgentActivity.this.findViewById(R.id.noservice).setVisibility(View.GONE);
                        //有数据便解析json
                        JSONObject jobject = new JSONObject(s);
                        JSONArray jarray = jobject.getJSONArray("agentList");
                        ArrayList<Agent> list = new ArrayList<Agent>();
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobjectTwo = jarray.getJSONObject(i);
                            Gson gson = new Gson();
                            //映射进代理人实体类中
                            Agent agent = gson.fromJson(jobjectTwo.toString(), new
                                    TypeToken<Agent>() {
                                    }.getType());
                            list.add(agent);
                        }
                        //设置代理人为全局变量
                        App.getInstance().setAgentsList(list);
                        //初始化数据
                        initViews();
                    }
                    //隐藏加载图片和文字
                    AgentActivity.this.findViewById(R.id.loading_text).setVisibility(View.GONE);
                    AgentActivity.this.findViewById(R.id.loading).setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        String url = getString(R.string.http_url) + "personal/agentlist/";
        JSONStringer js;
        String params = "";
        try {
            js = new JSONStringer();
            js.object();//.key("id").value(App.getInstance().getUser().getId());
            js.endObject();
            params = js.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        helper.execute(url, params);
    }


}
