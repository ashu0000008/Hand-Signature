package com.shoyu666.Analysis.HandwrittenSignature;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

/**
 * <pre>
 * author : zhouyang
 * time   : 2019/03/15
 * desc   :
 * version:
 * </pre>
 */
public class ResultActivity extends AppCompatActivity {

    static void start(Activity activity, String path){
        Intent intent = new Intent(activity, ResultActivity.class);
        intent.putExtra("path", path);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ImageView imageView = (ImageView) findViewById(R.id.result);

        String path = getIntent().getStringExtra("path");
        Bitmap bitmap = getBitmapFromFile(new File(path), 0, 0, Bitmap.Config.ARGB_8888);
        imageView.setImageBitmap(bitmap);
    }

    public static Bitmap getBitmapFromFile(File dst, int maxWidth, int maxHeight, Bitmap.Config config) {
        try {
            if (null != dst && dst.exists()) {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                if (maxWidth > 0 || maxHeight > 0) {
                    if (maxWidth == 0)
                        maxWidth = opts.outWidth * maxHeight / opts.outHeight;
                    if (maxHeight == 0)
                        maxHeight = opts.outHeight * maxWidth / opts.outWidth;
                    final int minSideLength = Math.min(maxWidth, maxHeight);
                    opts.inSampleSize = computeSampleSize(opts, minSideLength, maxWidth * maxHeight);
                }
                opts.inJustDecodeBounds = false;
                opts.inPreferredConfig = config;
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            }
        } catch (Exception | OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    //计算压缩比算法
    private static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}
