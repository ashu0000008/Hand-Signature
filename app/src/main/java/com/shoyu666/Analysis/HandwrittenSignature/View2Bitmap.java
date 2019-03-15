package com.shoyu666.Analysis.HandwrittenSignature;

import android.graphics.Bitmap;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <pre>
 * author : zhouyang
 * time   : 2019/03/15
 * desc   :
 * version:
 * </pre>
 */
public class View2Bitmap {
    public static void saveView2File(String path, View v){
        v.setDrawingCacheEnabled(true);
//        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        saveBitmap(bitmap, path);
    }

    public static void saveBitmap(Bitmap bitmap, String outPath) {
        try {
            FileOutputStream out = new FileOutputStream(outPath);
            BufferedOutputStream bos = new BufferedOutputStream(out);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
