package com.example.rosyrecipebox.saved.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.model.Meal;

import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder>{
        private final Context context;
        private List<Meal>meal;
        private static final String TAG = "RecyclerView";
        SaveOnclickListener listener;
        // Constructor
        public SavedAdapter(Context _context, List<Meal> _meal, SaveOnclickListener _listener ) {
            this.context = _context;
            this.meal= _meal;
            listener=_listener;

        }

        public void setList(List<Meal> _meal)
        {
            meal = _meal;
        }

        // ViewHolder inner class
        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView image;
            public TextView txtName;
            public TextView txtDesc;
            public ImageButton saveBtn;
            public CardView myCardView;
            public View layout;
            public ViewHolder(View v) {
                super(v);
                layout=v;
                image=v.findViewById(R.id.img_dish_thumbnail1);
                txtName=v.findViewById(R.id.tv_dish_name);
                txtDesc=v.findViewById(R.id.tv_dish_recipe);
                saveBtn=v.findViewById(R.id.img_btn_save);
                myCardView=v.findViewById(R.id.cardView);

            }
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
            View v = inflater.inflate(R.layout.card, recyclerView, false);
            ViewHolder vh = new ViewHolder(v);
            Log.i(TAG, "onCreateViewHolder: Home");
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            Glide.with(context).load(meal.get(position).strMealThumb)
                    .apply(new RequestOptions().override(200,200)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(holder.image);
            holder.txtName.setText(meal.get(position).strMeal);
            holder.txtDesc.setText(meal.get(position).strCategory);
            holder.saveBtn.setImageResource(R.drawable.saved);
            holder.saveBtn.setTag("colored");
            holder.saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.saveBtn.getTag().equals("uncolored")) {
                        listener.SaveMeal(meal.get(position));
                        holder.saveBtn.setImageResource(R.drawable.saved);
                        holder.saveBtn.setTag("colored");

                    } else {
                        holder.saveBtn.setImageResource(R.drawable.save);
                        holder.saveBtn.setTag("uncolored");
                        listener.DeleteMeal(meal.get(position));
                    }

                }
            });
        holder.myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OpenDetails(meal.get(position));
            }
        });


            Log.i(TAG, "***** onBindViewHolder **************");
        }

        @Override
        public int getItemCount() {
            return meal.size();
        }
}


