package com.ifunsoed13.rpl.lapar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    int mSplashingSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (CheckRequirementsActivity.internetStatus == "ok" &&
                CheckRequirementsActivity.gpsStatus == "ok") {
            mSplashingSeconds = 2;
        } else {
            mSplashingSeconds = 5;
        }

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(mSplashingSeconds * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, CheckRequirementsActivity
                            .class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        timer.start();
    }
}
