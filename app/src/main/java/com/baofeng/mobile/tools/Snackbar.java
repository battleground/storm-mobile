package com.baofeng.mobile.tools;

import android.graphics.Color;
import android.view.View;

/**
 * @author zhangjunpu
 * @date 15/7/28
 */
public class Snackbar {

    private static android.support.design.widget.Snackbar snackbar;
    private static String bg = "#90000000";

    public static void show(View view, String msg) {
        if (view == null) {
            return;
        }
        android.support.design.widget.Snackbar snackbar = android.support.design.widget.Snackbar.make(view, msg, android.support.design.widget.Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.parseColor(bg));
        snackbar.show();
    }

    public static void show(View view, int resid) {
        if (view == null) {
            return;
        }
        android.support.design.widget.Snackbar snackbar = android.support.design.widget.Snackbar.make(view, resid, android.support.design.widget.Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.parseColor(bg));
        snackbar.show();
    }

}
