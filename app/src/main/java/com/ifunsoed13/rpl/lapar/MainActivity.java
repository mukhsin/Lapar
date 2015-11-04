package com.ifunsoed13.rpl.lapar;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    protected ConnectivityManager mConnectivityManager;
    protected NetworkInfo mNetworkInfo;
    protected LocationManager mLocationManager;

    public static AppCompatActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mActivity = this;

        mConnectivityManager = (ConnectivityManager) getSystemService
                (Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        mLocationManager = (LocationManager) getSystemService(Context
                .LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isNetWorkAvailable() && isGpsAvailable()) {
            // Check user
            if (ParseUser.getCurrentUser() == null) {
                // Splash screen (2s) -> login
                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                intent.putExtra("next", "login");
                intent.putExtra("time", 2);
                startActivity(intent);
                finish();
            } else {
                // Map
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            // Splash screen (5s) -> activate internet and GPS
            Intent intent = new Intent(MainActivity.this, SplashActivity.class);
            intent.putExtra("next", "requirements");
            intent.putExtra("time", 5);
            startActivity(intent);
        }
    }

    protected boolean isNetWorkAvailable() {
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

    protected boolean isGpsAvailable() {
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
