package com.jd.jarvisdemonim.utils;

import java.io.Serializable;

/**
 * Auther: Jarvis Dong (MrDmBuopStudio@163.com)
 * Time: on 2016/8/15 0015:上午 11:51
 * Name:
 * OverView:
 * Usage:
 */
public class SortModelData implements Serializable{
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母

    @Override
    public String toString() {
        return "SortModelData{" +
                "name='" + name + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
