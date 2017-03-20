package com.jd.jarvisdemonim.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jd.jarvisdemonim.database.CPDataHelper;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/13 0013
 * Name:
 * OverView: contentprovider 内容提供者,数据共享;
 * Usage:
 * 外界获取时:
 * 1.先使用URI获取uricode;
 * 2.使用uricode获取那张表;
 * 3.对找到的那张表增删改查;
 *
 * tips: contentprovider改变时需要notifychange;
 */

public class JProvider extends ContentProvider {
    private String TAG = "jProvider";
    private Context mContext;
    private CPDataHelper mCpDataHelper;

    //contentprovider的唯一标识;
    private static String AUTHORITYID = "com.jd.jarvisdemonim.test.provider.Jprovider";
    //contentprovider的外界要访问哪个URI数据集合;
    public static final Uri MY_URI = Uri.parse("content://" + AUTHORITYID + "/testBean");
    //contentprovider的获取数据访问哪个表的uricode;
    public static final int MY_URI_CODE = 0;

    //设置匹配的Uri;
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITYID, "testBean", MY_URI_CODE);//将uri和uricode关联在一起;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            initDataBase();
        }
    };

    private void initDataBase() {
        mCpDataHelper = new CPDataHelper(mContext);
        mCpDataHelper.clearSelf();

    }

    @Override
    public boolean onCreate() {
        LogUtils.i(TAG,"onCreate in JProvider");
        mContext = getContext();
        new Thread(runnable).start();//初始化数据库,也可同步初始化,不然下面调用失败;但是同步耗时操作,可以
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        LogUtils.i(TAG,"query in JProvider");
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("unsupport uri:" + uri);
        }
        Cursor cursor = null;
        if (mCpDataHelper != null) {
            SQLiteDatabase db = mCpDataHelper.getReadableDatabase();
            //不需要写自定义的增删改查,直接使用sqliteOpenhelper的增删改查;
            if (db.isOpen()) {
                cursor = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
            }
        }
        return cursor;
    }

    //表明数据类型;
    @Nullable
    @Override
    public String getType(Uri uri) {
        LogUtils.i(TAG,"getType in JProvider");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        LogUtils.i(TAG,"insert in JProvider");
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("unsupport uri:" + uri);
        }
        if (mCpDataHelper != null) {
            SQLiteDatabase db = mCpDataHelper.getWritableDatabase();
            //不需要写自定义的增删改查,直接使用sqliteOpenhelper的增删改查;
            if (db.isOpen()) {
                long insert = db.insert(tableName, null, values);
                if (insert > 0) {
                    //第二个参数是观察者,监听是否contentprovider改变;
                    mContext.getContentResolver().notifyChange(uri, null);
                }
            }
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        LogUtils.i(TAG,"delete in JProvider");
        String tableName = getTableName(uri);
        int deleteNum = 0;
        if (tableName == null) {
            throw new IllegalArgumentException("unsupport uri:" + uri);
        }
        if (mCpDataHelper != null) {
            SQLiteDatabase db = mCpDataHelper.getWritableDatabase();
            if (db.isOpen()) {
                deleteNum = db.delete(tableName,selection,selectionArgs);
            }
            if(deleteNum >0){
                mContext.getContentResolver().notifyChange(uri, null);
            }
        }
        return deleteNum;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        LogUtils.i(TAG,"update in JProvider");
        String tableName = getTableName(uri);
        int updateNum = 0;
        if (tableName == null) {
            throw new IllegalArgumentException("unsupport uri:" + uri);
        }
        if(mCpDataHelper!=null){
            SQLiteDatabase db = mCpDataHelper.getWritableDatabase();
            if(db.isOpen()){
                updateNum = db.update(tableName,values,selection,selectionArgs);//使用的都不是自定义的增删改查方法,原生的方法
            }
            if(updateNum>0){
                mContext.getContentResolver().notifyChange(uri,null);
            }
        }
        return updateNum;
    }

    //自定义方法
    public long getCount(String uristring) {
        Uri uri = Uri.parse(uristring);
        String tableName = getTableName(uri);
        SQLiteDatabase db = mCpDataHelper.getReadableDatabase();
        long count = 0;
        Cursor cursor = db.rawQuery("select count(*) from " + tableName, null);
        cursor.moveToFirst();
        count = cursor.getLong(0);
        cursor.close();
        db.close();
        return count;
    }

    /*
     * 这个方法是与contentprovider和contentresolver方法相同;调用自定义的方法;
     * @param method
     * @param arg
     * @param extras
     * @return
     */
    @Nullable
    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        Bundle returnbundle = null;
        if(method.equals("getCount")){
            returnbundle = new Bundle();
            returnbundle.putLong("long",getCount(arg));
        }
        return returnbundle;
    }


    //根据uri获取相应的表名;
    public String getTableName(Uri mUri) {
        String tableName = null;
        switch (mUriMatcher.match(mUri)) {
            case MY_URI_CODE://通过uri找到uricode;
                tableName = CPDataHelper.TABLE_CONTENTPROVIDER_NAME;
                break;
            default:
                break;
        }
        return tableName;//返回表名,增删改查;
    }
}
