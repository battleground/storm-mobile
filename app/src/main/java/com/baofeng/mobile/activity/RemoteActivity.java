package com.baofeng.mobile.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.baofeng.mobile.R;
import com.baofeng.mobile.tools.ToolUtils;
import com.baofeng.mobile.view.EpisodeLayout;
import com.baofeng.mobile.widget.SlidDialog;

public class RemoteActivity extends AppCompatActivity implements View.OnClickListener{

    public static void launch(Context context){
        Intent intent = new Intent(context, RemoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
    }

    @Override
    public void onClick(View v) {
    }

    private SlidDialog dialog;
    public void onOpenSeries(View view){
        if(dialog == null){
            int height = ToolUtils.dp2px(this, 400);
            dialog = new SlidDialog(this, R.style.DialogSlideAnim);
            EpisodeLayout layout = new EpisodeLayout(this);
            layout.findViewById(R.id.Close).setOnClickListener(this);
            layout.setCloseable(true);
            layout.setBackgroundColor(Color.WHITE);
            layout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            layout.setAdapter(EpisodeLayout.getImageAdapter(R.layout.fragment_play_episode_image_horizontal_item, null));
            dialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        }
        dialog.show();
    }

    private SlidDialog dialogDefinition;
    public void onOpenDefinition(View view){
        if(dialogDefinition == null){
            int height = ToolUtils.dp2px(this, 300);
            dialogDefinition = new SlidDialog(this, R.style.DialogSlideAnim);
            EpisodeLayout layout = new EpisodeLayout(this);
            layout.findViewById(R.id.Close).setOnClickListener(this);
            layout.setTitle("选择清晰度");
            layout.setCloseable(true);
            layout.setBackgroundColor(Color.WHITE);
            layout.setLayoutManager(new GridLayoutManager(this, 4));
            layout.setAdapter(EpisodeLayout.getImageAdapter(R.layout.fragment_play_episode_item, null));
            dialogDefinition.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        }
        dialogDefinition.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_bottom);
    }

}
