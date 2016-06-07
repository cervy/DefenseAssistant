package com.szzaq.jointdefence;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.szzaq.jointdefence.model.Agent;
import com.szzaq.jointdefence.model.Configuration;
import com.szzaq.jointdefence.model.UserInfo;
import com.szzaq.jointdefence.utils.CrashHandler;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * App类
 *
 * @author jiajiaUser
 * @date 2015/07/07
 */
public class App extends Application {
    private static App instance;


    private UserInfo user;
    private String token;
    public Configuration config;

    private String sessionId;
    private ArrayList<Agent> agentsList;//当前登录可选代理人列表
    private ArrayList<Activity> activityList;
    private double[] lngLat = new double[]{0.0d, 0.0d};
    private String location;
    private boolean dataChanged = false;

    public void onCreate() {

        super.onCreate();
        instance = this;
        initImageLoader(this);

        // todo:设置开启日志,发布时请关闭日志  JPushInterface.setDebugMode(true);
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

        CrashHandler.getInstance().init(this);
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //U_ssp.initSP(getSharedPreferences(Messages, MODE_PRIVATE));
    }
    //public static final String Messages="messages";

    /**
     * 初始化imageloader框架
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.MAX_PRIORITY).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static App getInstance() {
            /* if(null == instance)
                     {
	                    instance = new App();
	                    instance.activityList = new ArrayList<Activity>();
	                 }*/
        instance.activityList = new ArrayList<Activity>();
        return instance;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());

// 退出程序System.exit(0);
        System.exit(1);

    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public ArrayList<Agent> getAgentsList() {
        return agentsList;
    }

    public void setAgentsList(ArrayList<Agent> agentsList) {
        this.agentsList = agentsList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }


    //地图定位相关，在loginActivity初始化
    public double[] getLngLat() {
        return lngLat;
    }

    public void setLngLat(double[] lngLat) {
        this.lngLat = lngLat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    // --Commented out by Inspection START (2016/5/19 0019 18:14):
    public boolean isDataChanged() {
        return dataChanged;
    }
// --Commented out by Inspection STOP (2016/5/19 0019 18:14)

    public void setDataChanged(boolean dataChanged) {
        this.dataChanged = dataChanged;
    }


}
