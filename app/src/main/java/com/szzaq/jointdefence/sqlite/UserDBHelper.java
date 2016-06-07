package com.szzaq.jointdefence.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fpplus";
    private static final int DATABASE_VERSION = 1;

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS 'KeyEnterpriseDeviceCheck' "
                    + "('id' integer NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "'status' integer NOT NULL, "
                    + "'fix_type' integer NOT NULL, "
                    + "'advice' text NULL, "
                    + "'created' datetime NOT NULL, "
                    + "'lat' real NOT NULL, "
                    + "'lng' real NOT NULL, "
                    + "'location' varchar(100) NOT NULL, "
                    + "'inspect_type' integer NOT NULL, "
                    + "'inspect_method' integer NULL, "
                    + "'inspect_time_type' integer NULL, "
                    + "'device_id' integer NOT NULL, "
                    + "'from_task_id' integer NULL , "
                    + "'parent_record_id' integer NULL, "
                    + "'expiration' datetime NULL)");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
