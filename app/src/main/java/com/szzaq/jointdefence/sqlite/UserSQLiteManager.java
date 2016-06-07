package com.szzaq.jointdefence.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class UserSQLiteManager {

    private UserDBHelper helper;
    private SQLiteDatabase db;

    /*public UserSQLiteManager(Context context) {
        helper = new UserDBHelper(context);
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            if (db.isOpen())
                db.close();
        }
    }*/


    /**
     * 存储重点单位检查数据
     *
     * @param msgjson
     */
    public void updateEnterpriseCheck(String msgjson) {
        if (!db.isOpen())
            db = helper.getWritableDatabase();
        db.beginTransaction();
        JSONArray jarr;
        try {
            jarr = new JSONArray(msgjson);
            for (int i = 0; i < jarr.length(); i++) {
                JSONObject json = jarr.getJSONObject(i);
                db.execSQL("INSERT INTO msg(receiver,receiver_name,sender,sender_name,mtype,mbody,created,isnew,chatgroup,isgroup) "
                                + "VALUES(?,?,?,?,?,?,?,?,?,?)"
                        , new Object[]{
                                json.getString("receiver"),
                                json.getString("receiver_name"),
                                json.getString("sender"),
                                json.getString("sender_name"),
                                json.getString("mtype"),
                                json.getString("mbody"),
                                json.getString("created"),
                                json.getInt("isnew"),
                                json.getString("chatgroup"),
                                json.getInt("isgroup"),
                        });
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public HashMap<String, Object> queryContact(int id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (!db.isOpen())
            db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT id,username,usertype,portrait FROM contacts WHERE id=?"
                , new String[]{id + ""});
        while (c.moveToNext()) {
            map.put("userid", c.getInt(0));
            map.put("username", c.getString(1));
            map.put("usertype", c.getInt(2));
            map.put("portrait", c.getString(3));
        }
        db.close();
        return map;
    }

    public String queryContactList() {
        String retStr = "";
        if (!db.isOpen())
            db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT contact_list FROM contact_list", null);
        while (c.moveToNext()) {
            retStr = c.getString(0);

        }
        db.close();
        return retStr;
    }

    public void updateContactList(String value) {
        if (!db.isOpen())
            db = helper.getWritableDatabase();
        try {
            db.rawQuery("DELETE FROM contact_list", null);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        db.close();
        if (!db.isOpen())
            db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO contact_list(contact_list) VALUES(?)", new String[]{value});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void updateContact(JSONObject contact) {
        if (!db.isOpen())
            db = helper.getWritableDatabase();
        db.beginTransaction();
        /*String dating_no = "";
        try {
            dating_no = contact.getString("user_dating_no");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }*/


        try {
            String[] strFields =
                    new String[]{"user_dating_no", "user_name", "first_letter", "user_portrait"};
            String fields = "";
            Object[] values = new Object[4];
            int counter = 0;
            for (String s : strFields) {
                values[counter++] = contact.getString(s);
                fields += s + ",";
            }
            fields = fields.substring(0, fields.length() - 1);
            db.execSQL("INSERT INTO contacts(" + fields + ") "
                            + "VALUES(?,?,?,?)",
                    values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public void closeDB() {
        db.close();
    }
}
