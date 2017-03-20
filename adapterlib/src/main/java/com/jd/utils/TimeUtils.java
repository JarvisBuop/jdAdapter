package com.jd.utils;

import java.util.Date;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/28 0028
 * Name:
 * OverView:
 * Usage:
 */

public class TimeUtils {
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    /**
     * 当前时间和所给时间的时间差
     *
     * @param date
     * @return
     */
    public static String formatTimeBefore(Long date) {
//			long delta = new Date().getTime() - date.getTime();
        long delta = System.currentTimeMillis() - date;
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 60L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    //存储当前时间;
    private static long storeTime = 0l;

    //获取当前时间和上次刷新的时间差;
    public static long getLastTime(Date data) {
        long currentTime = storeTime;
        if (currentTime == 0) {
            currentTime = System.currentTimeMillis();
            return storeTime = currentTime;
        } else {
            storeTime = data.getTime();
            return currentTime;
        }
    }

    /**
     * 获得距离时间的字符串;
     *
     * @param time 毫秒数
     * @return
     */
    public static String friendlyTime(long time) {
        //获取time距离当前的秒数
        int ct = (int) ((System.currentTimeMillis() - time) / 1000);

        if (ct == 0) {
            return "刚刚";
        }

        if (ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if (ct >= 60 && ct < 3600) {
            return Math.max(ct / 60, 1) + "分钟前";
        }
        if (ct >= 3600 && ct < 86400)
            return ct / 3600 + "小时前";
        if (ct >= 86400 && ct < 2592000) { //86400 * 30
            int day = ct / 86400;
            return day + "天前";
        }
        if (ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }


    /**
     * 毫秒转成秒
     *
     * @param date
     * @return
     */
    private static long toSeconds(long date) {
        return date / 1000L;
    }

    /**
     * 毫秒转成分钟
     *
     * @param date
     * @return
     */
    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    /**
     * 毫秒转成小时
     *
     * @param date
     * @return
     */
    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    /**
     * 毫秒转成天
     *
     * @param date
     * @return
     */
    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    /**
     * 毫秒转成月
     *
     * @param date
     * @return
     */
    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    /**
     * 毫秒转成年
     *
     * @param date
     * @return
     */
    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }
}
