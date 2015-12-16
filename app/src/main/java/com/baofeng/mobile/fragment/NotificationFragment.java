package com.baofeng.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abooc.common.Log;
import com.abooc.common.Toast;
import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.data.Preset;

import java.util.List;


/**
 * 通知页面
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-8-13.
 */
public class NotificationFragment extends Fragment implements ViewHolder.OnRecyclerItemChildClickListener {

    private RecyclerViewAdapter<Category> mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager manager = new LinearLayoutManager(getActivity());

    public NotificationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.anchor();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(manager);
        if (mAdapter == null) {
            mAdapter = new Adapter();
            List<Category> list = Preset.getCategories();
            mAdapter.getCollection().update(list);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemChildClick(RecyclerView recyclerView, View itemView, View clickView, int position) {
        Category category = mAdapter.getItem(position);
        switch (clickView.getId()) {
            case R.id.Avatar:
            case R.id.UserName:
                Toast.show("查看用户");
                break;
            case R.id.VideoInfoLayout:
                Toast.show("查看视频");
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setLayoutManager(null);
    }


    private class Adapter extends RecyclerViewAdapter<Category> {

        final int VIEW_TYPE_VIDEO = 0;
        final int VIEW_TYPE_LABEL = 1;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_VIDEO) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_notification_list_item_video, parent, false);
                return new VideoHolder(v, NotificationFragment.this);
            } else {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_notification_list_item_label, parent, false);
                return new LabelHolder(v, NotificationFragment.this);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder h, int position) {
            Category category = getItem(position);
            if (h.getItemViewType() == VIEW_TYPE_LABEL) {
                LabelHolder holder = (LabelHolder) h;
                switch (category.count % 3) {
                    case 0:
                        holder.shareCircle(category.name);
                        break;
                    case 1:
                        holder.daShangCircle(category.count, category.name);
                        break;
                    case 2:
                        holder.shenQing(category.name);
                        break;
                }
            } else {
                VideoHolder holder = (VideoHolder) h;
                switch (category.count % 4) {
                    case 0:
                        holder.acceptYourVideo(category.name);
                        break;
                    case 1:
                        holder.makeVideo(category.name);
                        break;
                    case 2:
                        holder.shareVideo(category.name);
                        break;
                    case 3:
                        holder.deliverVideoToYou(category.name);
                        break;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).count % 2;
        }
    }

    private class VideoHolder extends ViewHolder {

        public ImageView iAvatar;
        public TextView iUserName;
        public TextView iReplyContent;
        public View iByReplyPanel;
        public TextView iByReplyUserName;
        public TextView iByReplyContent;
        public TextView iVideoName;
        public TextView iTime;

        public VideoHolder(View itemLayoutView, OnRecyclerItemChildClickListener listener) {
            super(itemLayoutView, null, listener);
        }

        @Override
        public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
            iAvatar = (ImageView) itemLayoutView.findViewById(R.id.Avatar);
            iUserName = (TextView) itemLayoutView.findViewById(R.id.UserName);
            iReplyContent = (TextView) itemLayoutView.findViewById(R.id.ReplyContent);
            iByReplyPanel = itemLayoutView.findViewById(R.id.ByReplyPanel);
            iByReplyUserName = (TextView) itemLayoutView.findViewById(R.id.ByReplyUserName);
            iByReplyContent = (TextView) itemLayoutView.findViewById(R.id.ByReplyContent);
            iVideoName = (TextView) itemLayoutView.findViewById(R.id.VideoName);
            iTime = (TextView) itemLayoutView.findViewById(R.id.Time);
            iAvatar.setOnClickListener(this);
            iUserName.setOnClickListener(this);
            itemLayoutView.findViewById(R.id.VideoInfoLayout).setOnClickListener(this);
        }

        /**
         * 好友创建了一个片单
         * @param name
         */
        public void makeVideo(String name){
            iReplyContent.setText("创建了一个片单");
            iVideoName.setText(name);
        }

        /**
         * 好友分享了一个片单
         * @param name
         */
        public void shareVideo(String name){
            iReplyContent.setText("分享了一个片单");
            iVideoName.setText(name);
        }

        /**
         * 你投递到他人圈子的视频，已给于通过
         * @param name
         */
        public void acceptYourVideo(String name){
            iReplyContent.setText(Html.fromHtml("通过了你投递的" + toHtml(name) + "的请求"));
            iVideoName.setText(name);
        }

        /**
         * 他人向你的圈子投递了一个视频
         * @param name
         */
        public void deliverVideoToYou(String name){
            iReplyContent.setText(Html.fromHtml("投递了一个片单到" + toHtml(name) + "，请审核"));
            iVideoName.setText(name);
        }

        private String toHtml(String content) {
            return "<font color=\"#DD4C11\">"
                    + content
                    + "</font>";
        }

    }

    private class LabelHolder extends ViewHolder {

        public ImageView iAvatar;
        public TextView iUserName;
        public TextView iReplyContent;
        public TextView iTime;

        public LabelHolder(View itemLayoutView, OnRecyclerItemChildClickListener listener) {
            super(itemLayoutView, null, listener);
        }

        @Override
        public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
            iAvatar = (ImageView) itemLayoutView.findViewById(R.id.Avatar);
            iUserName = (TextView) itemLayoutView.findViewById(R.id.UserName);
            iReplyContent = (TextView) itemLayoutView.findViewById(R.id.ReplyContent);
            iTime = (TextView) itemLayoutView.findViewById(R.id.Time);
            iAvatar.setOnClickListener(this);
            iUserName.setOnClickListener(this);
        }

        /**
         * 他人打赏了你的圈子
         *
         * @param money
         * @param circleName
         */
        public void daShangCircle(int money, String circleName) {
            String str = "打赏了"
                    + "<font color=\"#8359C1\">"
                    + money
                    + "</font>"
                    + "到"
                    + toHtml(circleName);
            iReplyContent.setText(Html.fromHtml(str));
            iAvatar.setImageResource(R.drawable.ic_actor_default);
            iUserName.setText("用户名");
        }

        /**
         * 你申请的圈子已通过审核
         *
         * @param circleName
         */
        public void shenQing(String circleName) {
            String str = "你申请的圈子"
                    + toHtml(circleName)
                    + "，通过审核";
            iReplyContent.setText(Html.fromHtml(str));
            iAvatar.setImageResource(R.drawable.ic_message_system);
            iUserName.setText("系统通知");
        }

        /**
         * 好友给你分享一个圈子
         *
         * @param circleName
         */
        public void shareCircle(String circleName) {
            String str = "分享给你一个圈子"
                    + toHtml(circleName);
            iReplyContent.setText(Html.fromHtml(str));
            iAvatar.setImageResource(R.drawable.ic_actor_default);
            iUserName.setText("用户名");
        }

        private String toHtml(String content) {
            return "<font color=\"#DD4C11\">"
                    + content
                    + "</font>";
        }

    }
}
