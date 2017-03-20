package com.jd.jarvisdemonim;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/9 0009
 * Name:
 * OverView: aidl传递的数据类型;
 * Usage:
 */

public class MyIpcBean implements Parcelable {
    private String name;
    private int age;
    private boolean gender;
    private String language;

    @Override
    public String toString() {
        return "MyIpcBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", language='" + language + '\'' +
                '}';
    }

    public MyIpcBean(String name, int age, boolean gender, String language) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.language = language;
    }

    protected MyIpcBean(Parcel in) {
        name = in.readString();
        age = in.readInt();
        gender = in.readByte() != 0;
        language = in.readString();
    }

    public static final Creator<MyIpcBean> CREATOR = new Creator<MyIpcBean>() {
        @Override
        public MyIpcBean createFromParcel(Parcel in) {
            return new MyIpcBean(in);
        }

        @Override
        public MyIpcBean[] newArray(int size) {
            return new MyIpcBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeByte((byte) (gender ? 1 : 0));
        dest.writeString(language);
    }
}
