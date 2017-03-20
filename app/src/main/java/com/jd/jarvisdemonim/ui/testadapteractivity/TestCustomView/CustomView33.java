package com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.jarvisdemonim.R;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/2 0002
 * Name:
 * OverView:自定义view-组合控件;
 * Usage:
 */

public class CustomView33 extends FrameLayout {
    private String TAG = "customview33";
    private ImageView mImageView;
    private TextView mTextView;
    private boolean isSelected;

    private int imgId;
    private int txtColor;
    private OnClickListener mListener;

    public void setmListener(OnClickListener mListener) {
        this.mListener = mListener;
    }

    public CustomView33(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView33(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public CustomView33(Context context) {
        super(context);
    }

    public ImageView getmImageView() {
        return mImageView;
    }

    public void setmImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }

    public TextView getmTextView() {
        return mTextView;
    }

    public void setmTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        /**
         * 注意:如果使用LayoutInflater设置布局:直接findViewbyId即可,不用addView,会栈溢出;
         * 如果使用的New 控件,则使用addView(),添加到父布局中;
         */
        LayoutInflater.from(getContext()).inflate(R.layout.custom_combine, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customview_33);
        imgId = typedArray.getResourceId(R.styleable.customview_33_imageId, R.drawable.ic_delete_photo);
        txtColor = typedArray.getColor(R.styleable.customview_33_textcolor, getResources().getColor(R.color.black));
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.img_top);
        mTextView = (TextView) findViewById(R.id.txt_bottom);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(v);//事件回调
                    //样式改变;
                    if (isSelected) {
                        isSelected = false;
                    } else {
                        isSelected = true;
                    }
                    mImageView.setSelected(isSelected);
                    mTextView.setTextColor(getResources().getColor(isSelected ? R.color.blue_alpha : R.color.orange_alpha));
                }
            }
        });
        if (imgId != 0) {
            mImageView.setImageResource(imgId);
        }
        if (txtColor != 0) {
            mTextView.setTextColor(txtColor);
        }
//        addView(this, layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //第一个根布局是framelayout,如果直接获取layoutparams是获取的layout中的第一个linearlayout;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getChildAt(0).getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        getChildAt(0).setLayoutParams(layoutParams);
    }
}
