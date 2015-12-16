package com.baofeng.mobile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baofeng.mobile.R;

/**
 * 影片基本信息模块
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-29.
 */
public class VideoMakerLayout extends LinearLayout implements View.OnClickListener {

    private ImageView mAvatarView;
    private TextView mNicknameText;
    private TextView mSignText;


    private OnClickListener mListener;

    private User mUser;

    public VideoMakerLayout(Context context) {
        this(context, null);
    }

    public VideoMakerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoMakerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.fragment_play_video_maker, this);

        mAvatarView = (ImageView) findViewById(R.id.Avatar);
        mNicknameText = (TextView) findViewById(R.id.Nickname);
        mSignText = (TextView) findViewById(R.id.Sign);

    }

    public void setOnChildViewClickListener(OnClickListener listener) {
        mListener = listener;
    }

    @Override
    protected void onFinishInflate() {

    }

    public void setUser(User user) {
        mUser = user;

        update();
    }

    public User getUser() {
        return mUser;
    }

    private void update() {
        if (mUser != null) {
            mAvatarView.setImageResource(0);
            mNicknameText.setText(mUser.nickname);
            mSignText.setText(mUser.sign);
        }
    }

    @Override
    public void onClick(View v) {
        if (mListener != null)
            mListener.onClick(v);
    }

    class User {
        String nickname;
        String url;
        String sign;
    }
}
