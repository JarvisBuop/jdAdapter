package com.jd.jdkit.entity;

import java.io.Serializable;

/**
 * Auther: Jarvis Dong (MrDmBuopStudio@163.com)
 * Time: on 2016/8/15 0015:上午 11:51
 * Name:
 * OverView:
 * Usage:
 */
public class BaseSortIndexBean implements Serializable {
    public String topName;   //显示的数据
    public String sortLetters;  //显示数据拼音的首字母

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
