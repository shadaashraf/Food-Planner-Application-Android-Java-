package com.example.rosyrecipebox.home.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.model.Ingridients;

import java.util.List;
import java.util.Random;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{
        private final Context context;
        private List<Ingridients> ingridients;
        private SaveOnclickListener listener;
        private static final String TAG = "IngredientsAdapter";
        private String BaseimgURL="https://www.themealdb.com/images/ingredients/";
        // Constructor

    public IngredientsAdapter(Context context, List<Ingridients> ingridients, SaveOnclickListener listener) {
        this.context = context;
        this.ingridients = ingridients;
        this.listener = listener;
    }

    public void setList(List<Ingridients> _ingridients)
        {
            ingridients = _ingridients;
        }

        // ViewHolder inner class
        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView ingredientsImage;
            public ImageButton circleBtn;
            public Button viewIngredietsBtn;
            public View layout;
            public ViewHolder(View v) {
                super(v);
                layout=v;
                circleBtn=v.findViewById(R.id.Ingredientbtn);
                ingredientsImage=v.findViewById(R.id.Ingredients_tiny_image);


            }
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
            View v = inflater.inflate(R.layout.ingredient, recyclerView, false);
            ViewHolder vh = new ViewHolder(v);
            Log.i(TAG, "onCreateViewHolder: ");
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            Glide.with(context).load(BaseimgURL+ingridients.get(position).strIngredient+".png")
                    .apply(new RequestOptions().override(200,200)
                            .placeholder(R.drawable.filled_circle)
                            .error(R.drawable.ic_launcher_foreground))

                    .into(holder.ingredientsImage);

            int randomColor = generateRandomColor();
            holder.circleBtn.setBackgroundColor(randomColor);

            holder.circleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OpenMealByIngridients(ingridients.get(position));
                }
            });

            Log.i(TAG, "***** onBindViewHolder **************");
        }

        @Override
        public int getItemCount() {
            return ingridients.size();
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


