package com.szzaq.jointdefence.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.szzaq.jointdefence.Adialog.AlertActivity;
import com.szzaq.jointdefence.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOS on 2016/4/15 0015.
 */
public class MessageDBManager {
    private final SQLiteDatabase db;

    public MessageDBManager(Context context) {
        MessagesDBHelper DBHelper = new MessagesDBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化
        //db = mDBHelper.getWritableDatabase();
        db = DBHelper.getReadableDatabase();
    }


    public void add(Message message) {
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO message VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{message.time, message.username, message.msg, message.portrait, message.contenttype, message.url, message.type});

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /*public void add(String message) {
        //DBHelper.onUpgrade(db, MessagesDBHelper.DBVERSION, 2);
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO message VALUES(null, ?, ?, ?, ?, ?, ?)", new Object[]{mMessage.time, mMessage.username, mMessage.msg, mMessage.portrait, mMessage.contenttype, message});

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }*/

    public List<Message> query() {
        List<Message> persons = new ArrayList<Message>();
        Cursor c = queryTheCursor();
        Message person;
        while (c.moveToNext()) {
            person = new Message();
            person.time = c.getString(c.getColumnIndex(AlertActivity.TIME));
            person.username = c.getString(c.getColumnIndex("username"));
            person.msg = c.getString(c.getColumnIndex("msg"));
            person.portrait = c.getString(c.getColumnIndex(AlertActivity.PORTRAIT));
            person.contenttype = c.getString(c.getColumnIndex("contenttype"));
            person.url = c.getString(c.getColumnIndex(AlertActivity.URL));
            person.type = c.getString(c.getColumnIndex(AlertActivity.TYPE));
            persons.add(person);
        }
        c.close();
        return persons;
    }


    public Cursor queryTheCursor() {
        return db.rawQuery("SELECT * FROM message", null);
    }


    public void closeDB() {
        db.close();
    }
}
