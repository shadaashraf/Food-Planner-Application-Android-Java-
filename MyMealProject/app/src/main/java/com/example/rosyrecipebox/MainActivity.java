package com.example.rosyrecipebox;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rosyrecipebox.saved.view.SavedFragment;
import com.example.rosyrecipebox.search.view.SearchFragment;
import com.example.rosyrecipebox.home.view.HomeFragment; // Make sure to import your HomeFragment

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        }

        ImageButton buttonHome = findViewById(R.id.navigation_home);
        ImageButton buttonSearch = findViewById(R.id.navigation_search);
        ImageButton buttonSaved=findViewById(R.id.navigation_save);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new HomeFragment())
                        .addToBackStack(null) // Add to back stack if you want to navigate back
                        .commit();
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new SearchFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        buttonSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new SavedFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        }
    }


