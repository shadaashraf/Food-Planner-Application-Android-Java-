package com.example.rosyrecipebox;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rosyrecipebox.saved.view.SavedFragment;
import com.example.rosyrecipebox.search.view.SearchFragment;
import com.example.rosyrecipebox.home.view.HomeFragment;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton buttonHome = findViewById(R.id.navigation_home);
        ImageButton buttonSearch = findViewById(R.id.navigation_search);
        ImageButton buttonSaved = findViewById(R.id.navigation_save);
        View rootView = findViewById(android.R.id.content);

        if (!isNetworkAvailable()) {
            Snackbar.make(rootView, "You are offline", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_content, new SavedFragment())
                                    .commit();
                        }
                    })
                    .show();
            return;
        } else {
            if (savedInstanceState == null) {
                buttonHome.setImageResource(R.drawable.home_color);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
            }

            buttonHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonHome.setImageResource(R.drawable.home_color);
                    buttonSaved.setImageResource(R.drawable.save);
                    buttonSearch.setImageResource(R.drawable.search);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_content, new HomeFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });

            buttonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSearch.setImageResource(R.drawable.search_color);
                    buttonHome.setImageResource(R.drawable.home);
                    buttonSaved.setImageResource(R.drawable.save);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_content, new SearchFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });

            buttonSaved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonSearch.setImageResource(R.drawable.search);
                    buttonHome.setImageResource(R.drawable.home);
                    buttonSaved.setImageResource(R.drawable.saved);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_content, new SavedFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
