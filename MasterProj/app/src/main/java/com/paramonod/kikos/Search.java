package com.paramonod.kikos;

import android.os.AsyncTask;
import android.util.Log;

import com.paramonod.kikos.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ru.yandex.yandexmapkit.overlay.location.MyLocationItem;
import ru.yandex.yandexmapkit.overlay.location.OnMyLocationListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;

import static com.paramonod.kikos.MainActivity.combine;
import static com.paramonod.kikos.MainActivity.main;
import static com.paramonod.kikos.MainActivity.mc;
import static com.paramonod.kikos.MainActivity.shopInterfaces;


/**
 * Created by Varvara on 01.12.2016.
 */

public class Search {
    final static String STANDART_URL = "https://geocode-maps.yandex.ru/1.x/?format=json&geocode=";
    final static String NEW_STANDART_URL = "https://search-maps.yandex.ru/v1/?apikey=245e2b86-5cfb-40c3-a064-516c37dba6b2&lang=ru_RU&text=";
    public static URL url = null;
    public static MainActivity mainActivity = null;
    public static ArrayList<GeoPoint> points;
    public static ArrayList<Integer> geoPoints;
    public static GeoPoint my;

    public void doSearch(final String obj, MainActivity m) throws MalformedURLException {
        //this.url = new URL(STANDART_URL +obj);
        String k = "\"";
        this.url = new URL(NEW_STANDART_URL + k + obj + k + "&" + "results=500");
        System.out.println(url.toString());
        mainActivity = m;
        points = new ArrayList<>();
        geoPoints = new ArrayList<>();
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {


                try {
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
                    MainActivity.jsonObject = new JSONObject(response);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return null;
            }


            @Override
            protected void onPostExecute(Object ob) {
                super.onPostExecute(ob);
                main.updatePins(null);
                GeoPoint nearG = null;
                String resultString = "";
                try {
              /*      JSONObject j1 = MapActivity.jsonObject.getJSONObject("response");
                    JSONObject j2 = j1.getJSONObject("GeoObjectCollection");
                    JSONArray ja1 = j2.getJSONArray("featureMember");
                    items = new GeoPoint[ja1.length()];
                    */
                    JSONArray ja1 = MainActivity.jsonObject.getJSONArray("features");
                    System.out.println(mc);
                    my = mc.getOverlayManager().getMyLocation().getMyLocationItem().getGeoPoint();
                    for (int i = 0; i < ja1.length(); i++) {
                        JSONObject j0 = ja1.getJSONObject(i);
                        JSONObject j11 = j0.getJSONObject("properties");
                        //  JSONObject j21 = j11.getJSONObject("CompanyMetaData");
                        String name = j11.getString("name");
                        String description = j11.getString("description");
                        JSONObject j1 = j0.getJSONObject("geometry");
                        JSONArray ja2 = j1.getJSONArray("coordinates");

                       /* JSONObject j0 = ja1.getJSONObject(i);
                        JSONObject j3 = j0.getJSONObject("GeoObject");
                        System.out.println(j3.toString());
                        JSONObject j4 = j3.getJSONObject("Point");
                        resultString = j4.getString("pos");*/
                        resultString = ja2.get(0).toString() + " " + ja2.get(1).toString();
                        System.out.println(resultString);
                        String[] s1 = resultString.split(" ");
                        GeoPoint curG = new GeoPoint(Double.parseDouble(s1[1]), Double.parseDouble(s1[0]));
                        points.add(curG);
                        Log.e("Search_q",curG.toString());

                    }
                } catch (JSONException js) {
                    System.err.println("F.U.C.K");
                    js.printStackTrace();
                }


                System.out.println("hi");
                String[] strings = new String[MainActivity.shopInterfaces.size()];
                for (int i = 0; i < strings.length; i++) {
                    strings[i] = MainActivity.shopInterfaces.get(i).getDescription();
                }
                for (int i = 0; i < strings.length; i++) {
                    String a = strings[i];
                    String e[] = a.split(" ");
                    boolean w = false;
                    for (int j = 0; j < e.length; j++) {
                        if (obj.toLowerCase().contains(e[j].toLowerCase())) {
                            w = true;
                            Log.e(obj,e[j]);
                        }
                    }

                    if (w) {
                        geoPoints.add(i);
                    }
                }
                Log.e("q", points.size() + "_" + geoPoints.size());
                Collections.sort(points, new Comparator<GeoPoint>() {
                    @Override
                    public int compare(GeoPoint o1, GeoPoint o2) {
                        if (Math.sqrt((o1.getLat() - my.getLat()) * (o1.getLat() - my.getLat()) + (o1.getLon() - my.getLon()) * (o1.getLon() - my.getLon())) > Math.sqrt((o2.getLat() - my.getLat()) * (o2.getLat() - my.getLat()) + (o2.getLon() - my.getLon()) * (o2.getLon() - my.getLon()))) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                Collections.sort(geoPoints, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        GeoPoint o11 = new GeoPoint(shopInterfaces.get(o1).getCoordX(),shopInterfaces.get(o1).getCoordY());
                        GeoPoint o22 = new GeoPoint(shopInterfaces.get(o2).getCoordX(),shopInterfaces.get(o2).getCoordY());
                        if (Math.sqrt((o11.getLat() - my.getLat()) * (o11.getLat() - my.getLat()) + (o11.getLon() - my.getLon()) * (o11.getLon() - my.getLon())) > Math.sqrt((o22.getLat() - my.getLat()) * (o22.getLat() - my.getLat()) + (o22.getLon() - my.getLon()) * (o22.getLon() - my.getLon()))) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                if (points.size() != 0 && geoPoints.size() != 0) {
                    if (Math.sqrt((points.get(0).getLat() - my.getLat()) * (points.get(0).getLat() - my.getLat()) + (points.get(0).getLon() - my.getLon()) * (points.get(0).getLon() - my.getLon())) > Math.sqrt(shopInterfaces.get(geoPoints.get(0)).getCoordY() - my.getLat()) * (shopInterfaces.get(geoPoints.get(0)).getCoordY()- my.getLat()) + (shopInterfaces.get(geoPoints.get(0)).getCoordX() - my.getLon()) * (shopInterfaces.get(geoPoints.get(0)).getCoordX() - my.getLon())) {
                        mc.setPositionAnimationTo(new GeoPoint(shopInterfaces.get(geoPoints.get(0)).getCoordX(),shopInterfaces.get(geoPoints.get(0)).getCoordY()));
                        System.out.println(1);
                        Log.e("Search","1");
                    } else {
                        mc.setPositionAnimationTo(points.get(0));
                        System.out.println(-1);
                        Log.e("Search","-1");

                    }
                } else {
                    if (points.size() == 0 && geoPoints.size() != 0) {
                        mc.setPositionAnimationTo(new GeoPoint(shopInterfaces.get(geoPoints.get(0)).getCoordX(),shopInterfaces.get(geoPoints.get(0)).getCoordY()));
                        Log.e("Search","21");

                    }
                    if (geoPoints.size() == 0 && points.size() != 0) {
                        mc.setPositionAnimationTo(points.get(0));
                        Log.e("Search","12");

                    }
                }
                for (GeoPoint g : points) {
                    mainActivity.makingFullStackIcon(R.drawable.orpgshop, g);
                }
                for (int q : geoPoints) {
                    main.combine(q);
                }
            }
        }.execute();

    }

}
