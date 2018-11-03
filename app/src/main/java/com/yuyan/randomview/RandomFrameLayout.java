package com.yuyan.randomview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RandomFrameLayout extends FrameLayout {
    private List<TagViewBean> tagViews;
    private TagViewBean tagBean;
    private MyHandler mHandler = new MyHandler();
    private int term, termX, termY;
    private List<RandomView> textList;
    private OnRemoveListener onRemoveListener;

    public RandomFrameLayout(Context context) {
        super(context);
    }

    public RandomFrameLayout(Context context, AttributeSet attributes) {
        super(context, attributes);
        tagViews = Collections.synchronizedList(new ArrayList<TagViewBean>());
        textList = new ArrayList<>();
        termX = 50;
        termY = 50;
    }

    public RandomFrameLayout(Context context, AttributeSet attributeSet, int styles) {
        super(context, attributeSet, styles);
    }

    public void setOnRemoveListener(OnRemoveListener onRemoveListener) {
        this.onRemoveListener = onRemoveListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void updateView(String value) {
        initXY(value);
    }

    private void initXY(final String value) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (tagViews) {
                    int x = new Random().nextInt(getWidth() - 180);
                    int y = new Random().nextInt(getHeight() - 180);
                    while (!isContains(x, y)) {
                        x = new Random().nextInt(getWidth() - 180);
                        y = new Random().nextInt(getHeight() - 180);
                    }
                    tagBean = new TagViewBean();
                    tagBean.setX(x);
                    tagBean.setY(y);
                    tagViews.add(tagBean);
                    Message message = Message.obtain();
                    message.obj = value;
                    message.what = 0;
                    message.arg1 = x;
                    message.arg2 = y;
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    private boolean isContains(int x, int y) {
        if (x < termX || y < termY) {
            return false;
        }
        for (int i = 0; i < tagViews.size(); i++) {
            try {
                if (tagViews.get(i).getX() < x + 180 && tagViews.get(i).getX() > x - 180) {
                    if (tagViews.get(i).getY() < y + 180 && tagViews.get(i).getY() > y - 180) {
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RandomView textView = new RandomView(getContext());
            textView.setText(msg.obj.toString());
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = msg.arg1;
            params.topMargin = msg.arg2;
            addView(textView, params);
            textList.add(textView);
            textView.setOnRemoveListener(new OnRemoveListener() {
                @Override
                public void remove(RandomView randomView) {
                    if (onRemoveListener != null)
                        onRemoveListener.remove(randomView);
                }
            });
        }
    }


}
