package com.baofeng.mobile.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abooc.common.Toast;
import com.baofeng.mobile.R;
import com.baofeng.mobile.ViewHolder;


/**
 * 远程投屏播控页面
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-8-14.
 */
public class RemoteFragment extends Fragment implements View.OnClickListener, ViewHolder.OnRecyclerItemClickListener {

    public RemoteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_remote, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.Center).setOnClickListener(this);
        view.findViewById(R.id.Left).setOnClickListener(this);
        view.findViewById(R.id.Top).setOnClickListener(this);
        view.findViewById(R.id.Right).setOnClickListener(this);
        view.findViewById(R.id.Bottom).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.show(v.getId() + "");
        switch (v.getId()) {
            case R.id.Center:
                v.setSelected(!v.isSelected());
                break;
            case R.id.Left:
                break;
            case R.id.Top:
                break;
            case R.id.Right:
                break;
            case R.id.Bottom:
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
    }
}
