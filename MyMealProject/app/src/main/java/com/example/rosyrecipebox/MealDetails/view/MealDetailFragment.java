package com.example.rosyrecipebox.MealDetails.view;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rosyrecipebox.MealDetails.presenter.MealDetailsPresenter;
import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.calender.view.CalenderFragment;
import com.example.rosyrecipebox.db.MealLocalDataSourceImpl;
import com.example.rosyrecipebox.model.Meal;
import com.example.rosyrecipebox.model.MealsRepositoryImpl;
import com.example.rosyrecipebox.network.Area.AreaRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Category.CategoryRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.Ingridients.IngridientsRemoteDataSourceImpl;
import com.example.rosyrecipebox.network.ListOfMeals.MealsRemoteDataSourceImpl;

import java.util.List;

public class MealDetailFragment extends Fragment {

    private ImageView imgMealThumb;
    private TextView tvMealName, tvMealDetails, tvInstructions;
    private WebView webviewYoutube;
    private LinearLayout ingredientsContainer;
    private Meal meal;
    private String ingredientsImgBaseURL="https://www.themealdb.com/images/ingredients/";
    private MealDetailsPresenter mealDetailsPresenter;
    private ImageButton favBtn;
    private ImageButton calenderBtn;
    public MealDetailFragment(Meal meal) {
        this.meal = meal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details, container, false);
        MealsRepositoryImpl repo = MealsRepositoryImpl.getInstance( AreaRemoteDataSourceImpl.getInstance(), MealsRemoteDataSourceImpl.getInstance(), IngridientsRemoteDataSourceImpl.getInstance(), CategoryRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(getContext()));
        mealDetailsPresenter=new MealDetailsPresenter(repo);
        imgMealThumb = view.findViewById(R.id.imgMealThumb);
        tvMealName = view.findViewById(R.id.tvMealName);
        tvMealDetails = view.findViewById(R.id.tvMealDetails);
        tvInstructions = view.findViewById(R.id.tvInstructions);
        WebView webviewYoutube = view.findViewById(R.id.webviewYoutube);
        ingredientsContainer = view.findViewById(R.id.ingredientsLayout);
        favBtn=view.findViewById(R.id.FavDetails);
        calenderBtn=view.findViewById(R.id.CalenderDetails);


            tvMealName.setText(meal.strMeal);
            tvMealDetails.setText(meal.strCategory + " | " + meal.strArea);
            tvInstructions.setText(meal.strInstructions);

            Glide.with(this).load(meal.strMealThumb)
                    .apply(new RequestOptions().override(800, 400)
                            .placeholder(R.drawable.border_background)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(imgMealThumb);


        webviewYoutube.getSettings().setJavaScriptEnabled(true);
        webviewYoutube.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        String youtubeUrl = meal.strYoutube;
        String videoId = youtubeUrl.substring(youtubeUrl.lastIndexOf("=") + 1); // Extracting the video ID
        String videoHtml = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
        webviewYoutube.loadData(videoHtml, "text/html", "utf-8");

        List<String> ingredients = meal.getIngredients();
        List<String> measurements = meal.getMeasurements();

        LinearLayout ingredientsLayout = view.findViewById(R.id.ingredientsLayout);


        ingredientsLayout.removeAllViews();

        for (int i = 0; i < ingredients.size(); i++) {
            String ingredient = ingredients.get(i);
            String measurement = measurements.get(i);
            String imageUrl = ingredientsImgBaseURL+ingredient+".png"; // Get the image URL for the ingredient

            // Create a new horizontal layout for each ingredient and measurement pair
            LinearLayout row = new LinearLayout(view.getContext());
            row.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            row.setOrientation(LinearLayout.HORIZONTAL);

            // Create FrameLayout for image
            FrameLayout frameLayout = new FrameLayout(view.getContext());

            FrameLayout.LayoutParams circleParams = new FrameLayout.LayoutParams(60, 60);

            // Circle view for background
            View circleView = new View(view.getContext());
            circleView.setLayoutParams(circleParams);
            circleView.setBackgroundResource(R.drawable.filled_circle); // Use your drawable

            // ImageView for ingredient image
            ImageView tinyImage = new ImageView(view.getContext());
            FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(60, 60);
            imageParams.gravity = Gravity.CENTER;
            tinyImage.setLayoutParams(imageParams);

            // Load image with Glide
            Glide.with(view.getContext())
                    .load(imageUrl) // Load from URL or resource
                    .placeholder(R.drawable.filled_circle) // Optional placeholder
                    .into(tinyImage); // Load into the ImageView

            frameLayout.addView(circleView);
            frameLayout.addView(tinyImage);

            // TextView for ingredient and measurement
            TextView tvIngredient = new TextView(view.getContext());
            tvIngredient.setText("     "+ingredient + " (" + measurement + ")");
            tvIngredient.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            tvIngredient.setGravity(Gravity.CENTER_VERTICAL);
            tvIngredient.setTextColor(Color.BLACK);
            tvIngredient.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            // Add the image and text to the row
            row.addView(frameLayout);
            row.addView(tvIngredient);

            // Add the row to the ingredients layout
            ingredientsLayout.addView(row);
            isFavourite(meal.idMeal, new FavouriteCallback() {
                @Override
                public void onResult(boolean isFavourite) {

                    if (isFavourite) {
                        favBtn.setImageResource(R.drawable.saved);
                        favBtn.setTag("colored");

                    } else {
                        favBtn.setImageResource(R.drawable.save);
                        favBtn.setTag("uncolored");
                    }
                }
            });
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (favBtn.getTag() == null || favBtn.getTag().equals("uncolored")) {
                        SaveMeal(meal); // Save the meal as favorite
                        favBtn.setImageResource(R.drawable.saved); // Change icon to saved
                        favBtn.setTag("colored"); // Update the tag to indicate saved state
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setMessage("Are you Sure you want to delete")
                                .setPositiveButton(android.R.string.yes,((dialog, which) -> {
                                    DeleteMeal(meal); // Remove the meal from favorites
                                    favBtn.setImageResource(R.drawable.save); // Change icon back to unsaved
                                    favBtn.setTag("uncolored");
                                }))
                                .setNegativeButton(android.R.string.no,null)
                                .show();
                        // Update the tag to indicate unsaved state
                    }
                }
            });

            calenderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCalendarDialog(meal);
                }
            });
        }




        return view;
    }

    public void SaveMeal(Meal meal){
        mealDetailsPresenter.addToSaved(meal);
        Toast.makeText(getContext(), "Meal Added To Favourites", Toast.LENGTH_SHORT).show();

    }
    public void DeleteMeal(Meal meal){
        mealDetailsPresenter.removeFromSaved(meal);
        Toast.makeText(getContext(), "Meal Removed From Favourites", Toast.LENGTH_SHORT).show();

    }

    public void openCalendarDialog(Meal meal) {
        CalenderFragment myCalendar = new CalenderFragment(meal);
        myCalendar.show(getChildFragmentManager(),"CalenderFragment");

    }
    public interface FavouriteCallback {
        void onResult(boolean isFavourite);
    }

    public void isFavourite(String id, FavouriteCallback callback) {
        mealDetailsPresenter.SearchMealById(id).observe(getViewLifecycleOwner(), new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                if (meal != null) {
                    callback.onResult(true);
                } else {
                    callback.onResult(false);
                }
            }
        });
    }



}
