package com.jd.jarvisdemonim.ui.testfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.fragment.DBaseFragment;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/1/10 0010
 * Name:
 * OverView:
 * Usage:
 */

public class ThreeFragment extends DBaseFragment {
    @Bind(R.id.text_one_test)
    TextView txtTest;
    private static String KEY = "key";
    private static String KEYINT = "layout";

    public static ThreeFragment newIntance(String extra,int layoutId) {
        ThreeFragment fragment = new ThreeFragment();
        fragment.setContainerId(R.id.framelayout);
        fragment.setCurrentTag("2");
        Bundle bun = new Bundle();
        bun.putInt(KEYINT,layoutId);
        bun.putString(KEY, extra);
        fragment.setArguments(bun);
        return fragment;
    }

    private String str;
    public void setStr(String str) {
        this.str = str;
        if (txtTest != null) {
            txtTest.setText(str);
        }
    }
    @Override
    protected View initXml(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getArguments().getInt(KEYINT),container,false);
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        txtTest.setText(getArguments().getString(KEY));
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }
}
