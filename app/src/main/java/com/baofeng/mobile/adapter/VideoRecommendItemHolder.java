package com.baofeng.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baofeng.mobile.R;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.widget.LinearLayoutManager;

/**
 * 影库首页
 * <p/>
 * Created by author:张峻浦
 * on 15-7-28.
 */
public class VideoRecommendItemHolder extends ViewHolder {
    public int width = 0;
    public int height = 0;

    public TextView name;
    public RecyclerView recyclerView;
    public View arrow;

    public VideoRecommendItemAdapter adapter;
    public LinearLayoutManager layoutManager;

    public VideoRecommendItemHolder(View itemLayoutView, OnRecyclerItemClickListener listener, OnRecyclerItemChildClickListener childListener) {
        super(itemLayoutView, listener, childListener);
        width = itemLayoutView.getResources().getDimensionPixelOffset(R.dimen.video_storage_cover_width);
        height = itemLayoutView.getResources().getDimensionPixelOffset(R.dimen.video_storage_cover_height);
    }

    @Override
    public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
        name = (TextView) itemLayoutView.findViewById(R.id.item_name);
        recyclerView = (RecyclerView) itemLayoutView.findViewById(R.id.recyclerview_item);
        arrow = itemLayoutView.findViewById(R.id.item_arrow);
        itemLayoutView.setOnClickListener(this);
    }
}
