package com.baofeng.mobile.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baofeng.mobile.R;


/**
 * Implementation of App Widget functionality.
 */
public class TitleBar extends FrameLayout {

    public static final int ITEM_TITLE = R.id.TITLE;
    public static final int ITEM_BACK = R.id.BACK;
    public static final int ITEM_NEXT = R.id.NEXT;
    public static final int ITEM_NEXT2 = R.id.NEXT2;

    private TextView mTitle;
    private TextView mBack;
    private TextView mNext;
    private TextView mNext2;

    public TitleBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.title_bar_layout, this, true);
        mTitle = (TextView) findViewById(R.id.TITLE);
        mBack = (TextView) findViewById(R.id.BACK);
        mNext = (TextView) findViewById(R.id.NEXT);
        mNext2 = (TextView) findViewById(R.id.NEXT2);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);
        String textBack = a.getString(R.styleable.TitleBar_textBack);
        if (!TextUtils.isEmpty(textBack)) {
            mBack.setText(textBack);
        }
        String textNext = a.getString(R.styleable.TitleBar_textNext);
        if (!TextUtils.isEmpty(textNext)) {
            mNext.setText(textNext);
        }
        String textNext2 = a.getString(R.styleable.TitleBar_textNext2);
        if (!TextUtils.isEmpty(textNext2)) {
            mNext2.setText(textNext2);
        }
        String textTitle = a.getString(R.styleable.TitleBar_textTitle);
        if (!TextUtils.isEmpty(textTitle)) {
            mTitle.setText(textTitle);
        }
        Drawable drawableBack = a.getDrawable(R.styleable.TitleBar_drawableBack);
        if (drawableBack != null) {
            mBack.setCompoundDrawablesWithIntrinsicBounds(drawableBack, null, null, null);
        } else {
            mBack.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_menu_back, 0, 0, 0);
        }
        Drawable drawableNext = a.getDrawable(R.styleable.TitleBar_drawableNext);
        if (drawableNext != null) {
            mNext.setCompoundDrawablesWithIntrinsicBounds(drawableNext, null, null, null);
        }
        Drawable drawableNext2 = a.getDrawable(R.styleable.TitleBar_drawableNext2);
        if (drawableNext2 != null) {
            mNext2.setCompoundDrawablesWithIntrinsicBounds(drawableNext2, null, null, null);
        }
        Drawable drawableTitle = a.getDrawable(R.styleable.TitleBar_drawableTitle);
        if (drawableTitle != null) {
            mTitle.setCompoundDrawablesWithIntrinsicBounds(drawableTitle, null, null, null);
        }
        Drawable drawableTitleArrow = a.getDrawable(R.styleable.TitleBar_drawableTitleArrow);
        if (drawableTitleArrow != null) {
            mTitle.setCompoundDrawablesWithIntrinsicBounds(drawableTitleArrow, null, null, null);
        }

    }

    public TextView setItemText(int id, String text) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(text);
        return textView;
    }

    public TextView setItemTextRes(int id, int resid) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(resid);
        return textView;
    }

    public TextView setItemDrawableRes(int id, int resid) {
        TextView textView = (TextView) findViewById(id);
        textView.setCompoundDrawablesWithIntrinsicBounds(resid, 0, 0, 0);
        return textView;
    }

    public TextView setItemDrawable(int id, Drawable drawable) {
        TextView textView = (TextView) findViewById(id);
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        return textView;
    }

    public void setOnClickListener(int id, OnClickListener onClickListener) {
        findViewById(id).setOnClickListener(onClickListener);
    }

    public void setItemVisibility(int id, int visibility) {
        findViewById(id).setVisibility(visibility);
    }

    public TextView getItemView(int id) {
        TextView textView = (TextView) findViewById(id);
        return textView;
    }
}

