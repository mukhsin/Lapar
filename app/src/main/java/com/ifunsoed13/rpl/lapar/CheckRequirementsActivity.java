package com.ifunsoed13.rpl.lapar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CheckRequirementsActivity extends AppCompatActivity {

    ConnectivityManager mConnectivityManager;
    NetworkInfo mNetworkInfo;
    LocationManager mLocationManager;

    public static String internetStatus;
    public static String gpsStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_requirements);

        mConnectivityManager = (ConnectivityManager) getSystemService
                (Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        mLocationManager = (LocationManager) getSystemService(Context
                .LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkRequirements();
    }

    private void checkRequirements() {
        if (!isNetWorkAvailable() || !mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            activateRequirements();
        } else {
            internetStatus = "ok";
            gpsStatus = "ok";

            Intent intent = new Intent(CheckRequirementsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isNetWorkAvailable() {
        return (mNetworkInfo != null && mNetworkInfo.isConnected());
    }

    private void activateRequirements() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckRequirementsActivity.this);
        builder.setTitle("Title");
        builder.setMessage("Aplikasi ini membutuhkan akses internet dan GPS. Aktifkan sekarang?");
        builder.setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setPositiveButton("Aktifkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
