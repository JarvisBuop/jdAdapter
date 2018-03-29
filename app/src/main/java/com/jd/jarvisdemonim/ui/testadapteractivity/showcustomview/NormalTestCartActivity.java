package com.jd.jarvisdemonim.ui.testadapteractivity.showcustomview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.myadapterlib.RecyCommonAdapter;
import com.jd.myadapterlib.RecyMultiItemTypeAdapter;
import com.jd.myadapterlib.delegate.RecyViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/30 0030
 * Name:
 * OverView:
 * Usage:
 */

public class NormalTestCartActivity extends DBaseActivity {
    @Bind(R.id.recycler)
    RecyclerView mRecyler;
    @Bind(R.id.img_cart)
    ImageView ImgCart;
    @Bind(R.id.relation_id)
    RelativeLayout mRelation;
    List mdatas;
    private RecyCommonAdapter madapter;

    //动画
    private PathMeasure pathMeasure;
    HashMap<Integer, PathMeasure> map = new HashMap<>();
    int count=0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                int pos = msg.arg1;
                ImageView iv = (ImageView) msg.obj;
                addCartAnimator(iv, pos);
            }
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mdatas = new ArrayList();
        madapter = new RecyCommonAdapter(mRecyler, R.layout.item_cart, mdatas) {
            @Override
            protected void convert(RecyViewHolder viewHolder, Object item, int position) {
                if (item instanceof CartBean) {
                    CartBean cb = (CartBean) item;
                    viewHolder.setImageResource(R.id.img_cart_item, cb.imgSrc);
                }
            }
        };
    }

    @Override
    protected void initVariable() {
        mRecyler.setAdapter(madapter);
        mRecyler.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        madapter.setOnItemClickListener(new RecyMultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ImageView iv = (ImageView) view.findViewById(R.id.img_cart_item);
//                addCartAnimator(iv);
                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = iv;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mdatas.add(new CartBean(R.drawable.head_icon_2));
        mdatas.add(new CartBean(R.drawable.head_icon_3));
        mdatas.add(new CartBean(R.drawable.head_icon_4));
        mdatas.add(new CartBean(R.drawable.head_icon_5));
        mdatas.add(new CartBean(R.drawable.head_icon_6));
        madapter.setDatas(mdatas);
    }

    class CartBean {
        public CartBean(int imgSrc) {
            this.imgSrc = imgSrc;
        }

        private int imgSrc;
    }


    //添加到购物车的方法;
    public void addCartAnimator(final ImageView imageView, final int pos) {
        count++;
        if (imageView == null)
            throw new IllegalArgumentException("imageview must not null");
        /**
         * 防止点击的item使用的是同一个mCurrentPostion;造成动画跳位;
         */
        final float[] mCurrentPosition = new float[2];
        //执行动画的图片;
        final ImageView pathImg = new ImageView(mContext);
        pathImg.setImageResource(R.drawable.pic2);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRelation.addView(pathImg, rl);

        //计算动画的起始和终点坐标;
        int[] parentLoc = new int[2];
        mRelation.getLocationInWindow(parentLoc);//recyclerview

        int[] startLoc = new int[2];
        imageView.getLocationInWindow(startLoc);//item中的img;

        int[] endLoc = new int[2];
        ImgCart.getLocationInWindow(endLoc);//购物车;

        //计算动画的位置;
        //开始点:就是说getLocationInWindow得到的是View的起点,左上角;
        float startX = startLoc[0] - parentLoc[0] + imageView.getWidth() / 2 - 30;//-pathImg.getWidth()正中
        float startY = startLoc[1] - parentLoc[1] + imageView.getHeight() / 2 - 30;

        //结束点:
        float endX = endLoc[0] - parentLoc[0] + ImgCart.getWidth() / 2 - pathImg.getWidth();
        float endY = endLoc[1] - parentLoc[1] + ImgCart.getHeight() / 2 - pathImg.getHeight();

        //插值动画;
        Path path = new Path();
        path.moveTo(startX, startY);

        path.quadTo((startX + endX) / 2, startY, endX, endY);
        pathMeasure = new PathMeasure(path, false);
        /**
         * 添加至map中,避免造成动画的乱闯;
         */
        if (!map.keySet().contains(pos)) {
            map.put(pos, pathMeasure);
        }
        //使用属性动画插值,长度到0-贝塞尔曲线长度;
        ValueAnimator valueAnimator = new ValueAnimator().ofFloat(0, pathMeasure.getLength());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
//                float animatedFraction = animation.getAnimatedFraction();
                //计算切线,移动view到此;
                PathMeasure pathMeasure = map.get(pos);
                pathMeasure.getPosTan(animatedValue, mCurrentPosition, null);
                pathImg.setTranslationX(mCurrentPosition[0]);
                pathImg.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.setDuration(3000).start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRelation.removeView(pathImg);
                count--;
                if(count==0){
                    map.clear();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
