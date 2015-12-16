package com.baofeng.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baofeng.mobile.R;
import com.baofeng.mobile.RecyclerViewAdapter.Collection;
import com.baofeng.mobile.ViewHolder.OnRecyclerItemChildClickListener;
import com.baofeng.mobile.ViewHolder.OnRecyclerItemClickListener;
import com.baofeng.mobile.bean.VideoRecommend;
import com.baofeng.mobile.widget.LinearLayoutManager;

/**
 * @author zhangjunpu
 * @date 15/7/29
 */
public class VideoRecommendAdapter extends Adapter<ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;

    private Collection<VideoRecommend> mCollection = new Collection<VideoRecommend>();
    public Collection<VideoRecommend> getCollection() {
        return mCollection;
    }

    private Adapter mAdapter;
    private Context mContext;

    private LayoutInflater mInflater;
    public BaseHeaderPagerAdapter.OnItemClickListener mPagerItemClickListener;
    public OnRecyclerItemClickListener mListener;
    public OnRecyclerItemChildClickListener mChildListener;

    public VideoRecommendAdapter(Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
    }

    public void setOnPagerItemClickListener(BaseHeaderPagerAdapter.OnItemClickListener listener) {
        this.mPagerItemClickListener = listener;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnRecyclerItemChildClickListener(OnRecyclerItemChildClickListener listener) {
        this.mChildListener = listener;
    }

    private AdapterDataObserver observer = new AdapterDataObserver() {

        VideoRecommendAdapter adapter = VideoRecommendAdapter.this;

        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            adapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            adapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            adapter.notifyItemMoved(fromPosition, itemCount);
        }
    };

    public void setWrapAdapter(Adapter adapter) {
        if (adapter == null) {
            return;
        }
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(observer);
        }
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(observer);
    }

    public Adapter getWrapAdapter() {
        return mAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (mCollection.getItem(position).isTop()) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEADER) {
            view = mInflater.inflate(R.layout.video_recommend_header, null);
            return new VideoRecommendHeaderHolder(view, mChildListener);
        }
        view = mInflater.inflate(R.layout.video_recommend_item, null);
        return new VideoRecommendItemHolder(view, null, mChildListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoRecommend recommend = mCollection.getItem(position);
        if (recommend == null) {
            return;
        }
        if (holder instanceof VideoRecommendHeaderHolder) {
            VideoRecommendHeaderHolder headerHolder = (VideoRecommendHeaderHolder) holder;
            headerHolder.adapter = new HeaderPagerAdapter<>(mContext, recommend.getList(), true);
            headerHolder.adapter.setOnItemClickListener(mPagerItemClickListener);
            headerHolder.viewPager.setAdapter(headerHolder.adapter);
            headerHolder.indicator.setViewPager(headerHolder.viewPager);
        } else {
            VideoRecommendItemHolder itemHolder = (VideoRecommendItemHolder) holder;
            itemHolder.name.setText(recommend.getName());
            itemHolder.layoutManager = new LinearLayoutManager(mContext, android.support.v7.widget.LinearLayoutManager.HORIZONTAL, false){
            };
            itemHolder.recyclerView.setLayoutManager(itemHolder.layoutManager);
            itemHolder.adapter = new VideoRecommendItemAdapter(recommend.getList());
            itemHolder.adapter.setOnRecyclerItemClickListener(mListener);
            itemHolder.recyclerView.setAdapter(itemHolder.adapter);
        }
    }

    @Override
    public int getItemCount() {
        return mCollection.getCount();
    }

}
