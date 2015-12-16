package com.baofeng.mobile.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baofeng.mobile.Constant;
import com.baofeng.mobile.MainActivity;
import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.ViewHolder.OnRecyclerItemChildClickListener;
import com.baofeng.mobile.activity.CategoryActivity;
import com.baofeng.mobile.activity.PlayActivity;
import com.baofeng.mobile.activity.VideoStorageActivity;
import com.baofeng.mobile.adapter.BaseHeaderPagerAdapter;
import com.baofeng.mobile.adapter.HeaderPagerAdapter;
import com.baofeng.mobile.adapter.VideoRecommendAdapter;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.bean.Video;
import com.baofeng.mobile.data.Preset;
import com.baofeng.mobile.tools.ToolUtils;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class VideoMainFragment extends Fragment implements OnRefreshListener,
        ViewHolder.OnRecyclerItemClickListener, OnRecyclerItemChildClickListener,
        BaseHeaderPagerAdapter.OnItemClickListener {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleView;

    private MainActivity mActivity;

    private VideoRecommendAdapter mAdapter;


    private LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRefreshLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.purple);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setEnabled(false);

        mRecycleView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

    private void initAdapter(){
        mAdapter = new VideoRecommendAdapter(mActivity);
        mAdapter.getCollection().update(Preset.getVideoMian());
        mAdapter.setOnPagerItemClickListener(this);
        mAdapter.setOnRecyclerItemClickListener(this);
        mAdapter.setOnRecyclerItemChildClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initAdapter();
        mRecycleView.setAdapter(mAdapter);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {

    }

    private List<Category> list;

    /**
     * @param recyclerView RecyclerView
     * @param itemView     元素根布局
     * @param clickView    响应点击事件的View
     * @param position     the adapter position that the given child view corresponds to.
     */
    @Override
    public void onItemChildClick(RecyclerView recyclerView, View itemView, View clickView, int position) {
        if (list == null) {
            list = Preset.getCategories();
        }
        Category category = null;
        if (position == 0) {
            switch (clickView.getId()) {
                case R.id.movie:
                    category = list.get(0);
                    break;
                case R.id.tv:
                    category = list.get(1);
                    break;
                case R.id.zongyi:
                    category = list.get(2);
                    break;
                case R.id.dongman:
                    category = list.get(3);
                    break;
                case R.id.all:
                    CategoryActivity.launch(mActivity);
                    return;
            }
        } else {
            int categoryid = ToolUtils.parseIntSafely(mAdapter.getCollection().getItem(position).getCategory());
            switch (categoryid) {
                case Constant.CATEGORY_MOVIE:
                    category = list.get(0);
                    break;
                case Constant.CATEGORY_TV:
                    category = list.get(1);
                    break;
                case Constant.CATEGORY_VARIETY:
                    category = list.get(2);
                    break;
                case Constant.CATEGORY_CARTOON:
                    category = list.get(3);
                    break;
            }
        }
        VideoStorageActivity.launch(getActivity(), category);
    }

    /**
     * 横向RecyclerView Item点击事件
     *
     * @param recyclerView RecyclerView
     * @param itemView     元素根布局
     * @param position     the adapter position that the given child view corresponds to.
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        RecyclerViewAdapter<Video> rvAdapter = (RecyclerViewAdapter<Video>) adapter;
        PlayActivity.launch(getActivity(), rvAdapter.getItem(position));
    }

    /**
     * 顶部ViewPager Item点击事件
     *
     * @param pager    ViewPager
     * @param view     元素View
     * @param position
     */
    @Override
    public void onItemClick(ViewPager pager, View view, int position) {
        HeaderPagerAdapter<Video> adapter = (HeaderPagerAdapter<Video>) pager.getAdapter();
        PlayActivity.launch(getActivity(), adapter.getItem(position));
    }

    @Override
    public void onDestroyView() {
        mRecycleView.setLayoutManager(null);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        if (list != null)
            list.clear();
    }
}
