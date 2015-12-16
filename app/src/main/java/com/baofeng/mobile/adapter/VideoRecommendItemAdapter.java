package com.baofeng.mobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baofeng.mobile.R;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.bean.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 影库首页非顶置项RecyclerView.Adapter
 * @author zhangjunpu
 * @date 15/7/29
 */
public class VideoRecommendItemAdapter extends BaseRecyclerAdapter<Video> {

    public VideoRecommendItemAdapter(List<Video> list) {
        getCollection().update(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_recommend_normal_item, parent, false);
        return new VideoViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoViewHolder holder1 = (VideoViewHolder) holder;
        Video video = getItem(position);
        Picasso.with(holder.getContext()).load(video.cover).resize(holder1.width, holder1.height).into(holder1.cover);
        holder1.name.setText(video.name);
    }

}
