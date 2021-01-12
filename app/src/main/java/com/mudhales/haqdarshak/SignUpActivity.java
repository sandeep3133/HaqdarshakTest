package com.mudhales.haqdarshak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mudhales.haqdarshak.data.UserData;
import com.mudhales.haqdarshak.utils.LocalDatabase;
import com.mudhales.haqdarshak.utils.SharedPreferenceManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName, edtGender, edtAge, edtEmail, edtMobile, edtPassword;
    private LocalDatabase database;
    private ImageView imgProfile;
    private Uri mCropImageUri;
    private String strImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setUpUi();
    }

    private void setUpUi() {
        database = new LocalDatabase(this);
        imgProfile = findViewById(R.id.imgProfile);
        edtName = findViewById(R.id.edtName);
        edtName = findViewById(R.id.edtName);
        edtGender = findViewById(R.id.edtGender);
        edtAge = findViewById(R.id.edtAge);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        edtPassword = findViewById(R.id.edtPassword);
        imgProfile.setOnClickListener(this);
        findViewById(R.id.btnSubmit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgProfile:
                selectImage();
                break;
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
                                edtMobile.getText().toString().trim(), edtPassword.getText().toString().trim(), strImageUrl);
                        SharedPreferenceManager.with(this).setUserLoggedIn(true);
                        database.getUserRecord(edtMobile.getText().toString().trim());
                        UserData data = database.checkLoginUser(edtMobile.getText().toString().trim(), edtPassword.getText().toString().trim());
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

    private void selectImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            showMessage("Need Permission to access and upload your Image");
        } else {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1) //You can skip this for free form aspect ratio)
                    .start(this);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                // Set uri as Image in the ImageView:
                imgProfile.setImageURI(resultUri);
                strImageUrl=resultUri.toString();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                strImageUrl="";
            }
        }
    }
}