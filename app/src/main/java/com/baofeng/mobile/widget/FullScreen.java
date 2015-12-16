package com.baofeng.mobile.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abooc.common.Log;
import com.baofeng.mobile.R;
import com.baofeng.mobile.tools.ToolUtils;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-31.
 */
public class FullScreen extends LinearLayout implements View.OnClickListener,
        View.OnSystemUiVisibilityChangeListener {

    private TextView mFullScreenText;
    private TextView mNameText;
    int mLastSystemUiVis;
    private boolean isFullScreen;

    public FullScreen(Context context) {
        this(context, null);
    }

    public FullScreen(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullScreen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.player, this);
        setPadding(4, 4, 4, 4);
        setBackgroundColor(Color.RED);
        setOnSystemUiVisibilityChangeListener(this);
        setOnClickListener(this);
    }

    @Override
    protected void onFinishInflate() {
        mFullScreenText = (TextView) findViewById(R.id.FullScreen);
        mNameText = (TextView) findViewById(R.id.Name);
    }

    public boolean isLandscape() {
        return getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setOnFullScreenEvent(View.OnClickListener listener) {
        mFullScreenText.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        setNavVisibility(true);
    }

    public void enterFullScreen() {
        isFullScreen = true;

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        mFullScreenText.setText("退出全屏");

        setNavVisibility(false);
        setKeepScreenOn(true);
    }

    public void exitFullScreen() {
        isFullScreen = false;

        int height = ToolUtils.dp2px(getContext(), 200);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
        setLayoutParams(params);
        mFullScreenText.setText("全屏");

        setNavVisibility(true);
        setKeepScreenOn(false);
    }

    public void setVideo(String video) {
        mNameText.setText(video);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        Log.anchor();

        int diff = mLastSystemUiVis ^ visibility;
        mLastSystemUiVis = visibility;
        if ((diff & SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0
                && (visibility & SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
            setNavVisibility(true);
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        Log.anchor();
        super.onWindowVisibilityChanged(visibility);
        setNavVisibility(true);
    }

    private Runnable mNavHider = new Runnable() {
        @Override
        public void run() {
            setNavVisibility(false);
        }
    };

    public void setNavVisibility(boolean visible) {
        int newVis = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (!visible) {
            newVis |= SYSTEM_UI_FLAG_LOW_PROFILE | SYSTEM_UI_FLAG_FULLSCREEN
                    | SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // If we are now visible, schedule a timer for us to go invisible.
        if (visible) {
            Handler h = getHandler();
            if (h != null) {
                h.removeCallbacks(mNavHider);
                if (isFullScreen()) {
                    h.postDelayed(mNavHider, 1500);
                }
            }
        }

        // Set the new desired visibility.
        setSystemUiVisibility(newVis);
    }
}
