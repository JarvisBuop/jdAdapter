package com.jd.jarvisdemonim.views;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.jd.jarvisdemonim.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * overview: 实现选座功能(已去除概念图功能和添加部分注释)
 */
public class DSeatTable extends View {
    private final boolean DBG = true;

    Paint paint = new Paint();
    Paint lineNumberPaint;
    float lineNumberTxtHeight;

    public void setLineNumbers(HashMap<Integer, String> mapLineNumbers) {
        this.mapLineNumbers = mapLineNumbers;
    }

    HashMap<Integer, String> mapLineNumbers;

    ArrayList<String> lineNumbers = new ArrayList<>();//行数;row
    Paint.FontMetrics lineNumberPaintFontMetrics;
    Matrix matrix = new Matrix();
//    AnimationScaleRunnable animationScaleRunnable=new AnimationScaleRunnable();

    /**
     * 座位水平间距
     */
    int spacing;

    /**
     * 座位垂直间距
     */
    int verSpacing;

    /**
     * 行号宽度
     */
    int numberWidth;

    /**
     * 行数
     */
    int row;

    /**
     * 列数
     */
    int column;

    /**
     * 可选时座位的图片
     */
    Bitmap seatBitmap;

    /**
     * 选中时座位的图片
     */
    Bitmap checkedSeatBitmap;

    /**
     * 座位已经售出时的图片
     */
    Bitmap seatSoldBitmap;

//    Bitmap overviewBitmap;//概念图图片?

    int lastX;
    int lastY;

    /**
     * 整个座位图的宽度
     */
    int seatBitmapWidth;

    /**
     * 整个座位图的高度
     */
    int seatBitmapHeight;

    /**
     * 标识是否需要绘制座位图
     */
    boolean isNeedDrawSeatBitmap = true;

//    /**
//     * 概览图上方块高度
//     */
//    float rectHeight;
//
//    /**
//     * 概览图上方块的宽度
//     */
//    float rectWidth;
//
//    /**
//     * 概览图上方块的水平间距
//     */
//    float overviewSpacing;
//
//    /**
//     * 概览图上方块的垂直间距
//     */
//    float overviewVerSpacing;
//
//    /**
//     * 概览图的比例
//     */
//    float overviewScale = 4.8f;

    /**
     * 荧幕高度
     */
    float screenHeight;

    /**
     * 荧幕默认宽度与座位图的比例
     */
    float screenWidthScale = 0.5f;

    /**
     * 荧幕最小宽度
     */
    int defaultScreenWidth;

    /**
     * 标识是否正在缩放
     */
    boolean isScaling;
    float scaleX, scaleY;

    /**
     * 是否是第一次缩放
     */
    boolean firstScale = true;

    /**
     * 最多可以选择的座位数量
     */
    int maxSelected = Integer.MAX_VALUE;

    private SeatChecker seatChecker;

    /**
     * 荧幕名称(小标题)
     */
    private String screenName = "";

//    /**
//     * 整个概览图的宽度
//     */
//    float rectW;
//
//    /**
//     * 整个概览图的高度
//     */
//    float rectH;

    Paint headPaint;
    Bitmap headBitmap;

    /**
     * 是否第一次执行onDraw
     */
    boolean isFirstDraw = true;

//    /**
//     * 标识是否需要绘制概览图
//     */
//    boolean isDrawOverview = false;

//    /**
//     * 标识是否需要更新概览图
//     */
//    boolean isDrawOverviewBitmap = true;

    int overview_checked;
    int overview_sold;
    int seatCheckedResID;
    int seatSoldResID;
    int seatAvailableResID;

    boolean isOnClick;//手势点击放大;

    private static final int SEAT_TYPE_SOLD = 1;//已售
    private static final int SEAT_TYPE_SELECTED = 2;//选中
    private static final int SEAT_TYPE_AVAILABLE = 3;//可选
    private static final int SEAT_TYPE_NOT_AVAILABLE = 4;//不可选,空白;

    private int downX, downY;
    private boolean pointer;

    /**
     * 顶部高度,可选,已选,已售区域的高度
     */
    float headHeight;

    Paint pathPaint;
    RectF rectF;

    /**
     * 头部下面横线的高度
     */
    int borderHeight = 1;
    Paint redBorderPaint;

    public DSeatTable(Context context) {
        super(context);
    }

    public DSeatTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SeatTableView);
        overview_checked = typedArray.getColor(R.styleable.SeatTableView_overview_checked, Color.parseColor("#5A9E64"));
        overview_sold = typedArray.getColor(R.styleable.SeatTableView_overview_sold, Color.RED);
        seatCheckedResID = typedArray.getResourceId(R.styleable.SeatTableView_seat_checked, R.drawable.seat_selected);
        seatSoldResID = typedArray.getResourceId(R.styleable.SeatTableView_overview_sold, R.drawable.seat_sold);
        seatAvailableResID = typedArray.getResourceId(R.styleable.SeatTableView_seat_available, R.drawable.seat_sale);
        init();
    }

    public DSeatTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        spacing = (int) dip2Px(5); //座位水平间距 5dp
        verSpacing = (int) dip2Px(10);//座位垂直间距
        defaultScreenWidth = (int) dip2Px(80);//荧幕最小宽度

        seatBitmap = BitmapFactory.decodeResource(getResources(), seatAvailableResID);//可选
        checkedSeatBitmap = BitmapFactory.decodeResource(getResources(), seatCheckedResID);//已选
        seatSoldBitmap = BitmapFactory.decodeResource(getResources(), seatSoldResID);//已售

        seatBitmapWidth = column * seatBitmap.getWidth() + (column - 1) * spacing;//整个座位图的宽度
        seatBitmapHeight = row * seatBitmap.getHeight() + (row - 1) * verSpacing;//整个座位图的高度
        paint.setColor(Color.RED);
        numberWidth = (int) dip2Px(20);//行号宽度20dp


        screenHeight = dip2Px(20);//荧幕高度
//        headHeight = dip2Px(30);//头部高度;(现不需要设为0)
        headHeight = 0;//头部高度;(现不需要设为0)
        if (seatBitmapWidth > 0 && seatBitmapHeight > 0) {
        }
        headPaint = new Paint();
        headPaint.setStyle(Paint.Style.FILL);
        headPaint.setTextSize(24);
        headPaint.setColor(Color.WHITE);
        headPaint.setAntiAlias(true);

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.FILL);
        pathPaint.setColor(Color.parseColor("#e2e2e2"));

        redBorderPaint = new Paint();
        redBorderPaint.setAntiAlias(true);
        redBorderPaint.setColor(Color.RED);
        redBorderPaint.setStyle(Paint.Style.STROKE);
        redBorderPaint.setStrokeWidth(getResources().getDisplayMetrics().density * 1);

        rectF = new RectF();

//        rectHeight = seatBitmap.getHeight() / overviewScale;
//        rectWidth = seatBitmap.getWidth() / overviewScale;
//        overviewSpacing = spacing / overviewScale;
//        overviewVerSpacing = verSpacing / overviewScale;
//
//        rectW = column * rectWidth + (column - 1) * overviewSpacing + overviewSpacing * 2;
//        rectH = row * rectHeight + (row - 1) * overviewVerSpacing + overviewVerSpacing * 2;
//        overviewBitmap = Bitmap.createBitmap((int) rectW, (int) rectH, Bitmap.Config.ARGB_4444);

        lineNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineNumberPaint.setColor(bacColor);
        lineNumberPaint.setTextSize(getResources().getDisplayMetrics().density * 16);

        lineNumberTxtHeight = lineNumberPaint.measureText("4");//字符串显示的宽度值;
        lineNumberPaintFontMetrics = lineNumberPaint.getFontMetrics();//文字大小
        lineNumberPaint.setTextAlign(Paint.Align.CENTER);//文字对齐;

        for (int i = 0; i < row; i++) {
//            if (mapLineNumbers != null) {
                lineNumbers.add(mapLineNumbers.get(i));//如果没设置行列数则跳过此行,数字依次递增;
//            } else {
//                lineNumbers.add((i + 1) + "");//1234567...
//            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        long startTime = System.currentTimeMillis();
        if (row <= 0 || column == 0) {
            return;
        }
//        if (isNeedDrawSeatBitmap) {
//            drawSeat();
//        }
//
//        if (isFirstDraw) {
//            isFirstDraw = false;
//            matrix.postTranslate(getWidth() / 2 - seatBitmapWidth / 2, headHeight + screenHeight + borderHeight + verSpacing);
//        }
//
//        canvas.drawBitmap(seat, matrix, paint);

        drawSeat(canvas);//绘制座位;
        drawNumber(canvas);

//        if (headBitmap == null) {//绘制头部说明;
//            headBitmap = drawHeadInfo();
//        }
//        canvas.drawBitmap(headBitmap, 0, 0, null);

        drawScreen(canvas);

//        if (isDrawOverview) {
//            long s= System.currentTimeMillis();
//            if (isDrawOverviewBitmap) {
//                drawOverview();
//            }
//            canvas.drawBitmap(overviewBitmap, 0, 0, null);
//            drawOverview(canvas);
//            Log.d("drawTime","OverviewDrawTime:"+(System.currentTimeMillis()-s));
//        }

        if (DBG) {
            long drawTime = System.currentTimeMillis() - startTime;
            Log.d("drawTime", "totalDrawTime:" + drawTime);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int x = (int) event.getX();
        super.onTouchEvent(event);

        scaleGestureDetector.onTouchEvent(event);//使用放缩手势;
        gestureDetector.onTouchEvent(event);//点击效果放大;
        int pointerCount = event.getPointerCount();
        if (pointerCount > 1) {
            pointer = true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointer = false;
                downX = x;
                downY = y;
//                isDrawOverview = true;
//                handler.removeCallbacks(hideOverviewRunnable);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isScaling && !isOnClick) {
                    int downDX = Math.abs(x - downX);
                    int downDY = Math.abs(y - downY);
                    if ((downDX > 10 || downDY > 10) && !pointer) {
                        int dx = x - lastX;
                        int dy = y - lastY;
                        matrix.postTranslate(dx, dy);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
//                handler.postDelayed(hideOverviewRunnable, 1500);

                autoScale();
                int downDX = Math.abs(x - downX);
                int downDY = Math.abs(y - downY);
                if ((downDX > 10 || downDY > 10) && !pointer) {
                    autoScroll();
                }

                break;
        }
        isOnClick = false;
        lastY = y;
        lastX = x;

        return true;
    }

    //用于触摸后更新缩略图;
//    private Runnable hideOverviewRunnable = new Runnable() {
//        @Override
//        public void run() {
//            isDrawOverview = false;
//            invalidate();
//        }
//    };

    /**
     * 绘制头部文字说明;(暂不需)
     */
    Bitmap drawHeadInfo() {
        String txt = "已售";
        float txtY = getBaseLine(headPaint, 0, headHeight);
        int txtWidth = (int) headPaint.measureText(txt);//文字宽度;
        float spacing = dip2Px(10);
        float spacing1 = dip2Px(5);
        float y = (headHeight - seatBitmap.getHeight()) / 2;

        float width = seatBitmap.getWidth() + spacing1 + txtWidth + spacing + seatSoldBitmap.getWidth() + txtWidth + spacing1 + spacing + checkedSeatBitmap.getHeight() + spacing1 + txtWidth;
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), (int) headHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        //绘制背景
        canvas.drawRect(0, 0, getWidth(), headHeight, headPaint);
        headPaint.setColor(Color.BLACK);

        float startX = (getWidth() - width) / 2;

        canvas.drawBitmap(seatBitmap, startX, (headHeight - seatBitmap.getHeight()) / 2, headPaint);
        canvas.drawText("可选", startX + seatBitmap.getWidth() + spacing1, txtY, headPaint);

        float soldSeatBitmapY = startX + seatBitmap.getWidth() + spacing1 + txtWidth + spacing;
        canvas.drawBitmap(seatSoldBitmap, soldSeatBitmapY, (headHeight - seatBitmap.getHeight()) / 2, headPaint);
        canvas.drawText("已售", soldSeatBitmapY + seatSoldBitmap.getWidth() + spacing1, txtY, headPaint);

        float checkedSeatBitmapX = soldSeatBitmapY + seatSoldBitmap.getWidth() + spacing1 + txtWidth + spacing;
        canvas.drawBitmap(checkedSeatBitmap, checkedSeatBitmapX, y, headPaint);
        canvas.drawText("已选", checkedSeatBitmapX + spacing1 + checkedSeatBitmap.getWidth(), txtY, headPaint);

        //绘制分割线
        headPaint.setStrokeWidth(1);
        headPaint.setColor(Color.GRAY);
        canvas.drawLine(0, headHeight, getWidth(), headHeight, headPaint);
        return bitmap;

    }

    /**
     * 绘制中间屏幕(银幕)
     */
    void drawScreen(Canvas canvas) {
        pathPaint.setStyle(Paint.Style.FILL);
        pathPaint.setColor(Color.parseColor("#e2e2e2"));
        float startY = headHeight + borderHeight;//去除头部和横线的高度;

        float centerX = seatBitmapWidth * getMatrixScaleX() / 2 + getTranslateX();
        float screenWidth = seatBitmapWidth * screenWidthScale * getMatrixScaleX();
        if (screenWidth < defaultScreenWidth) {//最小宽度;
            screenWidth = defaultScreenWidth;
        }

        Path path = new Path();
        path.moveTo(centerX, startY);
        path.lineTo(centerX - screenWidth / 2, startY);
        path.lineTo(centerX - screenWidth / 2 + 20, screenHeight * getMatrixScaleY() + startY);
        path.lineTo(centerX + screenWidth / 2 - 20, screenHeight * getMatrixScaleY() + startY);
        path.lineTo(centerX + screenWidth / 2, startY);

        canvas.drawPath(path, pathPaint);

        pathPaint.setColor(Color.BLACK);
        pathPaint.setTextSize(20 * getMatrixScaleX());

        canvas.drawText(screenName, centerX - pathPaint.measureText(screenName) / 2, getBaseLine(pathPaint, startY, startY + screenHeight * getMatrixScaleY()), pathPaint);
    }

    Matrix tempMatrix = new Matrix();

    void drawSeat(Canvas canvas) {
        zoom = getMatrixScaleX();//matrix 的倍数;
        long startTime = System.currentTimeMillis();
        Log.d("zoomTest", "drawZoom" + zoom);
        float translateX = getTranslateX();
        float translateY = getTranslateY();
        float scaleX = zoom;
        float scaleY = zoom;

        float x = seatBitmap.getWidth() * scaleX * column / 2 + translateX + spacing * scaleX / 2 + spacing * scaleX * (column / 2.0f - 1);//座位的竖方向中位线
        float y = seatBitmap.getHeight() * scaleY * row / 2 + translateY + verSpacing * scaleY / 2 + verSpacing * scaleY * (row / 2.0f - 1);//座位的横方向中位线
        for (int i = 0; i < row; i++) {
            float top = i * seatBitmap.getHeight() * scaleY + i * verSpacing * scaleY + translateY;
            float bottom = top + seatBitmap.getHeight() * scaleY;
            if (bottom < 0 || top > getHeight()) {
                continue;
            }
            for (int j = 0; j < column; j++) {
                float left = j * seatBitmap.getWidth() * scaleX + j * spacing * scaleX + translateX;
                float right = (left + seatBitmap.getHeight() * scaleY);
                if (right < 0 || left > getWidth()) {
                    continue;
                }
                int seatType = getSeatType(i, j);//获取座位类型;
                tempMatrix.setTranslate(left, top);//设置放大效果;
                tempMatrix.postScale(scaleX, scaleY, left, top);

                //添加线;
                canvas.drawLine(x, top, x, bottom, paint);//中位线
                canvas.drawLine(left, y, right, y, paint);

                switch (seatType) {
                    case SEAT_TYPE_AVAILABLE:
                        canvas.drawBitmap(seatBitmap, tempMatrix, paint);
                        break;
                    case SEAT_TYPE_NOT_AVAILABLE://不可选  空白;
                        break;
                    case SEAT_TYPE_SELECTED:
                        canvas.drawBitmap(checkedSeatBitmap, tempMatrix, paint);
                        break;
                    case SEAT_TYPE_SOLD:
                        canvas.drawBitmap(seatSoldBitmap, tempMatrix, paint);
                        break;
                }

            }
        }

        if (DBG) {
            long drawTime = System.currentTimeMillis() - startTime;
            Log.d("drawTime", "seatDrawTime:" + drawTime);
        }
    }

    private int getSeatType(int row, int column) {//判断每个座位状态;

        if (isHave(getID(row, column)) >= 0) {
            return SEAT_TYPE_SELECTED;
        }

        if (seatChecker != null) {
            if (!seatChecker.isValidSeat(row, column)) {
                return SEAT_TYPE_NOT_AVAILABLE;
            } else if (seatChecker.isSold(row, column)) {
                return SEAT_TYPE_SOLD;
            }
        }

        return SEAT_TYPE_AVAILABLE;
    }

    private int getID(int row, int column) {
//        Log.i("jarvisid", row * this.column + (column + 1) + "");
        return row * this.column + (column + 1);
    }

    int bacColor = Color.parseColor("#7e000000");//左边行号背景色

    /**
     * 绘制行号
     */
    void drawNumber(Canvas canvas) {
        long startTime = System.currentTimeMillis();
        lineNumberPaint.setColor(bacColor);
        int translateY = (int) getTranslateY();
        float scaleY = getMatrixScaleY();

        rectF.top = translateY - lineNumberTxtHeight / 2;
        rectF.bottom = translateY + (seatBitmapHeight * scaleY) + lineNumberTxtHeight / 2;
        rectF.left = 0;
        rectF.right = numberWidth;
        canvas.drawRoundRect(rectF, numberWidth / 2, numberWidth / 2, lineNumberPaint);

        lineNumberPaint.setColor(Color.WHITE);//再次设置,用于文字;

        for (int i = 0; i < row; i++) {

            float top = (i * seatBitmap.getHeight() + i * verSpacing) * scaleY + translateY;
            float bottom = (i * seatBitmap.getHeight() + i * verSpacing + seatBitmap.getHeight()) * scaleY + translateY;
            float baseline = (bottom + top - lineNumberPaintFontMetrics.bottom - lineNumberPaintFontMetrics.top) / 2;

            canvas.drawText(lineNumbers.get(i), numberWidth / 2, baseline, lineNumberPaint);//设置文字1234567,,,
        }

        if (DBG) {
            long drawTime = System.currentTimeMillis() - startTime;
            Log.d("drawTime", "drawNumberTime:" + drawTime);
        }
    }

//    /**
//     * 绘制概览图
//     */
//    void drawOverview(Canvas canvas) {
//
//        //绘制红色框
//        int left = (int) -getTranslateX();
//        if (left < 0) {
//            left = 0;
//        }
//        left /= overviewScale;
//        left /= getMatrixScaleX();
//
//        int currentWidth = (int) (getTranslateX() + (column * seatBitmap.getWidth() + spacing * (column - 1)) * getMatrixScaleX());
//        if (currentWidth > getWidth()) {
//            currentWidth = currentWidth - getWidth();
//        } else {
//            currentWidth = 0;
//        }
//        int right = (int) (rectW - currentWidth / overviewScale / getMatrixScaleX());
//
//        float top = -getTranslateY() + headHeight;
//        if (top < 0) {
//            top = 0;
//        }
//        top /= overviewScale;
//        top /= getMatrixScaleY();
//        if (top > 0) {
//            top += overviewVerSpacing;
//        }
//
//        int currentHeight = (int) (getTranslateY() + (row * seatBitmap.getHeight() + verSpacing * (row - 1)) * getMatrixScaleY());
//        if (currentHeight > getHeight()) {
//            currentHeight = currentHeight - getHeight();
//        } else {
//            currentHeight = 0;
//        }
//        int bottom = (int) (rectH - currentHeight / overviewScale / getMatrixScaleY());
//
//        canvas.drawRect(left, top, right, bottom, redBorderPaint);
//    }
//
//    //概念图背景;
//    Bitmap drawOverview() {
//        isDrawOverviewBitmap = false;
//
//        int bac = Color.parseColor("#7e000000");
//        paint.setColor(bac);
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.FILL);
//
//        Canvas canvas = new Canvas(overviewBitmap);
//        //绘制透明灰色背景
//        canvas.drawRect(0, 0, rectW, rectH, paint);
//
//        paint.setColor(Color.WHITE);
//        for (int i = 0; i < row; i++) {
//            float top = i * rectHeight + i * overviewVerSpacing + overviewVerSpacing;
//            for (int j = 0; j < column; j++) {
//
//                int seatType = getSeatType(i, j);
//                switch (seatType) {
//                    case SEAT_TYPE_AVAILABLE:
//                        paint.setColor(Color.WHITE);
//                        break;
//                    case SEAT_TYPE_NOT_AVAILABLE:
//                        continue;
//                    case SEAT_TYPE_SELECTED:
//                        paint.setColor(overview_checked);
//                        break;
//                    case SEAT_TYPE_SOLD:
//                        paint.setColor(overview_sold);
//                        break;
//                }
//
//                float left;
//
//                left = j * rectWidth + j * overviewSpacing + overviewSpacing;
//                canvas.drawRect(left, top, left + rectWidth, top + rectHeight, paint);
//            }
//        }
//
//        return overviewBitmap;
//    }

    /**
     * 自动回弹
     * 整个大小不超过控件大小的时候:
     * 往左边滑动,自动回弹到行号右边
     * 往右边滑动,自动回弹到右边
     * 往上,下滑动,自动回弹到顶部
     * <p>
     * 整个大小超过控件大小的时候:
     * 往左侧滑动,回弹到最右边,往右侧滑回弹到最左边
     * 往上滑动,回弹到底部,往下滑动回弹到顶部
     */
    public void autoScroll() {
        float currentSeatBitmapWidth = seatBitmapWidth * getMatrixScaleX();
        float currentSeatBitmapHeight = seatBitmapHeight * getMatrixScaleY();
        float moveYLength = 0;
        float moveXLength = 0;

        //处理左右滑动的情况
        if (currentSeatBitmapWidth < getWidth()) {
            if (getTranslateX() < 0 || getMatrixScaleX() < numberWidth + spacing) {
                //计算要移动的距离

                if (getTranslateX() < 0) {
                    moveXLength = (-getTranslateX()) + numberWidth + spacing;
                } else {
                    moveXLength = numberWidth + spacing - getTranslateX();
                }

            }
        } else {

            if (getTranslateX() < 0 && getTranslateX() + currentSeatBitmapWidth > getWidth()) {

            } else {
                //往左侧滑动
                if (getTranslateX() + currentSeatBitmapWidth < getWidth()) {
                    moveXLength = getWidth() - (getTranslateX() + currentSeatBitmapWidth);
                } else {
                    //右侧滑动
                    moveXLength = -getTranslateX() + numberWidth + spacing;
                }
            }

        }

        float startYPosition = screenHeight * getMatrixScaleY() + verSpacing * getMatrixScaleY() + headHeight + borderHeight;

        //处理上下滑动
        if (currentSeatBitmapHeight < getHeight()) {

            if (getTranslateY() < startYPosition) {
                moveYLength = startYPosition - getTranslateY();
            } else {
                moveYLength = -(getTranslateY() - (startYPosition));
            }

        } else {

            if (getTranslateY() < 0 && getTranslateY() + currentSeatBitmapHeight > getHeight()) {

            } else {
                //往上滑动
                if (getTranslateY() + currentSeatBitmapHeight < getHeight()) {
                    moveYLength = getHeight() - (getTranslateY() + currentSeatBitmapHeight);
                } else {
                    moveYLength = -(getTranslateY() - (startYPosition));
                }
            }
        }

//        Message message = Message.obtain();
//        MoveInfo moveInfo = new MoveInfo();
//        moveInfo.moveXLength = moveXLength;
//        moveInfo.moveYLength = moveYLength;
//        message.obj = moveInfo;
//        handler.sendMessageDelayed(message, time);

        Point start = new Point();
        start.x = (int) getTranslateX();
        start.y = (int) getTranslateY();

        Point end = new Point();
        end.x = (int) (start.x + moveXLength);
        end.y = (int) (start.y + moveYLength);

        moveAnimate(start, end);

    }

    class MoveInfo {
        public float moveXLength;
        public float moveYLength;
    }


    int FRAME_COUNT = 10;
    int time = 10;
    int count;
    int SCALE_TIME = 15;

//    Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//
//            if (count < FRAME_COUNT) {
//                count++;
//                MoveInfo moveInfo = (MoveInfo) msg.obj;
//                float moveXLength = moveInfo.moveXLength;
//                float moveYLength = moveInfo.moveYLength;
//                float xValue = moveXLength / FRAME_COUNT;
//                float yValue = moveYLength / FRAME_COUNT;
//
//                matrix.postTranslate(xValue, yValue);
//                invalidate();
//                Message message = Message.obtain();
//                message.obj = msg.obj;
//                handler.sendMessageDelayed(message, time);
//                if (DBG) {
//                    Log.d("autoScroll", "moveCount:" + count);
//                }
//            } else {
//                count = 0;
//            }
//
//            return true;
//        }
//    });


    ArrayList<Integer> selects = new ArrayList<>();//存储选择的座位;

    public ArrayList<Integer> getSelectedSeats() {
        return selects;
    }

    private int isHave(Integer seat) {
        return Collections.binarySearch(selects, seat);
    }

    private void remove(int index) {
        selects.remove(index);
    }
    private void clear(){
        selects.clear();
    }

    float[] m = new float[9];

    private float getTranslateX() {
        matrix.getValues(m);
        return m[2];
    }

    private float getTranslateY() {
        matrix.getValues(m);
        return m[5];
    }

    private float getMatrixScaleY() {
        matrix.getValues(m);
        return m[4];
    }

    private float getMatrixScaleX() {
        matrix.getValues(m);
        return m[Matrix.MSCALE_X];
    }

    private float dip2Px(float value) {
        return getResources().getDisplayMetrics().density * value;
    }

    private float getBaseLine(Paint p, float top, float bottom) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        int baseline = (int) ((bottom + top - fontMetrics.bottom - fontMetrics.top) / 2);
        return baseline;
    }

//    public class AnimationScaleRunnable implements Runnable {
//        private float x, y;//缩放的中心点
//
//        private float targetScale;
//
//        private float tempScale;
//
//        public AnimationScaleRunnable() {
//
//        }
//
//        public AnimationScaleRunnable(float x, float y, float targetScale) {
//            float currentScale = getMatrixScaleX();
//            this.x = x;
//            this.y = y;
//            this.targetScale = targetScale;
//
//            if (currentScale < targetScale) {
//                tempScale = 1.06f;
//            } else {
//                tempScale = 0.95f;
//            }
//        }
//
//        public void setPar(float x, float y, float targetScale) {
//            float currentScale = getMatrixScaleX();
//            this.x = x;
//            this.y = y;
//            this.targetScale = targetScale;
//
//            if (currentScale < targetScale) {
//                tempScale = 1.06f;
//            } else {
//                tempScale = 0.95f;
//            }
//        }
//
//        @Override
//        public void run() {
//            matrix.postScale(tempScale, tempScale, x, y);
//            invalidate();
//            float currentScale = getMatrixScaleX();
//
//            if (tempScale > 1 && currentScale < targetScale) {
//                postDelayed(this, SCALE_TIME);
//            } else if (tempScale < 1 && currentScale > targetScale) {
//                postDelayed(this, SCALE_TIME);
//            }
//
//        }
//    }

    private void moveAnimate(Point start, Point end) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MoveEvaluator(), start, end);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        MoveAnimation moveAnimation = new MoveAnimation();
        valueAnimator.addUpdateListener(moveAnimation);
        valueAnimator.setDuration(400);
        valueAnimator.start();
    }

    private void zoomAnimate(float cur, float tar) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(cur, tar);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        ZoomAnimation zoomAnim = new ZoomAnimation();
        valueAnimator.addUpdateListener(zoomAnim);
        valueAnimator.addListener(zoomAnim);
        valueAnimator.setDuration(400);
        valueAnimator.start();
    }

    private float zoom;

    private void zoom(float zoom) {
        float z = zoom / getMatrixScaleX();
        matrix.postScale(z, z, scaleX, scaleY);
        invalidate();
    }

    private void move(Point p) {
        float x = p.x - getTranslateX();
        float y = p.y - getTranslateY();
        matrix.postTranslate(x, y);
        Log.d("moveTest", "x:" + x + "y:" + y + "rowx:" + p.x + "rowy:" + p.y);

        invalidate();
    }

    class MoveAnimation implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Point p = (Point) animation.getAnimatedValue();

            move(p);
        }
    }

    class MoveEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            int x = (int) (startPoint.x + fraction * (endPoint.x - startPoint.x));
            int y = (int) (startPoint.y + fraction * (endPoint.y - startPoint.y));
            return new Point(x, y);
        }
    }

    class ZoomAnimation implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            zoom = (Float) animation.getAnimatedValue();

            zoom(zoom);
            if (DBG) {
                Log.d("zoomTest", "zoom:" + zoom);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }

        @Override
        public void onAnimationStart(Animator animation) {
        }

    }

    public void setData(int row, int column) {
        this.row = row;
        this.column = column;
        init();
        invalidate();
    }

    //获取放大缩小的比例;
    ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            isScaling = true;
            float scaleFactor = detector.getScaleFactor();
            if (getMatrixScaleY() * scaleFactor > 3) {
                scaleFactor = 3 / getMatrixScaleY();
            }
            if (firstScale) {
                scaleX = detector.getCurrentSpanX();
                scaleY = detector.getCurrentSpanY();
                firstScale = false;
            }

            if (getMatrixScaleY() * scaleFactor < 0.5) {
                scaleFactor = 0.5f / getMatrixScaleY();
            }
            matrix.postScale(scaleFactor, scaleFactor, scaleX, scaleY);//设置放缩变换;
            invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            isScaling = false;
            firstScale = true;
        }
    });

    //点击效果放大;
    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            isOnClick = true;
            int x = (int) e.getX();
            int y = (int) e.getY();

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    int tempX = (int) ((j * seatBitmap.getWidth() + j * spacing) * getMatrixScaleX() + getTranslateX());
                    int maxTemX = (int) (tempX + seatBitmap.getWidth() * getMatrixScaleX());

                    int tempY = (int) ((i * seatBitmap.getHeight() + i * verSpacing) * getMatrixScaleY() + getTranslateY());
                    int maxTempY = (int) (tempY + seatBitmap.getHeight() * getMatrixScaleY());

                    if (seatChecker != null && seatChecker.isValidSeat(i, j) && !seatChecker.isSold(i, j)) {
                        if (x >= tempX && x <= maxTemX && y >= tempY && y <= maxTempY) {
                            int id = getID(i, j);
                            int index = isHave(id);
                            if (index >= 0) {
                                remove(index);
                                if (seatChecker != null) {
                                    seatChecker.unCheck(i, j);
                                }
                            } else {
                                if (selects.size() >= maxSelected) {
                                    Toast.makeText(getContext(), "最多只能选择" + maxSelected + "个", Toast.LENGTH_SHORT).show();
                                    return super.onSingleTapConfirmed(e);
                                } else {
                                    addChooseSeat(i, j);
                                    if (seatChecker != null) {
                                        seatChecker.checked(i, j);
                                    }
                                }
                            }
                            isNeedDrawSeatBitmap = true;
//                            isDrawOverviewBitmap = true;

//                            //点击放大;
//                            float currentScaleY = getMatrixScaleY();
//                            Log.i("jarvisY", currentScaleY + "");
//                            if (currentScaleY < 1.7) {
//                                scaleX = x;
//                                scaleY = y;
//                                zoomAnimate(currentScaleY, 1.9f);
//                            }
//
                            invalidate();
                            break;
                        }
                    }
                }
            }

            return super.onSingleTapConfirmed(e);
        }
    });

    public void changeScaleDown() {//取消所有选择,缩小到原来大小;
        if (getMatrixScaleX() > 1.1) {
            zoomAnimate(getMatrixScaleX(), 1.0f);
        }
    }

    public void changeScaleUp() {//选择座位,放大;
        if (getMatrixScaleX() < 1.8) {
            zoomAnimate(getMatrixScaleX(), 1.8f);
        }
    }

    private void autoScale() {//放大时,最多放大2.0f;缩小时,最小缩小1.0f,否则矩阵回弹;
        if (getMatrixScaleX() > 2.2) {
            zoomAnimate(getMatrixScaleX(), 2.0f);
        } else if (getMatrixScaleX() < 0.98) {
            zoomAnimate(getMatrixScaleX(), 1.0f);
        }
    }

    private void addChooseSeat(int row, int column) {//记录选中的座位;
        int id = getID(row, column);
        for (int i = 0; i < selects.size(); i++) {
            int item = selects.get(i);
            if (id < item) {
                selects.add(i, id);
                return;
            }
        }
        selects.add(id);
        Log.i("jarviselect", row + "/" + column + "/" + selects.toString());
    }

    public interface SeatChecker {
        /**
         * 是否可用座位
         *
         * @param row
         * @param column
         * @return
         */
        boolean isValidSeat(int row, int column);

        /**
         * 是否已售
         *
         * @param row
         * @param column
         * @return
         */
        boolean isSold(int row, int column);

        void checked(int row, int column);

        void unCheck(int row, int column);

    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setMaxSelected(int maxSelected) {
        this.maxSelected = maxSelected;
    }

    public void setSeatChecker(SeatChecker seatChecker) {
        this.seatChecker = seatChecker;
        invalidate();
    }

}
