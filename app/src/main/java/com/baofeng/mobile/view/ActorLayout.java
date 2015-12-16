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
 * 演员模块
 *
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-29.
 */
public class ActorLayout extends LinearLayout implements ViewHolder.OnRecyclerItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter<Category> mAdapter;

    public ActorLayout(Context context) {
        this(context, null);
    }

    public ActorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.fragment_play_actor, this);

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
                        .inflate(R.layout.fragment_play_actor_item, parent, false);
                return new ActorViewHolder(v, ActorLayout.this);
            }

            @Override
            public void onBindViewHolder(ViewHolder h, int position) {
                Category category = getItem(position);
                ActorViewHolder holder = (ActorViewHolder) h;
            }
        };
        List<Category> list = Preset.getCategories();
        mAdapter.getCollection().update(list);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class ActorViewHolder extends ViewHolder {

        public TextView mNameTextView;
        public ImageView mIconImageView;

        public ActorViewHolder(View itemLayoutView, OnRecyclerItemClickListener listener) {
            super(itemLayoutView, listener);
        }

        @Override
        public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
            mNameTextView = (TextView) itemLayoutView.findViewById(R.id.Name);
            mIconImageView = (ImageView) itemLayoutView.findViewById(R.id.Icon);
        }

    }
}
