package com.example.immortal.multiscreen.helper;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class Utils {
    private Context _context;

    public Utils(Context context) {
        this._context = context;
    }

    public ArrayList<String> getFilePaths() {
        ArrayList<String> filePaths = new ArrayList<String>();

        File directory = new File(android.os.Environment.getExternalStorageDirectory() +
                File.separator + AppConstant.PHOTO_ALBUM);

        if (directory.isDirectory()) {
            File[] listFiles = directory.listFiles();

            if (listFiles.length > 0) {
                for (int i = 0; i < listFiles.length; i++) {
                    String filePath = listFiles[i].getAbsolutePath();
                    if (isSupportedFile(filePath)) {
                        filePaths.add(filePath);
                    }
                }
            } else {
                Toast.makeText(_context, AppConstant.PHOTO_ALBUM + " порожній. " +
                        "Додайте до каталогу кілька світлин!", Toast.LENGTH_LONG).show();
            }
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
            alert.setTitle("Помилка!");
            alert.setMessage(directory + " шлях до каталогу зі світлинами" +
                    " не правильний! Вкажіть корректий шлях в AppConstant.java");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
        return filePaths;
    }

    private boolean isSupportedFile(String filePath) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1), filePath.length());
        if (AppConstant.FILE_EXT.contains(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;
    }

    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) {
            point.x = display.getWidth();
            point.y = display.getHeight();
        }

        columnWidth = point.x;
        return columnWidth;
    }
}