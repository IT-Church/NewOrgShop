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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ObbInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.*;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import com.example.android.materialdesigncodelab.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.paramonod.kikos.pack.Adress;
import com.paramonod.kikos.pack.Image;
import com.paramonod.kikos.pack.ShopInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
import ru.yandex.yandexmapkit.utils.Point;

import static ru.yandex.core.CoreApplication.getActivity;


/**
 * Provides UI for the main screen.
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    public static int AsyncTest = -1;
    final public FragmentManager Manager = getSupportFragmentManager();
    final public MapViewFragment MapFr = new MapViewFragment();
    final public ProgressView PrFr = new ProgressView();
    final public CardContentFragment Cardfr = new CardContentFragment();
    final public ListContentFragment1 Listfr = new ListContentFragment1();

    final public CategoryContentFragment CatFr = new CategoryContentFragment();
    public int x;
    public static BottomNavigationView bottomNavigationView;
    public static Intent intent;
    public static String namme;
    public static SharedPreferences sPref;
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
    public static NavigationView navigationView;
    final static float STANDART_ZOOM = 20.0f;
    public static String name;
    public static Drawable[] bitmap;
    public static String names[];
    public static String urls[];
    public int X;
    public int Y;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    public static GeoPoint my;
    public static final ArrayList<ShopInterface> shopInterfaces = new ArrayList<ShopInterface>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        myRef = FirebaseDatabase.getInstance().getReference("Shops");
        //ShopInterface shopInterface = new ShopInterface("asas","qaqa","wdwdwd","12","11","11");
        //myRef.child("Shops").child("1").setValue(shopInterface);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously();
        myRef.keepSynced(true);
        myRef.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                    shopInterfaces.add(snapshot.getValue(ShopInterface.class));
                main.setupMap();
                //System.out.println(shopInterfaces.get(1).getCoordX());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mAuth.signOut();
        main = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("OrgShop");
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        Display display = getWindowManager().getDefaultDisplay();
        android.graphics.Point size = new android.graphics.Point();
        display.getSize(size);
        X = size.x;
        Y = size.y;
        //  Y = displaymetrics.heightPixels;
        //  X = displaymetrics.widthPixels;
        System.out.println(X + " " + Y);
        // Adding Toolbar to Main screen
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        Manager.findFragmentById(R.id.fragment1);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //AsyncTask asyncTask = new NewAsyncTask();
                        switch (item.getItemId()) {
                            case R.id.Map:
                                //     Manager.beginTransaction()
                                //             .replace(R.id.fragment1, PrFr)
                                //             .commit();
                                Manager.beginTransaction()
                                        .replace(R.id.fragment1, MapFr)
                                        .addToBackStack("Map")
                                        .commit();
                                //asyncTask.execute(R.id.Map);
                                break;
                            case R.id.Shops:
                                //     Manager.beginTransaction()
                                //            .replace(R.id.fragment1, PrFr)
                                //            .commit();

                                //  Listfr.flag = 0;
                                Manager.beginTransaction()
                                        .replace(R.id.fragment1, CatFr)
                                        .addToBackStack("Categories")
                                        .commit();
                                //x = R.id.Shops;
                                //asyncTask.execute();
                                break;
                            case R.id.Third:
                                //    Manager.beginTransaction()
                                //           .replace(R.id.fragment1, PrFr)
                                //           .commit();
                                //     Manager.beginTransaction()
                                //             .replace(R.id.fragment1, PrFr)
                                //             .commit();
                                //      System.out.println(0);
                                int idx[] = new int[shopInterfaces.size()];
                                for (int i = 0; i < idx.length; i++) {
                                    idx[i] = i;
                                }
                                idx = sortArraywithGeo(idx);
                                CardContentFragment.flag = 1;
                                CardContentFragment.idx = idx;
                                Manager.beginTransaction()
                                        .replace(R.id.fragment1, Cardfr)
                                        .addToBackStack(null)
                                        .commit();
                                //new ImageLoad().execute();
                                //while (AsyncTest == 0){}
                                //System.out.println(1);
                                //Picturex();
                                //AsyncTest = -1;
                              /* Manager.beginTransaction()
                                        .replace(R.id.fragment1, Listfr)
                                        .commit();
*/
                                // asyncTask.execute(R.id.Third);
                                break;
                        }
                        return false;
                    }
                });

        // Setting ViewPager for each Tabs

        // Set Tabs inside Toolbar
        // Create Navigation drawer and inlfate layout

        navigationView = (NavigationView) findViewById(R.id.nav_view);
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
                        if (menuItem.getItemId() == R.id.favorite_button) {
                            sPref = getPreferences(MODE_PRIVATE);
                            String savedText = sPref.getString("q", "null");
                            if (savedText.equals(""))

                                Toast.makeText(main, "У вас пока что нет любимых магазинов", Toast.LENGTH_LONG).show();
                            else {
                                String[] q = savedText.split(" ");
                                int[] a = new int[q.length];
                                for (int i = 0; i < q.length; i++) {
                                    a[i] = Integer.parseInt(q[i]);
                                    System.out.println(a[i]);
                                }
                                a = sortArraywithGeo(a);
                                Toast.makeText(main, savedText, Toast.LENGTH_SHORT).show();
                                ListContentFragment l = new ListContentFragment();
                                l.flag = 1;
                                l.idx = a;
                                //  Manager.beginTransaction()
                                //          .replace(R.id.fragment1, PrFr)
                                //          .commit();
                                Manager.beginTransaction()
                                        .replace(R.id.fragment1, l)
                                        .addToBackStack("favorite")
                                        .commit();
                            }
                        }
                        if (menuItem.getItemId() == R.id.mapButton) {
                            //  Manager.beginTransaction()
                            //          .replace(R.id.fragment1, PrFr)
                            //          .commit();
                            Manager.beginTransaction()
                                    .replace(R.id.fragment1, MapFr)
                                    .addToBackStack("map")
                                    .commit();
                        }
                        if (menuItem.getItemId() == R.id.clear_button) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Разрабы")
                                    .setMessage("Для вас старались Амеличев Константин aka KiKoS, Парамонов Дмитрий aka paramomnod")
                                    .setIcon(R.drawable.itkerk)
                                    .setCancelable(false)
                                    .setNegativeButton("Я вам оч благодарен :)",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alert = builder.create();
                            alert.show();

                        }// Closing drawer on item click
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
        Manager.beginTransaction()
                .replace(R.id.fragment1, MapFr)
                .commit();
    }


    /*class ImageLoad extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AsyncTest = 0;
        }

        @Override
        protected Void doInBackground(Void... params) {
            final StorageReference storageRef = storage.getReference();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    for (int i = 1; i < 7; i++) {
                        String avatorPath = "a" + Integer.toString(i) + "_avator";
                        StorageReference avatorRef = storageRef.child(avatorPath + ".png");
                        File temp = new File(avatorPath);
                        File localFile = null;
                        if (!temp.isFile()) {
                            try {
                                localFile = File.createTempFile(avatorPath, "png", getExternalCacheDir());
                                final File finalLocalFile = localFile;
                                avatorRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        System.out.println("Q: What do you call it when a dinosaur gets in a car accident? \nA: Tyrannasaurus wreck! ");
                                        Toast.makeText(getApplicationContext(), "Q: What do you call it when a dinosaur gets in a car accident? \nA: Tyrannasaurus wreck! ", Toast.LENGTH_SHORT);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            System.out.println("Q: What do you call it when a dinosaur gets in a car accident? \nA: Tyrannasaurus wreck! ");
            Toast.makeText(getApplicationContext(), "Q: What do you call it when a dinosaur gets in a car accident? \nA: Tyrannasaurus wreck! ", Toast.LENGTH_SHORT);
            AsyncTest = 1;
        }
    }*/

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
                Menu menu = bottomNavigationView.getMenu();
                Menu mm = navigationView.getMenu();
                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();
                Fragment f = null;
                System.out.println(fragments);
                if(fragments != null){
                    for(Fragment fragment : fragments){
                        if(fragment != null && fragment.isVisible())
                           f = fragment;
                        System.out.println(f);                    }
                }
                if(f instanceof CardContentFragment){
                    main.searchListener(query, 2);

                }
                if(f instanceof CategoryContentFragment){
                    main.searchListener(query, 1);

                }
                if(f instanceof MapViewFragment){
                    main.searchListener(query, 0);

                }
                if(f instanceof ListContentFragment)
                {
                    main.searchListener(query,3);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
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

    public void setupMap() {
        /*searchView =(android.widget.SearchView) findViewById(R.id.search);
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
 */
        mp = (MapView) findViewById(R.id.map);
        mp.showBuiltInScreenButtons(true);
        mc = mp.getMapController();
       // mc.setPositionAnimationTo(new GeoPoint(new Adress().getCoordX(), new Adress().getCoordY()));
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
      /*  OverlayItem oi = new OverlayItem(new GeoPoint(new Adress().getP(), new Adress().getM()),
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
        //   Log.e("points","here");
        //     Log.e("points",Integer.toString(shopInterfaces.size()));
        GeoPoint[] g = new GeoPoint[shopInterfaces.size()];
        for (int i = 0; i < g.length; i++) {
            g[i] = new GeoPoint(shopInterfaces.get(i).getCoordX(), shopInterfaces.get(i).getCoordY());
            Log.e("points", g[i].toString());
        }
        updatePins(g);
      //  try {
        //   my = mc.getOverlayManager().getMyLocation().getMyLocationItem().getGeoPoint();
      //      mc.setPositionAnimationTo(my);
     //   } catch (NullPointerException e) {
     //       System.out.println("КМС по ловле NPE");
     //   }
    }


    public void makingFullStackIcon(int id, int width, int height, GeoPoint geoPoint) {
        OverlayItem oi = new OverlayItem(geoPoint, this.createScaledIcon(main.getResources().getDrawable(id), width, height, main.getResources()));
        BalloonItem bi = new BalloonItem(main, oi.getGeoPoint());
        if (id == R.drawable.shop) {
            bi.setOnBalloonListener(
                    new OnBalloonListener() {
                        @Override
                        public void onBalloonViewClick(BalloonItem balloonItem, View view) {
                        }

                        @Override
                        public void onBalloonShow(BalloonItem balloonItem) {
                            Intent intent = new Intent(main, DetailActivity.class);
                            int m = 0;
                            for (int i = 0; i < shopInterfaces.size(); i++) {
                                GeoPoint g = new GeoPoint(shopInterfaces.get(i).getCoordX(), shopInterfaces.get(i).getCoordY());
                                if (g.equals(balloonItem.getGeoPoint())) {
                                    m = i;
                                    Log.e("Search", "got here" + Integer.toString(m));
                                }
                            }
                            intent.putExtra(DetailActivity.EXTRA_POSITION, m);
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
        } else {
            bi.setOnBalloonListener(
                    new OnBalloonListener() {
                        @Override
                        public void onBalloonViewClick(BalloonItem balloonItem, View view) {
                        }

                        @Override
                        public void onBalloonShow(BalloonItem balloonItem) {
                            mc.getDownloader().getGeoCode(new GeoCodeListener() {
                                @Override
                                public boolean onFinishGeoCode(final GeoCode geoCode) {
                                    if (geoCode != null) {
                                        Log.e("Not so fucking", "title" + geoCode.getTitle() + "\nsubtitle" + geoCode.getSubtitle() + "\ndisplayname" + geoCode.getDisplayName() + "\nkind" + geoCode.getKind());
                                        main.name = geoCode.getTitle();
                                    } else {
                                        Log.e("OMFG", "fail");
                                    }
                                    AsyncTask asyncTask = new AsyncTask() {
                                        @Override
                                        protected Object doInBackground(Object[] params) {
                                            String namme = "";
                                            try {
                                                URL url = new URL("https://search-maps.yandex.ru/v1/?text=" + params[0] + "&type=biz&lang=ru_RU&apikey=245e2b86-5cfb-40c3-a064-516c37dba6b2");

                                                System.out.println(url);
                                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                                con.connect();
                                                // optional default is GET
                                                con.setRequestMethod("GET");
                                                //int responseCode = con.getResponseCode(); if smth crashes
                                                BufferedReader in = new BufferedReader(
                                                        new InputStreamReader(con.getInputStream()));
                                                String inputLine;
                                                String response = "";

                                                while ((inputLine = in.readLine()) != null) {
                                                    response += inputLine;
                                                }
                                                in.close();
                                                con.disconnect();
                                                MapActivity.jsonObject = new JSONObject(response);
                                                JSONArray ja1 = MapActivity.jsonObject.getJSONArray("features");
                                                for (int i = 0; i < ja1.length(); i++) {
                                                    JSONObject j0 = ja1.getJSONObject(i);
                                                    JSONObject j11 = j0.getJSONObject("properties");
                                                    main.namme += " " + j11.getString("name");
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            return null;
                                        }

                                        @Override
                                        protected void onPostExecute(Object o) {
                                            super.onPostExecute(o);
                                            main.selectName();
                                        }
                                    }.execute(main.name);
                                    return true;
                                }
                            }, balloonItem.getGeoPoint());
                            intent = new Intent(main, DetailYandexActivity.class);

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
        }
        bi.setDrawable(getResources().getDrawable(R.drawable.itkerk));
        oi.setBalloonItem(bi);
        o.addOverlayItem(oi);

    }

    public void searchListener(String a, int x) {
        Log.e("POISK",Integer.toString(x));
        if (x == 0) try {
            Search s = new Search();
            o.clearOverlayItems();

            s.doSearch(a, this);

        } catch (MalformedURLException q) {
            q.printStackTrace();
        }
        if (x == 1) {

            String[] q = main.getResources().getStringArray(R.array.categories_names);

            ArrayList<Integer> www = new ArrayList<>();
            String[] e = a.split(" ");
            for (int i = 0; i < q.length; i++) {

                boolean w = false;
                for (int j = 0; j < e.length; j++) {
                    if (q[i].contains(e[j])) {
                        w = true;
                    }

                }
                if (w) {
                    www.add(i);
                }
            }
            int[] idx = new int[www.size()];

            for (int i = 0; i < www.size(); i++) {
                idx[i] = www.get(i);
            }
            CategoryContentFragment l = new CategoryContentFragment();
            l.flag = 1;
            l.idx = idx;
            //  Manager.beginTransaction()
            //          .replace(R.id.fragment1, PrFr)
            //          .commit();
            Manager.beginTransaction()
                    .replace(R.id.fragment1, l)
                    .addToBackStack("card")
                    .commit();

        }
        if (x == 2) {
            String[] q = new String[MainActivity.shopInterfaces.size()];
            for (int i = 0; i < q.length; i++) {
                q[i] = MainActivity.shopInterfaces.get(i).getDescription();
            }
            ArrayList<Integer> www = new ArrayList<>();
            String[] e = a.split(" ");
            for (int i = 0; i < q.length; i++) {
                String aa = q[i];
                String r[] = aa.split(" ");
                boolean w = false;
                for (int j = 0; j < e.length; j++) {
                    if (q[i].contains(e[j])) {
                        w = true;
                    }

                }
                if (w) {
                    www.add(i);
                }
            }
            int[] idx = new int[www.size()];

            for (int i = 0; i < www.size(); i++) {
                idx[i] = www.get(i);
            }
            idx = sortArraywithGeo(idx);
            CardContentFragment l = new CardContentFragment();
            l.flag = 1;
            l.idx = idx;
            //  Manager.beginTransaction()
            //          .replace(R.id.fragment1, PrFr)
            //          .commit();
            Manager.beginTransaction()
                    .replace(R.id.fragment1, l)
                    .addToBackStack("Card")
                    .commit();
        }
        if (x == 3) {
            sPref = getPreferences(MODE_PRIVATE);
            String savedText = sPref.getString("q", "null");
            String[] qqq = savedText.split(" ");
            int[] xx = new int[qqq.length];
            for (int i = 0; i < qqq.length; i++) {
                xx[i] = Integer.parseInt(qqq[i]);
            }
            String[] qq = new String[MainActivity.shopInterfaces.size()];
            for (int i = 0; i < qq.length; i++) {
                qq[i] = MainActivity.shopInterfaces.get(i).getDescription();
            }
            String[] q = new String[xx.length];
            for (int i = 0; i < q.length; i++) {
                q[i] = qq[xx[i]];
            }
            ArrayList<Integer> www = new ArrayList<>();
            String[] e = a.split(" ");
            for (int i = 0; i < q.length; i++) {
                String aa = q[i];
                String r[] = aa.split(" ");
                boolean w = false;
                for (int j = 0; j < e.length; j++) {
                    if (q[i].contains(e[j])) {
                        w = true;
                    }

                }
                if (w) {
                    www.add(xx[i]);
                }
            }
            int[] idx = new int[www.size()];

            for (int i = 0; i < www.size(); i++) {
                idx[i] = www.get(i);
            }
            idx = sortArraywithGeo(idx);
            ListContentFragment l = new ListContentFragment();
            l.flag = 1;
            l.idx = idx;
            //  Manager.beginTransaction()
            //          .replace(R.id.fragment1, PrFr)
            //          .commit();
            Manager.beginTransaction()
                    .replace(R.id.fragment1, l)
                    .commit();
        }
    }

    public static Drawable createScaledIcon(Drawable id, int width, int height, Resources res) {
        Bitmap bitmap = ((BitmapDrawable) id).getBitmap();
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
        if (overlayItems != null) {
            for (int i = 0; i < overlayItems.length; i++) {
                this.makingFullStackIcon(R.drawable.shop, 55, 55, overlayItems[i]);
            }
        }
        o.setVisible(true);

        //  om.addOverlay(o);n
    }

    void selectName() {
        System.out.println(main.namme + " " + main.name);
        intent.putExtra(DetailYandexActivity.DESC, main.namme);
        intent.putExtra("loc", main.name);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        Manager.popBackStack();

        // Log.e("Viewq",bottomNavigationView.findFocus().toString());
    }

    public static int[] sortArraywithGeo(int[] idx) {
        my = mc.getOverlayManager().getMyLocation().getMyLocationItem().getGeoPoint();
        mc.setPositionAnimationTo(my);
/*        Collections.sort(shopInterfaces, new Comparator<ShopInterface>() {
            @Override
            public int compare(ShopInterface o1, ShopInterface o2) {
                GeoPoint g1 = new GeoPoint(o1.getCoordX(),o1.getCoordY());
                GeoPoint g2 = new GeoPoint(o2.getCoordX(),o2.getCoordY());
                if (Math.sqrt((g1.getLat() - my.getLat()) * (g1.getLat() - my.getLat()) + (g1.getLon() - my.getLon()) * (g1.getLon() - my.getLon())) > Math.sqrt((g2.getLat() - my.getLat()) * (g2.getLat() - my.getLat()) + (g2.getLon() - my.getLon()) * (g2.getLon() - my.getLon()))) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    */
        for (int i = 0; i < idx.length; i++) {
            for (int j = idx.length - 1; j > i; j--) {
                ShopInterface o1 = shopInterfaces.get(idx[j - 1]);
                ShopInterface o2 = shopInterfaces.get(idx[j]);
                GeoPoint g1 = new GeoPoint(o1.getCoordX(), o1.getCoordY());
                GeoPoint g2 = new GeoPoint(o2.getCoordX(), o2.getCoordY());
                if (Math.sqrt((g1.getLat() - my.getLat()) * (g1.getLat() - my.getLat()) + (g1.getLon() - my.getLon()) * (g1.getLon() - my.getLon())) > Math.sqrt((g2.getLat() - my.getLat()) * (g2.getLat() - my.getLat()) + (g2.getLon() - my.getLon()) * (g2.getLon() - my.getLon()))) {
                    int temp = idx[j - 1];
                    idx[j - 1] = idx[j];
                    idx[j] = temp;
                }
            }
        }
        return idx;
    }
}
