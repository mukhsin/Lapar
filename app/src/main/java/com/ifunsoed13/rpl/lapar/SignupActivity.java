package com.ifunsoed13.rpl.lapar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;
    EditText mEmail;
    Button mSignup;
    Button mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUsername = (EditText) findViewById(R.id.input_signup_username);
        mPassword = (EditText) findViewById(R.id.input_signup_password);
        mEmail = (EditText) findViewById(R.id.input_signup_email);

        mCancel = (Button) findViewById(R.id.button_signup_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSignup = (Button) findViewById(R.id.button_signup);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = (mUsername == null) ? "" : mUsername.getText().toString().trim();
                String password = (mPassword == null) ? "" : mPassword.getText().toString().trim();
                String email = (mEmail == null) ? "" : mEmail.getText().toString().trim();
                String message = "Signing in:\n" + username + "\n" + password + "\n" + email;
                Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
