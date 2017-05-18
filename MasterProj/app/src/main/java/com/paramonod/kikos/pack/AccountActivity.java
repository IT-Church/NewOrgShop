package com.paramonod.kikos.pack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paramonod.kikos.MainActivity;
import com.paramonod.kikos.R;

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
