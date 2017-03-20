package com.jd.jarvisdemonim.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/10 0010
 * Name:
 * OverView: 测试messenger的bean类;
 * attention:注意对象要使用classloader;
 * Usage:Messenger不支持自定义的bean类,因为找不到,只支持基本数据类型;
 */

public class MessengerBean implements Parcelable{
    private String content;
    private int mark;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public MessengerBean() {
    }

    public MessengerBean(String content, int mark) {

        this.content = content;
        this.mark = mark;
    }

    protected MessengerBean(Parcel in) {
        content = in.readString();
        mark = in.readInt();
    }

    public static final Creator<MessengerBean> CREATOR = new Creator<MessengerBean>() {
        @Override
        public MessengerBean createFromParcel(Parcel in) {
            return new MessengerBean(in);
        }

        @Override
        public MessengerBean[] newArray(int size) {
            return new MessengerBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeInt(mark);
    }

    @Override
    public String toString() {
        return "MessengerBean{" +
                "content='" + content + '\'' +
                ", mark=" + mark +
                '}';
    }
}
