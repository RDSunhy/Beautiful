package com.shy.beautiful.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Shy on 2019/3/30.
 */

public class ScalePageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        /**
         * 当前page 左上角坐标设0 右上角设1
         *      向左滑:
         *          左上角由0->-1
         *      向右滑:
         *          左上角由0->1
         * 由此可得 需要处理[-1,1]区间的逻辑判断
         */
        if (position < -1.0f) {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        } else if (position <= 0.0f) {
            view.setAlpha(1.0f);
            view.setTranslationX(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        } else if (position <= 1.0f) {
            view.setAlpha(1.0f - position);
            view.setTranslationX(-view.getWidth() * position);
            float scale = MIN_SCALE + (1.0f - MIN_SCALE) * (1.0f - position);
            view.setScaleX(scale);
            view.setScaleY(scale);
        } else {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        }
    }
}