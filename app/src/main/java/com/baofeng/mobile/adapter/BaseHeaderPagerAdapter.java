/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.baofeng.mobile.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import org.lee.java.util.ArrayUtils;

import java.util.List;

public abstract class BaseHeaderPagerAdapter<T> extends PagerAdapter {

    public interface OnItemClickListener{
        void onItemClick(ViewPager pager, View view, int position);
    }

	protected Context context;
    protected List<T> mData;
    private int mChildCount = 0;

    private boolean isInfiniteLoop; // 循环滑动

    protected OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

	public BaseHeaderPagerAdapter(Context context, List<T> data) {
		this.context = context;
		this.mData = data;
        this.isInfiniteLoop = false;
	}

	public BaseHeaderPagerAdapter(Context context, List<T> data, boolean isInfiniteLoop) {
        this(context, data);
        this.isInfiniteLoop = isInfiniteLoop;
	}

    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    public void setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
        }
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public int getDataSize() {
        return ArrayUtils.count(mData);
    }

    @Override
    public int getCount() {
        return isInfiniteLoop ? (getDataSize() > 1 ? Integer.MAX_VALUE : getDataSize()) : getDataSize();
    }

    public int getPosition(int position) {
        return isInfiniteLoop ? position % getDataSize() : position;
    }

    public T getItem(int position) {
        return mData == null ? null : mData.get(getPosition(position));
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getDataSize();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(container, position);
        container.addView(view);
        return view;
    }

    protected abstract View getView(ViewGroup container, int position);

}
