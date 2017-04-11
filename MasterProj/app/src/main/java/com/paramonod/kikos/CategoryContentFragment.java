package com.paramonod.kikos;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigncodelab.R;
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
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView a;
        public ImageView b;

        public TextView aa;
        public TextView bb;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_category, parent, false));
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
            a = (ImageView) itemView.findViewById(R.id.cat1);
            b = (ImageView) itemView.findViewById(R.id.cat2);
            a.setImageDrawable(main.getResources().getDrawable(R.drawable.ovosch110));
            b.setImageDrawable(main.getResources().getDrawable(R.drawable.shampoo));
            aa = (TextView) itemView.findViewById(R.id.twcat1);
            bb = (TextView) itemView.findViewById(R.id.twcat2);
            aa.setText("aa");
            bb.setText("bb");
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static int LENGTH = 4;

        public final String[] Atext;
        public final String[] Btext;
        public final Drawable[] Apic;
        public final Drawable[] Bpic;


        public ContentAdapter(Context context) {
            Resources resources = context.getResources();

            String[] q = resources.getStringArray(R.array.categories_names);
            TypedArray a = resources.obtainTypedArray(R.array.categories_photos);
            Apic = new Drawable[a.length() / 2];
            Bpic = new Drawable[a.length() / 2];
            LENGTH = a.length()/2;
            Atext = new String[q.length / 2];
            Btext = new String[q.length / 2];
            for (int i = 0; i < a.length(); i++) {
                if (i % 2 == 0) {
                    Apic[i / 2] = a.getDrawable(i);
                } else {
                    Bpic[i / 2] = a.getDrawable(i);
                }
            }
            for (int i = 0; i < q.length; i++) {
                if (i % 2 == 0) {
                    Atext[i / 2] =q[i];
                } else {
                    Btext[i / 2] =q[i];                }
            }

            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.a.setImageDrawable(Apic[position]);
            holder.b.setImageDrawable(Bpic[position]);
            holder.aa.setText(Atext[position]);
            holder.bb.setText(Btext[position]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

}
