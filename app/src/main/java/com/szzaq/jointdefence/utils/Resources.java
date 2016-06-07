package com.szzaq.jointdefence.utils;

public class Resources {/*户籍化账号 44021187:317,44020955:813*/
    public static final String RESULTS = "results";
    public static final String COUNT = "count";
    public static final String PREVIOUS = "previous";
    public static final String NEXT = "next";


    //public final static String URL_ROOT = "http://127.0.0.1:8000";
    //public final static String URL_ROOT = "http://120.24.220.29";
    public final static String URL_ROOT = "http://fpplus.mbintel.com";
    public final static String PREFIX = URL_ROOT + "/api";
    public final static String HOUSEHOLD = PREFIX + "/hjhinfo/";
    public static final String TODEAL = PREFIX + "/todo_list/";//代办事项
    public static final String ZONEDEFENSE = PREFIX + "/joint_defence/";//区域联防
    public static final String NOTIFICATIONS = PREFIX + "/notice/";//通知通告
    public static final String NOTIFICATIONSREADLOG = PREFIX + "/read_log/";//通知公告阅读日志
    public static final String DAILYCHECK = PREFIX + "/patrol_record/";//日常巡查
    public static final String MUN = PREFIX + "/new_msg/";//消息数量

    public final static String LOGIN = PREFIX + "/api-token-auth/";
    public final static String USER = PREFIX + "/user/";
    public final static String PERSONINFO = PREFIX + "/personalinfo/";//用户数据
    public final static String ROLE = PREFIX + "/role/";
    public final static String FIREMAN = PREFIX + "/fireman/";
    public final static String ORG = PREFIX + "/org/";
    public final static String DANGER = PREFIX + "/danger/";
    public final static String DANGERTYPE = PREFIX + "/dangertype/";//扫码三小场所后的意见填写列表
    public final static String FPCOMPANY = PREFIX + "/fpcompany/";
    public final static String KEYENTERPRISE = PREFIX + "/keyenterprise/";//先重点单位数据
    public final static String CROSS_CHECK = PREFIX + "/cross_check/";//再带ent_id交叉检查数据

    public final static String KEYENTERPRISE_DEVICE = PREFIX + "/keyenterprise/device/";
    public final static String KEYENTERPRISE_DEVICE_CHECK = PREFIX + "/keyenterprise/device/check/";//重点单位扫描结果检查记录
    public final static String MAINTAINER = PREFIX + "/maintainer/";
    public final static String SMALLSITE = PREFIX + "/smallsite/";
    public final static String SMALLSITE_CHECK = PREFIX + "/smallsitecheck/";
    public final static String SMALLSITE_RECHECK = PREFIX + "/smallsiterecheck/";
    public final static String SMALLSITE_TYPE = PREFIX + "/smallsitetype/";
    public final static String CONFIGURATION = PREFIX + "/config/";
    public final static String IMAGE_STORE = PREFIX + "/imagestore/";
    public final static String TASK = PREFIX + "/task/";
    public final static String VERSION = PREFIX + "/version/?client=3";//版本检测
    public final static String CHANGE_PWD = URL_ROOT + "/changepassword";
    public final static String COIN_TRANSACTION = PREFIX + "/coin_transaction/";
    public final static String COIN_DETAIL = PREFIX + "/coin_detail/";
    public final static String COIN_ACCOUNT = PREFIX + "/coin_account/";//金币todo：带user_id:  + "?user__id=" + App.getInstance().getUser().getId()
    public final static String MICRO_FIRE_STATION = PREFIX + "/microstation/";//微型消防站todo：带lng,lat
    public final static String MICRO_FIRE_STATION_MEMBERS = PREFIX + "/stationmembers/";
    public final static String MICRO_FIRE_STATION_EQUIP = PREFIX + "/firefightingequipment/";
    public final static String AUDIO = PREFIX + "/audio/";
    public final static String HJH_CONFIG = PREFIX + "/hjhconfig/";
}
