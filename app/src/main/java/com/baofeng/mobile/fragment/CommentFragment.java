package com.baofeng.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.abooc.common.Log;
import com.abooc.common.Toast;
import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.android.AppApplication;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.data.Preset;
import com.baofeng.mobile.widget.LinearLayoutManager;

import java.util.List;


/**
 * 评论页面
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-8-12.
 */
public class CommentFragment extends Fragment implements ViewHolder.OnRecyclerItemChildClickListener {

    private RecyclerViewAdapter<Category> mAdapter;
    private RecyclerView mRecyclerView;

    public CommentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.anchor();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.anchor();
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.anchor();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
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
            case R.id.Reply:
                Toast.show("回复");
                EditText mReplyEdit = (EditText) getActivity().findViewById(R.id.ReplyEdit);
                AppApplication.showInputMethod(getActivity(), mReplyEdit);
                mReplyEdit.setVisibility(View.VISIBLE);
                mReplyEdit.requestFocus();
                mReplyEdit.setHint("回复@" + category.name);
                break;
            case R.id.VideoInfoLayout:
                Toast.show("查看视频");
                break;
        }

    }

    private class Adapter extends RecyclerViewAdapter<Category> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_comment_list_item, parent, false);
            return new CommentViewHolder(v, CommentFragment.this);
        }

        @Override
        public void onBindViewHolder(ViewHolder h, int position) {
            Category category = getItem(position);
            CommentViewHolder holder = (CommentViewHolder) h;
        }

    }

    private class CommentViewHolder extends ViewHolder {

        public ImageView iAvatar;
        public TextView iUserName;
        public TextView iReplyContent;
        public TextView iByReplyUserName;
        public TextView iByReplyContent;
        public TextView iVideoName;
        public TextView iTime;
        public TextView iReply;

        public CommentViewHolder(View itemLayoutView, OnRecyclerItemChildClickListener listener) {
            super(itemLayoutView, null, listener);
        }

        @Override
        public void onBuildView(View itemLayoutView, OnRecyclerItemClickListener listener) {
            iAvatar = (ImageView) itemLayoutView.findViewById(R.id.Avatar);
            iUserName = (TextView) itemLayoutView.findViewById(R.id.UserName);
            iReplyContent = (TextView) itemLayoutView.findViewById(R.id.ReplyContent);
            iByReplyUserName = (TextView) itemLayoutView.findViewById(R.id.ByReplyUserName);
            iByReplyContent = (TextView) itemLayoutView.findViewById(R.id.ByReplyContent);
            iVideoName = (TextView) itemLayoutView.findViewById(R.id.VideoName);
            iTime = (TextView) itemLayoutView.findViewById(R.id.Time);
            iReply = (TextView) itemLayoutView.findViewById(R.id.Reply);
            iAvatar.setOnClickListener(this);
            iUserName.setOnClickListener(this);
            iReply.setOnClickListener(this);
            itemLayoutView.findViewById(R.id.VideoInfoLayout).setOnClickListener(this);
        }

    }
}
