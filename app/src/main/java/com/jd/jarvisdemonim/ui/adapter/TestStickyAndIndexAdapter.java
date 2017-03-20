package com.jd.jarvisdemonim.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.SectionIndexer;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.model.StickyIndexBean;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.util.List;

import static com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestStickyActivity.FIRST_STICKY_VIEW;
import static com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestStickyActivity.HAS_STICKY_VIEW;
import static com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestStickyActivity.NONE_STICKY_VIEW;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/22 0022
 * Name:
 * OverView:索引列表和吸顶列表的适配器;
 * Usage:
 */
public class TestStickyAndIndexAdapter extends RecyCommonAdapter implements SectionIndexer {

    public TestStickyAndIndexAdapter(RecyclerView mRecycler, int layoutId, List datas) {
        super(mRecycler, layoutId, datas);
    }

    @Override
    protected void convert(RecyViewHolder viewHolder, Object item, int position) {
        if (item instanceof StickyIndexBean) {
            /**
             * sticky
             */
            StickyIndexBean model = (StickyIndexBean) item;
            viewHolder.setText(R.id.tv_name, model.name);
            viewHolder.setText(R.id.tv_gender, model.gender);
            viewHolder.setText(R.id.tv_profession, model.profession);
            int section = getSectionForPosition(position);

            if (position == 0) {
                viewHolder.setVisible(R.id.tv_sticky_header_view, true);
                viewHolder.setText(R.id.tv_sticky_header_view, model.topName);
                viewHolder.getConvertView().setTag(FIRST_STICKY_VIEW);
            } else {
                if (mDatas.get(position - 1) instanceof StickyIndexBean &&
                        !TextUtils.equals(model.topName, ((StickyIndexBean) mDatas.get(position - 1)).topName)
                        && position == getPositionForSection(section)/*此为index设置*/) {
                    viewHolder.setVisible(R.id.tv_sticky_header_view, true);
                    viewHolder.setText(R.id.tv_sticky_header_view, model.topName);
                    viewHolder.getConvertView().setTag(HAS_STICKY_VIEW);
                } else {
                    viewHolder.setVisible(R.id.tv_sticky_header_view, false);
                    viewHolder.getConvertView().setTag(NONE_STICKY_VIEW);
                }
            }
            viewHolder.getConvertView().setContentDescription(model.topName);

        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 根据首字母获取列表第一次出现首字母的位置;
     *
     * @param sectionIndex
     * @return
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i) instanceof StickyIndexBean) {
                StickyIndexBean bean = (StickyIndexBean) mDatas.get(i);
                String sortStr = bean.getSortLetters();
                char firstChar = sortStr.toUpperCase().charAt(0);//大写;
                if (firstChar == sectionIndex) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 获取列表当前位置字段的首字母
     *
     * @param position
     * @return
     */
    @Override
    public int getSectionForPosition(int position) {
        if (mDatas.get(position) instanceof StickyIndexBean) {
            StickyIndexBean bean = (StickyIndexBean) mDatas.get(position);
            return bean.getSortLetters().charAt(0);
        }
        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }
}
