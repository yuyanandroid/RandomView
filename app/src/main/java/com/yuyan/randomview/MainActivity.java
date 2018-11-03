package com.yuyan.randomview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RandomFrameLayout randomFrameLayout;
    private TextView tvWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        randomFrameLayout = (RandomFrameLayout) findViewById(R.id.fl_random);
        tvWait = (TextView) findViewById(R.id.tv_wait);
        randomFrameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                randomFrameLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                updateViewValue();
            }
        });
        randomFrameLayout.setOnRemoveListener(new OnRemoveListener() {
            @Override
            public void remove(RandomView randomView) {
                if (randomFrameLayout.getChildCount() == 2) {
                    tvWait.startAnimation(animation());
                    tvWait.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void updateViewValue() {
        for (int i = 0; i < 10; i++) {
            randomFrameLayout.updateView("0.0000" + i);
        }
    }


    private TranslateAnimation animation() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -15, 15);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }
}
