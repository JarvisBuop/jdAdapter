package com.jd.jarvisdemonim.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/12 0012
 * Name:
 * OverView: 使用于json下载是列表时候
 * Usage:
 */

public class BaseModelList<T> implements Serializable {
    private List<T> mlist;

    public List<T> getMlist() {
        return mlist;
    }

    public void setMlist(List<T> mlist) {
        this.mlist = mlist;
    }
}
