package com.jd.jarvisdemonim.views;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.RemoteViews;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/21 0021
 * Name:
 * OverView: 定义桌面小工具;
 * Usage:实现的效果是点击一次后,旋转,然后不停的旋转;
 */

public class MyAppwidget extends AppWidgetProvider {
    public static final String CLICK_ACTION = "com.jd.jarvisdemonim.views.action.click";
    public static final String TAG = "MyAppwidget";


    public MyAppwidget() {
        super();
    }

    //如果小部件被单机了,执行的动作,判断是否自己的action;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context, intent);
        LogUtils.i(TAG, "onReceive action=" + intent.getAction());
        if (intent.getAction().equals(CLICK_ACTION)) {
            ToastUtils.showToast("被点击");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.nim_emoji_icon);
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    //动画效果;
                    for (int i = 0; i < 37; i++) {
                        LogUtils.i(TAG, "onReceive i="+i);
                        float degree = (i * 10) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_windows);
                        remoteViews.setImageViewBitmap(R.id.widget_img, rotateBitmap(context, bitmap, degree));
                        remoteViews.setTextViewText(R.id.widget_txt, "桌面工具");
                        Intent intent1 = new Intent();
                        intent1.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteViews.setOnClickPendingIntent(R.id.widget_img, pendingIntent);
                        manager.updateAppWidget(new ComponentName(context, MyAppwidget.class), remoteViews);
                        SystemClock.sleep(50);
                    }
                }
            }).start();
        }
    }

    //矩阵旋转图片;
    private Bitmap rotateBitmap(Context context, Bitmap srcBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
        return bitmap;
    }

    //每次更新时调用一次的方法;updatePeriodMillis由这个决定;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        int length = appWidgetIds.length;
        LogUtils.i(TAG, "onUpdate " + length);
        for (int i = 0; i < length; i++) {
            int appId = appWidgetIds[i];
            onwidgetUpdata(context, appWidgetManager, appId);
        }
    }

    //更新;
    private void onwidgetUpdata(Context context, AppWidgetManager appWidgetManager, int appwidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_windows);
        //点击事件发送的广播;
        Intent intent = new Intent();
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_img, pendingIntent);
        appWidgetManager.updateAppWidget(appwidgetId, remoteViews);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        LogUtils.i(TAG, "onAppWidgetOptionsChanged");
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    /**
     * 每次删除一次小部件运行一次;
     *
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        LogUtils.i(TAG, "onDeleted");
        super.onDeleted(context, appWidgetIds);
    }

    /**
     * 第一次添加到桌面;
     *
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        LogUtils.i(TAG, "onEnabled");
        super.onEnabled(context);
    }

    /**
     * 最后一个被删除运行;
     *
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        LogUtils.i(TAG, "onDisabled");
        super.onDisabled(context);
    }

    /**
     * 保存备用,马上运行onUpdata;
     *
     * @param context
     * @param oldWidgetIds
     * @param newWidgetIds
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        LogUtils.i(TAG, "onRestored");
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}
