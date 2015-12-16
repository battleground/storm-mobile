package com.baofeng.mobile.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.baofeng.mobile.R;

public class FragmentActivity extends AppCompatActivity {

    public static void launch(Context context, String title, Class<? extends Fragment> cls) {
        Intent intent = new Intent(context, FragmentActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("fragment", cls);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        String title = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(title);

        Class<? extends Fragment> cls = (Class<? extends Fragment>) getIntent().getSerializableExtra("fragment");
        installFragment(cls);
    }

    private void installFragment(Class<? extends Fragment> cls) {
        Fragment fragment = Fragment.instantiate(this, cls.getName());
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

}