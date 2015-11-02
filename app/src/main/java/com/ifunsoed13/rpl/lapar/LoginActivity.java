package com.ifunsoed13.rpl.lapar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;
    Button mLogin;
    TextView mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.input_login_username);
        mPassword = (EditText) findViewById(R.id.input_login_password);

        mLogin = (Button) findViewById(R.id.button_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = (mUsername == null) ? "" : mUsername.getText().toString().trim();
                String password = (mPassword == null) ? "" : mPassword.getText().toString().trim();
                String message = "Logging in:\n" + username + "\n" + password;
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        mSignup = (TextView) findViewById(R.id.label_go_to_signup);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
