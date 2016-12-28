package com.jd.jarvisdemonim.entity;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/19 0019 56
 * Name:
 * OverView:
 * Usage:
 */
public class TestBean {
    private int TAG = 1;
    private int res;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {

        this.res = res;
    }

    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestBean(int res, String name, String content) {
        this.res = res;
        this.name = name;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TestBean(String name, String content) {

        this.name = name;
        this.content = content;
    }
}
