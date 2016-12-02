package com.purplefront.jiro_dev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.purplefront.jiro_dev.model.Databaseaccess;

import java.util.ArrayList;
import java.util.List;

public class dbhelp {


    private static final String DATABASE_NAME = "jiro";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "user_master";

    public static final String KEY_USID = "id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STATUS = "status";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_BUSINESS_ID = "business_id";
    public static final String KEY_USER = "name";


    private SQLiteDatabase ourdb;
    private DatabaseHelper2 ourhelper;
    private final Context ourctx;


    protected static class DatabaseHelper2 extends SQLiteOpenHelper {

        public DatabaseHelper2(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_USID + " INTEGER NOT NULL, " +
                    KEY_BUSINESS_ID + " INTEGER NOT NULL, " +
                    KEY_USER + " TEXT, " +
                    KEY_PHONE + " TEXT, " +
                    KEY_EMAIL + " TEXT NOT NULL, " +
                    KEY_PASSWORD + " TEXT NOT NULL, " +
                    KEY_STATUS + " TINYINT NOT NULL DEFAULT 0);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }

        public List<Databaseaccess> getTodo() {

            List<Databaseaccess> tags = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM user_master";

            Log.e("query", selectQuery);

            Cursor c = db.rawQuery(selectQuery, null);
            if (c != null) {
                c.moveToFirst();

                Databaseaccess td = new Databaseaccess();
                td.setId(c.getString(c.getColumnIndex("id")));
                td.setBusiness_id((c.getString(c.getColumnIndex("business_id"))));
                td.setName(c.getString(c.getColumnIndex("name")));
                td.setPhone(c.getString(c.getColumnIndex("phone")));
                td.setEmail((c.getString(c.getColumnIndex("email"))));
                td.setPassword(c.getString(c.getColumnIndex("password")));
                td.setActive(c.getString(c.getColumnIndex("active")));
                tags.add(td);
                c.close();
            }
            return tags;
        }
    }

    public dbhelp(Context ctx) {
        ourctx = ctx;
    }

    public dbhelp open() throws SQLException {
        ourhelper = new DatabaseHelper2(ourctx);
        ourdb = ourhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourhelper.close();
    }

    public void createuser(String id2, String business_id, String email2, String password2,  String name, String contact) {
        ourdb.delete(DATABASE_TABLE, null, null);
        ContentValues cv = new ContentValues();
        cv.put(KEY_USID, id2);
        cv.put(KEY_BUSINESS_ID, business_id);
        cv.put(KEY_USER, name);
        cv.put(KEY_EMAIL, email2);
        cv.put(KEY_PASSWORD, password2);
        cv.put(KEY_PHONE, contact);
        cv.put(KEY_STATUS, "1");
        ourdb.insert(DATABASE_TABLE, null, cv);

    }

    public void logout_user() {
        ourdb.delete(DATABASE_TABLE, null, null);
    }

    public void updateeuser(String id, String name, String phone, String email) {
        ContentValues cv4 = new ContentValues();
        cv4.put(KEY_USER, name);
        cv4.put(KEY_PHONE, phone);
        cv4.put(KEY_EMAIL, email);
        ourdb.update(DATABASE_TABLE, cv4, KEY_USID + "=" + id, null);

    }


    public void updateeuser(String id, String name) {
        ContentValues cv4 = new ContentValues();
        cv4.put(KEY_USER, name);
        ourdb.update(DATABASE_TABLE, cv4, KEY_USID + "=" + id, null);

    }




    public void updatepassword(String id, String password) {
        ContentValues cv4 = new ContentValues();
        cv4.put(KEY_PASSWORD, password);
        ourdb.update(DATABASE_TABLE, cv4, KEY_USID + "=" + id, null);

    }
}