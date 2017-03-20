package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.adapter.XRecyAdapter;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.myadapterlib.RecyMultiItemTypeAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;
import com.jd.myrecyclerviewlib.DRecyclerView;
import com.jd.myrecyclerviewlib.ProgressStyle;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/24 0024
 * Name:
 * OverView: 测试更改XRecyclerview的属性效果;
 * Usage:
 */

public class NormalTestXRecyActivity extends DBaseActivity {
    @Bind(R.id.xrecycler)
    DRecyclerView mXRecy;
    List<String> mdatas;

    XRecyAdapter myAdapter;
    int i = 0;

    @Override
    public int getContentViewId() {
        return R.layout.activity_xrecy;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mdatas = new ArrayList<>();
        myAdapter = new XRecyAdapter(mXRecy, R.layout.test4_item, mdatas);
    }

    @Override
    protected void initVariable() {
        //设置recyclerview的样式;
        mXRecy.setLayoutManager(new LinearLayoutManager(mContext));
        mXRecy.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecy.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecy.setArrowImageView(R.drawable.ic_delete_photo);


        mXRecy.setAdapter(myAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mXRecy.setLoadingListener(new DRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                i = 0;
                //////////////////////////////////////////////////
                //注意这里需要remove掉Runnable,否则退出时会崩溃;
                //////////////////////////////////////////////////
                getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mdatas.clear();
                        for (int i = 0; i < 15; i++) {
                            mdatas.add("点击设置刷新样式 test" + i);
                        }
                        if(mXRecy==null) return;
                        mXRecy.refreshComplete();
                        myAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                i++;
                if (i < 5) {//加载更多;
                    getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 5; i++) {
                                mdatas.add("loadmore" + i);
                            }
                            if(mXRecy==null) return;
                            mXRecy.loadMoreComplete();
                            myAdapter.notifyDataSetChanged();
                        }
                    }, 2000);
                } else {//没有更多了
                    getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 5; i++) {
                                mdatas.add("nomore" + i);
                            }
                            if(mXRecy==null) return;
                            mXRecy.setNoMore(true);
                            myAdapter.notifyDataSetChanged();
                        }
                    }, 2000);
                }
            }
        });

        myAdapter.setOnItemClickListener(new RecyMultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (position) {
                    case 15:
                        mXRecy.setRefreshProgressStyle(-1);
                        mXRecy.setLoadingMoreProgressStyle(0);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "SysProgress+BallPulse"+position);
                        break;
                    case 1:
                        mXRecy.setRefreshProgressStyle(1);
                        mXRecy.setLoadingMoreProgressStyle(2);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "BallGridPulse+BallClipRotate"+position);
                        break;
                    case 2:
                        mXRecy.setRefreshProgressStyle(3);
                        mXRecy.setLoadingMoreProgressStyle(4);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "BallClipRotatePulse+SquareSpin"+position);
                        break;
                    case 3:
                        mXRecy.setRefreshProgressStyle(5);
                        mXRecy.setLoadingMoreProgressStyle(6);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "BallClipRotateMultiple+BallPulseRise"+position);
                        break;
                    case 4:
                        mXRecy.setRefreshProgressStyle(7);
                        mXRecy.setLoadingMoreProgressStyle(8);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "BallRotate+CubeTransition"+position);
                        break;
                    case 5:
                        mXRecy.setRefreshProgressStyle(9);
                        mXRecy.setLoadingMoreProgressStyle(10);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "BallZigZag+BallZigZagDeflect"+position);
                        break;
                    case 6:
                        mXRecy.setRefreshProgressStyle(11);
                        mXRecy.setLoadingMoreProgressStyle(12);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "BallTrianglePath+BallScale"+position);
                        break;
                    case 7:
                        mXRecy.setRefreshProgressStyle(13);
                        mXRecy.setLoadingMoreProgressStyle(14);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "LineScale+LineScaleParty"+position);
                        break;
                    case 8:
                        mXRecy.setRefreshProgressStyle(15);
                        mXRecy.setLoadingMoreProgressStyle(16);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "BallScaleMultiple+BallPulseSync"+position);
                        break;
                    case 9:
                        mXRecy.setRefreshProgressStyle(17);
                        mXRecy.setLoadingMoreProgressStyle(18);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "BallBeat+LineScalePulseOut"+position);
                        break;
                    case 10:
                        mXRecy.setRefreshProgressStyle(19);
                        mXRecy.setLoadingMoreProgressStyle(20);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "LineScalePulseOutRapid+BallScaleRipple"+position);
                        break;
                    case 11:
                        mXRecy.setRefreshProgressStyle(21);
                        mXRecy.setLoadingMoreProgressStyle(22);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "BallScaleRippleMultiple+BallSpinFadeLoader"+position);
                        break;
                    case 12:
                        mXRecy.setRefreshProgressStyle(23);
                        mXRecy.setLoadingMoreProgressStyle(24);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "LineSpinFadeLoader+TriangleSkewSpin"+position);
                        break;
                    case 13:
                        mXRecy.setRefreshProgressStyle(25);
                        mXRecy.setLoadingMoreProgressStyle(26);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "Pacman+BallGridBeat"+position);
                        break;
                    case 14:
                        mXRecy.setRefreshProgressStyle(27);
                        mXRecy.setLoadingMoreProgressStyle(27);
                        ((RecyViewHolder) (holder)).setText(R.id.txt_two, "SemiCircleSpin"+position);
                        break;

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mXRecy.refresh();
    }
}
