package com.ifunsoed13.rpl.lapar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class UploadActivity extends AppCompatActivity {

    ImageView imgDisplay;
    Button btnKirim, btnBatal;
    double lat, lng;

    protected ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmap");
        final CharSequence namaFile = intent.getExtras().getCharSequence("namaFile");
        imgDisplay = (ImageView) findViewById(R.id.imgDisplay);
        imgDisplay.setImageBitmap(bitmap);

        btnKirim = (Button) findViewById(R.id.btnKirim);
        btnBatal = (Button) findViewById(R.id.btnBatal);

        GPSTracker gps = new GPSTracker(this);
        lat = gps.getLatitude();
        lng = gps.getLongitude();

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(UploadActivity.this, "", "Mengunggah" +
                        " gambar", true);

                // Ubah ke byteStream.
                ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, arrayStream);
                byte[] byteArray = arrayStream.toByteArray();

                if (byteArray != null) {
                    ParseFile file = new ParseFile(namaFile + ".jpg", byteArray);
                    file.saveInBackground();

                    ParseObject spot = new ParseObject("Locations");
                    spot.put("photo", file);
                    spot.put("latitude", lat);
                    spot.put("longitude", lng);
                    spot.put("uploader", ParseUser.getCurrentUser().getUsername());
                    spot.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            loading.dismiss();

                            if (e == null) {
                                // sucses.
                                Toast.makeText(getApplicationContext(), "sukses", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                // err.
                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
