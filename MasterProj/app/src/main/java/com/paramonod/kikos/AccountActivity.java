package com.paramonod.kikos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        MainActivity.navigationView.setCheckedItem(R.id.mapButton);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.navigationView.setCheckedItem(R.id.mapButton);
    }
}