package com.jd.jarvisdemonim.ui.testfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.fragment.DBaseLazyFragment;
import com.jd.jdkit.elementkit.utils.log.LogUtils;

import java.util.Random;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/4 0004
 * Name:
 * OverView: 懒加载1;
 * Usage:
 */

public class TestLazyFragment3 extends DBaseLazyFragment {
    TextView textView;
    private static String KEY = "key";

    public static TestLazyFragment3 newInstance(String argstr) {
        Bundle args = new Bundle();
        args.putString(KEY, argstr);
        TestLazyFragment3 fragment = new TestLazyFragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initXml(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        textView = (TextView) view.findViewById(R.id.txtOne);
    }

    /**
     * 需要懒加载的部分,联网代码;
     */
    @Override
    protected void initData(boolean isNeed) {
        LogUtils.i("DBaseLazyFragment", this.getClass().getSimpleName() + "初始化了");
        String string = getArguments().getString(KEY);
        if (isNeed) {
            textView.setText(string);
            Random random = new Random();
            int i1 = random.nextInt(255);
            int i2 = random.nextInt(255);
            int i3 = random.nextInt(255);
            textView.setBackgroundColor(Color.rgb(i1, i2, i3));
        }else {
            if (textView != null) {
                textView.setText("");
                textView.setBackgroundColor(Color.WHITE);
            }
        }
    }

}
