package com.baofeng.mobile.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.abooc.common.Toast;
import com.baofeng.mobile.R;
import com.baofeng.mobile.android.AppApplication;
import com.baofeng.mobile.bean.Area;
import com.baofeng.mobile.bean.Base;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.bean.Tag;
import com.baofeng.mobile.bean.Year;
import com.baofeng.mobile.fragment.VideoFilterPresenter;
import com.baofeng.mobile.fragment.VideoFilterPresenter.FilterType;
import com.baofeng.mobile.fragment.VideoStorageFragment;
import com.google.gson.Gson;

import java.util.List;

public class VideoStorageActivity extends AppCompatActivity implements OnClickListener, OnTabSelectedListener {

    private Toolbar mToolBar;
    private View mFilterPanel;
    private View mFilterLayout;

    private View mBtnFilter;
    private View mBtnClear;
    private TextView mTextResult;

    private TabLayout mTabSort;
    private TabLayout mTabType;
    private TabLayout mTabArea;
    private TabLayout mTabYear;

    private VideoStorageFragment mFragment;
    private VideoFilterPresenter mPresenter;

    private Category mCategory;

    public static void launch(Context context, Category category) {
        Intent intent = new Intent(context, VideoStorageActivity.class);
        intent.putExtra("data", new Gson().toJson(category));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_storage);
        initActionBar();
        initFilter();
        mPresenter = new VideoFilterPresenter(this, mCategory);

        mFragment = (VideoStorageFragment) getSupportFragmentManager().findFragmentById(R.id.video_storage_fragment);

        attachData(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        attachData(intent);
    }

    private void attachData(Intent intent) {
        String json = intent.getStringExtra("data");
        Gson gson = new Gson();
        mCategory = gson.fromJson(json, Category.class);
        mPresenter.setCategory(mCategory);
        mFragment.setCategory(mCategory);
        mToolBar.setTitle(mCategory.name);
    }

    private void initActionBar() {
        mFilterPanel = findViewById(R.id.FilterPanel);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initFilter() {
        mFilterLayout = findViewById(R.id.filter_layout);
        mBtnFilter = findViewById(R.id.btn_filter);
        mBtnClear = findViewById(R.id.btn_clear);
        mBtnFilter.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);

        mTextResult = (TextView) findViewById(R.id.text_filter_result);

        mTabType = (TabLayout) findViewById(R.id.tablayout_type);
        mTabSort = (TabLayout) findViewById(R.id.tablayout_sort);
        mTabArea = (TabLayout) findViewById(R.id.tablayout_area);
        mTabYear = (TabLayout) findViewById(R.id.tablayout_year);
        mTabType.setOnTabSelectedListener(this);
        mTabSort.setOnTabSelectedListener(this);
        mTabArea.setOnTabSelectedListener(this);
        mTabYear.setOnTabSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_category:
                CategoryActivity.launch(this);
                break;
            case R.id.action_search:
                Toast.show("搜索");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filter:
                if (isFilterOpened()) {
                    closeFilter();
                } else {
                    openFilter();
                }
                break;
            case R.id.btn_clear: //清理
                if (mTabSort.getTabCount() > 0) {
                    setTabDefault();
                    saveFilter("最新榜");
                    mPresenter.doFilter();
                }
                break;
        }
    }

    public boolean isFilterOpened() {
        return mFilterLayout.getVisibility() == View.VISIBLE;
    }

    /**
     * 关闭筛选
     */
    public void closeFilter() {
        mFilterLayout.setVisibility(View.GONE);
        mBtnClear.setVisibility(View.GONE);
        mPresenter.close();
    }

    /**
     * 打开筛选
     */
    public void openFilter() {
        mFilterLayout.setVisibility(View.VISIBLE);
        mBtnClear.setVisibility(View.VISIBLE);
        mPresenter.load();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if ((ev.getAction() == MotionEvent.ACTION_DOWN) && isFilterOpened()) {
            int scrcoords[] = new int[2];
            View v = mFilterPanel;
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()){
                closeFilter();
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setTabDefault() {
        mTabSort.getTabAt(0).select();
        mTabType.getTabAt(0).select();
        mTabArea.getTabAt(0).select();
        mTabYear.getTabAt(0).select();
    }

    @Override
    public void onTabSelected(Tab tab) {
        int position = tab.getPosition();
        FilterType type = (FilterType) tab.getTag();
        mPresenter.update(type, position);
    }

    @Override
    public void onTabUnselected(Tab tab) {

    }

    @Override
    public void onTabReselected(Tab tab) {

    }

    public void initTab(FilterType type, List<? extends Base> list) {
        TabLayout tab = null;
        switch (type) {
            case SORT:
                tab = mTabSort;
                break;
            case TYPE:
                tab = mTabType;
                break;
            case AREA:
                tab = mTabArea;
                break;
            case YEAR:
                tab = mTabYear;
                break;
        }
        for (Base base : list) {
            tab.addTab(tab.newTab().setText(base.getName()).setTag(type));
        }
    }


    //============================================

    /**
     * 设置筛选结果
     *
     * @param msg
     */
    public void saveFilter(String msg) {
        mTextResult.setText(msg);
    }

    /**
     * 开始根据条件筛选
     */
    public void doFilter(Base sort, Tag type, Area area, Year year) {
        if (mFragment != null) {
            mFragment.setFilter(sort, type, area, year);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppApplication.getRefWatcher(this).watch(this);
    }
}
