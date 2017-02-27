package com.paramonod.kikos;

/**
 * Created by gamiy_000 on 27.02.2017.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.materialdesigncodelab.R;

/**
 * Created by Dmitry Paramonov on 12/29/2016.
 */
public class ProgressView extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.progress_view, container, false);
    }

}
