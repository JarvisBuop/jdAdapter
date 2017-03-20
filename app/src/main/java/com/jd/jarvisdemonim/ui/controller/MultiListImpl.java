package com.jd.jarvisdemonim.ui.controller;

import com.jd.jarvisdemonim.ui.model.ContentBean;
import com.jd.jarvisdemonim.ui.model.TitleBean;

import java.util.ArrayList;
import java.util.List;
public class MultiListImpl implements MultiListModel {
    private List<Object> beans;
    @Override
    public List<Object> getMultiListBeans() {
        beans=new ArrayList<>();
        return doPost();
    }

    @Override
    public List<Object> LoadMoreDatas() {
        return doPost();
    }

    @Override
    public List<Object> clearDatas() {
        beans.clear();
        return beans;
    }

    private List<Object> doPost() {
        beans.add(new TitleBean("title1"));
        beans.add(new ContentBean("Content1"));
        beans.add(new ContentBean("Content2"));
        beans.add(new ContentBean("Content3"));
        beans.add(new ContentBean("Content4"));
        beans.add(new ContentBean("Content5"));
        beans.add(new TitleBean("title2"));
        beans.add(new ContentBean("Content6"));
        beans.add(new ContentBean("Content7"));
        beans.add(new ContentBean("Content8"));
        beans.add(new ContentBean("Content9"));
        beans.add(new TitleBean("title3"));
        beans.add(new ContentBean("Content11"));
        beans.add(new ContentBean("Content12"));
        beans.add(new ContentBean("Content13"));
        beans.add(new ContentBean("Content14"));
        beans.add(new ContentBean("Content15"));
        beans.add(new ContentBean("Content16"));
        beans.add(new ContentBean("Content17"));
        beans.add(new ContentBean("Content18"));
        beans.add(new TitleBean("title4"));
        beans.add(new ContentBean("Content21"));
        beans.add(new ContentBean("Content22"));
        beans.add(new ContentBean("Content23"));
        beans.add(new ContentBean("Content24"));
        return beans;
    }
}
