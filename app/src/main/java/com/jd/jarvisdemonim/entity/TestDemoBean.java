package com.jd.jarvisdemonim.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/15 0015
 * Name:
 * OverView:
 * Usage:
 */
@Entity
public class TestDemoBean {
    public TestDemoBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 1722308902)
    public TestDemoBean(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 785103190)
    public TestDemoBean() {
    }

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
