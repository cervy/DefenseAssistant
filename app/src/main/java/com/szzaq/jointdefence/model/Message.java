package com.szzaq.jointdefence.model;

/**
 * Created by DOS on 2016/4/15 0015.
 */
public class Message {
    public String username, msg, time, portrait, contenttype, url,type;


    public Message(String time, String username, String msg, String portrait, String contenttype, String url,String type) {
        this.time = time;
        this.url = url;
        this.username = username;
        this.msg = msg;
        this.portrait = portrait;
        this.contenttype = contenttype;
        this.type=type;

    }

    public Message() {

    }

    public String audioFileName;
    /*public Message(String msg, String contenttype, String extra) {
        this.msg = msg;
        this.contenttype = contenttype;
        this.extra = extra;

    }

    public String extra;*/
}
