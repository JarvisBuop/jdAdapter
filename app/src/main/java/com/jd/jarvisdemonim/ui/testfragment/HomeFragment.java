package com.jd.jarvisdemonim.ui.testfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.NimActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.NormalTestTabReminderActivity;
import com.jd.jdkit.elementkit.fragment.DBaseFragment;
import com.jd.jdkit.reminder.ReminderManager;
import com.netease.nim.uikit.common.util.log.LogUtil;

/**
 * Auther: Jarvis Dong
 * Time: on 2016/12/30 0030
 * Name:
 * OverView:
 * Usage:
 */
public class HomeFragment extends DBaseFragment {
    TextView txtOne;
    Button btnOne;
    Button btnTwosend;
    Button btnTwostop;
    int i = 0;
    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            i++;
            ReminderManager.getInstance().upDateUnReadNum(i, true, Integer.parseInt(getArguments().getString(KEY)));
            mHandler.postDelayed(runnable, 1000);
        }
    };

    private static String KEY = "key";

    public static HomeFragment newInstance(String key) {
        HomeFragment fragment = new HomeFragment();
        Bundle bun = new Bundle();
        bun.putString(KEY, key);
        fragment.setArguments(bun);
        return fragment;
    }

    @Override
    protected View initXml(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        txtOne = (TextView) view.findViewById(R.id.txtOne);
        btnOne = (Button) view.findViewById(R.id.btnOne);
        btnTwosend = (Button) view.findViewById(R.id.btnTwo);
        btnTwostop = (Button) view.findViewById(R.id.btnTwo2);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        String string = getArguments().getString(KEY);
        txtOne.setText(string);
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity instanceof NimActivity) {
                    ((NimActivity) mActivity).setCurrentMsg(Integer.parseInt(getArguments().getString(KEY)));
                } else if (mActivity instanceof NormalTestTabReminderActivity) {
                    ((NormalTestTabReminderActivity) mActivity).setCurrentMsg(Integer.parseInt(getArguments().getString(KEY)));
                }
            }
        });

        btnTwosend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i("jarvisclick", "发送");
                mHandler.postDelayed(runnable, 1000);
            }
        });
        btnTwostop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i("jarvisclick", "停止");
                i = Integer.parseInt(getArguments().getString(KEY));
                mHandler.removeCallbacks(runnable);
                ReminderManager.getInstance().upDateUnReadNum(0, true, Integer.parseInt(getArguments().getString(KEY)));
            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(runnable);
    }
}
