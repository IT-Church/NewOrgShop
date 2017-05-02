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
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Target;
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

import com.paramonod.kikos.R;
import com.paramonod.kikos.pack.Image;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.paramonod.kikos.MainActivity.jsonObject;
import static com.paramonod.kikos.MainActivity.main;
import static com.paramonod.kikos.MainActivity.sPref;
import static com.paramonod.kikos.MainActivity.shopInterfaces;

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
        if (flag == 1 && idx.length == 0) flag = 0;
        if (flag == 0) {
            adapter = new ContentAdapter(recyclerView.getContext());
        } else {
            adapter = new ContentAdapter(recyclerView.getContext(), idx);
        }
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
                    if (flag == 0)
                        intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    else {
                        intent.putExtra(DetailActivity.EXTRA_POSITION, idx[getAdapterPosition()]);
                    }
                    context.startActivity(intent);
                }
            });


            final ImageButton favoriteImageButton =
                    (ImageButton) itemView.findViewById(R.id.favorite_button);
            sPref = main.getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            String savedText = sPref.getString("q", "null");
            System.out.println(savedText);
            Log.e("card", favoriteImageButton.toString() + favoriteImageButton.getDrawable().toString());
            Log.e("card_text", savedText);
            Log.e("card_flag", Integer.toString(flag));
            if (savedText.contains(Integer.toString(getAdapterPosition()))) {
                favoriteImageButton.setImageDrawable(main.getDrawable(R.drawable.ic_favorite_black_24dp));
            } else {
                favoriteImageButton.setImageDrawable(main.getDrawable(R.drawable.ic_favorite_border_black_24dp));
            }
            favoriteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //      Log.e("asd","Almost there");
                    Log.e("card", "I am really here");
                    sPref = main.getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    String savedText = sPref.getString("q", "null");
                    System.out.println(savedText);
                    if (flag == 0) {
                        if (savedText.contains(Integer.toString(getAdapterPosition()))) {
                            favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                            String[] s = savedText.split(Integer.toString(getAdapterPosition()));
                            char[] q1 = s[0].toCharArray();
                            char[] q2 = s[1].toCharArray();
                            String res = "";
                            for (int i = 0; i < q1.length; i++) {
                                res += q1[i];
                            }
                            for (int i = 1; i < q2.length; i++) {
                                res += q2[i];
                            }
                            ed.putString("q", res);
                            Log.e("FUCK", "a");
                        } else {
                            favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite));
                            ed.putString("q", savedText + Integer.toString(getAdapterPosition()) + " ");

                            Log.e("FUCK", "b");
                        }
                    } else {
                        if (savedText.contains(Integer.toString(idx[getAdapterPosition()]))) {
                            favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                            String[] s = savedText.split(Integer.toString(idx[getAdapterPosition()]));
                            char[] q1 = s[0].toCharArray();
                            char[] q2 = s[1].toCharArray();
                            String res = "";
                            for (int i = 0; i < q1.length; i++) {
                                res += q1[i];
                            }
                            for (int i = 1; i < q2.length; i++) {
                                res += q2[i];
                            }
                            ed.putString("q", res);
                            Log.e("FUCK", "a");
                        } else {
                            favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite));
                            ed.putString("q", savedText + Integer.toString(idx[getAdapterPosition()]) + " ");
                            Log.e("FUCK", "b");
                        }
                    }
                    ed.commit();
                }
            });

            final ImageButton shareImageButton = (ImageButton) itemView.findViewById(R.id.share_button);
            shareImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(main,"Loading Picture",Toast.LENGTH_LONG).show();
                    Picasso.with(main).load(shopInterfaces.get(idx[getAdapterPosition()]).getPictureName()).into(new com.squareup.picasso.Target() {
                        @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("image/*");
                            String shareBody = "Look what I've found in OrgShop: " + shopInterfaces.get(idx[getAdapterPosition()]).getName();
                            i.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                            main.startActivity(Intent.createChooser(i, "Share Image"));
                        }
                        @Override public void onBitmapFailed(Drawable errorDrawable) { }
                        @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
                        public Uri getLocalBitmapUri(Bitmap bmp) {
                            Uri bmpUri = null;
                            try {
                                File file =  new File(main.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
                                FileOutputStream out = new FileOutputStream(file);
                                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                                out.close();
                                bmpUri = Uri.fromFile(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return bmpUri;
                        }
                    });
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
        private final String[] mPlacePictures;

        public ContentAdapter(Context context) {
            LENGTH = MainActivity.shopInterfaces.size();
            mPlaces = new String[LENGTH];
            mPlaceDesc = new String[LENGTH];
            mPlacePictures = new String[LENGTH];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlaces[i] = MainActivity.shopInterfaces.get(i).getName();
                mPlaceDesc[i] = MainActivity.shopInterfaces.get(i).getDescription();
                mPlacePictures[i] = MainActivity.shopInterfaces.get(i).getPictureName();
            }
        }


        public ContentAdapter(Context context, int[] idx) {


            LENGTH = idx.length;
            mPlaces = new String[LENGTH];
            mPlaceDesc = new String[LENGTH];
            mPlacePictures = new String[LENGTH];

            for (int i = 0; i < idx.length; i++) {
                mPlaces[i] = MainActivity.shopInterfaces.get(idx[i]).getName();
                mPlaceDesc[i] = MainActivity.shopInterfaces.get(idx[i]).getDescription();
                mPlacePictures[i] = MainActivity.shopInterfaces.get(idx[i]).getPictureName();
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(flag==0) {
                Picasso.with(main)
                        .load(MainActivity.shopInterfaces.get(position).getPictureName())
                        .into(holder.picture);
            }
            else{
                Picasso.with(main)
                        .load(MainActivity.shopInterfaces.get(idx[position]).getPictureName())
                        .into(holder.picture);
            }
            holder.name.setText(mPlaces[position]);
            holder.description.setText(mPlaceDesc[position]);
            final ImageButton favoriteImageButton =
                    (ImageButton) holder.itemView.findViewById(R.id.favorite_button);
            sPref = main.getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            String savedText = sPref.getString("q", "null");
            Log.e("card_bind", favoriteImageButton.toString() + favoriteImageButton.getDrawable().toString());
            Log.e("card_text", savedText);
            if (flag == 0) {
                if (savedText.contains(Integer.toString(holder.getAdapterPosition()))) {
                    favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite));
                } else {
                    favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                }
            } else {
                if (savedText.contains(Integer.toString(idx[holder.getAdapterPosition()]))) {
                    favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite));
                } else {
                    favoriteImageButton.setImageDrawable(main.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                }

            }
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

}
