package com.baofeng.mobile.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baofeng.mobile.R;
import com.baofeng.mobile.ViewHolder;

/**
 * 影库列表
 * <p/>
 * Created by author:张峻浦
 * on 15-7-28.
 */
public class VideoViewHolder extends ViewHolder {
    public int width = 0;
    public int height = 0;

    public ImageView cover;
    public TextView name;

    public VideoViewHolder(View itemLayoutView, OnRecyclerItemClickListener listener) {
        super(itemLayoutView, listener);
        width = itemLayoutView.getResources().getDimensionPixelOffset(R.dimen.video_storage_cover_width);
        height = itemLayoutView.getResources().getDimensionPixelOffset(R.dimen.video_storage_cover_height);
    }

    @Override
    public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
        cover = (ImageView) itemLayoutView.findViewById(R.id.cover);
        name = (TextView) itemLayoutView.findViewById(R.id.name);
    }
}
