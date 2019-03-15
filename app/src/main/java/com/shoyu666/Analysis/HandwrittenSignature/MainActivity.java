package com.shoyu666.Analysis.HandwrittenSignature;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View target = findViewById(R.id.signature);
        View v = findViewById(R.id.btn);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fileName = getFilesDir().toString() + File.separator + "test.png";
                View2Bitmap.saveView2File(fileName, target);

                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ResultActivity.start(MainActivity.this, fileName);
                    }
                }, 2000);
            }
        });
    }
}
