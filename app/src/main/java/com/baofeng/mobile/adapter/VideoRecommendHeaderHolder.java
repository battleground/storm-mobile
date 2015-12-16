package com.baofeng.mobile.adapter;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.baofeng.mobile.R;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.bean.Video;
import com.baofeng.mobile.widget.VideoRecommendHeaderIndicator;

/**
 * 影库首页
 * <p/>
 * Created by author:张峻浦
 * on 15-7-28.
 */
public class VideoRecommendHeaderHolder extends ViewHolder {

    public ViewPager viewPager;
    public VideoRecommendHeaderIndicator indicator;
    public HeaderPagerAdapter<Video> adapter;

    public TextView movie;
    public TextView tv;
    public TextView zongyi;
    public TextView dongman;
    public TextView all;

    public VideoRecommendHeaderHolder(View itemLayoutView, OnRecyclerItemChildClickListener childListener) {
        super(itemLayoutView, null, childListener);
    }

    @Override
    public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
        viewPager = (ViewPager) itemLayoutView.findViewById(R.id.header_ViewPager);
        indicator = (VideoRecommendHeaderIndicator) itemLayoutView.findViewById(R.id.header_indicator);
        movie = (TextView) itemLayoutView.findViewById(R.id.movie);
        tv = (TextView) itemLayoutView.findViewById(R.id.tv);
        zongyi = (TextView) itemLayoutView.findViewById(R.id.zongyi);
        dongman = (TextView) itemLayoutView.findViewById(R.id.dongman);
        all = (TextView) itemLayoutView.findViewById(R.id.all);
        movie.setOnClickListener(this);
        tv.setOnClickListener(this);
        zongyi.setOnClickListener(this);
        dongman.setOnClickListener(this);
        all.setOnClickListener(this);
    }

}
