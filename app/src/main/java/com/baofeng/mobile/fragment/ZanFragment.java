package com.baofeng.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * 赞页面
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-8-12.
 */
public class ZanFragment extends Fragment implements ViewHolder.OnRecyclerItemChildClickListener {

    private RecyclerViewAdapter<Category> mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager manager = new LinearLayoutManager(getActivity());

    public ZanFragment() {
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
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_zan_list_item_1, parent, false);
            return new Holder(v, ZanFragment.this);
        }

        @Override
        public void onBindViewHolder(ViewHolder h, int position) {
            Category category = getItem(position);
            Holder holder = (Holder) h;

            if(category.count % 2 == 0){
                holder.iByReplyPanel.setVisibility(View.GONE);
            }else{
                holder.iByReplyPanel.setVisibility(View.VISIBLE);
            }
        }

    }

    private class Holder extends ViewHolder {

        public ImageView iAvatar;
        public TextView iUserName;
        public TextView iReplyContent;
        public View iByReplyPanel;
        public TextView iByReplyUserName;
        public TextView iByReplyContent;
        public TextView iVideoName;
        public TextView iTime;

        public Holder(View itemLayoutView, OnRecyclerItemChildClickListener listener) {
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

    }
}
