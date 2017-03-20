package com.jd.jarvisdemonim.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/16 0016
 * Name:
 * OverView:
 * Usage:
 */

@Entity
public class InnerDemo {
    @Id
    private Long id;
    private String target;
    private String color;

    @Generated(hash = 72969862)
    public InnerDemo(Long id, String target, String color) {
        this.id = id;
        this.target = target;
        this.color = color;
    }

    @Generated(hash = 1932067561)
    public InnerDemo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
