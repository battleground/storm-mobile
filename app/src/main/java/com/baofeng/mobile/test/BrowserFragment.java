package com.baofeng.mobile.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.abooc.common.Log;
import com.abooc.common.Toast;
import com.abooc.emptyview.EmptyView;
import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.android.AppApplication;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.Picasso;


/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-23.
 */
public class BrowserFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ViewHolder.OnRecyclerItemClickListener {

    private static BrowserPresenter mPresenter;
    private EmptyView mEmptyView;

    private RecyclerViewAdapter<Image> mAdapter;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    private GridLayoutManager mManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private final int PAGE_SIZE = 50;

    public BrowserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPresenter = new BrowserPresenter();

        mAdapter = new RecyclerViewAdapter<Image>(){
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerview_item, parent, false);
                return new DevViewHolder(v, BrowserFragment.this);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                Image image = getItem(position);
                DevViewHolder devViewHolder = (DevViewHolder)holder;
                devViewHolder.mTitleTextView.setText(image.column + image.picture_id);
                Picasso.with(holder.getContext()).load(image.thumb_url).into(devViewHolder.mImageView);
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dev, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPresenter.onTakeView(this);
        mSwipeRefreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout));
        mSwipeRefreshLayout.setColorSchemeColors(R.color.red, R.color.orange, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mEmptyView = (EmptyView) view.findViewById(R.id.EmptyView);
        mEmptyView.hide();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        mManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(OnScrollListener);

        if(mAdapter.isEmpty()){
            mPresenter.load(1, 10);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        Toast.show("position:" + position);
    }

    public void hideEmptyView() {
        mEmptyView.hide();
    }

    /**
     * 数据加载出错时被调用。(网络不可用、数据解析出错等)
     *
     * @param message
     */
    public void onError(String message) {
        if (mAdapter.isEmpty()) {
            mEmptyView.setMessage(message);
        } else {
            mEmptyView.hide();
            showRetry(message);
        }
    }

    /**
     * 显示‘加载’效果
     */
    public void onLoading() {
        if (mAdapter.isEmpty()) {
            mEmptyView.show();
            mEmptyView.loading();
        } else {
//          其它处理
        }
    }

    /**
     * 下拉刷新事件
     */
    @Override
    public void onRefresh() {
        mAdapter.getCollection().update(null);
        mAdapter.notifyDataSetChanged();
        mPresenter.load(1, 10);

        new Handler().postDelayed(DismissRefresh, 500);
    }

    private Runnable DismissRefresh = new Runnable() {
        @Override
        public void run() {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    public Handler getUIHandler() {
        return getView().getHandler();
    }

    private RecyclerView.OnScrollListener OnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            visibleItemCount = mManager.getChildCount();
            totalItemCount = mManager.getItemCount();
            pastVisibleItems = mManager.findFirstVisibleItemPosition();

            if (!mPresenter.isLoading()) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    int page = (mAdapter.getCollection().getCount() + 1) / PAGE_SIZE;
                    mPresenter.load(page, PAGE_SIZE);
                }
            }
        }
    };

    private void showRetry(String message) {
        if (!isDetached()) {
            Snackbar
                    .make(getView(), message, Snackbar.LENGTH_SHORT)
                    .setAction("重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPresenter.load(1, 10);
                        }
                    })
                    .show();
        }
    }

    public void onAttachData(Image[] list) {
        if (!isDetached()) {
            mAdapter.getCollection().add(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        Log.anchor();
        mPresenter.cancel();
        mPresenter.onTakeView(null);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.anchor();
        super.onDestroy();

        RefWatcher refWatcher = AppApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
