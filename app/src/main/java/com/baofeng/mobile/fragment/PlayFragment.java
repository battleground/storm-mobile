package com.baofeng.mobile.fragment;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.abooc.common.Log;
import com.abooc.emptyview.EmptyView;
import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.activity.PlayActivity;
import com.baofeng.mobile.android.AppApplication;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.bean.Video;
import com.baofeng.mobile.tools.ToolUtils;
import com.baofeng.mobile.view.CommentLayout;
import com.baofeng.mobile.view.EpisodeLayout;
import com.baofeng.mobile.view.StillsLayout;
import com.baofeng.mobile.view.VideoInfoLayout;
import com.baofeng.mobile.view.VideoMakerLayout;
import com.baofeng.mobile.widget.FullScreen;
import com.baofeng.mobile.widget.SlidDialog;

/**
 * 影片播放页面
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-29.
 */
public class PlayFragment extends Fragment implements ViewHolder.OnRecyclerItemClickListener,
        View.OnTouchListener, View.OnClickListener {

    private ScrollView mScrollView;
    private FrameLayout mContentPanel;
    private EmptyView mEmptyView;

    private FullScreen iFullScreen;
    private VideoInfoLayout iVideoInfoLayout;
    private VideoMakerLayout iVideoMakerLayout;
    private EpisodeLayout iEpisodeLayout;
    private StillsLayout iStillsLayout;
    private CommentLayout iCommentLayout;
    private EmptyView mLoadMoreView;

    private static PlayPresenter mPresenter;

    public PlayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PlayPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPresenter.onTakeViewer(this);
        mPresenter.load();

        mScrollView = (ScrollView) view.findViewById(R.id.ScrollView);
        mScrollView.setSmoothScrollingEnabled(true);
        mContentPanel = (FrameLayout) view.findViewById(R.id.contentPanel);
        mEmptyView = (EmptyView) view.findViewById(R.id.EmptyView);
        mEmptyView.setOnTouchListener(this);

        iFullScreen = (FullScreen) view.findViewById(R.id.PlayerLayout);
        iFullScreen.setOnFullScreenEvent(this);
        iVideoInfoLayout = (VideoInfoLayout) view.findViewById(R.id.VideoInfoLayout);
        iVideoInfoLayout.setOnMenuItemClickListener(this);
        iVideoMakerLayout = (VideoMakerLayout) view.findViewById(R.id.VideoMakerLayout);
        iVideoMakerLayout.setOnChildViewClickListener(this);

        iEpisodeLayout = (EpisodeLayout) view.findViewById(R.id.EpisodeLayout);
        iEpisodeLayout.findViewById(R.id.All).setOnClickListener(this);
        iStillsLayout = (StillsLayout) view.findViewById(R.id.StillsLayout);
        iCommentLayout = (CommentLayout) view.findViewById(R.id.CommentLayout);
        mLoadMoreView = (EmptyView) view.findViewById(R.id.LoadMore);
        mLoadMoreView.setOnClickListener(this);
        mLoadMoreView.setMessage("加载更多...");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && mEmptyView.isVisible()) {
            mEmptyView.loading();
            mPresenter.load();
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mContentPanel.setVisibility(View.GONE);
            iFullScreen.enterFullScreen();
        } else {
            iFullScreen.exitFullScreen();
            mContentPanel.setVisibility(View.VISIBLE);
        }
    }

    public void setVideo(Video video) {
        mPresenter.setVideo(video);
    }

    public void onError(String error) {
        mEmptyView.setMessage(error);
    }

    public void onShowData(Video.VideoInfo info) {
        mEmptyView.hide();

        iVideoInfoLayout.setVideo(info);
        iEpisodeLayout.setAdapter(mPresenter.getAdapter(this));
        iStillsLayout.setData();
        iCommentLayout.setData();
    }

    /**
     * 全屏事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FullScreen:
                if (iFullScreen.isFullScreen()) {
                    ((PlayActivity) getActivity()).requestOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                }
                break;
            case R.id.LoadMore:
                mLoadMoreView.loading();
                mLoadMoreView.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoadMoreView.setMessage("已加载全部!");
                    }
                }, 600);
                break;
            case R.id.Zan:
                break;
            case R.id.Shoucang:
                break;
            case R.id.Pinglun:
                break;
            case R.id.Share:
                break;
            case R.id.Avatar:
                break;
            case R.id.Guanzhu:
                break;
            case R.id.All:
                if (mSlidDialog == null) {
                    mSlidDialog = createDialog();
                }
                if(mSlidDialog.isShowing()){
                    mSlidDialog.dismiss();
                }else{
                    mScrollView.smoothScrollTo(0, 0);
                    mSlidDialog.show();
                }
                break;
        }
    }

    private SlidDialog createDialog() {
        SlidDialog dialog = new SlidDialog(getActivity(), R.style.DialogSlideAnim);
        int height = ToolUtils.dp2px(getActivity(), 400);
        EpisodeLayout episodeLayout = new EpisodeLayout(getActivity());
        episodeLayout.setCloseable(true);
        episodeLayout.findViewById(R.id.Close).setOnClickListener(this);
        episodeLayout.setBackgroundColor(Color.WHITE);
        episodeLayout.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecyclerViewAdapter adapter = EpisodeLayout.getImageAdapter(R.layout.fragment_play_episode_image_horizontal_item, this);
        episodeLayout.setAdapter(adapter);
        dialog.setContentView(episodeLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        return dialog;
    }

    private SlidDialog mSlidDialog;

    /**
     * 切换选集事件
     *
     * @param recyclerView RecyclerView
     * @param itemView     元素根布局
     * @param position     the adapter position that the given child view corresponds to.
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        RecyclerViewAdapter<Category> adapter = (RecyclerViewAdapter<Category>) recyclerView.getAdapter();
        Category category = adapter.getItem(position);
        iFullScreen.setVideo(category.name);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.anchor("停止");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onTakeViewer(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppApplication.getRefWatcher(getActivity()).watch(this);
    }

}
