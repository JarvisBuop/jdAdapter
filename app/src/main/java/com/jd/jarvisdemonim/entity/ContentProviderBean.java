package com.jd.jarvisdemonim.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/13 0013
 * Name:
 * OverView: contentprovider 测试bean类;
 * Usage:
 */

public class ContentProviderBean implements Parcelable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public ContentProviderBean() {
    }

    public ContentProviderBean(String content, int mark) {
        this.content = content;
        this.mark = mark;
    }

    protected ContentProviderBean(Parcel in) {
        content = in.readString();
        mark = in.readInt();
        id= in.readInt();
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
        dest.writeInt(id);
    }

    @Override
    public String toString() {
        return "MessengerBean{" +
                "content='" + content + '\'' +
                ", mark=" + mark +"/id="+id+
                '}';
    }
}
