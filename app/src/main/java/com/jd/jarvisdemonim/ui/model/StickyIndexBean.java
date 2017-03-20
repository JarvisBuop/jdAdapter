package com.jd.jarvisdemonim.ui.model;

import com.jd.jdkit.entity.BaseSortIndexBean;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/12 0012
 * Name:
 * OverView: 索引列表和吸顶列表的数据类型;
 * Usage:
 */

public class StickyIndexBean extends BaseSortIndexBean {
    //其他数据;
    public String name;
    public String gender;
    public String profession;

    public StickyIndexBean(String stickyTop, String name, String gender, String profession) {
        this.topName= stickyTop;
        this.name = name;
        this.gender = gender;
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "StickyIndexBean{" +
                "stickyTop='" + topName + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", profession='" + profession + '\'' +
                '}';
    }
}
