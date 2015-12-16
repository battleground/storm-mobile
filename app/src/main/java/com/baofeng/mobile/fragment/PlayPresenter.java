package com.baofeng.mobile.fragment;

import android.os.Handler;

import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.bean.Video;
import com.baofeng.mobile.view.EpisodeLayout;

import java.util.Random;

public class PlayPresenter {

    private Video mVideo;

    private PlayFragment mViewer;

    public void onTakeViewer(PlayFragment view) {
        mViewer = view;
    }

    public void setVideo(Video video) {
        mVideo = video;
    }


    Handler handler = new Handler();

    public void load() {
//        Handler handler = mViewer.getView().getHandler();
        if (handler != null)
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    if (random.nextBoolean()) {

                        Video.VideoInfo info = getVideoInfo(mVideo);
                        mViewer.onShowData(info);
                    } else {
                        mViewer.onError("请确保网络链接可用！");
                    }
                }
            }, 600);
    }

    public Video.VideoInfo getVideoInfo(Video video) {
        Video.VideoInfo info = new Video.VideoInfo(video);
        info.count = (int) (Math.random() * 10000);
        info.desc = "电影描述说明确电影基本信息介绍，有故事有人物，有情节发展...";
        return info;
    }

    public RecyclerViewAdapter getAdapter(ViewHolder.OnRecyclerItemClickListener listener) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return (EpisodeLayout.getTextAdapter(listener));
        } else {
            int resId = R.layout.fragment_play_episode_image_item;
            RecyclerViewAdapter adapter = EpisodeLayout.getImageAdapter(resId, listener);
            return adapter;
        }
    }
}