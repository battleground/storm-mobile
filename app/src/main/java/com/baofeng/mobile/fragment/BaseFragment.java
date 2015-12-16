package com.baofeng.mobile.fragment;

import android.support.v4.app.Fragment;

/**
 * @author zhangjunpu
 * @date 15/7/30
 */
public class BaseFragment extends Fragment {

    private boolean isDestroyView = false;

    @Override
    public void onResume() {
        super.onResume();
        isDestroyView = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDestroyView = true;
    }

    /**
     * 是否被销毁
     */
    public boolean isDestroyView() {
        return isDestroyView;
    }
}
