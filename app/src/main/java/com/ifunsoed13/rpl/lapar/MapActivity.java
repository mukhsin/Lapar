package com.ifunsoed13.rpl.lapar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = MapActivity.class.getSimpleName();
    public static final int camRequestCode = 1;

    protected double latitude, longitude;
    protected LatLng position;
    protected CameraPosition cameraPosition;
    protected ProgressDialog loading;

    ImageView img;
    AlertDialog dialog;
    View imgview;

    Bitmap bitmap;

    List<Address> addresses;
    private CharSequence namaFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

//        ParseObject location = new ParseObject("Locations");
//        location.put("latitude", latitude);
//        location.put("longitude", longitude);
//        location.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e  == null) {
//                    Toast.makeText(MapActivity.this, "Yeay", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MapActivity.this, "Hmmm", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        position = new LatLng(latitude, longitude);
        cameraPosition = new CameraPosition.Builder().target(position).zoom(15).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        ParseQuery<ParseObject> query = new ParseQuery<>("Locations");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> locations, ParseException e) {
                // Success
                if (e == null) {
                    Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());

//                    ArrayList<String[]> mapTable = new ArrayList<String[]>();
//                    String[] markersId = new String[locations.size()];
//                    String[] locationsId = new String[locations.size()];

                    for (int i = 0; i < locations.size(); i++) {

                        double lat = locations.get(i).getDouble("latitude");
                        double lng = locations.get(i).getDouble("longitude");

//                        markersId[i] = "Marker " + i+1;
//                        locationsId[i] = locations.get(i).getObjectId();

                        try {
//                            ParseFile fileGambar = (ParseFile) locations.get(i).get("photo");
//                            fileGambar.getDataInBackground(new GetDataCallback() {
//                                @Override
//                                public void done(byte[] data, ParseException e) {
//                                    if(e == null){
//                                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                                    }
//                                    else{
//                                        Toast.makeText(getApplicationContext(), "Tidak dapat " +
//                                                "mengambil gambar", Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            });
                            addresses = gcd.getFromLocation(lat, lng, 1);
                            Log.i("KUPRETTTTTTTTTTT", addresses.get(0).toString());

//                            AlertDialog.Builder dialogb = new AlertDialog.Builder(MapActivity.this);
//                            LayoutInflater inflater = LayoutInflater.from(MapActivity.this);
//                            imgview = inflater.inflate(R.layout.image_view, null);
//                            img = (ImageView) imgview.findViewById(R.id.markerImage);
//                            img.setImageBitmap(bitmap);
//                            dialogb.setTitle(addresses.get(0).getFeatureName());
//                            dialogb.setView(imgview);
//                            dialogb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                            dialog = dialogb.create();

                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }

                        MarkerOptions newMarker = new MarkerOptions().position(new LatLng(lat,lng));
                        newMarker.title(locations.get(i).getObjectId());
                        newMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
                        map.addMarker(newMarker);
                    }
                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Locations");
                            query.whereEqualTo("objectId", marker.getTitle());
                            query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> locations, ParseException e) {
                                    ParseFile fileGambar = (ParseFile) locations.get(0).get("photo");
                                    fileGambar.getDataInBackground(new GetDataCallback() {
                                        @Override
                                        public void done(byte[] data, ParseException e) {
                                            if(e == null){
                                                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                Log.i("SEMOGA JADIIIII", bitmap.toString());
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "Tidak dapat " +
                                                        "mengambil gambar", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            });

                            AlertDialog.Builder dialogb = new AlertDialog.Builder(MapActivity.this);
                            LayoutInflater inflater = LayoutInflater.from(MapActivity.this);
                            imgview = inflater.inflate(R.layout.image_view, null);
                            img = (ImageView) imgview.findViewById(R.id.markerImage);
                            img.setImageBitmap(bitmap);
                            dialogb.setTitle(addresses.get(0).getFeatureName());
                            dialogb.setView(imgview);
                            dialogb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                            dialog = dialogb.create();
                            dialog.show();
                            return false;
                        }
                    });
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Nama file gambar agar unik.
                Date date = new Date();
                namaFile = DateFormat.format("yyyy_MM_dd_HH_mm_ss", date.getTime());

                startActivityForResult(intent, camRequestCode);
                return true;
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == camRequestCode) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Intent intent = new Intent(this, UploadActivity.class);
                intent.putExtra("bitmap", bitmap);
                intent.putExtra("namaFile", namaFile);
                startActivity(intent);
            }
        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, R.string.error_message_general, Toast.LENGTH_LONG).show();
        }
    }
}
