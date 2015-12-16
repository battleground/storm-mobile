package com.baofeng.mobile;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.abooc.common.Log;
import com.baofeng.mobile.activity.RemoteActivity;
import com.baofeng.mobile.activity.VideoStorageActivity;
import com.baofeng.mobile.android.AppApplication;
import com.baofeng.mobile.bean.Category;
import com.baofeng.mobile.fragment.MessageFragment;
import com.baofeng.mobile.fragment.VideoMainFragment;
import com.baofeng.mobile.test.DevFragment;
import com.baofeng.mobile.test.EmptyFragment;
import com.baofeng.mobile.test.FragmentAdapter;
import com.baofeng.mobile.test.FragmentAdapter.Tab;
import com.baofeng.mobile.widget.NonSwipebleViewPager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NonSwipebleViewPager iViewPager;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initNavigationView();
//        initPagerView();
        getAdapter();

        buildVersion();
        show(0);
    }

    private void buildVersion() {
        String versionInfo = ((AppApplication) getApplication()).buildVersionInfo(this);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(versionInfo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.action_settings:
                break;
            case R.id.action_search:
                Category category = new Category("2", "电影", "drawable.ic_c_dianying", 1002);
                VideoStorageActivity.launch(this, category);
                break;
            case R.id.action_remote:
                RemoteActivity.launch(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void initNavigationView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    private void initPagerView() {
        iViewPager = (NonSwipebleViewPager) findViewById(R.id.viewpager);
        iViewPager.setBlockSwipe(true);
        iViewPager.setAdapter(getAdapter());
    }

    private FragmentAdapter mAdapter;

    private FragmentAdapter getAdapter() {
        mAdapter = new FragmentAdapter(this, getSupportFragmentManager());
        mAdapter.addTab(new Tab("Home", VideoMainFragment.class));
        mAdapter.addTab(new Tab("Messages", MessageFragment.class));
        mAdapter.addTab(new Tab("Friends", DevFragment.class));
        mAdapter.addTab(new Tab("Discussion", EmptyFragment.class));
        return mAdapter;
    }

    private void show(int position) {
        useAttach(position);
        if (true) return;

        Tab tab = mAdapter.getTab(position);
        Log.anchor("tab.name:" + tab.name);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment fragment = manager.findFragmentByTag(tab.name);
        if (fragment == null) {
            fragment = mAdapter.getItem(position);
        }
        transaction.replace(R.id.contentPanel, fragment, tab.name);
        transaction.commit();

    }

    private Fragment mCurrent;

    private void useAttach(int position) {
        Tab tab = mAdapter.getTab(position);
        Log.anchor("tab.name:" + tab.name);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (mCurrent != null) {
            transaction.detach(mCurrent);
            transaction.hide(mCurrent);
        }

        Fragment fragment = manager.findFragmentByTag(tab.name);
        if (fragment == null) {
            fragment = mAdapter.getItem(position);
            transaction.add(R.id.contentPanel, fragment, tab.name);
        } else {
            transaction.attach(fragment);
            transaction.show(fragment);
        }
        transaction.commit();

        mCurrent = fragment;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                showFragmentByIndex(0);
                break;
            case R.id.nav_messages:
                showFragmentByIndex(1);
                break;
            case R.id.nav_friends:
                showFragmentByIndex(2);
                break;
            case R.id.nav_discussion:
                showFragmentByIndex(3);
                break;
            default:
                showFragmentByIndex(0);
                break;
        }
        return true;
    }

    private void showFragmentByIndex(int index){
        show(index);
//      iViewPager.setCurrentItem(0, false);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }

        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentByTag(bye) == null) {
            showBye(manager);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setVisibility(View.GONE);
            mDrawerLayout.setEnabled(false);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.getMenu().clear();
        }else{
            super.onBackPressed();
        }

    }

    private String bye = "再按一次，\n再见...";
    private void showBye(FragmentManager manager){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentPanel, new EmptyFragment(), bye);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
}
