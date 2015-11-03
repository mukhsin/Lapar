package com.ifunsoed13.rpl.lapar;

import android.app.Application;

import com.parse.Parse;

public class LaparApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "1RY5h0ZyEZPnvVXxEsQBmiBkx1IeZAQbYSUcpeAX", "HL3AAI9OMB16CDhO0DaKZOjZHvfz0qILiBygEVm1");
    }
}
