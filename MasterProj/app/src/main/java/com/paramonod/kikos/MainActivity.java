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

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import 	android.support.v4.*;
import android.app.SearchableInfo;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.*;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import com.example.android.materialdesigncodelab.R;
import com.paramonod.kikos.pack.Adress;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.map.GeoCode;
import ru.yandex.yandexmapkit.map.GeoCodeListener;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;


/**
 * Provides UI for the main screen.
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    final public FragmentManager Manager = getSupportFragmentManager();
    final public MapViewFragment MapFr = new MapViewFragment();
    final public ProgressView PrFr = new ProgressView();
    final public CardContentFragment Cardfr = new CardContentFragment();
    final public ListContentFragment1 Listfr = new ListContentFragment1();
    public int x;

    public static MainActivity main;
    public static Context ctx;
    public static MapController mc;
    public static MapView mp;
    public static Overlay o;
    public static OverlayManager om;
    public static JSONObject jsonObject;
    public static Drawable shop;
    public static Drawable itkerk;
    public static android.widget.SearchView searchView;
    final static float STANDART_ZOOM = 20.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("OrgShop");
        // Adding Toolbar to Main screen
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        Manager.findFragmentById(R.id.fragment1);
        Manager.beginTransaction()
                .replace(R.id.fragment1, MapFr)
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //AsyncTask asyncTask = new NewAsyncTask();
                        switch (item.getItemId()) {
                            case R.id.Map:
                                Manager.beginTransaction()
                                        .replace(R.id.fragment1, PrFr)
                                        .commit();
                                Manager.beginTransaction()
                                        .replace(R.id.fragment1, MapFr)
                                        .commit();
                                //asyncTask.execute(R.id.Map);
                                break;
                            case R.id.Shops:
                                Manager.beginTransaction()
                                        .replace(R.id.fragment1, PrFr)
                                        .commit();
                                Manager.beginTransaction()
                                        .replace(R.id.fragment1, Listfr)
                                        .commit();
                                //x = R.id.Shops;
                                //asyncTask.execute();
                                break;
                            case R.id.Third:
                                Manager.beginTransaction()
                                        .replace(R.id.fragment1, PrFr)
                                        .commit();
                                Manager.beginTransaction()
                                        .replace(R.id.fragment1, Cardfr)
                                        .commit();

                                // asyncTask.execute(R.id.Third);
                                break;
                        }
                        return false;
                    }
                });
        // Setting ViewPager for each Tabs

        // Set Tabs inside Toolbar
        // Create Navigation drawer and inlfate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), R.color.white, getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        //Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
        // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);

                        // TODO: handle navigation

                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
        // Adding Floating Action Button to bottom right of main view
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hello Snackbar!",
                        Snackbar.LENGTH_LONG).show();
            }
        });*/

    }
 /*   public class NewAsyncTask extends AsyncTask<Void, Void, Void>{
        FragmentTransaction ft = Manager.beginTransaction();
        @Override
        protected Void doInBackground(Void... params) {
            //int x = params[0];
            //int x = 1;
            switch (x) {
                case R.id.Map:
                            ft.replace(R.id.fragment1, MapFr);
                    break;
                case R.id.Shops:
                            ft.replace(R.id.fragment1, Listfr);
                    break;
                case R.id.Third:
                            ft.replace(R.id.fragment1, Cardfr);
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ft.commit();
        }

    }*/ {
     // Add Fragments to Tabs
   /* private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ListContentFragment(), "Shops");
        adapter.addFragment(new MapViewFragment(), "MapView");
        adapter.addFragment(new CardContentFragment(), "Third");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/
 }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_settings));
        System.out.println(searchView);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               main.searchListener(query);
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });


        return  super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    public void setupMap(){
        searchView =(android.widget.SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                main.searchListener(searchView.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mp = (MapView)findViewById(R.id.map);
        mp.showBuiltInScreenButtons(true);
        mc = mp.getMapController();
        mc.setPositionAnimationTo(new GeoPoint(new Adress().getP(), new Adress().getM()));
        mc.setZoomCurrent(20);
        FloatingActionButton plus = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        FloatingActionButton minus = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        plus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mc.setZoomCurrent(mc.getZoomCurrent() + 0.1f);

                return false;
            }
        });
        minus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mc.setZoomCurrent(mc.getZoomCurrent() - 0.1f);
                return false;
            }
        });

        om = mc.getOverlayManager();
        o = new Overlay(mc);

        o.setVisible(true);
        om.addOverlay(o);
        OverlayItem oi = new OverlayItem(new GeoPoint(new Adress().getP(), new Adress().getM()),
                getResources().getDrawable(R.drawable.shop));

        mc.getDownloader().getGeoCode(new GeoCodeListener() {
            @Override
            public boolean onFinishGeoCode(final GeoCode geoCode) {
                if(geoCode!=null){
                    Log.d("Not so fucking","title"+geoCode.getTitle()+"\nsubtitle"+geoCode.getSubtitle()+"\ndisplayname"+geoCode.getDisplayName()+"\nkind"+geoCode.getKind());

                }
                else{
                    Log.e("OMFG","fail");
                }
                return true;
            }
        },oi.getGeoPoint());
        makingFullStackIcon(R.drawable.orpgshop,50,50,oi.getGeoPoint());
        OverlayItem oi2 = new OverlayItem(new GeoPoint(new Adress().getP() + 0.0001, new Adress().getM() + 0.0001), getResources().getDrawable(R.drawable.shop));
        o.addOverlayItem(oi);
        o.addOverlayItem(oi2);
        // Set behavior of Navigation drawer
        //navigationView.setNavigationItemSelectedListener(
        //    new NavigationView.OnNavigationItemSelectedListener() {
        // This method will trigger on item Click of navigation menu
        /*            @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);

                        // TODO: handle navigation

                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hello Snackbar!",
                        Snackbar.LENGTH_LONG).show();
            }
        });*/
    }
    public  void makingFullStackIcon(int id, int width, int height,GeoPoint geoPoint){
        this.makingFullStackIcon(id,width,height,geoPoint,main.getString(R.string.itch__kerk),"");
    }
    public void makingFullStackIcon(int id, int width, int height, GeoPoint geoPoint, final String name, final String description){
        OverlayItem oi = new OverlayItem(geoPoint,this.createScaledIcon(main.getResources().getDrawable(id),width,height,main.getResources()));
        BalloonItem bi = new BalloonItem(main, oi.getGeoPoint());
        bi.setOnBalloonListener(
                new OnBalloonListener() {
                    @Override
                    public void onBalloonViewClick(BalloonItem balloonItem, View view) {
                    }

                    @Override
                    public void onBalloonShow(BalloonItem balloonItem) {
                        Intent intent = new Intent(main, DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_POSITION, 0 + (int)Math.random() * 6);
                        startActivity(intent);
                    }

                    @Override
                    public void onBalloonHide(BalloonItem balloonItem) {

                    }

                    @Override
                    public void onBalloonAnimationStart(BalloonItem balloonItem) {


                    }

                    @Override
                    public void onBalloonAnimationEnd(BalloonItem balloonItem) {

                    }
                }
        );
        oi.setBalloonItem(bi);
        o.addOverlayItem(oi);

    }
    public  void searchListener(String a) {
        try {
            Search s = new Search();
            updatePins(null);
            s.doSearch(a,this);
        } catch (MalformedURLException q) {
            q.printStackTrace();
        }
    }

    public static Drawable createScaledIcon(Drawable id, int width, int height, Resources res){
        Bitmap bitmap = ((BitmapDrawable)id ).getBitmap();
        // Scale it to 50 x 50
        shop = new BitmapDrawable(res, Bitmap.createScaledBitmap(bitmap, width, height, true));
        return shop;
    }

    public void updatePins(GeoPoint[] overlayItems) {
        // List<Overlay> l = mc.getOverlayManager().getOverlays();
        // for (Overlay q : l) {
        //     q.setVisible(false);
        // }

        //o = new Overlay(mc);
        o.clearOverlayItems();
        if(overlayItems != null){
            for (int i = 0; i < overlayItems.length; i++) {
                this.makingFullStackIcon(R.drawable.orpgshop,55,55,overlayItems[i]);
            }}
        o.setVisible(true);

        //  om.addOverlay(o);
    }
}
