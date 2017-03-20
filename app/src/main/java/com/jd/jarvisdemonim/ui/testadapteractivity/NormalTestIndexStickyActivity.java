package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.adapter.TestStickyAndIndexAdapter;
import com.jd.jarvisdemonim.ui.model.BaseModelList;
import com.jd.jarvisdemonim.ui.model.StickyIndexBean;
import com.jd.jarvisdemonim.ui.controller.StickyIndexImpl;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.PinyinUtils;
import com.jd.jdkit.elementkit.utils.system.PinyinComparatorUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.jdkit.IInterface.mvc.ModelImpl;
import com.jd.jdkit.okhttp.IOnHttpListener;
import com.jd.jdkit.viewskit.SideBar;
import com.jd.myadapterlib.RecyMultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Response;

import static com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestStickyActivity.FIRST_STICKY_VIEW;
import static com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestStickyActivity.HAS_STICKY_VIEW;
import static com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestStickyActivity.NONE_STICKY_VIEW;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/12 0012
 * Name:
 * OverView: mvc模式;
 * Usage: 索引列表和吸顶列表;
 */

public class NormalTestIndexStickyActivity extends DBaseActivity implements IOnHttpListener {
    @Bind(R.id.rv_sticky_example)
    RecyclerView mRecycler;
    @Bind(R.id.tv_sticky_header_view)
    TextView mTxtHead;
    @Bind(R.id.sliderbar_indexlocal)
    SideBar sideBar;
    @Bind(R.id.txt_indexlocal)
    TextView mTxtIndex;

    List<Object> mDatas;
    PinyinUtils characterParserUtils;
    PinyinComparatorUtils pinyinComparatorUtils;
    LinearLayoutManager linearLayoutManager;
    TestStickyAndIndexAdapter mAdapter;

    private ModelImpl siModel;


    @Override
    public int getContentViewId() {
        return R.layout.activity_sticky_inde;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        characterParserUtils = PinyinUtils.getInstance();
        pinyinComparatorUtils = new PinyinComparatorUtils();
        linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TestStickyAndIndexAdapter(mRecycler, R.layout.item_sticky, mDatas);
        siModel = new StickyIndexImpl(characterParserUtils, pinyinComparatorUtils);
    }

    @Override
    protected void initVariable() {
        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setAdapter(mAdapter);
        sideBar.setTextView(mTxtIndex);

        siModel.getModel(this, null);//下载数据;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                LogUtils.i("jarvisslide", s + "//" + position);
                if (position != -1) {
                    mRecycler.scrollToPosition(position);
                }
            }
        });
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View childViewUnder = recyclerView.findChildViewUnder(mTxtHead.getMeasuredWidth() / 2, 5);
                if (childViewUnder != null && childViewUnder.getContentDescription() != null) {
                    mTxtHead.setText(String.valueOf(childViewUnder.getContentDescription()));
                }
                View txtTrans = recyclerView.findChildViewUnder(mTxtHead.getMeasuredWidth() / 2, mTxtHead.getMeasuredHeight() + 1);
                if (txtTrans != null && txtTrans.getTag() != null) {
                    int status = (int) txtTrans.getTag();
                    int dealtY = txtTrans.getTop() - mTxtHead.getMeasuredHeight();
                    switch (status) {
                        case FIRST_STICKY_VIEW:
                        case HAS_STICKY_VIEW:
                            if (txtTrans.getTop() > 0) {
                                mTxtHead.setTranslationY(dealtY);
                            } else {
                                mTxtHead.setTranslationY(0);
                            }
                            break;
                        case NONE_STICKY_VIEW:
                            mTxtHead.setTranslationY(0);
                            break;
                    }

                }
            }
        });
        mAdapter.setOnItemClickListener(new RecyMultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (mDatas.get(position) instanceof StickyIndexBean) {
                    StickyIndexBean bean = (StickyIndexBean) mDatas.get(position);
                    ToastUtils.showToast(position + "/" + bean.profession+ "/" + bean.getSortLetters());
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    //下载;
    @Override
    public void onSuccess(Object o) {
        if (o instanceof BaseModelList) {
            BaseModelList baseModelList = (BaseModelList) o;
            mDatas.addAll(baseModelList.getMlist());
            LogUtils.i("jarvisclick", mDatas.size() + " 数据下载完毕");
            mAdapter.setDatas(mDatas);
        }
    }

    @Override
    public void onError(Response response, Exception e) {

    }

    @Override
    public void onCache(Object o) {

    }

    @Override
    public void onAfter() {

    }

    @Override
    public void onBefore() {

    }
}
