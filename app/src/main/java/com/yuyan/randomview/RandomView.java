package com.yuyan.randomview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;


public class RandomView extends AppCompatTextView implements View.OnClickListener{
    private OnRemoveListener removeListener;
    public RandomView(Context context) {
        super(context);
        init("测试");
    }

    public RandomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init("測試");
    }

    public RandomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void init(String text) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_img);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        setCompoundDrawables(null, drawable, null, null);
        setCompoundDrawablePadding(-ScreenUtils.dip2px(getContext(), 5));
        setTextColor(Color.WHITE);
        setTextSize(11);
        setGravity(Gravity.CENTER);
        startAnimation(animation());
        setOnClickListener(this);
        super.setText(text);
    }

    private TranslateAnimation animation() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -10, 10);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }


    @Override
    public void onClick(View view) {
        this.clearAnimation();
        this.startAnimation(translateAnimation());
    }

    private TranslateAnimation translateAnimation() {
        setEnabled(false);
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -3000);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((RandomFrameLayout)getParent()).removeView(RandomView.this);
                if (removeListener!=null) removeListener.remove(RandomView.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return animation;
    }

    public void setOnRemoveListener(OnRemoveListener removeListener){
        this.removeListener=removeListener;
    }

}
