package com.jd.jarvisdemonim.ui.testadapteractivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.activity.DBaseActivity;

import butterknife.Bind;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/4/16 0016
 * Name:
 * OverView:测试富文本加载;
 * Usage:
 */

public class NormalTestRichTextActivity extends DBaseActivity {
    @Bind(R.id.txt_rich)
    TextView txtRich;
    @Bind(R.id.txt_rich2)
    TextView txtRich2;
    @Bind(R.id.btn_display)
    Button btnRich;

    @Override
    public int getContentViewId() {
        return R.layout.activity_rich_text;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {


        SpannableString ss = new SpannableString("红色打电话斜体删除线绿色下划线图片:.");
        //用颜色标记文本
        ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 2,
                //setSpan时需要指定的 flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括).
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //用超链接标记文本
        ss.setSpan(new URLSpan("tel:4155551212"), 2, 5,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //用样式标记文本（斜体）
        ss.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 5, 7,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //用删除线标记文本
        ss.setSpan(new StrikethroughSpan(), 7, 10,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //用下划线标记文本
        ss.setSpan(new UnderlineSpan(), 10, 16,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //用颜色标记
        ss.setSpan(new ForegroundColorSpan(Color.GREEN), 10, 13,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //获取Drawable资源
        Drawable d = getResources().getDrawable(R.drawable.head_icon_1);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //创建ImageSpan
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        //用ImageSpan替换文本
        ss.setSpan(span, 18, 19, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        txtRich.setText(ss);
        txtRich.setMovementMethod(LinkMovementMethod.getInstance()); //实现文本的滚动
//        通常用于显示文字，但有时候也需要在文字中夹杂一些图片，比如QQ中就可以使用表情图片，又比如需要的文字高亮显示等等，如何在Android中也做到这样呢？
//        记得android中有个android.text包，这里提供了对文本的强大的处理功能。
//        添加图片主要用SpannableString和ImageSpan类：

//        Drawable drawable = getResources().getDrawable(R.drawable.head_icon_2);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        //需要处理的文本，[smile]是需要被替代的文本
//        SpannableString spannable = new SpannableString(/*getText().toString()+*/"[smile]");
//        //要让图片替代指定的文字就要用ImageSpan
//        ImageSpan span2 = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
//        //开始替换，注意第2和第3个参数表示从哪里开始替换到哪里替换结束（start和end）
//        //最后一个参数类似数学中的集合,[5,12)表示从5到12，包括5但不包括12
//        spannable.setSpan(span2, getText().length(),getText().length()+"[smile]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        txtRich.setText(spannable);

        String html="<font>草莓</font><img src='head_icon_1'>";
        txtRich2.setText(Html.fromHtml(html,new UrlImageGetter(),null));

        txtRich2.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public class UrlImageGetter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) {
            if (source.equals("head_icon_1")) {
                Drawable draw = getResources().getDrawable(R.drawable.head_icon_1);
                draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
                return draw;
            }
            return null;
        }
    }
}
