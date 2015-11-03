package com.ifunsoed13.rpl.lapar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.parse.ParseUser;

public class CheckRequirementsActivity extends AppCompatActivity {

    public static String internetStatus;
    public static String gpsStatus;

    private static ConnectivityManager mConnectivityManager;
    private static NetworkInfo mNetworkInfo;
    private static LocationManager mLocationManager;

    public static boolean isNetWorkAvailable() {
        return (mNetworkInfo != null && mNetworkInfo.isConnected());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_requirements);

        internetStatus = "";
        gpsStatus = "";

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
            if (getIntent().getStringExtra("isFirstRun") == "y") {
                Intent intent = new Intent(CheckRequirementsActivity.this, SplashActivity.class);
                startActivity(intent);
            } else {
                activateRequirements();
            }
        } else {
            internetStatus = "ok";
            gpsStatus = "ok";

            if (ParseUser.getCurrentUser() == null) {
                Intent intent = new Intent(CheckRequirementsActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(CheckRequirementsActivity.this, MapActivity.class);
                startActivity(intent);
            }

            finish();
        }
    }

    private void activateRequirements() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckRequirementsActivity.this);
        builder.setTitle(getString(R.string.error_title));
        builder.setMessage(getString(R.string.error_message_requirements));
        builder.setNegativeButton(getString(R.string.error_button_exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setPositiveButton(getString(R.string.error_button_activate_reuirements), new DialogInterface.OnClickListener() {
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
