package com.shy.beautiful.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.shy.beautiful.R;


public class Indcator extends LinearLayout {

    private int dashGap;
    private int position;
    private ViewPager viewPager;
    private int slider_width;
    private int slider_height;
    private int sliderAlign;

    public Indcator(Context context) {
        super(context);
    }

    public Indcator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);//设置水平布局
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        //间隙
        dashGap = (int) array.getDimension(R.styleable.Indicator_gap,3.0f);
        //宽
        slider_width = (int) array.getDimension(R.styleable.Indicator_slider_width,
                10.0f);
        //高
        slider_height = (int) array.getDimension(R.styleable.Indicator_slider_height, 4.0f);
        //变化方向
        sliderAlign = array.getInt(R.styleable.Indicator_sleider_align, 1);
        array.recycle();
    }

    public Indcator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);//设置水平布局
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.Indicator);
        //间隙
        dashGap = (int) array.getDimension(R.styleable.Indicator_gap,3.0f);
        //宽
        slider_width = (int) array.getDimension(R.styleable.Indicator_slider_width,
                10.0f);
        //高
        slider_height = (int) array.getDimension(R.styleable.Indicator_slider_height, 4.0f);
        //变化方向
        sliderAlign = array.getInt(R.styleable.Indicator_sleider_align, 1);
        array.recycle();
    }

    //和viewpager联动,根据viewpager页面动态生成相应的小圆点
    public void setUpWidthViewPager(ViewPager viewPager) {
        //removeAllViews();
        //为空判断，或者<2的时候 也不需要指示器
        if (viewPager == null || viewPager.getAdapter()==null||viewPager.getAdapter().getCount() < 2) {
            return;
        }
        position = 0;
        this.viewPager = viewPager;
        Log.e("viewPager.getCount()",""+viewPager.getAdapter().getCount());
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            //传入1  (LEFT)
            Pointer pointer = new Pointer(getContext(), sliderAlign);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(slider_width, slider_height);
            //设置小圆点之间的距离
            if (i > 0) {
                layoutParams.setMargins(dashGap, 0, 0, 0);
                pointer.setAlpha(0.4f);//设置透明度
            } else {
                layoutParams.setMargins(0, 0, 0, 0);
                pointer.setAlpha(1);
            }
            pointer.setLayoutParams(layoutParams);
            addView(pointer);
            //默认第一个指示器增大
            setLarge(0);
        }
        //根据viewpager页面切换事件，设置指示器大小变化
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (Indcator.this.position != position) {
                    //传入需要变化的圆点的position
                    resetSize(Indcator.this.position, position);
                    Indcator.this.position = position;
                }
            }
        });
    }
    //重置指示器样式
    private void resetSize(int last, int current) {
        //增大目前选中的page对应的圆点
        setLarge(current);
        //缩小上一个大圆点
        setSmall(last);
    }

    //指示器增大同时设置透明度变化
    private void setLarge(int position) {
        if (getChildAt(position) instanceof Pointer) {
            AnimatorSet set = new AnimatorSet();
            //设置放大动画
            ValueAnimator animator = getEnlarge((Pointer) getChildAt(position));
            //设置透明度动画
            ValueAnimator alpha = ObjectAnimator.ofFloat(getChildAt(position), "alpha", 0.4f, 1f);
            set.play(animator).with(alpha);
            set.setDuration(500);
            set.start();
        }
    }

    //放大动画
    private ValueAnimator getEnlarge(Pointer pointer) {
        return ObjectAnimator.ofFloat(pointer,
                "rectWidth",
                0, getOffset(pointer));
    }

    //根据大小变化方向获取指示器大小偏移量
    private int getOffset(Pointer Pointer) {
        int offsest = 0;
        Log.e("getLoction()",""+Pointer.getLocation());
        switch (Pointer.getLocation()) {
            case com.shy.beautiful.view.Pointer.CENTER:
                offsest = (slider_width - slider_height) / 2;
                break;
            case com.shy.beautiful.view.Pointer.LEFT:
                offsest = slider_width - slider_height;
                break;
            case com.shy.beautiful.view.Pointer.RIGHT:
                offsest = slider_width - slider_height;
                break;
        }
        return offsest;
    }

    //缩小动画
    private ValueAnimator getShrink(Pointer pointer) {
        return ObjectAnimator.ofFloat(pointer,
                "rectWidth",
                getOffset(pointer), 0);
    }

    //缩小动画同时伴随透明度变化
    public void setSmall(int small) {
        if (getChildAt(small) instanceof Pointer) {
            AnimatorSet set = new AnimatorSet();
            ValueAnimator alpha = ObjectAnimator.ofFloat(getChildAt(position), "alpha", 1, 0.4f);
            ValueAnimator animator = getShrink((Pointer) getChildAt(small));
            set.play(animator).with(alpha);
            set.setDuration(618);
            set.start();
        }
    }
}
