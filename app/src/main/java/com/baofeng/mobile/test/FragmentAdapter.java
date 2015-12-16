package com.baofeng.mobile.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    public static class Tab {
        public Class<? extends Fragment> fragment;
        public String name;
        public Bundle args;

        public Tab(String name, Class<? extends Fragment> fragment) {
            this(name, fragment, null);
        }

        public Tab(String name, Class<? extends Fragment> fragment, Bundle args) {
            this.name = name;
            this.fragment = fragment;
            this.args = args;
        }
    }

    private final List<Tab> mTabs = new ArrayList<>();

    public FragmentAdapter addTab(Tab tab) {
        mTabs.add(tab);
        return this;
    }

    private Context mContext;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        Tab tab = getTab(position);
        return Fragment.instantiate(mContext, tab.fragment.getName(), mTabs.get(position).args);
    }

    public Tab getTab(int position) {
        return mTabs.get(position);
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).name;
    }
}