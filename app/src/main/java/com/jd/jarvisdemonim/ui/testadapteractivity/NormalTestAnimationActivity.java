package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.RecyMultiItemTypeAdapter;
import com.jd.myadapterlib.animation.BaseAnimation;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/3 0003
 * Name:
 * OverView:
 * Usage:
 */

public class NormalTestAnimationActivity extends DBaseActivity {
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    List<String> beans;
    private RecyCommonAdapter<String> recyCommonAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.test1;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        beans = new ArrayList<>();
        doPost();
    }

    private void doPost() {
        for (int i = 0; i < 100; i++) {
            beans.add("item" + i);
        }
    }

    @Override
    protected void initVariable() {
        mRecycler.setHasFixedSize(true);
        recyCommonAdapter = new RecyCommonAdapter<String>(mRecycler, R.layout.test2_item, beans) {
            @Override
            protected void convert(RecyViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.txt_two, item);
                if(position%2==0){
                    viewHolder.setBackgroundRes(R.id.bg_ll,R.color.blue_alpha);
                }else {
                    viewHolder.setBackgroundRes(R.id.bg_ll,R.color.green);
                }
            }
        };
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        recyCommonAdapter.isFirstOnly(false);//是否只加载一次;
        mRecycler.setAdapter(recyCommonAdapter);
        recyCommonAdapter.setOnItemClickListener(new RecyMultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (position) {
                    case 0:
                        recyCommonAdapter.openLoadAnimation(RecyMultiItemTypeAdapter.ALPHAIN);
                        break;
                    case 1:
                        recyCommonAdapter.openLoadAnimation(RecyMultiItemTypeAdapter.SCALEIN);
                        break;
                    case 2:
                        recyCommonAdapter.openLoadAnimation(RecyMultiItemTypeAdapter.SLIDEIN_BOTTOM);
                        break;
                    case 3:
                        recyCommonAdapter.openLoadAnimation(RecyMultiItemTypeAdapter.SLIDEIN_LEFT);
                        break;
                    case 4:
                        recyCommonAdapter.openLoadAnimation(RecyMultiItemTypeAdapter.SLIDEIN_RIGHT);
                        break;
                    case 5:
                        recyCommonAdapter.openLoadAnimation(new CustomAnimation());
                        break;
                }
                ToastUtils.showToast(position + "");
//                mRecycler.setAdapter(recyCommonAdapter);
                recyCommonAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    public class CustomAnimation implements BaseAnimation {

        @Override
        public Animator[] getAnimators(View view) {
            return new Animator[]{
                    ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                    ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
            };
        }
    }
}
