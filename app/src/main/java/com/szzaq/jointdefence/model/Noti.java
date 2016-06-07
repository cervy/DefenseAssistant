package com.szzaq.jointdefence.model;

/**
 * Created by DOS on 2016/5/12 0012.
 */
public class Noti {
    public String msg, time, imgUrl, type;

    public Noti(String msg, String time, String type, String imgUrl) {
        this.msg = msg;
        this.type = type;
        this.time = time;
        this.imgUrl = imgUrl;
    }
}
