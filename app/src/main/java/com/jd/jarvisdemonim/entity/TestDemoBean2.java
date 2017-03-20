package com.jd.jarvisdemonim.entity;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/15 0015
 * Name:
 * OverView: ToOne 是1:1;用于一个对象;
 * Usage:   ToMany 是1:N;用于集合对象;
 * 设置的为内部类,greendao使用;
 */
@Entity
public class TestDemoBean2 {
    @Id(autoincrement = true)
    private Long id;

    private long innerId;
    @ToOne(joinProperty = "innerId")
    private InnerDemo demo;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 2019421435)
    private transient TestDemoBean2Dao myDao;

    @Generated(hash = 215242210)
    public TestDemoBean2(Long id, long innerId) {
        this.id = id;
        this.innerId = innerId;
    }

    @Generated(hash = 144572231)
    public TestDemoBean2() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getInnerId() {
        return this.innerId;
    }

    public void setInnerId(long innerId) {
        this.innerId = innerId;
    }

    @Generated(hash = 1697154002)
    private transient Long demo__resolvedKey;

    /** To-one relationship, resolved on first access. */
//    @Generated(hash = 118889204)
    @Keep
    public InnerDemo getDemo() {
        long __key = this.innerId;
        if (demo__resolvedKey == null || !demo__resolvedKey.equals(__key)) {
            DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InnerDemoDao targetDao = daoSession.getInnerDemoDao();
            InnerDemo demoNew = targetDao.load(__key);
            synchronized (this) {
                demo = demoNew;
                demo__resolvedKey = __key;
            }
        }
        return demo;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 601578255)
    public void setDemo(@NotNull InnerDemo demo) {
        if (demo == null) {
            throw new DaoException(
                    "To-one property 'innerId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.demo = demo;
            innerId = demo.getId();
            demo__resolvedKey = innerId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 695786621)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTestDemoBean2Dao() : null;
    }

}

//@Entity
//class InnerDemo2 {
//    @Id
//    private Long id;
//    private String target;
//    private String color;
//}
