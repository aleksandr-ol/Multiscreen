package com.example.immortal.multiscreen;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.immortal.multiscreen.adapter.FullScreenImageAdapter;
import com.example.immortal.multiscreen.helper.Utils;

import java.util.ArrayList;

public class Fullscreen_View extends AppCompatActivity {
    private ViewPager viewPager;
    private FullScreenImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen__view);

        viewPager = (ViewPager) findViewById(R.id.fullscreenviewpager);
        Utils utils = new Utils(Fullscreen_View.this);
        ArrayList<String> image_paths = utils.getFilePaths();
        adapter = new FullScreenImageAdapter(Fullscreen_View.this, image_paths);

        Intent intent = getIntent();
        Integer position = intent.getIntExtra("position", 0);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
