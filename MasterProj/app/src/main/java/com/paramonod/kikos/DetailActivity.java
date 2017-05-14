

package com.paramonod.kikos;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

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

        //collapsingToolbar.setTitle(MainActivity.shopInterfaces.get(postion).getName());

        collapsingToolbar.setTitle(MainActivity.shopInterfaces.get(postion).getName());

        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText(MainActivity.shopInterfaces.get(postion).getDescription());

TextView placeLocation =  (TextView) findViewById(R.id.place_location);
        placeLocation.setText(MainActivity.shopInterfaces.get(postion).getStreet() + " " +MainActivity.shopInterfaces.get(postion).getHouse());

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
            Picasso.with(main)
                    .load(MainActivity.shopInterfaces.get(postion).getPictureName())
                    .placeholder(R.drawable.ymk_tlight_loading)
                    .into(placePicutre);

        //placePicutre.setImageDrawable(placePictures.getDrawable(postion % placePictures.length()));
        Picasso.with(main)
                .load(MainActivity.shopInterfaces.get(postion).getPictureName())
                .placeholder(R.drawable.ymk_tlight_loading)
                .into(placePicutre);
        //placePictures.recycle();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
