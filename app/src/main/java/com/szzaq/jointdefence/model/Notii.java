package com.szzaq.jointdefence.model;

/**
 * Created by DOS on 2016/5/13 0013.
 */
public class Notii {
    public String url, content, created, to_user//通知用户，该字段为null时表示通知所有用户
            ;
    public  String[] attachment_urls, attachments;

    public Notii(String msg, String time,  String[] imgUrl) {
        this.content = msg;
        this.created = time;
        this.attachment_urls = imgUrl;
    }

    public READLOG read_log;

   class READLOG {     //key为userId

        String userId;


    }
}
