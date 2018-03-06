package com.demo_google_integration.pulkit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class LogoutActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button googleLogout;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleApiClient googleApiClient;
    private ImageView userImage;
    private TextView userName, userEmail, userGender;
    private Intent getIntentData;
    private String user_name, user_pic, user_email, user_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        getIntentData = getIntent();
        user_name = getIntentData.getStringExtra("user_name");
        user_pic = getIntentData.getStringExtra("user_pic");
        user_email = getIntentData.getStringExtra("user_email");

        googleLogout = (Button) findViewById(R.id.google_logout);
        userImage = (ImageView) findViewById(R.id.user_pic);
        userName = (TextView) findViewById(R.id.user_name);
        userEmail = (TextView) findViewById(R.id.user_email);
        userGender = (TextView) findViewById(R.id.user_gender);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        if (userImage != null) {
            Glide.with(getApplicationContext())
                    .load(user_pic)
                    .thumbnail(0.9f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(userImage);

            userName.setText(user_name);
            userEmail.setText(user_email);

        }

        googleLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
