package com.paramonod.kikos;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.materialdesigncodelab.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ru.yandex.yandexmapkit.overlay.location.MyLocationItem;
import ru.yandex.yandexmapkit.overlay.location.OnMyLocationListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;

import static com.paramonod.kikos.MainActivity.mc;


/**
 * Created by Varvara on 01.12.2016.
 */

public class Search {
    final static String STANDART_URL = "https://geocode-maps.yandex.ru/1.x/?format=json&geocode=";
    final static String NEW_STANDART_URL = "https://search-maps.yandex.ru/v1/?apikey=245e2b86-5cfb-40c3-a064-516c37dba6b2&lang=ru_RU&text=";
    public static URL url = null;
    public static MainActivity mainActivity = null;

    public void doSearch(final String obj, MainActivity m) throws MalformedURLException {
        //this.url = new URL(STANDART_URL +obj);
        String k = "\"";
        this.url = new URL(NEW_STANDART_URL + k + obj+k+"&"+"results=500");
        System.out.println(url.toString());
        mainActivity = m;
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Log.e("BEFORE I FUCK", "YOU");

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
                    MapActivity.jsonObject = new JSONObject(response);
                    System.out.println(MapActivity.jsonObject.toString());
                } catch (Exception e) {
                    Log.e("FUCK", "YOU");
                    e.printStackTrace();

                }
                return null;
            }


            @Override
            protected void onPostExecute(Object ob) {
                super.onPostExecute(ob);

                GeoPoint nearG = null;
                Log.e("PRINT", "IFUCKYOUR MUM");
                mainActivity.updatePins(null);
                String resultString = "";
                try {
              /*      JSONObject j1 = MapActivity.jsonObject.getJSONObject("response");
                    JSONObject j2 = j1.getJSONObject("GeoObjectCollection");
                    JSONArray ja1 = j2.getJSONArray("featureMember");
                    items = new GeoPoint[ja1.length()];
                    */
                    JSONArray ja1 = MapActivity.jsonObject.getJSONArray("features");
                    System.out.println(mc);
                    GeoPoint my = mc.getOverlayManager().getMyLocation().getMyLocationItem().getGeoPoint();
                    System.out.println(my.getLat()+" " +my.getLon());
                    float maxL = Float.MAX_VALUE;
                    int maxI = 0;
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

                        mainActivity.makingFullStackIcon(R.drawable.orpgshop, 55, 55, curG, name, description);
                        if (maxL > Math.sqrt((curG.getLat() - my.getLat()) * (curG.getLat() - my.getLat()) + (curG.getLon() - my.getLon()) * (curG.getLon() - my.getLon()))) {
                            maxL = (float) Math.sqrt((curG.getLat() - my.getLat()) * (curG.getLat() - my.getLat()) + (curG.getLon() - my.getLon()) * (curG.getLon() - my.getLon()));
                        nearG =    curG;
                        }
                    }

                    Log.e("FUCKUP",nearG.toString());
                    mc.setPositionAnimationTo(nearG);
                } catch (JSONException js) {
                    System.err.println("F.U.C.K");
                    js.printStackTrace();
                }

                System.out.println("hi");
                String[] geos = mainActivity.getResources().getStringArray(R.array.places_coords);
                String[] strings = mainActivity.getResources().getStringArray(R.array.place_details);
                String[] r = obj.split(" ");
                for (int i = 0;i<strings.length;i++) {
                    String a = strings[i];
                    String e[] = a.split(" ");
                    boolean w = false;
                    for (int j = 0; j <e.length ; j++) {
                        for (int l = 0; l <r.length ; l++) {


                            if (e[j].equalsIgnoreCase(r[l])) {
                                w = true;
                            }
                        }
                    }

                    if (w) {
                        String[] q = geos[i].split(" ");
                        mainActivity.makingFullStackIcon(R.drawable.shop, 55, 55, new GeoPoint(Double.parseDouble(q[0]), Double.parseDouble(q[1])));
                    }
                }

            }
        }.execute();

    }

}
