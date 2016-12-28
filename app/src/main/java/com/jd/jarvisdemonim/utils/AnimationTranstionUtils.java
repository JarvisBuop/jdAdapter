package com.jd.jarvisdemonim.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/**
 * Auther: Jarvis Dong (MrDmBuopStudio@163.com)
 * Time: on 2016/11/5 0005:下午 1:17
 * Name:
 * OverView:
 * Usage:
 */
public class AnimationTranstionUtils {
    private static boolean isShowAnim = true;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNormalAnimation(final View view) {
        if (isShowAnim) {
            view.animate()
                    .translationZ(15f).setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.animate().translationZ(1f).setDuration(500).start();
                        }
                    }).start();
        }
    }

}
