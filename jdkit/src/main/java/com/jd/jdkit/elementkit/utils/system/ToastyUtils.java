package com.jd.jdkit.elementkit.utils.system;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jd.jdkit.BaseConfig;
import com.jd.jdkit.R;

/**
 * Auther: Jarvis Dong
 * Time: on 2017/2/20 0020
 * Name:
 * OverView: toast工具类,优化美观;
 * Usage:
 */

public class ToastyUtils {
    private static final
    @ColorInt
    int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
    private static final
    @ColorInt
    int ERROR_COLOR = Color.parseColor("#D50000");
    private static final
    @ColorInt
    int INFO_COLOR = Color.parseColor("#3F51B5");
    private static final
    @ColorInt
    int SUCCESS_COLOR = Color.parseColor("#388E3C");
    private static final
    @ColorInt
    int WARNING_COLOR = Color.parseColor("#FFA900");

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Toast currentToast;

    public static
    @CheckResult
    Toast normal(@NonNull Context context, @NonNull String message) {
        return normal(context, message, Toast.LENGTH_SHORT, null, false);
    }

    public static
    @CheckResult
    Toast normal(@NonNull Context context, @NonNull String message, Drawable icon) {
        return normal(context, message, Toast.LENGTH_SHORT, icon, true);
    }

    public static
    @CheckResult
    Toast normal(@NonNull Context context, @NonNull String message, int duration) {
        return normal(context, message, duration, null, false);
    }

    public static
    @CheckResult
    Toast normal(@NonNull Context context, @NonNull String message, int duration,
                 Drawable icon) {
        return normal(context, message, duration, icon, true);
    }

    public static
    @CheckResult
    Toast normal(@NonNull Context context, @NonNull String message, int duration,
                 Drawable icon, boolean withIcon) {
        return custom(context, message, icon, DEFAULT_TEXT_COLOR, duration, withIcon);
    }

    public static
    @CheckResult
    Toast warning(@NonNull Context context, @NonNull String message) {
        return warning(context, message, Toast.LENGTH_SHORT, true);
    }

    public static
    @CheckResult
    Toast warning(@NonNull Context context, @NonNull String message, int duration) {
        return warning(context, message, duration, true);
    }

    public static
    @CheckResult
    Toast warning(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_48dp),
                DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    public static
    @CheckResult
    Toast info(@NonNull Context context, @NonNull String message) {
        return info(context, message, Toast.LENGTH_SHORT, true);
    }

    public static
    @CheckResult
    Toast info(@NonNull Context context, @NonNull String message, int duration) {
        return info(context, message, duration, true);
    }

    public static
    @CheckResult
    Toast info(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_48dp),
                DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }

    public static
    @CheckResult
    Toast success(@NonNull Context context, @NonNull String message) {
        return success(context, message, Toast.LENGTH_SHORT, true);
    }

    public static
    @CheckResult
    Toast success(@NonNull Context context, @NonNull String message, int duration) {
        return success(context, message, duration, true);
    }

    public static
    @CheckResult
    Toast success(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_check_white_48dp),
                DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    public static
    @CheckResult
    Toast error(@NonNull Context context, @NonNull String message) {
        return error(context, message, Toast.LENGTH_SHORT, true);
    }

    public static
    @CheckResult
    Toast error(@NonNull Context context, @NonNull String message, int duration) {
        return error(context, message, duration, true);
    }

    public static
    @CheckResult
    Toast error(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_clear_white_48dp),
                DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    public static
    @CheckResult
    Toast custom(@NonNull Context context, @NonNull String message, Drawable icon,
                 @ColorInt int textColor, int duration, boolean withIcon) {
        return custom(context, message, icon, textColor, -1, duration, withIcon, false);
    }

    public static
    @CheckResult
    Toast custom(@NonNull Context context, @NonNull String message, @DrawableRes int iconRes,
                 @ColorInt int textColor, @ColorInt int tintColor, int duration,
                 boolean withIcon, boolean shouldTint) {
        return custom(context, message, ToastyUtils.getDrawable(context, iconRes), textColor,
                tintColor, duration, withIcon, shouldTint);
    }

    public static
    @CheckResult
    Toast custom(@NonNull Context context, @NonNull String message, Drawable icon,
                 @ColorInt int textColor, @ColorInt int tintColor, int duration,
                 boolean withIcon, boolean shouldTint) {
        cancelToast();
        currentToast = new Toast(context);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.toast_layout, null);
        final ImageView toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint)
            drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);
        else
            drawableFrame = ToastyUtils.getDrawable(context, R.drawable.toast_frame);
        ToastyUtils.setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            ToastyUtils.setBackground(toastIcon, icon);
        } else
            toastIcon.setVisibility(View.GONE);

        toastTextView.setTextColor(textColor);
        toastTextView.setText(message);
        toastTextView.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);
        return currentToast;
    }


    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (currentToast != null) {
            currentToast.cancel();
            currentToast = null;
        }
    }

    //*******************************************
    //*******************************************

    /**
     * 将.9png着色;
     *
     * @param context
     * @param tintColor
     * @return
     */
    static Drawable tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor) {
        final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, R.drawable.toast_frame);
        toastDrawable.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        return toastDrawable;
    }

    /**
     * 给view设置背景色;
     *
     * @param view
     * @param drawable
     */
    static void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else
            view.setBackgroundDrawable(drawable);
    }

    /**
     * 获得对应id的drawable
     *
     * @param context
     * @param id
     * @return
     */
    static Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getDrawable(id);
        else
            return context.getResources().getDrawable(id);
    }

    //*****************************************************
    //简单使用;
    //*****************************************************
    public static void showErr(final String str) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                error(BaseConfig.application, str).show();
            }
        });
    }

    public static void showInfo(final String str) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                info(BaseConfig.application, str).show();
            }
        });
    }

    public static void showWarning(final String str) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                warning(BaseConfig.application, str).show();
            }
        });
    }

    public static void showNormal(final String str) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                normal(BaseConfig.application, str).show();
            }
        });
    }

    public static void showSuccess(final String str) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                success(BaseConfig.application, str).show();
            }
        });
    }

}
