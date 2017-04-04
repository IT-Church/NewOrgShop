package com.paramonod.kikos;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigncodelab.R;


public class Empty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        ImageView iw = (ImageView) findViewById(R.id.kartinka);
        iw.setImageDrawable(MapActivity.itkerk);
        TextView tw = (TextView) findViewById(R.id.textView);
        tw.setTextSize(25.0f);
        tw.setText(getIntent().getStringExtra("description"));
        TextView address = (TextView) findViewById(R.id.geocode);
        //   Log.d("FYCK",getIntent().getStringExtra("geocode"));
        address.setText(getIntent().getStringExtra("geocode"));

    }
}
