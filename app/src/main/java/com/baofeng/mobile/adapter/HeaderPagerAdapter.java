package com.baofeng.mobile.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.baofeng.mobile.R;
import com.baofeng.mobile.bean.Video;
import com.baofeng.mobile.tools.ToolUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HeaderPagerAdapter<T> extends BaseHeaderPagerAdapter<T> implements OnClickListener{

    public HeaderPagerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public HeaderPagerAdapter(Context context, List<T> data, boolean isInfiniteLoop) {
        super(context, data, isInfiniteLoop);
    }

    @Override
    protected View getView(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ScaleType.CENTER_CROP);
        imageView.setBackgroundResource(R.mipmap.ic_default_video_recommend);

        Video video = (Video) getItem(position);
        if (video != null) {
            Picasso.with(context).load(video.cover).into(imageView);
        }
        imageView.setTag(position);
        imageView.setOnClickListener(this);
        return imageView;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            int position = ToolUtils.parseIntSafely(v.getTag());
            mListener.onItemClick(findTarget(v.getParent()), v, position);
        }
    }

    private ViewPager findTarget(ViewParent parent) {
        if (parent instanceof ViewPager)
            return (ViewPager) parent;
        else
            return findTarget(parent);
    }
}
