package com.jd.jarvisdemonim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/13 0013
 * Name:
 * OverView: 测试contentprovider 的数据库;
 * Usage:
 */

public class CPDataHelper extends SQLiteOpenHelper {
    //建立数据库;
    private static final String DB_CONTENTPROVIDER_NAME = "test_provider.db";
    //表名
    public static final String TABLE_CONTENTPROVIDER_NAME = "testprovider";

    //参数名称:
    public static String PARAM_ONE = "content";
    public static String PARAM_TWO = "mark";
    public static String PARAM_THREE = "";

    //建表参数语句;
    private static final String TABLE_PARAMS = "(" + "_id integer primary key autoincrement," +
            PARAM_ONE + " varchar,"
            + PARAM_TWO + " integer"
            + ")";

    //建表语句;
    private static final String CREATE_TABLE = "create table if not exists " + TABLE_CONTENTPROVIDER_NAME + TABLE_PARAMS;
    //删表语句;
    private static final String DROP_TABLE = "drop table if exists" + TABLE_CONTENTPROVIDER_NAME;
    //版本号;
    private static final int VERSION = 1;
    private SQLiteDatabase db;

    public CPDataHelper(Context context) {
        this(context, DB_CONTENTPROVIDER_NAME, null, VERSION);
    }

    //注意onUpdata;
    public CPDataHelper(Context context, int version) {
        this(context, TABLE_CONTENTPROVIDER_NAME, null, version);
    }

    public CPDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, TABLE_CONTENTPROVIDER_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TABLE);//创建表,这里可建多张表;
    }

    //oldVersion和newVersion不同时,会运行;
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    //操作方法,增删改查;
    public boolean insertSelf(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long insertNum = 0l;
        if (db.isOpen()) {
            insertNum = db.insert(TABLE_CONTENTPROVIDER_NAME, null, values);
            db.close();
        }
        return insertNum > 0;
    }

    //"delete from test_table"
    public boolean deleteSelf(@Nullable String whereClause, @Nullable String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        long deleteNum = 0l;
        if (db.isOpen()) {
            deleteNum = db.delete(TABLE_CONTENTPROVIDER_NAME, whereClause, whereArgs);
            db.close();
        }
        return deleteNum > 0;
    }

    //修改方法,如果传递的参数都是null,则修改所有的数据;
    public boolean updateSelf(ContentValues values, @Nullable String whereClause, @Nullable String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        long updataNum = 0l;
        if (db.isOpen()) {
            updataNum = db.update(TABLE_CONTENTPROVIDER_NAME, values, whereClause, whereArgs);
            db.close();
        }
        return updataNum > 0;
    }

    public Cursor querySelf() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if (db.isOpen()) {
            cursor = db.query(TABLE_CONTENTPROVIDER_NAME, null, null, null, null, null, null);
            db.close();
        }
        return cursor;
    }

    public Cursor querySelf(@Nullable String selected, @Nullable String[] selectionArgus, boolean order) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = null; //需要查询的列
        String selection = selected; // 选择条件，给null查询所有
        String[] selectionArgs = selectionArgus;//选择条件参数,会把选择条件中的？替换成这个数组中的值
        String groupBy = null; // 分组语句 group by name  注意些的时候需要要group by 去掉
        String having = null;  // 过滤语句
        String orderBy = order ? "_id desc" : "_id asc"; //排序
        Cursor cursor = null; //返回的游标对象;
        if (db.isOpen()) {
            cursor = db.query(TABLE_CONTENTPROVIDER_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
            db.close();
        }
        return cursor;
    }

    public long getCountSelf() {
        SQLiteDatabase db = getReadableDatabase();
        long count = 0;
        Cursor cursor = db.rawQuery("select count(*) from " + TABLE_CONTENTPROVIDER_NAME, null);
        cursor.moveToFirst();
        count = cursor.getLong(0);
        cursor.close();
        db.close();
        return count;
    }

    //清除数据库所有数据;
    public void clearSelf() {
        if (db != null) {
            deleteSelf(null, null);
            db.close();
        }
    }

    //关闭
    private void closeDb() {
        if (db != null) {
            db.close();
        }
    }

    /*
getReadableDatabase()并不是以只读方式打开数据库，而是先执行getWritableDatabase()，失败的情况下才以只读方式打开数据库.。
但getWritableDatabase()方法以读写方式打开数据库，一旦数据库的磁盘空间满了，数据库就只能读而不能写，
  getWritableDatabase()打开数据库就会出错。getReadableDatabase()方法先以读写方式打开数据库，
  倘若使用如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库.*/
}
