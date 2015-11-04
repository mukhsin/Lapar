package com.ifunsoed13.rpl.lapar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseUser;

public class SplashActivity extends AppCompatActivity {

    protected String nextActivity;
    protected int splashingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Bundle bundle = getIntent().getExtras();
        nextActivity = bundle.getString("next");
        splashingTime = bundle.getInt("time");
        
//        if (nextActivity.equals("login")) {
//            Toast.makeText(SplashActivity.this, "Login", Toast.LENGTH_SHORT).show();
//        } else if (nextActivity.equals("requirements")) {
//            Toast.makeText(SplashActivity.this, "Requirements", Toast.LENGTH_SHORT).show();
//        }

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(splashingTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (nextActivity.equals("login")) {
                        // Login
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (nextActivity.equals("requirements")) {
                        // Requirements
                        Intent intent = new Intent(SplashActivity.this, RequirementsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };

        timer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.mActivity.finish();
        finish();
    }
}
