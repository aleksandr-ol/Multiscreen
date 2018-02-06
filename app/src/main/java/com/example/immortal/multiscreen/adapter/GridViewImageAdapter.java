package com.example.immortal.multiscreen.adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;

import com.example.immortal.multiscreen.Fullscreen_View;

import java.util.ArrayList;

public class GridViewImageAdapter extends BaseAdapter {
    private Activity _activity;
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private int imageWidth;

    public GridViewImageAdapter(Activity activity, ArrayList<String> filePaths, int imageWidth) {
        this._activity = activity;
        this._filePaths = filePaths;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this._filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return this._filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(_activity);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap image = decodeFile(_filePaths.get(position), imageWidth, imageWidth);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));//LayoutParams????
        imageView.setImageBitmap(image);

        imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }

    class OnImageClickListener implements View.OnClickListener {
        int _position;

        public OnImageClickListener(int position) {
            this._position = position;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(_activity, Fullscreen_View.class);//import
            i.putExtra("position", _position);
            _activity.startActivity(i);
        }
    }

    public static Bitmap decodeFile(String filePath, int WIDTH, int HEIGHT) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HEIGHT = HEIGHT;
            int scale = 1;
            while (((options.outWidth / scale / 2) >= REQUIRED_WIDTH) && ((options.outHeight / scale / 2) >= REQUIRED_HEIGHT))
                scale *= 2;

            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = scale;
            options2.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(filePath, options2);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
