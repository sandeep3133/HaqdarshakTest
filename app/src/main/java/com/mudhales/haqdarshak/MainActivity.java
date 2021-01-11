package com.mudhales.haqdarshak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.mudhales.haqdarshak.data.UserData;
import com.mudhales.haqdarshak.ui.HomeFragment;
import com.mudhales.haqdarshak.utils.LocalDatabase;
import com.mudhales.haqdarshak.utils.SharedPreferenceManager;
import com.mudhales.haqdarshak.utils.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUserName, edtPassword;
    private LocalDatabase database;
    private Button btnLogin;
    private boolean isViewPass = false;
    private ImageView imgPasswordStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (SharedPreferenceManager.with(this).isUserLoggedIn()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else setUpUi();
    }



    private void setUpUi() {
        database = new LocalDatabase(this);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        imgPasswordStatus = findViewById(R.id.imgPasswordStatus);
        imgPasswordStatus.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        findViewById(R.id.btnRegister).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                checkLogin();
                break;
            case R.id.btnRegister:
                startActivity(new Intent(this,SignUpActivity.class));
                break;
            case R.id.imgPasswordStatus:
                visibilityPassword();
                break;
        }
    }
    private void visibilityPassword(){
        if (isViewPass) {
            isViewPass = false;
            // hide password
            edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imgPasswordStatus.setImageResource(R.drawable.ic_visibility_black_24dp);
        } else {
            isViewPass = true;
            // show password
            edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imgPasswordStatus.setImageResource(R.drawable.ic_visibility_off_black_24dp);

        }
    }
    private void checkLogin() {
        //validateUserBlock();
        if (TextUtils.isEmpty(edtUserName.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {
            showMessage("Please enter username & password.");
        } else {
            UserData data = database.checkLoginUser(edtUserName.getText().toString().trim(), edtPassword.getText().toString().trim());
            if (data != null && data.getMobile() != null) {
                SharedPreferenceManager.with(this).setUserLoggedIn(true);
                database.getUserRecord(edtUserName.getText().toString().trim());
                SharedPreferenceManager.with(this).updateLoggedInUser(data);
                startActivity(new Intent(this, HomeActivity.class));
                SharedPreferenceManager.with(this).setLoginCount(false);
            } else {
                showMessage("Please check your login credentials.");
                SharedPreferenceManager.with(this).setLoginCount(true);
            }
        }
    }

    private void validateUserBlock() {
        if(SharedPreferenceManager.with(this).loginCount()>5 && SharedPreferenceManager.with(this).getLoginBlockTime()>(System.currentTimeMillis()-300000))
        {
            btnLogin.setVisibility(View.INVISIBLE);
            showMessage("Please wait for 3 min to re attempts.");
        }
        else
        {
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    private void showMessage(String strMessage) {
        Snackbar snackbar = Snackbar
                .make(edtUserName.getRootView(), strMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}