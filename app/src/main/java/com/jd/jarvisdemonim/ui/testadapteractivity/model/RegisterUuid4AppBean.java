package com.jd.jarvisdemonim.ui.testadapteractivity.model;

import java.io.Serializable;

/**
 * 作　　者: guyj
 * 修改日期: 2016/11/19
 * 描　　述: 获取token的实体类
 * 备　　注:
 */
public class RegisterUuid4AppBean implements Serializable{
    private boolean sucflag;
    private String token;

    public boolean isSucflag() {
        return sucflag;
    }

    public void setSucflag(boolean sucflag) {
        this.sucflag = sucflag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
