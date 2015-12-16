package com.baofeng.mobile.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abooc.common.Toast;
import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.data.Preset;
import com.baofeng.mobile.widget.LinearLayoutManager;

import java.util.List;

/**
 *
 * 剧照模块
 *
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-29.
 */
public class StillsLayout extends LinearLayout  implements ViewHolder.OnRecyclerItemClickListener {

    private TextView mTitleText;
    private TextView mCountText;
    private TextView mDescText;

    private RecyclerViewAdapter<Category> mAdapter;
    private RecyclerView mRecyclerView;

    public StillsLayout(Context context) {
        this(context, null);
    }

    public StillsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StillsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.fragment_play_stills, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    protected void onFinishInflate() {

    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        Toast.show(mAdapter.getItem(position).name);
    }

    public void setData() {
        mAdapter = new RecyclerViewAdapter<Category>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_play_stills_item, parent, false);
                return new StillsViewHolder(v, StillsLayout.this);
            }

            @Override
            public void onBindViewHolder(ViewHolder h, int position) {
                Category category = getItem(position);
                StillsViewHolder holder = (StillsViewHolder) h;
            }
        };
        List<Category> list = Preset.getCategories();
        mAdapter.getCollection().update(list);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class StillsViewHolder extends ViewHolder {

        public ImageView mIconImageView;

        public StillsViewHolder(View itemLayoutView, OnRecyclerItemClickListener listener) {
            super(itemLayoutView, listener);
        }

        @Override
        public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
            mIconImageView = (ImageView) itemLayoutView.findViewById(R.id.ImageView);
        }

    }

    class Still{
        String id;
        String url;
    }
}
