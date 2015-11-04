package com.ifunsoed13.rpl.lapar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MapActivity extends AppCompatActivity {

    protected ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_camera:
                Toast.makeText(MapActivity.this, "Buka kamera", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logout:
                loading = ProgressDialog.show(MapActivity.this, "Loading", "Logging out...", true);

                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        loading.dismiss();

                        if (e == null) {
                            // Success
                            Intent intent = new Intent(MapActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Error
                            AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                            builder.setTitle(R.string.error_title);
                            builder.setMessage(e.getMessage());
                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
