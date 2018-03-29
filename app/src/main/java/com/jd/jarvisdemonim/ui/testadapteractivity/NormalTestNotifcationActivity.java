package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.receiver.LockReceiver;
import com.jd.jarvisdemonim.ui.MainActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView.CustomDrawable;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ScreenUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/20 0020
 * Name:
 * OverView: 123测试notification的通知栏的提示消息,4测试桌面插件的使用;
 * AppWidgetProvider extends broadcastreceiver;
 * <p>
 * Usage: 和桌面悬浮框;
 */

public class NormalTestNotifcationActivity extends DBaseActivity implements View.OnClickListener {
    @Bind(R.id.txt1)
    TextView txt1;
    @Bind(R.id.txt2)
    TextView txt2;
    @Bind(R.id.txt3)
    TextView txt3;
    @Bind(R.id.txt4)
    TextView txt4;
    @Bind(R.id.txt5)
    TextView txt5;
    @Bind(R.id.txt6)
    TextView txt6;
    private boolean isChanged = false;

    private WindowManager.LayoutParams layoutParams;
    private WindowManager wm;
    private TextView floatButton;
    boolean isadd = false;
    private float rawX;
    private float rawY;

    private DevicePolicyManager dpManager;
    private ComponentName componentName;
    boolean isClose;

    @Override
    public int getContentViewId() {
        return R.layout.activity_notifcation;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //设备管理接受者;
        dpManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, LockReceiver.class);

    }

    private boolean lock() {
        boolean active = dpManager.isAdminActive(componentName);
        LogUtils.e("enter in");
        if (active) {
            //已获得管理员的权限，则直接锁屏
            dpManager.lockNow();
            return true;
        } else {
            //没有管理员的权限，则获取管理员的权限
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            //会在激活界面中显示的额外内容
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "----这是一键锁屏激活界面- by project jarvisDemoNim-----");
            startActivityForResult(intent, 0);
            //锁屏
            dpManager.lockNow();
            return true;
        }
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        txt1.setOnClickListener(this);
        txt2.setOnClickListener(this);
        txt3.setOnClickListener(this);
        txt4.setOnClickListener(this);
        txt5.setOnClickListener(this);
        txt6.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt1:
                doOne();
                TransitionDrawable td = (TransitionDrawable) txt1.getBackground();
                if (isChanged) {
                    td.reverseTransition(500);
                } else {
                    td.startTransition(500);
                }
                isChanged = !isChanged;
                break;
            case R.id.txt2:
                doTwo();
                ScaleDrawable sd = (ScaleDrawable) txt2.getBackground();
                sd.setLevel(1);
                break;
            case R.id.txt3:
                doThree();
                ClipDrawable cd = (ClipDrawable) txt3.getBackground();
                cd.setLevel(5000);
                break;
            case R.id.txt4:
                doFour();
                txt4.setBackground(new CustomDrawable(R.color.blue_alpha));
                break;
            case R.id.txt5:
                doFiveWindow();
                break;
            case R.id.txt6:
                doLockScreen();
                break;
        }
    }

    /**
     * 一键锁屏
     */
    private void doLockScreen() {
        isClose = lock();
        if (isClose) finish();
    }

    //window 悬浮window;
    private void doFiveWindow() {
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        floatButton = new TextView(mContext);
        floatButton.setText("点我");
        floatButton.setTextColor(getResources().getColor(R.color.black));
        floatButton.setBackgroundResource(R.drawable.head_icon_5);
        layoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.RGBA_8888);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE/*| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED*/;//锁屏;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.x = 200;
        layoutParams.y = 400;
        floatButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rawX = event.getRawX() - floatButton.getMeasuredWidth() / 2;
                rawY = event.getRawY() - floatButton.getMeasuredHeight() / 2;
                LogUtils.e("wm" + rawX + "/" + rawY);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_UP:
                        final int width = ScreenUtils.getScreenWidth();
                        if (rawX > width / 2) {
                            performAnimate(floatButton, (int) rawX, width - floatButton.getMeasuredWidth() / 2);
                        } else {
                            performAnimate(floatButton, (int) rawX, 0);
                        }

                        break;
                    case MotionEvent.ACTION_MOVE:
                        layoutParams.x = (int) rawX;
                        layoutParams.y = (int) rawY;
                        wm.updateViewLayout(floatButton, layoutParams);
                        break;
                }


                return false;
            }
        });
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("一点击");
            }
        });
        if (!isadd) {
            wm.addView(floatButton, layoutParams);
            isadd = true;
        }
    }

    /**
     * 使用属性动画ValueAnimator ,监听windowmanager的动画;
     *
     * @param target
     * @param start
     * @param ent
     */
    private void performAnimate(final View target, final int start, final int ent) {
        final ValueAnimator va = ValueAnimator.ofFloat(1, 100);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator ie = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentF = (int) animation.getAnimatedFraction();
                float fraction = va.getAnimatedFraction();//0-1
                Integer evaluate = ie.evaluate(fraction, start, ent);
                layoutParams.x = evaluate;
                layoutParams.y = (int) rawY;
                wm.updateViewLayout(target, layoutParams);
            }
        });
        va.setDuration(1000).start();
    }


    //桌面小部件;

    /**
     * //     * @see MyAppwidget.class
     */
    private void doFour() {
        Intent intent = new Intent();
        intent.setAction("com.jd.jarvisdemonim.views.action.click");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void doThree() {//很多手机不支持;
        Notification.Builder builder = new Notification.Builder(this);
        Intent mIntent = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.nim_emoji_icon);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle("悬挂式通知");
        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(this, NormalTestNotifcationActivity.class);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, builder.build());
    }

    /**
     * 折叠式notification,自定义视图;
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void doTwo() {
        //不支持折叠式
        Notification.Builder builder = new Notification.Builder(this);
        Intent mIntent = new Intent(mContext, NormalTestNotifcationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.nim_emoji_icon);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle("折叠式通知");
        //用RemoteViews来创建自定义Notification视图
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.item_left);
        remoteViews.setTextViewText(R.id.txt_title, "woqu");
        remoteViews.setImageViewResource(R.id.img_view, R.drawable.nim_emoji_icon);
        Intent intent1 = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(mContext, 2, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.ll_left, pendingIntent1);

        Notification notification = builder.build();
        //指定展开时的视图
        notification.bigContentView = remoteViews;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void doOne() {
        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setSmallIcon(R.drawable.nim_emoji_icon);
        builder.setTicker("hello world");//通知时的显示;
        builder.setContentText("你好");//下拉下来的显示内容;
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setContentInfo("info");
        builder.setContentTitle("title");
        Intent intent = new Intent(mContext, NormalTestNotifcationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(11, builder.build());


//        notificationManager.cancel(11);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, MainActivity.class), 0);
//        Notification notify1 = new Notification();
//        notify1.icon = R.drawable.nim_emoji_icon;
//        notify1.tickerText = "TickerText:您有新短消息，请注意查收！";
//        notify1.when = System.currentTimeMillis();
//        notify1.setLatestEventInfo(this, "Notification Title",
//                "This is the notification message", pendingIntent);
//        notify1.defaults =
//        notify1.number = 1;
//        notify1.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
//        // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1, notify1);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wm != null && floatButton.isAttachedToWindow()) {
            wm.removeViewImmediate(floatButton);
            isadd = false;
        }
    }
}
