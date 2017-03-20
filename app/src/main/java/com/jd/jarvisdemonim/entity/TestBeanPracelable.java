package com.jd.jarvisdemonim.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/9 0009
 * Name:
 * OverView: 测试parcelable实体类;
 * Usage:
 */

public class TestBeanPracelable implements Parcelable{
    private String name;
    private int age;
    private String gender;
    private InnerObject obj;

    @Override
    public String toString() {
        return "TestBeanPracelable{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", obj=" + obj +
                '}';
    }

    public InnerObject getObj() {
        return obj;
    }

    public void setObj(InnerObject obj) {
        this.obj = obj;
    }


    public TestBeanPracelable(String name, int age, String gender, InnerObject obj) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.obj = obj;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public TestBeanPracelable(Parcel in) {
        age = in.readInt();
        name = in.readString();
        gender = in.readString();
        obj = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    /**
     * 反序列化方法;
     */
    public static final Creator<TestBeanPracelable> CREATOR = new Creator<TestBeanPracelable>() {
        @Override
        public TestBeanPracelable createFromParcel(Parcel in) {
            return new TestBeanPracelable(in);
        }

        @Override
        public TestBeanPracelable[] newArray(int size) {
            return new TestBeanPracelable[size];
        }
    };

    /**
     * 内容描述;
     * @return 1:含有文件描述;
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 序列化对象方法;
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeParcelable(obj,0);//1:当前对象需要作为返回值返回,不能立即释放资源;
    }

    /**
     * 内部类;
     */
    public static class InnerObject implements Parcelable{
        private String innerName;

        protected InnerObject(Parcel in) {
            innerName = in.readString();
        }

        public InnerObject(String innerName) {
            this.innerName = innerName;
        }

        public static final Creator<InnerObject> CREATOR = new Creator<InnerObject>() {
            @Override
            public InnerObject createFromParcel(Parcel in) {
                return new InnerObject(in);
            }

            @Override
            public InnerObject[] newArray(int size) {
                return new InnerObject[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(innerName);
        }

        @Override
        public String toString() {
            return "InnerObject{" +
                    "innerName='" + innerName + '\'' +
                    '}';
        }
    }
}
