package com.paramonod.kikos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paramonod.kikos.R;

import static com.paramonod.kikos.MainActivity.main;
import static com.paramonod.kikos.MainActivity.mc;
import static com.paramonod.kikos.MainActivity.myLoc;

/**
 * Created by Dmitry Paramonov on 12/29/2016.
 */
public class MapViewFragment extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // View map = inflater.inflate(R.layout.map,container);

        return inflater.inflate(R.layout.map, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        main.setupMap();
    }

}
