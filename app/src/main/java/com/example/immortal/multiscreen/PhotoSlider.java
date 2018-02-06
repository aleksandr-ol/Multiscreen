package com.example.immortal.multiscreen;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.GridView;

import com.example.immortal.multiscreen.adapter.GridViewImageAdapter;
import com.example.immortal.multiscreen.helper.AppConstant;
import com.example.immortal.multiscreen.helper.Utils;

import java.util.ArrayList;

public class PhotoSlider extends AppCompatActivity {
    private Utils utils;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridView = (GridView) findViewById(R.id.grid_view);

        utils = new Utils(this);

        InitializeGridLayout();
        imagePaths = utils.getFilePaths();
        adapter = new GridViewImageAdapter(PhotoSlider.this, imagePaths, columnWidth);

        gridView.setAdapter(adapter);
    }

    private void InitializeGridLayout() {
        Resources resources = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstant.GRID_PADDING, resources.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth() -
                ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

        gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }
}
