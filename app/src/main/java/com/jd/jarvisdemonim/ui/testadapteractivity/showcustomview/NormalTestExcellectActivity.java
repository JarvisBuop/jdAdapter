package com.jd.jarvisdemonim.ui.testadapteractivity.showcustomview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/22 0022
 * Name:
 * OverView: 进度条自定义view测试
 * Usage:
 */

public class NormalTestExcellectActivity extends DBaseActivity {
    @Bind(R.id.progressbar1)
    ProgressBar pb1;
    @Bind(R.id.progressbar2)
    ProgressBar pb2;
    @Bind(R.id.progressbar3)
    ProgressBar pb3;
    @Bind(R.id.progressbar4)
    ProgressBar pb4;
    @Bind(R.id.progressbar5)
    ProgressBar pb5;
    @Bind(R.id.btn)
    Button btn;
    int count = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                count++;
                if (count > 100) removeMessages(0);
                pb1.setProgress(count);
                pb2.setProgress(count);
                pb3.setProgress(count);
                pb4.setProgress(count);
                pb5.setProgress(count);

                sendEmptyMessageDelayed(0, 200);
            }
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_excellect;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null)
            handler.removeMessages(0);
    }
}
