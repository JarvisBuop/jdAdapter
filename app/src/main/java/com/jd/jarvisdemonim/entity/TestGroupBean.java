package com.jd.jarvisdemonim.entity;

import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/23 0023
 * Name:
 * OverView:
 * Usage:
 */
public class TestGroupBean {
    private boolean isExpand;

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    private String contentTitle;

    public TestGroupBean(boolean isExpand, String contentTitle, List<TestChildBean> bean) {
        this.isExpand = isExpand;
        this.contentTitle = contentTitle;
        this.bean = bean;
    }

    public TestGroupBean(boolean isExpand, String contentTitle) {
        this.isExpand = isExpand;
        this.contentTitle = contentTitle;
    }
    public TestGroupBean(boolean isExpand, List<TestChildBean> bean) {
        this.isExpand = isExpand;
        this.bean = bean;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public List<TestChildBean> getBean() {
        return bean;
    }

    public void setBean(List<TestChildBean> bean) {
        this.bean = bean;
    }

    private List<TestChildBean> bean;

}
