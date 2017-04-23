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
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.materialdesigncodelab.R;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.paramonod.kikos.MainActivity.main;
import static com.paramonod.kikos.MainActivity.sPref;

/**
 * Provides UI for the view with Cards.
 */
public class CardContentFragment extends Fragment {
    public static int flag = 0;
    public static int[] idx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter;
        if(flag == 0){
            adapter = new ContentAdapter(recyclerView.getContext());}
        else{
            adapter = new ContentAdapter(recyclerView.getContext(),idx);}
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            System.out.println(super.getItemViewType());
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    if(flag == 0)  intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    else{
                        intent.putExtra(DetailActivity.EXTRA_POSITION,idx[getAdapterPosition()]);
                    }
                    context.startActivity(intent);
                }
            });

            // Adding Snackbar to Action Button inside card
            Button button = (Button)itemView.findViewById(R.id.action_button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Action is pressed",
                            Snackbar.LENGTH_LONG).show();
                }
            });

            final ImageButton favoriteImageButton =
                    (ImageButton) itemView.findViewById(R.id.favorite_button);
            favoriteImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
              //      Log.e("asd","Almost there");
                Log.e("wq","I am really here");
                    Resources resources = main.getResources();
                    sPref = main.getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    String savedText = sPref.getString("q", "null");
                    System.out.println(savedText);
                    if(savedText.contains(Integer.toString(getAdapterPosition()))) {
                        favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                        String[] s = savedText.split(Integer.toString(getAdapterPosition()));
                        char[] q1 = s[0].toCharArray();
                        char[] q2 = s[1].toCharArray();
                        String res = "";
                        for (int i = 0; i <q1.length ; i++) {
                            res+=q1[i];
                        }
                        for (int i = 1; i <q2.length; i++) {
                            res+=q2[i];
                        }
                        ed.putString("q",res);
                        Log.e("FUCK","a");
                    }
                    else{
                        favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite));
                        ed.putString("q", savedText + Integer.toString(idx[getAdapterPosition()]) + " ");

                        Log.e("FUCK","b");
                    }
                   ed.commit();
                }
            });

            ImageButton shareImageButton = (ImageButton) itemView.findViewById(R.id.share_button);
            shareImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Share article",
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of Card in RecyclerView.
        private static int LENGTH = 6;

        private final String[] mPlaces;
        private final String[] mPlaceDesc;
        private final Drawable[] mPlacePictures;

        public ContentAdapter(Context context) {
            LENGTH = 6;
            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.places);
            mPlaceDesc = resources.getStringArray(R.array.place_desc);
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mPlacePictures = new Drawable[a.length()];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }
        public ContentAdapter(Context context,int[] idx) {
            Resources resources = context.getResources();
            String[] temMPictures = resources.getStringArray(R.array.places);
            String[] temMNames = resources.getStringArray(R.array.place_desc);
            TypedArray a= resources.obtainTypedArray(R.array.places_picture);

            LENGTH = idx.length;
            mPlaces = new String[LENGTH];
            mPlaceDesc = new String[LENGTH];
            mPlacePictures = new Drawable[LENGTH];

            for (int i = 0; i <idx.length ; i++) {
                mPlaces[i] = temMPictures[idx[i]];
                mPlaceDesc[i] = temMNames[idx[i]];
                mPlacePictures[i] = a.getDrawable(idx[i]);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.picture.setImageDrawable(mPlacePictures[position]);
            holder.name.setText(mPlaces[position]);
            holder.description.setText(mPlaceDesc[position]);
            final ImageButton favoriteImageButton =
                    (ImageButton) holder.itemView.findViewById(R.id.favorite_button);
            sPref = main.getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            String savedText = sPref.getString("q", "null");
            if(savedText.contains(Integer.toString(holder.getAdapterPosition()))){
                favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite));
            }
            else{
                favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
            }
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
