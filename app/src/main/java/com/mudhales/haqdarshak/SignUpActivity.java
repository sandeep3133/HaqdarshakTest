package com.mudhales.haqdarshak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mudhales.haqdarshak.data.UserData;
import com.mudhales.haqdarshak.utils.LocalDatabase;
import com.mudhales.haqdarshak.utils.SharedPreferenceManager;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName, edtGender, edtAge, edtEmail, edtMobile, edtPassword;
    private LocalDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setUpUi();
    }

    private void setUpUi() {
        database = new LocalDatabase(this);
        edtName = findViewById(R.id.edtName);
        edtGender = findViewById(R.id.edtGender);
        edtAge = findViewById(R.id.edtAge);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtPassword = findViewById(R.id.edtPassword);
        findViewById(R.id.btnSubmit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                if (TextUtils.isEmpty(edtName.getText().toString()) || TextUtils.isEmpty(edtGender.getText().toString()) ||
                        TextUtils.isEmpty(edtAge.getText().toString()) || TextUtils.isEmpty(edtEmail.getText().toString()) ||
                        TextUtils.isEmpty(edtMobile.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())) {
                    // Toast.makeText(this,"This field is required.",Toast.LENGTH_SHORT).show();
                    showMessage("Please enter required fields.");
                } else {
                    if (!database.checkUserExit(edtMobile.getText().toString().trim()))
                        showMessage("Mobile number already exit.");
                    else {
                        database.addRecords(edtName.getText().toString().trim(), edtAge.getText().toString().trim(),
                                edtGender.getText().toString().trim(), edtEmail.getText().toString().trim(),
                                edtMobile.getText().toString().trim(), edtPassword.getText().toString().trim(), "");
                        SharedPreferenceManager.with(this).setUserLoggedIn(true);
                        database.getUserRecord(edtMobile.getText().toString().trim());
                        UserData data = database.checkLoginUser(edtMobile.getText().toString().trim(),edtPassword.getText().toString().trim());
                        SharedPreferenceManager.with(this).updateLoggedInUser(data);
                        registerUser();
                    }
                }

                break;
        }
    }

    private void registerUser() {
        this.finish();
        startActivity(new Intent(this, HomeActivity.class));
    }

    private void showMessage(String strMessage) {
        Snackbar snackbar = Snackbar
                .make(edtName.getRootView(), strMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}