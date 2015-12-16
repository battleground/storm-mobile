package com.baofeng.mobile.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.baofeng.mobile.R;
import com.baofeng.mobile.bean.Video;
import com.baofeng.mobile.fragment.PlayFragment;
import com.google.gson.Gson;

/**
 * 影片播放页面，包括电影、电视剧、综艺、纪录片等
 * <p/>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-29.
 */
public class PlayActivity extends AppCompatActivity {

    private PlayFragment mViewer;

    public static void launch(Context context, Video video) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra("data", new Gson().toJson(video));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

//        String json = getIntent().getStringExtra("data");
//        Video video = new Gson().fromJson(json, Video.class);

        Video video = new Video();
        video.name = "大影片大电影";

        mViewer = (PlayFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mViewer.setVideo(video);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void onClose(View view){
        finish();
    }

    @Override
    public void onBackPressed() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_bottom);
    }

    /**
     * @param requestedOrientation An orientation constant as used in
     *                             {@link ActivityInfo#screenOrientation ActivityInfo.screenOrientation}.
     */
    public void requestOrientation(int requestedOrientation) {
        setRequestedOrientation(requestedOrientation);
        Handler h = getWindow().getDecorView().getHandler();
        if (h != null)
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                }
            }, 1500);
    }
}
