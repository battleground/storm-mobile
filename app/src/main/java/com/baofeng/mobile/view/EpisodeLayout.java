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

import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.data.Preset;
import com.baofeng.mobile.widget.LinearLayoutManager;

import java.util.List;

/**
 * 选集模块
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-29.
 */
public class EpisodeLayout extends LinearLayout {

    private TextView mTitleView;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter<Category> mAdapter;

    public EpisodeLayout(Context context) {
        this(context, null);
    }

    public EpisodeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EpisodeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.fragment_play_episode, this);

        mTitleView = (TextView) findViewById(R.id.Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
        });
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    public RecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(RecyclerViewAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setTitle(String title){
        mTitleView.setText(title);
    }

    public void setCloseable(boolean enable){
        findViewById(R.id.All).setVisibility(enable ? View.GONE : View.INVISIBLE);
        findViewById(R.id.Close).setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public static RecyclerViewAdapter getTextAdapter(final ViewHolder.OnRecyclerItemClickListener listener) {
        RecyclerViewAdapter<Category> adapter = new RecyclerViewAdapter<Category>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_play_episode_item, parent, false);
                return new EpisodeViewHolder(v, listener);
            }

            @Override
            public void onBindViewHolder(ViewHolder h, int position) {
                Category category = getItem(position);
                EpisodeViewHolder holder = (EpisodeViewHolder) h;
                holder.mNameTextView.setText(category.id);
            }
        };

        List<Category> list = Preset.getCategories();
        adapter.getCollection().update(list);
        return adapter;
    }

    public static RecyclerViewAdapter getImageAdapter(final int resId, final ViewHolder.OnRecyclerItemClickListener listener) {
        RecyclerViewAdapter<Category> adapter = new RecyclerViewAdapter<Category>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(resId, parent, false);
                return new EpisodeImageViewHolder(v, listener);
            }

            @Override
            public void onBindViewHolder(ViewHolder h, int position) {
                Category category = getItem(position);
                EpisodeImageViewHolder holder = (EpisodeImageViewHolder) h;
                holder.mNameTextView.setText(category.name);
            }
        };

        List<Category> list = Preset.getCategories();
        adapter.getCollection().update(list);
        return adapter;
    }

    private static class EpisodeViewHolder extends ViewHolder {

        public TextView mNameTextView;
        public ImageView mIconImageView;

        public EpisodeViewHolder(View itemLayoutView, OnRecyclerItemClickListener listener) {
            super(itemLayoutView, listener);
        }

        @Override
        public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
            mNameTextView = (TextView) itemLayoutView.findViewById(R.id.Name);
            mIconImageView = (ImageView) itemLayoutView.findViewById(R.id.isNew);
        }

        public void show() {
            mIconImageView.setVisibility(View.VISIBLE);
        }

        public void hide() {
            mIconImageView.setVisibility(View.INVISIBLE);
        }
    }

    private static class EpisodeImageViewHolder extends ViewHolder {

        public TextView mNameTextView;
        public ImageView mIconImageView;

        public EpisodeImageViewHolder(View itemLayoutView, OnRecyclerItemClickListener listener) {
            super(itemLayoutView, listener);
        }

        @Override
        public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
            mNameTextView = (TextView) itemLayoutView.findViewById(R.id.Name);
            mIconImageView = (ImageView) itemLayoutView.findViewById(R.id.ImageView);
        }

    }
}
