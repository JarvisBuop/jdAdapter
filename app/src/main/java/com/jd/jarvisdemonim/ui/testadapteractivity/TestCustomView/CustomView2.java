package com.jd.jarvisdemonim.ui.testadapteractivity.TestCustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.jd.jarvisdemonim.R;
import com.jd.jdkit.elementkit.utils.log.LogUtils;
import com.jd.jdkit.elementkit.utils.system.ToastUtils;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/3/1 0001
 * Name:
 * OverView: 自定义View-绘制控件
 * Usage:
 */

public class CustomView2 extends View implements View.OnClickListener {
    private String TAG = "CustomView2";
    private Bitmap bitmap;
    private int mBackgroundRes;
    private String mTestContent;
    private int mTestColor;
    private int mTestDimen;
    private int mHeight;
    private int mWidth;
    private Paint mPaint;

    public CustomView2(Context context) {
        this(context, null);
    }

    public CustomView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeight = this.getHeight();
        mWidth = this.getWidth();
        LogUtils.i("长宽:" + mHeight + "/" + mWidth);

        init(context, attrs, defStyleAttr);
        mPaint = new Paint();
        bitmap = BitmapFactory.decodeResource(context.getResources(), mBackgroundRes);
        setOnClickListener(this);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_view_2);

        mTestColor = typedArray.getColor(R.styleable.custom_view_2_textContentColor, Color.WHITE);
        mTestDimen = typedArray.getDimensionPixelSize(R.styleable.custom_view_2_textContentSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
        mTestContent = typedArray.getString(R.styleable.custom_view_2_textContent);
        if (TextUtils.isEmpty(mTestContent)) {
            mTestContent = "";
        }
        mBackgroundRes = typedArray.getResourceId(R.styleable.custom_view_2_textBg, R.drawable.ic_folder_check);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.i(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureDimension(100, widthMeasureSpec);
        int height = measureDimension(100, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public static int measureDimension(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.max(size, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    /**
     * 自定义view不需要重写此方法;
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        LogUtils.i(TAG, "onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.i(TAG, "onDraw");
        super.onDraw(canvas);
//        canvas.drawBitmap(bitmap,0,0,mPaint);

        mPaint.setAntiAlias(true);

        //画矩形中带文字;
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        //画触摸坐标/文字;
        mPaint.setColor(mTestColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(mTestDimen);
        Rect rect = new Rect();
        String substring1 = mTestContent.substring(0, (int) mTestContent.length() / 2);
        String substring2 = mTestContent.substring((int) mTestContent.length() / 2, mTestContent.length());
        mPaint.getTextBounds(substring1, 0, substring1.length(), rect);
        canvas.drawText(substring1, getWidth() / 2 - rect.width() / 2, getHeight() - rect.height() * 2, mPaint);
        mPaint.getTextBounds(substring2, 0, substring2.length(), rect);
        canvas.drawText(substring2, getWidth() / 2 - rect.width() / 2, getHeight() - rect.height(), mPaint);

        //画线;
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
        canvas.drawLine(0, 0, getWidth(), getWidth(), mPaint);

        //画矩形
        mPaint.setColor(Color.GREEN);
        Rect rect2 = new Rect();
        rect2.set(10, 20, getWidth() - 10, 40);
        canvas.drawRect(rect2, mPaint);

        //画点
        mPaint.setColor(Color.CYAN);
        canvas.drawPoint(25, 25, mPaint);

        //画椭圆
        mPaint.setColor(mTestColor);
        RectF rf = new RectF(20, 60, getWidth() - 20, 120);
        canvas.drawOval(rf, mPaint);

        //画圆;
        mPaint.setColor(Color.RED);
        canvas.drawCircle(40, 120, 40, mPaint);

        //画扇形
        mPaint.setColor(Color.BLUE);
        RectF rf2 = new RectF();
        rf2.set(0, 120, getWidth(), 300);
        canvas.drawArc(rf2, 0, 90, true, mPaint);

        mPaint.setColor(Color.RED);
        RectF rf22 = new RectF();
        rf22.set(0, 300, getWidth(), 600);
        canvas.drawArc(rf22, 90, 180, false, mPaint);

        //画图片;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.computer);
//        canvas.drawBitmap(bitmap,20,300,mPaint);

        //画圆角矩形
        RectF rf3 = new RectF(0, 600, getWidth(), 700);
        mPaint.setStyle(Paint.Style.FILL);
        //设置渐变色的矩形;
        Shader mShader = new LinearGradient(0, 100, 0, 100, new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                Color.LTGRAY}, null, Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);
        canvas.drawRoundRect(rf3, 10, 15, mPaint);

        //画路径
        mPaint.reset();
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        Path path = new Path();
        path.moveTo(0, 360);
        path.lineTo(360, 360);
        path.lineTo(400, 400);
        path.close();
        canvas.drawPath(path, mPaint);

        mPaint.setStrokeWidth(3);
        //画贝塞尔
        mPaint.setColor(Color.GREEN);
        Path path2 = new Path();
        path2.moveTo(500, 500);
        path2.lineTo(500, 550);
        path2.quadTo(getWidth(), 0, 600, 700);
        canvas.drawPath(path2, mPaint);

        //画扇形
        mPaint.setStyle(Paint.Style.FILL);
        RectF rf4 = new RectF();
        rf4.set(500, 700, 700, 900);
//        canvas.drawCircle(600,800,100,mPaint);
        canvas.drawArc(rf4, 90, 270, true, mPaint);

        //三次贝塞尔曲线
        mPaint.setStyle(Paint.Style.STROKE);
        Path path3 = new Path();
        path3.moveTo(500, 500);
        path3.cubicTo(200, 400, 400, 900, 100, 800);
        canvas.drawPath(path3, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setFakeBoldText(true);
        mPaint.setUnderlineText(true);//设置下滑线效果
        mPaint.setStrikeThruText(true);//设置删除线效果
        canvas.drawText("测试下划线和删除线", 500, 1000, mPaint);

        mPaint.setTextSkewX(0.3f);
        canvas.drawText("测试倾斜字体", 500, 1100, mPaint);
        mPaint.setTextScaleX(2);
        canvas.drawText("测试水平拉伸字体", 500, 1200, mPaint);

        mPaint.reset();
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.CYAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            canvas.drawTextRun("测试textrun文字效果", 0, 10, 0, 10, 800, 1200, true, mPaint);
//            canvas.drawTextRun("textrun文字效果".toCharArray(),0,10,0,10,500,1300,true,mPaint);
        }

        //画小动画;
        int everyWidth = getMeasuredWidth()/4-20;
        canvas.drawLine(startX,620,startX+everyWidth,620,mPaint);
        startX+=10;
        if(startX+everyWidth>=getMeasuredWidth()){
            startX = 0;
        }
        postInvalidateDelayed(200);
    }
    int startX=0;

    /**
     * view不需要此方法,主要是viewgroup重绘子视图;
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        LogUtils.i(TAG, "dispatchDraw");
        super.dispatchDraw(canvas);

    }

    float x = 0;
    float y = 0;
    float rawX = 0;
    float rawY = 0;
    private String firstString;
    private String directX;
    private String directY;

    /**
     * getrawx()获取的是相对于屏幕的坐标系;
     * getx()获取的是相对于整个VIew的坐标系;
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.i(TAG, "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                rawX = event.getRawX();
                rawY = event.getRawY();
                firstString = "x=" + x + "\ny=" + y + "\nrawx=" + rawX + "\nrawy" + rawY;
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                rawX = event.getRawX();
                rawY = event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                float moveRawX = event.getRawX();
                float moveRawY = event.getRawY();

                if (moveX - x > 10) {
                    directX = "向右";
                } else {
                    directX = "向左";
                }
                if (moveY - y > 10) {
                    directY = "向下";
                } else {
                    directY = "向上";
                }
                break;
        }
        mTestContent = firstString + "\n-->\n" + "x=" + x + "\ny=" + y + "\nrawx=" + rawX + "\nrawy" + rawY + "/" + directX + directY;
//        invalidate();
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtils.i(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(event);

    }


    @Override
    public void onClick(View v) {
        ToastUtils.showToast("点击");
    }
}
