package com.jd.jarvisdemonim.ui.testadapteractivity.model;

import com.jd.jdkit.elementkit.utils.system.CharacterParserUtils;
import com.jd.jdkit.elementkit.utils.system.PinyinComparatorUtils;
import com.jd.jdkit.entity.BaseSortIndexBean;
import com.jd.jdkit.entity.ModelImpl;
import com.jd.jdkit.okhttp.IOnHttpListener;
import com.lzy.okhttputils.model.HttpParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/12 0012
 * Name:
 * OverView:   索引列表和吸顶列表的数据加载类;
 * Usage:
 */

public class StickyIndexImpl implements ModelImpl {
    CharacterParserUtils characterParserUtils;
    PinyinComparatorUtils pinyinComparatorUtils;

    public StickyIndexImpl(CharacterParserUtils characterParserUtils,
                           PinyinComparatorUtils pinyinComparatorUtils) {
        this.characterParserUtils = characterParserUtils;
        this.pinyinComparatorUtils = pinyinComparatorUtils;
    }

    @Override
    public void getModel(IOnHttpListener mListener, HttpParams mParams) {
        BaseModelList bml = new BaseModelList();
        List<StickyIndexBean> mlist = new ArrayList<>();
        for (int index = 0; index < 30; index++) {
            if (index < 5) {
                mlist.add(new StickyIndexBean(
                        "a吸顶文本1", "name" + index, "gender" + index, "one" + index));
            } else if (index < 15) {
                mlist.add(new StickyIndexBean(
                        "c吸顶文本2", "name" + index, "gender" + index, "two" + index));
            } else if (index < 25) {
                mlist.add(new StickyIndexBean(
                        "d吸顶文本3", "name" + index, "gender" + index, "three" + index));
            } else {
                mlist.add(new StickyIndexBean(
                        "b吸顶文本4", "name" + index, "gender" + index, "four" + index));
            }
        }

        bml.setMlist(initSourceDatas(mlist));
        mListener.onSuccess(bml);
    }

    public List<? extends BaseSortIndexBean> initSourceDatas(List<? extends BaseSortIndexBean> mSourceDatas) {
        if (null == mSourceDatas || mSourceDatas.isEmpty()) {
            return null;
        }
        List<? extends BaseSortIndexBean> mdata = new ArrayList<>(mSourceDatas.size());
        for (int i = 0; i < mSourceDatas.size(); i++) {
            //汉字转换成拼音
            String pinyin = characterParserUtils.getSelling(mSourceDatas.get(i).getTopName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                mSourceDatas.get(i).setSortLetters(sortString.toUpperCase());
            } else {
                mSourceDatas.get(i).setSortLetters("#");
            }
        }
        Collections.sort(mSourceDatas, pinyinComparatorUtils);
        return mSourceDatas;
    }
}
