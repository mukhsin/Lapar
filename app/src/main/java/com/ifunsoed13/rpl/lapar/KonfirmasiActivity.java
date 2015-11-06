package com.ifunsoed13.rpl.lapar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class KonfirmasiActivity extends AppCompatActivity {

    ImageView imgDisplay;
    Button btnKirim, btnBatal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);

        Intent intent = getIntent();
        final Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmap");
        final String namaFile = intent.getExtras().getString("namaFile");
        imgDisplay = (ImageView) findViewById(R.id.imgDisplay);
        imgDisplay.setImageBitmap(bitmap);

        btnKirim = (Button) findViewById(R.id.btnKirim);
        btnBatal = (Button) findViewById(R.id.btnBatal);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ubah ke byteStream.
                ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, arrayStream);
                byte[] byteArray = arrayStream.toByteArray();

                if (byteArray != null) {
                    ParseFile file = new ParseFile(namaFile + ".jpg", byteArray);
                    file.saveInBackground();

                    ParseObject spot = new ParseObject("Locations");
                    spot.put("photo", file);
                    spot.put("latitude", 0);
                    spot.put("longtitude", 0);
                    spot.put("uploader", ParseUser.getCurrentUser().getUsername());
                    spot.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // sucses.
                                Toast.makeText(getApplicationContext(), "sukses", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                                startActivity(i);
                            } else {
                                // err.
                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
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
