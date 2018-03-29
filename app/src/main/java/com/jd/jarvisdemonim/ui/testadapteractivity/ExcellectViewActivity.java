package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jd.jarvisdemonim.R;
import com.jd.jarvisdemonim.ui.testadapteractivity.showcustomview.NormalTestCartActivity;
import com.jd.jarvisdemonim.ui.testadapteractivity.showcustomview.NormalTestExcellectActivity;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/30 0030
 * Name:
 * OverView: 各种自定义view的展示;
 * Usage:
 */

public class ExcellectViewActivity extends ListActivity {
    String[] arr = new String[]{"进度条", "添加购物车动画"};
    Class[] mClass = new Class[]{NormalTestExcellectActivity.class, NormalTestCartActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setBackgroundResource(R.color.blue_alpha);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, mClass[position]);
        startActivity(intent);
    }
}
