package com.baofeng.mobile.android;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.abooc.common.Log;
import com.abooc.common.Toast;
import com.baofeng.mobile.network.OkHttpUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-6-17.
 */
public class AppApplication extends Application {


    public static RefWatcher getRefWatcher(Context context) {
        AppApplication application = (AppApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    private RefWatcher mRefWatcher;


    @Override
    public void onCreate() {
        super.onCreate();

        AppContext.getInstance().setContext(this);
        Toast.init(this);

        String path = getCacheDir().getPath();
        Log.anchor(path);
        path += File.separator + "okhttp/";
        OkHttpUtil.enableCache(path);
        Log.anchor(path);

        mRefWatcher = LeakCanary.install(this);
    }


    public static String buildVersionInfo(Context context) {
        String versionName = getVersionName(context);
        int versionCode = getVersionCode(context);
        return versionName + "(build:" + versionCode + ")";
    }

    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 隐藏键盘
     *
     * @param context
     * @param view
     * @return
     */
    public static boolean hideInputMethod(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        return false;
    }

    public static boolean showInputMethod(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.showSoftInput(view, 0);
        }

        return false;
    }


}
