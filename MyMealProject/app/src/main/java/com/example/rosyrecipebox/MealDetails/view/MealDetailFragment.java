package com.example.rosyrecipebox.MealDetails.view;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rosyrecipebox.R;
import com.example.rosyrecipebox.model.Meal;

import java.util.List;

public class MealDetailFragment extends Fragment {

    private ImageView imgMealThumb;
    private TextView tvMealName, tvMealDetails, tvInstructions;
    private WebView webviewYoutube;
    private LinearLayout ingredientsContainer;
    private Meal meal;
    private String ingredientsImgBaseURL="https://www.themealdb.com/images/ingredients/";
    public MealDetailFragment(Meal meal) {
        this.meal = meal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details, container, false);

        imgMealThumb = view.findViewById(R.id.imgMealThumb);
        tvMealName = view.findViewById(R.id.tvMealName);
        tvMealDetails = view.findViewById(R.id.tvMealDetails);
        tvInstructions = view.findViewById(R.id.tvInstructions);
        WebView webviewYoutube = view.findViewById(R.id.webviewYoutube);
        ingredientsContainer = view.findViewById(R.id.ingredientsLayout);


            tvMealName.setText(meal.strMeal);
            tvMealDetails.setText(meal.strCategory + " | " + meal.strArea);
            tvInstructions.setText(meal.strInstructions);

            Glide.with(this).load(meal.strMealThumb)
                    .apply(new RequestOptions().override(200, 200)
                            .placeholder(R.drawable.ic_launcher_background)
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
                    .placeholder(R.drawable.ic_launcher_foreground) // Optional placeholder
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
        }






        return view;
    }
}
