package com.jd.jarvisdemonim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Auther: Jarvis Dong (MrDmBuopStudio@163.com)
 * Time: on 2016/8/25 0025:下午 12:55
 * Name:
 * OverView: sqlite 数据库; (可加缓存,暂没使用)
 * Usage:
 */
public class SyDataHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sycache.db";
    private static final String TABLE_NAME = "cachetable";

    public static final String IMG = "img";
    public static final String TITLE = "title";
    public static final String TIME = "time";
    public static final String LOCAL = "local";
    public static final String ACCOUNT = "account";
    public static final String LOOKNUM = "looknum";
    public static final String SPARE1 = "spare1";//空余;

    private static final int VERSION = 1;
    private SQLiteDatabase db;
    private static final String CREATE_TAB = "create table " + TABLE_NAME + "(_id integer primary key autoincrement," + IMG + " blob," + TITLE + " text," + TIME + " text," + LOCAL + " text," + ACCOUNT + " double," + LOOKNUM + " integer," + SPARE1 + " integer)";
    private static final String DROP_TAB = "drop table if exists " + TABLE_NAME;

    public SyDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, DB_NAME, null, VERSION, null);
    }

    public SyDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        Log.i("jarvisdb", "created--database");
        db.execSQL(CREATE_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("jarvisdbup", oldVersion + "/" + newVersion);
        db.execSQL(DROP_TAB);
        onCreate(db);
    }

    //增
    public void insert(ContentValues values) {
        if (db.isOpen()) {
            db.insert(TABLE_NAME, null, values);
        }
    }

    //删
    public void delete(String title) {
        if (db.isOpen()) {
            if (title != null) {
                int num = db.delete(TABLE_NAME, "title like ?%", new String[]{title});
                Log.i("jarvisdbde", num + "");
            } else {
                db.delete(TABLE_NAME, null, null);
            }
        }
    }

    //改
    public void update(ContentValues values, String whereArgs) {
        int num = db.update(TABLE_NAME, values, "title like ?%", new String[]{whereArgs});
        Log.i("jarvisdbup", num + "");
    }

    //查
    public Cursor query(String whereArgs) {
        if (db.isOpen()) {
            Cursor c;
            if (whereArgs != null) {
                c = db.query(TABLE_NAME, null, "title like ?%", new String[]{whereArgs}, null, null, "_id desc");
            } else {
                c = db.query(TABLE_NAME, null, null, null, null, null, null);
            }
//        while (c.moveToNext()) {
//            Log.i("jarvisdbquery", c.getString(c.getColumnIndex("title")));
//        }//只能迭代一次;
            return c;
        }
        return null;

    }

    //类型
    public Cursor query(int spare) {
        Cursor c;
        if (spare > -1) {
            c = db.query(TABLE_NAME, null, SPARE1 + "=?", new String[]{spare + ""}, null, null, "_id asc");//asc desc
        } else {
            c = db.query(TABLE_NAME, null, null, null, null, null, null);
        }
        return c;
    }

    //清除数据库所有数据;
    public void clear() {
        if (db != null) {
            delete(null);
        }
    }

    //关闭
    public void closeDb() {
        if (db != null) {
            db.close();
        }
    }
}
