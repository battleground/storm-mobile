package com.baofeng.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.abooc.common.Log;
import com.baofeng.mobile.R;
import com.baofeng.mobile.activity.FragmentActivity;
import com.baofeng.mobile.android.AppApplication;


/**
 * 消息
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-8-12.Z
 */
public class MessageFragment extends Fragment implements View.OnClickListener, EditText.OnFocusChangeListener {

    private static View view;
    private View mInputShadow;
    private EditText mReplyEdit;

    public MessageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.anchor();
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.anchor();
        // 处理再次加载R.layout.fragment_message布局时，布局中的Fragment会重复加载
        // 报异常:Duplicate id, tag null, or parent id 0x0 with another fragment for xxx Fragment.
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_message, container, false);
        } catch (InflateException e) {
        /* fragment is already there, just return view as it is */
        }
        return view;
//        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.anchor();
        view.findViewById(R.id.OpenNotifyPage).setOnClickListener(this);
        view.findViewById(R.id.OpenZanPage).setOnClickListener(this);
        mInputShadow = view.findViewById(R.id.InputShadow);
        mReplyEdit = (EditText) view.findViewById(R.id.ReplyEdit);
        mReplyEdit.setOnFocusChangeListener(this);
        mInputShadow.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.anchor("hasFocus:" + hasFocus);
        if (hasFocus) {
            mInputShadow.setVisibility(View.VISIBLE);
        } else {
            mInputShadow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.InputShadow:
                Log.anchor();
                AppApplication.hideInputMethod(getActivity(), v);
                mReplyEdit.setVisibility(View.GONE);
                break;
            case R.id.OpenNotifyPage:
                FragmentActivity.launch(getActivity(), "通知", NotificationFragment.class);
                break;
            case R.id.OpenZanPage:
                FragmentActivity.launch(getActivity(), "赞", ZanFragment.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.anchor();
//        destroyChildFragment(getActivity().getSupportFragmentManager());
//        destroyChildFragment(getChildFragmentManager());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
    }

    private void destroyChildFragment(FragmentManager manager) {
        Fragment fragment = manager.findFragmentById(R.id.CommentFragment);
        if (fragment != null && fragment instanceof CommentFragment) {
            manager.beginTransaction().remove(fragment).commit();
            Log.anchor();
        }
    }
}
