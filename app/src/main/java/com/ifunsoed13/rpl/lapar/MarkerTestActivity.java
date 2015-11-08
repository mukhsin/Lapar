package com.ifunsoed13.rpl.lapar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MarkerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_test);

        Intent i = getIntent();
        Bitmap bitmap = i.getParcelableExtra("bitmap");
        ImageView imgTest = (ImageView) findViewById(R.id.imgTest);
        imgTest.setImageBitmap(bitmap);
    }
}
