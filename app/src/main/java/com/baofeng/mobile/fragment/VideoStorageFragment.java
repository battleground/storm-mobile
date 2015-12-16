package com.baofeng.mobile.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.activity.PlayActivity;
import com.baofeng.mobile.activity.VideoStorageActivity;
import com.baofeng.mobile.adapter.VideoViewHolder;
import com.baofeng.mobile.android.AppApplication;
import com.baofeng.mobile.bean.Area;
import com.baofeng.mobile.bean.Base;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.bean.Tag;
import com.baofeng.mobile.bean.Video;
import com.baofeng.mobile.bean.Year;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 影库
 */
public class VideoStorageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ViewHolder.OnRecyclerItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter<Video> mAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    private VideoStoragePresenter mPrecenter;

    private VideoStorageActivity mActivity;

    public static VideoStorageFragment newInstance() {
        VideoStorageFragment fragment = new VideoStorageFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (VideoStorageActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RecyclerViewAdapter<Video>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.video_storage_item, parent, false);
                return new VideoViewHolder(v, VideoStorageFragment.this);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                VideoViewHolder holder1 = (VideoViewHolder) holder;
                Video video = getItem(position);
                Picasso.with(holder.getContext()).load(video.cover).resize(holder1.width, holder1.height).into(holder1.cover);
                holder1.name.setText(video.name);
            }

        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_storage, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_videostorage);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRefreshLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.purple);
        mRefreshLayout.setOnRefreshListener(this);

        mPrecenter = new VideoStoragePresenter(this);
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        RecyclerViewAdapter<Video> adapter = (RecyclerViewAdapter<Video>) recyclerView.getAdapter();
        PlayActivity.launch(getActivity(), adapter.getItem(position));
    }

    //===========================================

    /**
     * 设置加载的影片类型
     *
     * @param category
     */
    public void setCategory(Category category) {
        mPrecenter.updateCategory(category);
    }

    @Override
    public void onRefresh() {
        mPrecenter.load(1, 0);
    }

    /**
     * 结束刷新
     */
    public void onCompleteRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 更新界面数据 - 刷新
     *
     * @param list
     */
    public void update(List<Video> list) {
        if (mAdapter != null) {
            mRecyclerView.scrollToPosition(0);
            mAdapter.getCollection().update(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 更新界面数据 - 添加
     *
     * @param list
     */
    public void add(List<Video> list) {
        if (mAdapter != null) {
            mAdapter.getCollection().add(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置筛选条件
     */
    public void setFilter(Base sort, Tag type, Area area, Year year) {
        mPrecenter.updateFilter(sort, type, area, year);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppApplication.getRefWatcher(getActivity()).watch(this);
    }

}
