package com.jd.jarvisdemonim.entity;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/19 0019 56
 * Name:
 * OverView:
 * Usage:
 */
public class TestBean3 {
    public String getContent2() {
        return content2;
    }

    public TestBean3(String content, String content2) {
        this.content = content;
        this.content2 = content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    private int TAG = 3;
    private String content;
    private String content2;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TestBean3(String content) {

        this.content = content;
    }
}
