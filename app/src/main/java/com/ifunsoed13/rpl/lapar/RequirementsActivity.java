package com.ifunsoed13.rpl.lapar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class RequirementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);

        AlertDialog.Builder builder = new AlertDialog.Builder(RequirementsActivity.this);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.error_title));
        builder.setMessage(getString(R.string.error_message_requirements));
        builder.setNegativeButton(getString(R.string.error_button_exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.mActivity.finish();
                finish();
            }
        });
        builder.setPositiveButton(getString(R.string.error_button_activate_reuirements), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
