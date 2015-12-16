package com.baofeng.mobile;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-23.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private Collection<T> mCollection = new Collection<T>();

    public Collection<T> getCollection() {
        return mCollection;
    }

    public T getItem(int position) {
        return mCollection.getItem(position);
    }

    public boolean isEmpty() {
        return mCollection.getCount() == 0;
    }

    @Override
    public int getItemCount() {
        return mCollection.getCount();
    }

    public static class Collection<T> {
        private List<T> mImages;

        public List<T> getList() {
            return mImages;
        }

        public int getCount() {
            return mImages == null ? 0 : mImages.size();
        }

        public void update(List<T> list) {
            mImages = list;
        }

        public void add(List<T> list) {
            if (mImages == null) {
                mImages = list;
            } else {
                mImages.addAll(list);
            }
        }

        public void add(T[] images) {
            if (mImages == null) {
                List<T> list = Arrays.asList(images);
                mImages = new ArrayList(list);
            } else {
                mImages.addAll(Arrays.asList(images));
            }
        }

        public T getItem(int position) {
            if (mImages == null) return null;
            return mImages.get(position);
        }

        public void clear() {
            if (mImages == null) mImages.clear();
        }
    }
}