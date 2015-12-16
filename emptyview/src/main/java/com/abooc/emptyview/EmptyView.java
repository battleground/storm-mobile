package com.abooc.emptyview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-27.
 */
public class EmptyView extends FrameLayout {

    private TextView mMessageText;
    private View mProgressBar;
    private String mMessageContent;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.emptyview, this);

        mMessageText = (TextView) findViewById(R.id.MessageText);
        mProgressBar = findViewById(R.id.ProgressBar);
    }

    public String getMessage(){
        return mMessageContent;
    }

    public boolean isVisible(){
        return getVisibility() == View.VISIBLE;
    }

    public void hide(){
        if(getVisibility() == View.VISIBLE){
            setVisibility(View.INVISIBLE);
        }
    }

    public void show(){
        if(getVisibility() != View.VISIBLE){
            setVisibility(View.VISIBLE);
        }
    }

    public void loading() {
        mUI = UI.LOADING;
        update();
    }

    public void setMessage(String message) {
        mUI = UI.MESSAGE;
        mMessageContent = message;
        update();
    }

    private void update() {
        switch (mUI) {
            case LOADING:
                mMessageText.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            case MESSAGE:
                mProgressBar.setVisibility(View.INVISIBLE);
                mMessageText.setText(mMessageContent);
                mMessageText.setVisibility(View.VISIBLE);
                break;
        }
    }

    private UI mUI;

    private enum UI {
        LOADING,
        MESSAGE;
    }

}
