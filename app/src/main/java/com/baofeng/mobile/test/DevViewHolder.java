package com.baofeng.mobile.test;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baofeng.mobile.R;
import com.baofeng.mobile.ViewHolder;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-23.
 */
public class DevViewHolder extends ViewHolder {

    public TextView mTitleTextView;
    public ImageView mImageView;
    public ImageView mIconImageView;

    public DevViewHolder(View itemLayoutView, OnRecyclerItemClickListener listener) {
        super(itemLayoutView, listener);
    }

    @Override
    public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
        mTitleTextView = (TextView) itemLayoutView.findViewById(R.id.Text);
        mImageView = (ImageView) itemLayoutView.findViewById(R.id.ImageView);
        mIconImageView = (ImageView) itemLayoutView.findViewById(R.id.Icon);
        mTitleTextView.setOnClickListener(this);
        mIconImageView.setOnClickListener(this);
    }
}
