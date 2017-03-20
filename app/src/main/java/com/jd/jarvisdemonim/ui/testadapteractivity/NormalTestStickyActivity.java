package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.base.BaseActivity;
import com.jd.jarvisdemonim.entity.StickyExampleModel;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/27 0027
 * Name:
 * OverView: 顶部浮顶效果;
 * Usage:
 */
public class NormalTestStickyActivity extends BaseActivity {
    @Bind(R.id.rv_sticky_example)
    RecyclerView mRecycler;
    @Bind(R.id.tv_sticky_header_view)
    TextView txtTop;
    LinearLayoutManager llm;
    List<Object> beans;

    @Override
    public int getContentViewId() {
        return R.layout.activity_sticky;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        beans = new ArrayList<>();
        initdata();
    }

    private void initdata() {
        for (int index = 0; index < 30; index++) {
            if (index < 5) {
                beans.add(new StickyExampleModel(
                        "吸顶文本1", "name" + index, "gender" + index, "one" + index));
            } else if (index < 15) {
                beans.add(new StickyExampleModel(
                        "吸顶文本2", "name" + index, "gender" + index, "two" + index));
            } else if (index < 25) {
                beans.add(new StickyExampleModel(
                        "吸顶文本3", "name" + index, "gender" + index, "three" + index));
            } else {
                beans.add(new StickyExampleModel(
                        "吸顶文本4", "name" + index, "gender" + index, "four" + index));
            }
        }
    }

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    @SuppressWarnings("unchecked")
    @Override
    protected void initVariable() {
        mRecycler.setLayoutManager(llm);
        mRecycler.setAdapter(new RecyCommonAdapter(mRecycler, R.layout.item_sticky, beans) {
            @Override
            protected void convert(RecyViewHolder viewHolder, Object item, int position) {
                if (item instanceof StickyExampleModel) {
                    StickyExampleModel model = (StickyExampleModel) item;
                    viewHolder.setText(R.id.tv_name, model.name);
                    viewHolder.setText(R.id.tv_gender, model.gender);
                    viewHolder.setText(R.id.tv_profession, model.profession);

                    if (position == 0) {
                        viewHolder.setVisible(R.id.tv_sticky_header_view, true);
                        viewHolder.setText(R.id.tv_sticky_header_view, model.sticky);
                        viewHolder.getConvertView().setTag(FIRST_STICKY_VIEW);
                    } else {
                        if (beans.get(position - 1) instanceof StickyExampleModel &&
                                !TextUtils.equals(model.sticky, ((StickyExampleModel) beans.get(position - 1)).sticky)) {
                            viewHolder.setVisible(R.id.tv_sticky_header_view, true);
                            viewHolder.setText(R.id.tv_sticky_header_view, model.sticky);
                            viewHolder.getConvertView().setTag(HAS_STICKY_VIEW);
                        } else {
                            viewHolder.setVisible(R.id.tv_sticky_header_view, false);
                            viewHolder.getConvertView().setTag(NONE_STICKY_VIEW);
                        }
                    }
                    viewHolder.getConvertView().setContentDescription(model.sticky);
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View childViewUnder = recyclerView.findChildViewUnder(txtTop.getMeasuredWidth() / 2, 5);
                if (childViewUnder != null && childViewUnder.getContentDescription() != null) {
                    txtTop.setText(String.valueOf(childViewUnder.getContentDescription()));
                }
                View txtTrans = recyclerView.findChildViewUnder(txtTop.getMeasuredWidth() / 2, txtTop.getMeasuredHeight() + 1);
                if (txtTrans != null && txtTrans.getTag() != null) {
                    int status = (int) txtTrans.getTag();
                    int dealtY = txtTrans.getTop() - txtTop.getMeasuredHeight();
                    switch (status) {
                        case FIRST_STICKY_VIEW:
                        case HAS_STICKY_VIEW:
                            if (txtTrans.getTop() > 0) {
                                txtTop.setTranslationY(dealtY);
                            } else {
                                txtTop.setTranslationY(0);
                            }
                            break;
                        case NONE_STICKY_VIEW:
                            txtTop.setTranslationY(0);
                            break;
                    }

                }
            }
        });

    }
}
