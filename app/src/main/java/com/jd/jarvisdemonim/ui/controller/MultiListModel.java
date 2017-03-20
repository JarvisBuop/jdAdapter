package com.jd.jarvisdemonim.ui.controller;

import java.util.List;

/**
 * 作　　者: guyj
 * 修改日期: 2016/12/23
 * 描　　述:
 * 备　　注:
 */
public interface MultiListModel {
    List<Object> getMultiListBeans();
    List<Object> LoadMoreDatas();
    List<Object> clearDatas();
}
