package com.baofeng.mobile.adapter;

import com.baofeng.mobile.RecyclerViewAdapter;
import com.baofeng.mobile.ViewHolder.OnRecyclerItemClickListener;

/**
 * @author zhangjunpu
 * @date 15/7/27
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerViewAdapter<T> {

    public OnRecyclerItemClickListener mListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener mListener) {
        this.mListener = mListener;
    }

}
