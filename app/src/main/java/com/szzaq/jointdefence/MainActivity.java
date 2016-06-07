package com.szzaq.jointdefence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szzaq.interfaces.HttpCallback;
import com.szzaq.jointdefence.model.Household;
import com.szzaq.jointdefence.model.UserInfo;
import com.szzaq.jointdefence.net.GetRequest;
import com.szzaq.jointdefence.utils.ImageloaderUtils;
import com.szzaq.jointdefence.utils.Resources;
import com.szzaq.jointdefence.utils.UIUtils;
import com.szzaq.jointdefence.utils.UpdateManager;
import com.szzaq.jointdefence.widget.BadgeView;
import com.szzaq.jointdefence.widget.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * DOS
 */
public class MainActivity extends Activity implements OnClickListener {


    private CircleImageView main_totel;//头像
    private TextView main_username, main_position, tel, unitName, unitNum, unitAddr, fireDangerProperties, elseProperties;


    private String curUserRole = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getInstance().addActivity(this);
        initview();
        checkViersion();


        //badges
        notifications = (TextView) findViewById(R.id.notifications);
        toDeal = (TextView) findViewById(R.id.todeal);
        zoneDefense = (TextView) findViewById(R.id.zonedefense);
        badgeOfNotifications = new BadgeView(this, notifications);
        badgeOftoDeal = new BadgeView(this, toDeal);
        badgeOfZoneDefense = new BadgeView(this, zoneDefense);
        badgeOftoDeal.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeOfZoneDefense.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeOfNotifications.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        setMsgNum();//初始化badges显示


        findViewById(R.id.tohousehold).setOnClickListener(this);//导航到户籍化修改页面
        // setHousehold();
    }

    private TextView notifications, toDeal, zoneDefense;
    BadgeView badgeOfNotifications;
    BadgeView badgeOftoDeal;
    BadgeView badgeOfZoneDefense;

    @Override
    protected void onResume() {
        super.onResume();
        setHousehold();//填充户籍化数据

        if (App.getInstance().getUser() != null) {
            UserInfo userInfo = App.getInstance().getUser();
            if (userInfo.getProfile() != null) {
                UserInfo.Profile profile = userInfo.getProfile();

                String imgUrl = profile.getPortrait_url();
                if (!"".equals(imgUrl)) {
                    if (!imgUrl.contains(Resources.URL_ROOT))
                        imgUrl = Resources.URL_ROOT + imgUrl;
                    ImageloaderUtils.loaderimage(imgUrl, main_totel);//加载头像
                }
                //updateCoinCount();
                main_username.setText(profile.getNickname());

                main_position.setText(App
                        .getInstance()
                        .getConfig()
                        .getTypeName(
                                App.getInstance().getConfig().ROLE_CHOICES,
                                curUserRole));
                tel.setText("电话：" + profile.getPhone());
            }
        }
       /* if ("[ADMIN][SUPERVISOR][LEADER]".contains(curUserRole)) {//不同角色不同用户配置
            if (App.getInstance().getUser().getAsFireMan() != null) {
                String[] workUnit = App.getInstance().getUser()
                        .getAsFireMan().split(",");
                main_brigade.setText(workUnit.length > 1 ? workUnit[1]
                        : "");
            }
        } else if ("[MAINTAINER]".contains(curUserRole)) {
            if (App.getInstance().getUser().getAsMaintainer() != null) {
                String[] workUnit = App.getInstance().getUser()
                        .getAsMaintainer().split(",");
                main_brigade.setText(workUnit.length > 1 ? workUnit[1]
                        : "");
            }
        } else if ("[FPADMIN][INCHARGE]".contains(curUserRole)) {
            if (App.getInstance().getUser().getAsStaff() != null) {todo:重点单位人
                String[] workUnit = App.getInstance().getUser()
                        .getAsStaff().split(",");
                main_brigade.setText(workUnit.length > 1 ? workUnit[1]
                        : "");
            }
        } else if ("[BIGGRID][MIDGRID]".contains(curUserRole)) {
            if (App.getInstance().getUser().getAsGridAdmin() != null) {
                String[] workUnit = App.getInstance().getUser()
                        .getAsGridAdmin().split(",");
                main_brigade.setText(workUnit.length > 1 ? workUnit[1]
                        : "");
            }
        }*/
        
        
        /*if (App.getInstance().isDataChanged()) {
            nextPage = new String[]{"", ""};
            isLastPage = new boolean[]{false, false};
            getdata(0);
            getdata(1);
            App.getInstance().setDataChanged(false);
        }*/
    }


    @Override
    protected void onDestroy() {
        //unregisterReceiver(mMessageReceiver);反注册
        super.onDestroy();
    }


   /* private void updateCoinCount() {
        GetRequest request = new GetRequest();

        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                JSONObject json;
                try {
                    json = new JSONObject(s);
                    int count = json.optInt("count", 0);
                    if (count == 0) {
                        updateCoinRank("0");
                    } else {
                        String balance = json.getJSONArray("results").getJSONObject(0).optString("balance", "0");
                        //coin.setText(balance);//多少枚
                        updateCoinRank(balance);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        request.execute(Resources.COIN_ACCOUNT + "?user__id=" + App.getInstance().getUser().getId());
    }*/

    /*private void updateCoinRank(String balance) {
        GetRequest request = new GetRequest();
        request.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                JSONObject json;
                try {
                    json = new JSONObject(s);
                    int rank = json.optInt("count", 0) + 1;
                    //ranks.setText(String.valueOf(rank));//第几名
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        request.execute(Resources.COIN_ACCOUNT + "?base=" + balance);
    }*/


    private void initview() {


        tel = (TextView) findViewById(R.id.main_brigade1);
        unitAddr = (TextView) findViewById(R.id.unitaddr);
        unitName = (TextView) findViewById(R.id.unitname);
        unitNum = (TextView) findViewById(R.id.unitnum);
        fireDangerProperties = (TextView) findViewById(R.id.firedangerproperties);
        elseProperties = (TextView) findViewById(R.id.elseproperties);
        try {
            curUserRole = App.getInstance().getUser().getRole()[0];
        } catch (Exception e) {
            finish();
            Toast.makeText(getApplicationContext(), "当前用户未配置角色，请联系管理员", Toast.LENGTH_LONG).show();
        }


        main_username = (TextView) this.findViewById(R.id.main_username);

        main_position = (TextView) this.findViewById(R.id.main_position);

        main_totel = (CircleImageView) this.findViewById(R.id.main_totel);
        findViewById(R.id.main_my).setOnClickListener(this);


        findViewById(R.id.main_check_userinfo).setOnClickListener(this);


        findViewById(R.id.layoutmessages).setOnClickListener(this);
        findViewById(R.id.layoutRecord).setOnClickListener(this);
        findViewById(R.id.layoutTask).setOnClickListener(this);
        findViewById(R.id.layoutMicroFireStation).setOnClickListener(this);
        findViewById(R.id.layoutSOS).setOnClickListener(this);
        findViewById(R.id.layoutCrossCheck).setOnClickListener(this);
    }

    private void checkViersion() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GetRequest request = new GetRequest();
                request.setCallback(new HttpCallback() {
                    @Override
                    public void handleData(String s) {
                        if (TextUtils.isEmpty(s)) return;
                        try {
                            JSONObject version = new JSONObject(s).getJSONArray("results").getJSONObject(0);
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            int versionCode = version.optInt("versionCode");
                            map.put("url", version.optString("apk_url"));
                            map.put("versionCode", version.optInt("versionCode"));
                            map.put("desc", version.optString("desc"));
                            UpdateManager um = new UpdateManager(MainActivity.this, map);
                            um.checkUpdate(versionCode);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }

                });
                request.execute(Resources.VERSION, "");
            }

        }, 1000);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        switch (id) {


            case R.id.main_my:
                // 跳转到我的
                intent = new Intent(this, MyActivity.class);
                finish();
                break;

            // 跳转个人资料/
            case R.id.main_check_userinfo:
                intent = new Intent(this, UserInfoActivity.class);
                break;
            //户籍化资料修改
            case R.id.tohousehold:
                if (enterprise != null) {

                    //     startActivityForResult(new Intent(this, A_HouseholdEdit.class).putExtra("account", enterprise), REQUEST_CONFIRM);
                    startActivity(new Intent(this, A_HouseholdEdit.class).putExtra("account", enterprise));
                }

                break;

            case R.id.layoutRecord://预警互助
                intent = new Intent(MainActivity.this, A_AlertEachother.class);
                break;
            case R.id.layoutTask://zoneDefense
                hideBadge(badgeOfZoneDefense);
                intent = new Intent(MainActivity.this, A_ZoneDefenses.class);
                break;
            case R.id.layoutMicroFireStation://微型消防站
                intent = new Intent(MainActivity.this, MicroFireStationActivity.class);
                break;
            case R.id.layoutmessages://日常查询
                if (enterprise != null && !enterprise.account.isEmpty()) {
                    intent = new Intent(this, A_DailyCheck.class);
                    intent.putExtra("enterprise", enterprise.account);
                }

                break;
            case R.id.layoutSOS:
                Intent confirm = new Intent(MainActivity.this, A_Notifications.class);
                hideBadge(badgeOfNotifications);
                // startActivityForResult(confirm, REQUEST_CONFIRM);
                startActivity(confirm);
                break;
            case R.id.layoutCrossCheck://
                hideBadge(badgeOftoDeal);

                intent = new Intent(MainActivity.this, A_ToDeal.class);
                break;


            default:
                break;
        }
        if (intent != null) this.startActivity(intent);

    }

    private void hideBadge(BadgeView badge) {

        /*if (!badge.getText().toString().isEmpty() && Integer.parseInt(badge.getText().toString()) > 1) {
            badge.increment(-1);
            return;
        }*/
        if (badge.getVisibility() != View.GONE)
            badge.setVisibility(View.GONE);
    }

    /*private void sendSOS() {
        JPushHelper helper = new JPushHelper();
        *//*helper.setCallback(new HttpCallback() {

            @Override
            public void handleData(String s) {

                try {
                    @SuppressWarnings("unused")
                    JSONObject json = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });*//*
        JSONStringer js = new JSONStringer();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        try {
            Log.e("dingweile", App.getInstance().getLocation() == null ? "kong" : App.getInstance().getLocation());


            js.object()//封装发送json参数
                    .key("platform").array().value("android").endArray()
                    .key("audience").value("all")
                    .key("message").object().key("msg_content").value("[" + App.getInstance().getLocation() + "] 附近发生警情，请求前往增援！")
                    .key("content_type").value("text")
                    .key("title").value("msg")
                    .key("extras").object()
                    .key("userName").value(App.getInstance().getUser() != null && null != App.getInstance().getUser().getProfile() ? App.getInstance().getUser().getProfile().getNickname() : "")
                    .key("portrait").value(App.getInstance().getUser() != null && null != App.getInstance().getUser().getProfile() ? App.getInstance().getUser().getProfile().getPortrait_url() : "")//
                    .key("time").value(df.format(new Date()))
                    .key("lat").value(App.getInstance().getLngLat()[1])
                    .key("lng").value(App.getInstance().getLngLat()[0])
                    .endObject()
                    .endObject()
                    .endObject();

            // Log.e("dingweile", App.getInstance().getLocation());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        helper.execute("https://api.jpush.cn/v3/push", js.toString());
    }*/


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                UIUtils.quit(MainActivity.this);

            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private long mExitTime;

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONFIRM) {
            if (resultCode == RESULT_OK) {
                if (data.getParcelableExtra("e") != null) {
                    Household nterprise = data.getParcelableExtra("e");
                    unitName.setText("单位名称：" + nterprise.ctl00_MainContent_txtDWMC);
                    fireDangerProperties.setText("火灾危险性：" + nterprise.ctl00_MainContent_lblZDDWLB);
                    unitAddr.setText("单位地址：" + nterprise.ctl00_MainContent_txtDWDZ);
                    //unitNum.setText("单位代号：" + nterprise.account);
                    elseProperties.setText("其它属性：" + nterprise.ctl00_MainContent_lblDWQTQK);
                }


            }
        }
    }*/

    Household enterprise;

    private void setHousehold() {
        GetRequest re = new GetRequest();
        re.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                //mList = new ArrayList<Household>();
                Gson gson = new Gson();
                try {
                    if (!s.isEmpty()) {

                        JSONArray objects = (new JSONObject(s)).getJSONArray(Resources.RESULTS);
                        //for (int i = 0; i < objects.length(); i++) {
                        JSONObject obj = objects.getJSONObject(0);
                        enterprise = gson.fromJson(obj.toString(), new TypeToken<Household>() {
                        }.getType());
                        //mList.add(enterprise);
                        unitName.setText("单位名称：" + enterprise.ctl00_MainContent_txtDWMC);
                        fireDangerProperties.setText("火灾危险性：" + enterprise.ctl00_MainContent_lblZDDWLB);
                        unitAddr.setText("单位地址：" + enterprise.ctl00_MainContent_txtDWDZ);
                        unitNum.setText("单位代号：" + enterprise.account);
                        elseProperties.setText("其它属性：" + enterprise.ctl00_MainContent_lblDWQTQK);

                        //  }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "网络异常或该账号无权限", Toast.LENGTH_LONG).show();
                    getSharedPreferences("fpplus", MODE_PRIVATE)
                            .edit().putString("token", "")
                            .commit();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }

            }
        });
        re.execute(Resources.HOUSEHOLD);
    }


    private void setMsgNum() {
        GetRequest re = new GetRequest();
        re.setCallback(new HttpCallback() {
            @Override
            public void handleData(String s) {
                JSONObject object = null;
                try {
                    if (!s.isEmpty()) {
                        object = new JSONObject(s);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (object != null) {

                    if (object.optInt("joint_defence_count") > 0)
                       /* badgeOfZoneDefense.setVisibility(View.GONE);
                    else*/ {
                        badgeOfZoneDefense.setText(String.valueOf(object.optInt("joint_defence_count")));

                        badgeOfZoneDefense.show();


                    }

                    if (object.optInt("todo_list_count") > 0)
                        /*badgeOftoDeal.setVisibility(View.GONE);
                    else */ {
                        badgeOftoDeal.setText(String.valueOf(object.optInt("todo_list_count")));

                        badgeOftoDeal.show();

                    }

                    if (object.optInt("notice_count") > 0)
                        /*badgeOfNotifications.setVisibility(View.GONE);
                    else*/ {
                        badgeOfNotifications.setText(String.valueOf(object.optInt("notice_count")));

                        badgeOfNotifications.show();

                    }
                }
            }

        });
        re.execute(Resources.MUN);

    }

}