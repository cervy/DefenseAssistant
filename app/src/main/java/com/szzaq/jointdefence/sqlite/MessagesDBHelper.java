package com.szzaq.jointdefence.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
public class MessagesDBHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "messages.db";
    public static final int DBVERSION = 1;

    public MessagesDBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    public MessagesDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL("CREATE TABLE [message](\n" +
                    "    [_id] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "    [time] VARCHAR, \n" +
                    "    [username] VARCHAR, \n" +
                    "    [msg] VARCHAR, \n" +
                    "    [portrait] VARCHAR, \n" +
                    "    [contenttype] VARCHAR, \n" +
                    "    [url] VARCHAR, \n" +
                    "    [type] VARCHAR);");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*switch (oldVersion) {
            case 0:
                if (newVersion <= 1) {
                    return;
                }

                db.beginTransaction();
                try {
                    db.execSQL("ALTER TABLE message ADD contenttype VARCHAR， url VARCHAR");
                    db.setTransactionSuccessful();
                } catch (Throwable ex) {
                    Log.e("sqlUpgrade", ex.getMessage(), ex);
                    break;
                } finally {
                    db.endTransaction();
                }

                return;
        }

        dropAll(db);
        onCreate(db);*/
    }

    /*private void dropAll(SQLiteDatabase db) {
        Log.e("sqlUpgrade", "Destroying all old data.");
        db.execSQL("DROP TABLE IF EXISTS config");
        db.execSQL("DROP TABLE IF EXISTS application");

    }*/


}
