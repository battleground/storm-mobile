package com.baofeng.mobile.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baofeng.mobile.R;
import com.baofeng.mobile.ViewHolder;


/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-28.
 */
public class CategoryViewHolder extends ViewHolder {

    public TextView mNameTextView;
    public TextView mCountTextView;
    public ImageView mIconImageView;

    public CategoryViewHolder(View itemLayoutView, OnRecyclerItemClickListener listener) {
        super(itemLayoutView, listener);
    }

    @Override
    public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
        mNameTextView = (TextView) itemLayoutView.findViewById(R.id.Name);
        mCountTextView = (TextView) itemLayoutView.findViewById(R.id.Count);
        mIconImageView = (ImageView) itemLayoutView.findViewById(R.id.ImageView);
    }

    public void setIcon(String resId) {
        int id = getContext().getResources().getIdentifier(resId, "drawable/", getContext().getPackageName());
        mIconImageView.setImageResource(id);
    }
}