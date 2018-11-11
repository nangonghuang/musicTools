package com.example.alan.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {
    public static Bitmap loadBitmapFromView(View view) {
        Bitmap screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(screenshot);
        canvas.drawColor(Color.WHITE);
//        canvas.translate(-getScrollX(), -getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域 v.draw(canvas);// 将 view 画到画布上 return screenshot; }
        view.draw(canvas);
        return screenshot;
    }

    private static final String TAG = "BitmapUtils";

    public static String saveBitmapToSDcard(Bitmap bm, String name) throws IOException {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory() + "/musicTools/";
            File targetDir = new File(path);
            if (!targetDir.exists()) {
                try {
                    boolean succ = targetDir.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String fileName = name + ".jpg";
            File savedFile = new File(path + fileName);
            if (!savedFile.exists()) {
                savedFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(savedFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            bm.recycle();
            return savedFile.getAbsolutePath();
        } else {
            Log.e(TAG, "saveBitmapToSDcard:error,sd card not found ");
            return null;
        }
    }
}
