package com.example.rosyrecipebox.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.model.Area;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
        private final Context context;
        private List<Area> areas;
        private SaveOnclickListener listener;
        private static final String TAG = "AreaAdapter";
        private String areaImg;
        private String imageName;  // The variable for the image name
        private String dropboxBaseUrl ="https://raw.githubusercontent.com/shadaashraf/Food-Planner-Application-Android-Java-/main/flag/";
        private String imageUrl;


    public AreaAdapter(Context context, List<Area> areas, SaveOnclickListener listener) {
        this.context = context;
        this.areas = areas;
        this.listener = listener;
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

        @SuppressLint("SuspiciousIndentation")
        @Override
        public void onBindViewHolder(AreaAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        imageName=areas.get(position).strArea;

        imageUrl=dropboxBaseUrl + imageName.toLowerCase() + ".png";

            Log.i("image", "onBindViewHolder: "+imageUrl);
        Glide.with(context).load(imageUrl)
                .apply(new RequestOptions().override(90, 90)
                        .placeholder(R.drawable.border_background)
                       .error(R.drawable.ic_launcher_foreground))
               .into(holder.flagImage);


            holder.flagImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OpenMealByArea(areas.get(position));
                }
            });

            Log.i(TAG, "***** onBindViewHolder **************");
        }

        @Override
        public int getItemCount() {
            return areas.size();
        }

    }




