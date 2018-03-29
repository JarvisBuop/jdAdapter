package com.jd.jarvisdemonim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.lottieutils.LottieFontViewGroup;
import com.jd.jdkit.elementkit.activity.DBaseActivity;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/4/1 0001
 * Name:
 * OverView:
 * Usage:
 */

public class WelcomeActivity extends DBaseActivity {
    @Bind(R.id.lottie_animation)
    LottieAnimationView mLottieAnimation;
    @Bind(R.id.btn_enter)
    Button btnEnter;
    @Bind(R.id.font_view)
    LottieFontViewGroup fontView;

    @Override
    protected void onStart() {
        super.onStart();
        mLottieAnimation.setProgress(0f);
        mLottieAnimation.playAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLottieAnimation.cancelAnimation();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });

        fontView.setAnimDatas("jarvis");
    }
}
