package com.example.rosyrecipebox.search.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.model.Meal;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private final Context context;
    private List<Meal> meal;
    private static final String TAG = "RecyclerViewSearch";
    public boolean ById=false;
    ViewAllOnclickListener  listener;
    // Constructor
    public SearchAdapter(Context _context, List<Meal> _meal, ViewAllOnclickListener _listener ) {
        this.context = _context;
        this.meal= _meal;
        listener=_listener;

    }

    public void setList(List<Meal> _meal)
    {
        meal = _meal;
        Log.i("setLIst", "setList: "+meal.get(0)+"the size is"+meal.size());
    }

    // ViewHolder inner class
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView txtName;
        public TextView txtDesc;
        public ImageButton saveBtn;
        public View layout;
        public CardView myCard;
        public ImageButton calender;
        public ViewHolder(View v) {
            super(v);
            layout=v;
            image=v.findViewById(R.id.img_dish_thumbnail1);
            txtName=v.findViewById(R.id.tv_dish_name);
            txtDesc=v.findViewById(R.id.tv_dish_recipe);
            saveBtn=v.findViewById(R.id.img_btn_save);
            myCard =v.findViewById(R.id.cardView);
            calender=v.findViewById(R.id.CalenderCard);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public   ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.card, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        Log.i("onSearch", "onCreateViewHolder: Search");
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(meal.get(position).strMealThumb)
                .apply(new RequestOptions().override(200,200)
                        .placeholder(R.drawable.border_background)
                        .error(R.drawable.ic_launcher_foreground))
                .into(holder.image);
        holder.txtName.setText(meal.get(position).strMeal);
        holder.txtDesc.setText(meal.get(position).strCategory);
        Log.i("onSearch", "onBindViewHolder: ");

        listener.isFavorite(meal.get(position).idMeal, isFavorite -> {
            if (isFavorite) {
                holder.saveBtn.setImageResource(R.drawable.saved); // Show saved icon
                holder.saveBtn.setTag("colored"); // Set tag to indicate saved state
            } else {// Default color
                holder.saveBtn.setImageResource(R.drawable.save); // Show default save icon
                holder.saveBtn.setTag("uncolored"); // Set tag to indicate unsaved state
            }
        });

        // Handle button click events
        holder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.saveBtn.getTag() == null || holder.saveBtn.getTag().equals("uncolored")) {
                    listener.SaveMeal(meal.get(position)); // Save the meal as favorite
                    holder.saveBtn.setImageResource(R.drawable.saved); // Change icon to saved
                    holder.saveBtn.setTag("colored"); // Update the tag to indicate saved state
                } else {
                    new AlertDialog.Builder(context)
                            .setMessage("Are you Sure you want to delete")
                            .setPositiveButton(android.R.string.yes,((dialog, which) -> {
                                listener.DeleteMeal(meal.get(position)); // Remove the meal from favorites
                                holder.saveBtn.setImageResource(R.drawable.save); // Change icon back to unsaved
                                holder.saveBtn.setTag("uncolored");
                            }))
                            .setNegativeButton(android.R.string.no,null)
                            .show();
                    // Update the tag to indicate unsaved state
                }
            }
        });
        holder.myCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ById=true;
                listener.OpenDetails(meal.get(position));
            }
        });
        holder.calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.openCalendarDialog(meal.get(position));
            }
        });



    }

    @Override
    public int getItemCount() {
        return meal.size();
    }
    }




