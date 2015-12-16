package com.baofeng.mobile.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baofeng.mobile.R;
import com.baofeng.mobile.adapter.HeaderPagerAdapter;
import com.baofeng.mobile.bean.Video;

public class VideoRecommendHeaderIndicator extends LinearLayout {

	private TextView mName;
	private TextView mDesc;
	private RadioGroup mIndicator;
	private ViewPager mViewPager;
	private OnPageChangeListener mListener;
    private HeaderPagerAdapter mAdapter;

	public VideoRecommendHeaderIndicator(Context context) {
		super(context);
		initView(context);
	}

	public VideoRecommendHeaderIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public VideoRecommendHeaderIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}
	
	private void initView(Context context) {
		LayoutInflater.from(context).inflate(R.layout.video_recommend_header_indicator, this, true);
		mName = (TextView) findViewById(R.id.name);
        mDesc = (TextView) findViewById(R.id.desc);
		mIndicator = (RadioGroup) findViewById(R.id.layout_indicator);
	}

	public void setInformation(int position) {
		Video video = (Video) mAdapter.getItem(position);
		if (video != null) {
			mName.setText(video.name);
			mDesc.setText(video.desc);
			mIndicator.getChildAt(mAdapter.getPosition(position)).performClick();
		}
	}
	
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		mListener = listener;
	}
	
	public void setViewPager(ViewPager viewPager) {
		mViewPager = viewPager;
		if (mViewPager != null) {
            mAdapter = (HeaderPagerAdapter) mViewPager.getAdapter();
            int count = mAdapter.getDataSize();
			addRadioButton(count);
			setInformation(mViewPager.getCurrentItem());
			mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		}
	}
	
	private void addRadioButton(int size) {
        removeAllIndicator();
		LayoutInflater inflater = LayoutInflater.from(getContext());
		for (int i = 0; i < size; i++) {
			View v = inflater.inflate(R.layout.header_indicator_radiobutton, mIndicator, false);
            v.setClickable(false);
			mIndicator.addView(v);
		}
	}

    private void removeAllIndicator() {
        if (mIndicator.getChildCount() > 0) {
            mIndicator.removeAllViews();
        }
    }

	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
            setInformation(position);
			
			if (mListener != null) {
				mListener.onPageSelected(position);
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			if (mListener != null) {
				mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			if (mListener != null) {
				mListener.onPageScrollStateChanged(arg0);
			}
		}
	}
}
