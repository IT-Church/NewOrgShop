package com.paramonod.kikos;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigncodelab.R;
import com.google.android.gms.common.api.Api;
import com.paramonod.kikos.pack.Image;

import static com.paramonod.kikos.MainActivity.main;

/**
 * Created by KiKoS on 11.04.2017.
 */

public class CategoryContentFragment extends Fragment {
    public static int flag = 0;
    public static int[] idx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter;
        if(flag==1&&idx.length == 0) flag = 0;

        if(flag == 0){
            adapter = new ContentAdapter(recyclerView.getContext());}
        else{
            adapter = new ContentAdapter(recyclerView.getContext(),idx);}

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView a;
        public TextView aa;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_category, parent, false));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int[] b;
                    String[] s = main.getResources().getStringArray(R.array.categories_idx);
                    String[] e = s[getAdapterPosition()].split(" ");
                    b = new int[e.length];
                    for (int i = 0; i <e.length ; i++) {
                        b[i] = Integer.parseInt(e[i]);
                    }
                    CardContentFragment l= new CardContentFragment();
                    l.flag = 1;
                    l.idx = b;
                    //  Manager.beginTransaction()
                    //          .replace(R.id.fragment1, PrFr)
                    //          .commit();
                    main.Manager.beginTransaction()
                            .replace(R.id.fragment1, l)
                            .commit();
                }
            });
            a = (ImageView) itemView.findViewById(R.id.country_photo);
            aa = (TextView) itemView.findViewById(R.id.country_name);

        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static int LENGTH = 4;

        public final String[] Atext;
       // public final String[] Btext;
        public final Drawable[] Apic;
        //public final Drawable[] Bpic;


        public ContentAdapter(Context context) {
            Resources resources = context.getResources();

            String[] q = resources.getStringArray(R.array.categories_names);
            TypedArray a = resources.obtainTypedArray(R.array.categories_photos);
            Apic = new Drawable[a.length()];
        //    Bpic = new Drawable[a.length() / 2];
            LENGTH = a.length();
            Atext = new String[a.length()];
         //   Btext = new String[q.length / 2];
            for (int i = 0; i < a.length(); i++) {
                    Apic[i] = a.getDrawable(i);
                    Atext[i] = q[i];
            }

            a.recycle();
        }
        public ContentAdapter(Context context,int[] idx) {
            Resources resources = context.getResources();

            String[] q = resources.getStringArray(R.array.categories_names);
            TypedArray a = resources.obtainTypedArray(R.array.categories_photos);
            //    Bpic = new Drawable[a.length() / 2];

            LENGTH = idx.length;
            Atext= new String[LENGTH];
            Apic = new Drawable[LENGTH];

            for (int i = 0; i <idx.length ; i++) {
                Apic[i] = a.getDrawable(idx[i]);
                Atext[i] = q[idx[i]];
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
         //   holder.a.setMaxWidth(main.X/2);
         //   holder.a.setMaxHeight(main.Y/3);
         //   holder.a.setMinimumHeight(main.Y/3);
            holder.a.setImageDrawable(Apic[position]);
//            Log.e("width",Integer.toString(holder.a.getWidth()));
       //     holder.b.setMaxWidth(main.X/2);
         //   holder.b.setMaxHeight(main.Y/3);
         //   holder.b.setMinimumHeight(main.Y/3);
          //  holder.b.setImageDrawable(Bpic[position]);
       //     Bpic[position].setBounds(main.X/2,position*main.Y/3,main.X,(position+1)*main.Y/3);
        //    Apic[position].setBounds(0,position*main.Y/3,main.X/2,(position+1)*main.Y/3);
            holder.aa.setText(Atext[position]);
          //  holder.bb.setText(Btext[position]);
          //  Log.e("pos",holder.a.getX()+ ' '+ holder.a.getY() + " "+ holder.a.getWidth()+ " " + holder.a.getHeight());

            System.out.println("Im doing bind");
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

}
