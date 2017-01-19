package com.jd.myadapterlib.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jd.myadapterlib.R;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/23 0023
 * Name:
 * OverView: 日历适配器(待完成)
 * Usage: 太乱,并不推荐,未优化;
 */
public class RecyCarlendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String Tag = "calendar";
    private Context mContext;
    private int layoutId;
    private ArrayList<Long> dateList;
    private Map<Long, Integer> enableList;//是否可选
    private Map<Long, Integer> selectList;//是否选中
    private Map<Long, Integer> contentList;//是否包含活动
    private Map<Long, DateState> dateStateList;//
    private final int DEFAULT = 0;
    private final int ENABLE = 1;
    private final int DISABLE = 2;//不可选
    private final int SELECT = 3;//选中
    private final int CONTENT = 4;//包含活动

    private boolean isLeapyear = false; // 是否为闰年
    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某一天是星期几
    private int lastDaysOfMonth = 0; // 上一个月的总天数
    private String[] dayNumber = new String[35]; // 一个gridview中的日期存入此数组中
    // private static String week[] = {"周日","周一","周二","周三","周四","周五","周六"};
    private Resources res = null;
    private Drawable drawable = null;

    private String currentYear = "";
    private String currentMonth = "";
    private String currentDay = "";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    private int currentFlag = -1; // 用于标记当天
    private int[] schDateTagFlag = null; // 存储当月所有的日程日期

    private String showYear = ""; // 用于在头部显示的年份
    private String showMonth = ""; // 用于在头部显示的月份
    private String animalsYear = "";
    private String leapMonth = ""; // 闰哪一个月
    private String cyclical = ""; // 天干地支
    // 系统当前时间
    private String sysDate = "";
    private int sys_year = 0;
    private String sys_month = "";
    private String sys_day = "";
    Long currentTime;


    Calendar c;
    Long oneday = 86400000L;//1000L*3600L*24L;
    String tempStr;
    private long tempNum;
    private int tempLoop;//当前item的数量;

    private OnCalendarItemClickListener mListener;

    public void setOnCalendarItemClickListener(OnCalendarItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnCalendarItemClickListener {
        /**
         * 当前位置,当前毫秒数,当前状态,当前view;
         *
         * @param pos
         * @param time
         * @param state
         * @param view
         */
        void calendarItem(int pos, long time, DateState state, View view);
    }

    public RecyCarlendarAdapter(RecyclerView mRecycler, int layoutId) {
        mContext = mRecycler.getContext();
        this.layoutId = layoutId;
        c = Calendar.getInstance();
        isLeapyear = isLeap(c.get(Calendar.YEAR));
        //时间戳设置成0时0分0秒，但是还有毫秒数
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        //清零毫秒数
        currentTime = c.getTimeInMillis() / 1000L * 1000L;

        dateList = new ArrayList<>();
        enableList = new HashMap<>();
        dateStateList = new HashMap<>();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        doStateAllocate();

    }

    Long temp;//与当日相邻的天数
    Long temp2;//某天的毫秒数

    private void doStateAllocate() {
        tempLoop = getItemCount();
        DateState dateState;
        for (int i = 0; i < tempLoop; i++) {
            dateState = new DateState();
            if (i < dayOfWeek - 1) {//今天的这一周的前几天，如当天星期四，那么这里是记录从星期日到星期三的时间戳
                temp = Long.valueOf(dayOfWeek - 1 - i);
                temp2 = currentTime - oneday * temp;
                dateList.add(temp2);
//                enableList.put(temp2,DISABLE);
                dateState.setDisable(true);
                dateStateList.put(temp2, dateState);
            } else {//记录今天起到getItemCount();的时间戳
                temp = Long.valueOf(i - dayOfWeek + 1);
                temp2 = currentTime + oneday * temp;
                dateList.add(temp2);
//                enableList.put(temp2,DEFAULT);
                dateState.setHasContent(true);
                dateStateList.put(temp2, dateState);
            }
        }

    }

    /**
     * 选中N天
     *
     * @param startTime 开始日期
     * @param dayNum    天数
     * @return
     */
    public boolean chooseTime(Long startTime, int dayNum) {
        c.setTimeInMillis(startTime);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        c.set(Calendar.MONTH, c.get(Calendar.MONTH));
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        startTime = c.getTimeInMillis();
        if (!dateList.contains(startTime)) {//时间戳不包含在list中
            return false;
        }
        tempLoop = getItemCount();
        DateState dateState;
        for (int i = 0; i < tempLoop; i++) {
            dateState = dateStateList.get(dateList.get(i));
            if (i < dayOfWeek - 1) {//今天的这一周的前几天，如当天星期四，那么这里是记录从星期天到星期三的时间戳
                temp = Long.valueOf(dayOfWeek - 1 - i);
                temp2 = currentTime - oneday * temp;
//                dateList.add(temp2);
//                enableList.put(temp2,DISABLE);
            } else {//记录今天起到getItemCount();的时间戳
                temp = Long.valueOf(i - dayOfWeek + 1);
                temp2 = currentTime + oneday * temp;
                dateState.setSelect(false);
                dateStateList.put(temp2, dateState);
//                dateList.add(temp2);
//                enableList.put(temp2,DEFAULT);
            }
        }
        for (int i = 0; i < dayNum; i++) {
//            enableList.put(startTime+oneday*i,ENABLE);
            dateStateList.get(startTime).setSelect(true);
            dateStateList.put(startTime + oneday * i, dateStateList.get(startTime));
        }
        this.notifyDataSetChanged();
        return true;
    }

    public boolean setEnableTime(Long startTime, Long endTime) {
//        if(startTime%oneday!=0){
//            startTime=startTime/oneday*oneday;
//        }
//        if(endTime%oneday!=0){
//            endTime=endTime/oneday*oneday;
//        }
        if (startTime < dateList.get(0)) {
            startTime = dateList.get(0);
        }
        if (startTime > endTime) {
            return false;
        }
        if (!dateList.contains(endTime)) {//时间戳不包含在list中
            return false;
        }
        tempLoop = getItemCount();
        for (int i = 0; i < tempLoop; i++) {
            if (i < dayOfWeek - 1) {//今天的这一周的前几天，如当天星期四，那么这里是记录从星期一到星期三的时间戳
                temp = Long.valueOf(dayOfWeek - 1 - i);
                temp2 = currentTime - oneday * temp;
//                dateList.add(temp2);
                enableList.put(temp2, DISABLE);
            } else {//记录今天起到getItemCount();的时间戳
                temp = Long.valueOf(i - dayOfWeek + 1);
                temp2 = currentTime + oneday * temp;
//                dateList.add(temp2);
                enableList.put(temp2, DEFAULT);
            }
        }

        tempNum = (endTime - startTime) / oneday + 1;
        for (int i = 0; i < tempNum; i++) {
            enableList.put(startTime + oneday * i, ENABLE);
        }
        this.notifyDataSetChanged();
        return true;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        return new RecyViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyViewHolder) {
            RecyViewHolder viewHolder = (RecyViewHolder) holder;
            sdf.applyPattern("d");
            tempStr = sdf.format(dateList.get(position));
            //全局优先设置1号金色日期，和黑色普通日期
            if (tempStr.equals("1")) {
                sdf.applyPattern("M月\nd");
                tempStr = sdf.format(dateList.get(position));
                viewHolder.setText(R.id.tv_date, tempStr);
//                itemView.tv.setTextColor(con.getResources().getColor(R.color.black));
//                itemView.tv.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.bg_rect_whitesolid));
            } else {
                viewHolder.setText(R.id.tv_date, tempStr);
//                itemView.tv.setTextColor(con.getResources().getColor(R.color.black));
//                itemView.tv.setBackgroundDrawable(con.getResources().getDrawable(R.drawable.bg_rect_whitesolid));
            }
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dateStateList.get(dateList.get(position)).isDisable()) {

                    } else {
                        if (mListener != null) {
                            mListener.calendarItem(position, dateList.get(position), dateStateList.get(dateList.get(position)), v);
                        }
                    }
                }
            });
            if (dateStateList.get(dateList.get(position)).isSelect()) {
                viewHolder.setVisible(R.id.iv_date_select, true);
                viewHolder.setTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.white));
            } else {
                viewHolder.setVisible(R.id.iv_date_select, false);
                viewHolder.setTextColor(R.id.tv_date, mContext.getResources().getColor(R.color.black));
            }
            if (dateStateList.get(dateList.get(position)).isHasContent() && position % 2 == 0) {
                viewHolder.setVisible(R.id.iv_date_point, true);
            } else {
                viewHolder.setVisible(R.id.iv_date_point, false);
            }
            if (dateStateList.get(dateList.get(position)).isDisable()) {
                viewHolder.setVisible(R.id.iv_date_point, false);
                viewHolder.setVisible(R.id.tv_date, false);
                viewHolder.setVisible(R.id.iv_date_select, false);
            } else {
                viewHolder.setVisible(R.id.tv_date, true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return isLeapyear == false ? 365 : 366;
    }

    public class DateState {
        //private  int ENABLE=1;
        private boolean isDisable = false;//默认可选
        private boolean isSelect = false;//默认不选中
        private boolean hasContent = false;//默认不包含活动

        public boolean isDisable() {
            return isDisable;
        }

        public void setDisable(boolean disable) {
            isDisable = disable;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isHasContent() {
            return hasContent;
        }

        public void setHasContent(boolean hasContent) {
            this.hasContent = hasContent;
        }
    }

    /**
     * 判断是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeap(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据传入的年份和月份，判断当前月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (isLeap(year)) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return -1;
    }
}
