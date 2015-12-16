package com.baofeng.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder;
import com.baofeng.mobile.activity.VideoStorageActivity;
import com.baofeng.mobile.adapter.CategoryViewHolder;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.data.Preset;
import com.baofeng.mobile.widget.GridLayoutManager;

import java.util.List;


/**
 *
 * 影片分类页面，如电影、电视剧、动漫等
 *
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-28.
 */
public class CategoryFragment extends Fragment implements ViewHolder.OnRecyclerItemClickListener {

    private RecyclerViewAdapter<Category> mAdapter;
    private RecyclerView mRecyclerView;

    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new RecyclerViewAdapter<Category>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_category_item, parent, false);
                return new CategoryViewHolder(v, CategoryFragment.this);
            }

            @Override
            public void onBindViewHolder(ViewHolder h, int position) {
                Category category = getItem(position);
                CategoryViewHolder holder = (CategoryViewHolder) h;
                holder.mNameTextView.setText(category.name);
                holder.mCountTextView.setText(category.count + "部");
                holder.setIcon(category.iconResId);
            }

        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(manager);
        List<Category> list = Preset.getCategories();
        mAdapter.getCollection().update(list);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        Category category = mAdapter.getItem(position);
        VideoStorageActivity.launch(getActivity(), category);
    }
}
