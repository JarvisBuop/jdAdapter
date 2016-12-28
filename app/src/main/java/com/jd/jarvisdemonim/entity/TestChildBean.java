package com.jd.jarvisdemonim.entity;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/23 0023
 * Name:
 * OverView:
 * Usage:
 */
public class TestChildBean {
    private String name;
    private String content;

    public TestChildBean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
