package com.baofeng.mobile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baofeng.mobile.R;
import com.baofeng.mobile.bean.Video.VideoInfo;

/**
 * 影片基本信息模块
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-29.
 */
public class VideoInfoLayout extends LinearLayout implements View.OnClickListener {

    private TextView mTitleText;
    private TextView mCountText;
    private TextView mDescText;

    private TextView mZanView;
    private TextView mShoucangView;
    private TextView mPinglunView;
    private TextView mShareView;

    private View.OnClickListener mListener;

    private VideoInfo mVideo;

    public VideoInfoLayout(Context context) {
        this(context, null);
    }

    public VideoInfoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.fragment_play_video_info, this);

        mTitleText = (TextView) findViewById(R.id.Title);
        mCountText = (TextView) findViewById(R.id.Count);
        mDescText = (TextView) findViewById(R.id.Text);

        initToolBar();
    }

    private void initToolBar() {
        mZanView = (TextView) findViewById(R.id.Zan);
        mShoucangView = (TextView) findViewById(R.id.Shoucang);
        mPinglunView = (TextView) findViewById(R.id.Pinglun);
        mShareView = (TextView) findViewById(R.id.Share);

        mZanView.setOnClickListener(this);
        mShoucangView.setOnClickListener(this);
        mPinglunView.setOnClickListener(this);
        mShareView.setOnClickListener(this);
    }

    public void setOnMenuItemClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    @Override
    protected void onFinishInflate() {

    }

    public void setVideo(VideoInfo video) {
        mVideo = video;

        update();
    }

    public VideoInfo getVideo() {
        return mVideo;
    }

    private void update() {
        if (mVideo == null) {
            mTitleText.setText(null);
            mCountText.setText("播放" + 0);
            mDescText.setText(null);
        } else {
            mTitleText.setText(mVideo.name);
            mCountText.setText("播放" + mVideo.count);
            mDescText.setText(mVideo.desc);
        }
    }

    @Override
    public void onClick(View v) {
        if (mListener != null)
            mListener.onClick(v);
    }
}
