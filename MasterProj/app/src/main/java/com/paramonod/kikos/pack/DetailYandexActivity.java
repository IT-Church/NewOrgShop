package com.paramonod.kikos.pack;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.paramonod.kikos.R;

/**
 * Created by KiKoS on 08.04.2017.
 */

    public class DetailYandexActivity extends AppCompatActivity {

        public static final String DESC = "q";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Set Collapsing Toolbar layout to the screen
            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            // Set title of Detail page
            // collapsingToolbar.setTitle(getString(R.string.item_title));

            String desc = getIntent().getStringExtra(DESC);
            String loc = getIntent().getStringExtra("loc");
            Resources resources = getResources();

            collapsingToolbar.setTitle("");


            TextView placeDetail = (TextView) findViewById(R.id.place_detail);
            placeDetail.setText(desc);


            TextView placeLocation =  (TextView) findViewById(R.id.place_location);
            placeLocation.setText(loc);


            ImageView placePicutre = (ImageView) findViewById(R.id.image);
            placePicutre.setImageDrawable(resources.getDrawable(R.drawable.ymk_ya_logo));
        }


}
