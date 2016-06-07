package com.szzaq.jointdefence.model;

/**
 * Created by DOS on 2016/5/12 0012.
 */
public class HissAlert {
    public String msg, headurl, fireType, time, name;

    public HissAlert(String msg, String headurl, String fireType, String time, String name) {
        this.msg = msg;
        this.headurl = headurl;
        this.fireType = fireType;
        this.time = time;
        this.name = name;
    }
}
