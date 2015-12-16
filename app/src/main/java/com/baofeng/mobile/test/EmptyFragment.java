package com.baofeng.mobile.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abooc.common.Log;
import com.baofeng.mobile.R;
import com.baofeng.mobile.android.AppApplication;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-23.
 */
public class EmptyFragment extends Fragment {

    private String mTag;

    public EmptyFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = getTag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.blank, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(mTag);
    }

    @Override
    public void onDestroyView() {
        Log.anchor(mTag);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.anchor(mTag);
        super.onDestroy();
        RefWatcher refWatcher = AppApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}
