package com.example.rosyrecipebox.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.model.Category;
import com.example.rosyrecipebox.search.view.ViewAllOnclickListener;


import java.util.List;
import java.util.Random;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final Context context;
    private List<Category> categories;
    private ViewAllOnclickListener listener;
    private static final String TAG = "CategoryAdapter";

    // Constructor
    public CategoryAdapter(Context _context, List<Category> _categories) {
        this.context = _context;
        this.categories = _categories;
    }

    public void setList(List<Category> _categories) {
        categories = _categories;
    }

    // ViewHolder inner class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView CategoryImage;
        public ImageButton regtangleBtn;
        public TextView CategoryName;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            CategoryName = v.findViewById(R.id.CategoryName1);
            CategoryImage = v.findViewById(R.id.Category_tiny_image);
            regtangleBtn = v.findViewById(R.id.Category_regtangle_img);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.category, parent, false);
        CategoryAdapter.ViewHolder vh = new CategoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(categories.get(position).strCategoryThumb)
                .apply(new RequestOptions().override(200, 200)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.CategoryImage);

        holder.CategoryName.setText(categories.get(position).strCategory);

        holder.regtangleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
            }
        });

        Log.i(TAG, "***** onBindViewHolder **************");
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


}



