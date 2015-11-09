package com.ifunsoed13.rpl.lapar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected EditText mEmail;
    protected Button mSignup;
    protected Button mCancel;
    protected ProgressDialog loading;

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
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String email = mEmail.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setTitle(R.string.error_title);
                    builder.setMessage(R.string.error_message_signup);
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    loading = ProgressDialog.show(SignupActivity.this, "", "Sedang memuat...", true);

                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setEmail(email);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            loading.dismiss();
                            
                            if (e == null) {
                                // Success
                                Intent intent = new Intent(SignupActivity.this, MapActivity.class);
                                startActivity(intent);
                                LoginActivity.mActivity.finish();
                                finish();
                            } else {
                                // Error
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
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
                }
            }
        });
    }
}
