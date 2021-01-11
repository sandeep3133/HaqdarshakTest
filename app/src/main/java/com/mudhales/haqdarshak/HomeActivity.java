package com.mudhales.haqdarshak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.mudhales.haqdarshak.ui.HomeFragment;
import com.mudhales.haqdarshak.utils.SharedPreferenceManager;
import com.mudhales.haqdarshak.utils.Utils;

public class HomeActivity extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        view = findViewById(R.id.screenContainer);

        installListener();
        // Added fragment for list data by SM
        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.screenContainer, new HomeFragment()).commit();

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    private void installListener() {
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (!Utils.isInternetConnectionAvailable(context)) {
                        showMessage(getString(R.string.no_internet));
                    }
                }
            };
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }
    private void showMessage(String strMessage){
        Snackbar snackbar = Snackbar
                .make(view, strMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.screenContainer);
        if (fragment instanceof HomeFragment)
            finish();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.screenContainer, new HomeFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        SharedPreferenceManager.with(this).logout();
        finish();
    }
}