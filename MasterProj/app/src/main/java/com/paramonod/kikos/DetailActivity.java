/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paramonod.kikos;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigncodelab.R;
import com.squareup.picasso.Picasso;

import static com.paramonod.kikos.MainActivity.main;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));
        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();

        collapsingToolbar.setTitle(MainActivity.shopInterfaces.get(postion).getName());

        collapsingToolbar.setTitle(MainActivity.shopInterfaces.get(postion).getName());

        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText(MainActivity.shopInterfaces.get(postion).getDescription());

TextView placeLocation =  (TextView) findViewById(R.id.place_location);
        placeLocation.setText(MainActivity.shopInterfaces.get(postion).getStreet() + " " +MainActivity.shopInterfaces.get(postion).getHouse());

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
            Picasso.with(main)
                    .load(MainActivity.shopInterfaces.get(postion).getPictureName())
                    .into(placePicutre);

        //placePicutre.setImageDrawable(placePictures.getDrawable(postion % placePictures.length()));
        Picasso.with(main)
                .load(MainActivity.shopInterfaces.get(postion).getPictureName())
                .into(placePicutre);
        //placePictures.recycle();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
