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

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paramonod.kikos.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.paramonod.kikos.pack.ShopInterface;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import static com.paramonod.kikos.MainActivity.main;

/**
 * Provides UI for the view with List.
 */

public class ListContentFragment1 extends Fragment {
    public static int flag = 0;
    public static int[] idx;
    public static Context ctx;

    //private static FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter;
        adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avator;
        public TextView name;
        public TextView description;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    if (flag == 0)
                        intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    else {
                        intent.putExtra(DetailActivity.EXTRA_POSITION, idx[getAdapterPosition()]);
                    }
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */


    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static int LENGTH = 6;

        public final String[] mPlaces;
        public final String[] mPlaceDesc;
        public final Drawable[] mPlaceAvators;

        /* public ContentAdapter(Context context) {
             Resources resources = context.getResources();
             LENGTH =6;
             mPlaces = resources.getStringArray(R.array.places);
             mPlaceDesc = resources.getStringArray(R.array.place_desc);
             TypedArray a = resources.obtainTypedArray(R.array.place_avator);
             mPlaceAvators = new Drawable[a.length()];
             for (int i = 0; i < mPlaceAvators.length; i++) {
                 mPlaceAvators[i] = a.getDrawable(i);
             }
             a.recycle();
         }*/

        public ContentAdapter(Context context) {
            //ShopInterface shopInterface = new ShopInterface("asas","qaqa","wdwdwd","12","11","11");
            //myReference.setValue(shopInterface);
            Resources resources = context.getResources();
            String[] temMPlaces = resources.getStringArray(R.array.places);
            String[] temMPlaceDesc = resources.getStringArray(R.array.place_desc);
            TypedArray a = resources.obtainTypedArray(R.array.place_avator);
            LENGTH = MainActivity.shopInterfaces.size();
            mPlaces = new String[LENGTH];
            mPlaceDesc = new String[LENGTH];
            mPlaceAvators = new Drawable[LENGTH];
            String PictureStore = context.getCacheDir().toString();
            PictureStore += "";
            System.out.println(PictureStore);
            for (int i = 0; i < MainActivity.shopInterfaces.size(); i++) {
                mPlaces[i] = MainActivity.shopInterfaces.get(i).getName();
                mPlaceDesc[i] = MainActivity.shopInterfaces.get(i).getDescription();
//                mPlaceAvators[i] = MainActivity.bitmap[i];
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Picasso.with(main)
                    .load(MainActivity.shopInterfaces.get(position).getPictureAvatorName())
                    .placeholder(R.drawable.ic_wallpaper_black_24dp)
                    .into(holder.avator);

            // holder.avator.setImageDrawable(mPlaceAvators[position]);
            holder.name.setText(mPlaces[position]);
            holder.description.setText(mPlaceDesc[position]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

}
