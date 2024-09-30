package com.example.rosyrecipebox.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.model.Area;
import com.example.rosyrecipebox.search.view.ViewAllOnclickListener;

import java.util.List;
import java.util.Random;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
        private final Context context;
        private List<Area> areas;
        private ViewAllOnclickListener listener;
        private static final String TAG = "AreaAdapter";

        // Constructor
        public AreaAdapter(Context _context, List<Area> _areas) {
            this.context = _context;
            this.areas = _areas;

        }

        public void setList(List<Area> _areas) {
            areas = _areas;
        }

        // ViewHolder inner class
        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageButton flagImage;
            public View layout;

            public ViewHolder(View v) {
                super(v);
                layout = v;
                flagImage = v.findViewById(R.id.flag1);

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.flag_layout, parent, false);
            AreaAdapter.ViewHolder vh = new AreaAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(AreaAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//        Glide.with(context).load(Areas.get(position).imgURL)
//                .apply(new RequestOptions().override(200, 200)
//                        .placeholder(R.drawable.ic_launcher_background)
//                        .error(R.drawable.ic_launcher_foreground))
//                .into(holder.flagImage);


            holder.flagImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click
                }
            });

            Log.i(TAG, "***** onBindViewHolder **************");
        }

        @Override
        public int getItemCount() {
            return areas.size();
        }

        private int generateRandomColor() {
            Random random = new Random();
            // Generate random RGB values
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            return Color.rgb(r, g, b);
        }
    }




