package com.ifunsoed13.rpl.lapar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = MapActivity.class.getSimpleName();

    protected GPSTracker gps;
    protected double latitude;
    protected double longitude;
    protected LatLng position;
    protected CameraPosition cameraPosition;
    protected MarkerOptions marker;
    protected ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
        gps = new GPSTracker(getApplicationContext());
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        ParseObject location = new ParseObject("Locations");
        location.put("latitude", latitude);
        location.put("longitude", longitude);
        location.saveInBackground();
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        position = new LatLng(gps.getLatitude(), gps.getLongitude());
        cameraPosition = new CameraPosition.Builder().target(position).zoom(5).build();
        marker = new MarkerOptions().position(position);
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));

        map.addMarker(marker);
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        ParseQuery<ParseObject> query = new ParseQuery<>("Locations");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> locations, ParseException e) {
                // Success
                if (e == null) {
                    double[] lat = new double[locations.size()];
                    double[] lng = new double[locations.size()];

                    for (int i = 0; i < lat.length; i++) {
                        lat[i] = locations.get(i).getDouble("latitude");
                        lng[i] = locations.get(i).getDouble("longitude");

                        MarkerOptions newMarker = new MarkerOptions().position(new LatLng(lat[i],
                                lng[i]));
                        newMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));

                        map.addMarker(newMarker);
                    }
                } else {
                    // Error
                    Log.e(TAG, e.getMessage());
                }
            }

        });
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
