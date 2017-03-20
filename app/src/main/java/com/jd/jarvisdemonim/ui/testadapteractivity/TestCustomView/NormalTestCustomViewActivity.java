package com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/1 0001
 * Name:
 * OverView: 测试自定义View的使用;
 * Usage:
 */

public class NormalTestCustomViewActivity extends DBaseActivity {
    CustomView1 mMarquee;//继承
    CustomView33 customView33;//组合
    CustomView2 mcustom2;//自定义View
    CustomView22 mcustom22;//自定义ViewGroup
    private boolean isVIew = false;

    @Override
    public int getContentViewId() {
        return R.layout.activity_custom;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mMarquee = (CustomView1) findViewById(R.id.custom1);
        customView33 = (CustomView33) findViewById(R.id.custom33_1);
        mcustom2 = (CustomView2) findViewById(R.id.custom2);
        mcustom22 = (CustomView22) findViewById(R.id.custom22);
    }

    @Override
    protected void initVariable() {
        mMarquee.setTextColor(Color.GREEN);
        mMarquee.setText("123412354123333333333333333333333333333333333555555555555555555555555555555555555555555555555555555512341234123412341324123");

        ImageView imageView = customView33.getmImageView();
        imageView.setImageResource(R.drawable.ic_gf_camera);
        customView33.setmImageView(imageView);
        TextView textView = customView33.getmTextView();
        textView.setText("Test");
        customView33.setmTextView(textView);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        customView33.setmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i("customview 33");
                isVIew = !isVIew;
                if (isVIew) {
                    mcustom2.setVisibility(View.VISIBLE);
                    mcustom22.setVisibility(View.GONE);
                } else {
                    mcustom2.setVisibility(View.GONE);
                    mcustom22.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
