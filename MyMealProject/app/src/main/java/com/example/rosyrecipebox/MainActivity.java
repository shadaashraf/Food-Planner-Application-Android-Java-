package com.example.rosyrecipebox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rosyrecipebox.WeekPlanner.view.WeekPlannerFragment;
import com.example.rosyrecipebox.network.NetworkChangeReceiver;
import com.example.rosyrecipebox.saved.view.SavedFragment;
import com.example.rosyrecipebox.search.view.SearchFragment;
import com.example.rosyrecipebox.home.view.HomeFragment;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.NetworkChangeListener {

    private Animation buttonClickAnimation;
    private FragmentManager fragmentManager;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the button click animation
        buttonClickAnimation = AnimationUtils.loadAnimation(this, R.anim.button_click);

        // Initialize the FragmentManager
        fragmentManager = getSupportFragmentManager();

        ImageButton buttonHome = findViewById(R.id.navigation_home);
        ImageButton buttonSearch = findViewById(R.id.navigation_search);
        ImageButton buttonSaved = findViewById(R.id.navigation_save);
        ImageButton buttonPlan = findViewById(R.id.navigation_grid);
        TextView searchTxt = findViewById(R.id.text_search);
        TextView gridTxt = findViewById(R.id.text_grid);
        TextView homeTxt = findViewById(R.id.text_home);

        View rootView = findViewById(android.R.id.content);

        // Register network change receiver
        networkChangeReceiver = new NetworkChangeReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);

        // Check network availability
        if (!isNetworkAvailable()) {
            showOfflineDialog();
            setupOfflineButtonListeners(buttonHome, buttonSearch, buttonSaved, buttonPlan, homeTxt, searchTxt, gridTxt);
            return;
        }

        // Set the initial fragment and button states
        if (savedInstanceState == null) {
            setInitialFragment(buttonHome, homeTxt, new HomeFragment());
        }

        // Set button click listeners
        setupButtonListeners(buttonHome, buttonSearch, buttonSaved, buttonPlan, homeTxt, searchTxt, gridTxt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the receiver to avoid memory leaks
        unregisterReceiver(networkChangeReceiver);
    }

    private void setupButtonListeners(ImageButton buttonHome, ImageButton buttonSearch, ImageButton buttonSaved, ImageButton buttonPlan,
                                      TextView homeTxt, TextView searchTxt, TextView gridTxt) {
        buttonPlan.setOnClickListener(v -> switchFragment(buttonHome, buttonSearch, buttonSaved, buttonPlan, homeTxt, searchTxt, gridTxt, new WeekPlannerFragment(), "Planner"));
        buttonHome.setOnClickListener(v -> switchFragment(buttonHome, buttonSearch, buttonSaved, buttonPlan, homeTxt, searchTxt, gridTxt, new HomeFragment(), "Home"));
        buttonSearch.setOnClickListener(v -> switchFragment(buttonHome, buttonSearch, buttonSaved, buttonPlan, homeTxt, searchTxt, gridTxt, new SearchFragment(), "Search"));
        buttonSaved.setOnClickListener(v -> switchFragment(buttonHome, buttonSearch, buttonSaved, buttonPlan, homeTxt, searchTxt, gridTxt, new SavedFragment(), "Saved"));
    }

    private void setupOfflineButtonListeners(ImageButton buttonHome, ImageButton buttonSearch, ImageButton buttonSaved, ImageButton buttonPlan,
                                             TextView homeTxt, TextView searchTxt, TextView gridTxt) {
        buttonPlan.setOnClickListener(v -> switchFragment(buttonHome, buttonSearch, buttonSaved, buttonPlan, homeTxt, searchTxt, gridTxt, new WeekPlannerFragment(), "Planner"));
        buttonHome.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Home needs network to open it please", Toast.LENGTH_LONG).show());
        buttonSearch.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Search needs network to open it please", Toast.LENGTH_LONG).show());
        buttonSaved.setOnClickListener(v -> switchFragment(buttonHome, buttonSearch, buttonSaved, buttonPlan, homeTxt, searchTxt, gridTxt, new SavedFragment(), "Saved"));
    }

    private void setInitialFragment(ImageButton buttonHome, TextView homeTxt, Fragment fragment) {
        buttonHome.setImageResource(R.drawable.home_color);
        homeTxt.setTextColor(getResources().getColor(R.color.pink));
        homeTxt.setText("Home");
        homeTxt.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
    }

    private void switchFragment(ImageButton buttonHome, ImageButton buttonSearch, ImageButton buttonSaved, ImageButton buttonPlan,
                                TextView homeTxt, TextView searchTxt, TextView gridTxt, Fragment newFragment, String text) {
        // Reset all button icons and text visibility
        resetButtonStates(buttonHome, buttonSearch, buttonSaved, buttonPlan, homeTxt, searchTxt, gridTxt);

        // Set the selected button and its text
        switch (text) {
            case "Home":
                buttonHome.setImageResource(R.drawable.home_color);
                homeTxt.setTextColor(getResources().getColor(R.color.pink));
                homeTxt.setText("Home");
                homeTxt.setVisibility(View.VISIBLE);
                break;
            case "Search":
                buttonSearch.setImageResource(R.drawable.search_color);
                searchTxt.setTextColor(getResources().getColor(R.color.pink));
                searchTxt.setText("Search");
                searchTxt.setVisibility(View.VISIBLE);
                break;
            case "Planner":
                buttonPlan.setImageResource(R.drawable.planner_color);
                gridTxt.setTextColor(getResources().getColor(R.color.pink));
                gridTxt.setText("Planner");
                gridTxt.setVisibility(View.VISIBLE);
                break;
            case "Saved":
                buttonSaved.setImageResource(R.drawable.saved); // Assuming you have a colored icon
                break;
        }

        replaceFragment(newFragment);
    }

    private void resetButtonStates(ImageButton buttonHome, ImageButton buttonSearch, ImageButton buttonSaved, ImageButton buttonPlan,
                                   TextView homeTxt, TextView searchTxt, TextView gridTxt) {
        buttonHome.setImageResource(R.drawable.home);
        buttonSaved.setImageResource(R.drawable.save);
        buttonSearch.setImageResource(R.drawable.search);
        buttonPlan.setImageResource(R.drawable.planner);
        homeTxt.setVisibility(View.GONE);
        searchTxt.setVisibility(View.GONE);
        gridTxt.setVisibility(View.GONE);
    }

    private void showOfflineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Network Unavailable")
                .setMessage("You are offline. Please select an option:")
                .setPositiveButton("Go to Saved", (dialog, which) -> {
                    replaceFragment(new SavedFragment());
                })
                .setNegativeButton("Plan Meal", (dialog, which) -> {
                    replaceFragment(new WeekPlannerFragment());
                })
                .setCancelable(false) // Prevent dismissal by tapping outside
                .show();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack(null); // Allow back navigation
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Get the current fragment and update the button states accordingly
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_content);
        if (currentFragment instanceof HomeFragment) {
            updateButtonStates(R.id.navigation_home);
        } else if (currentFragment instanceof SearchFragment) {
            updateButtonStates(R.id.navigation_search);
        } else if (currentFragment instanceof SavedFragment) {
            updateButtonStates(R.id.navigation_save);
        } else if (currentFragment instanceof WeekPlannerFragment) {
            updateButtonStates(R.id.navigation_grid);
        }
    }

    private void updateButtonStates(int selectedButtonId) {
        ImageButton buttonHome = findViewById(R.id.navigation_home);
        ImageButton buttonSearch = findViewById(R.id.navigation_search);
        ImageButton buttonSaved = findViewById(R.id.navigation_save);
        ImageButton buttonPlan = findViewById(R.id.navigation_grid);
        TextView homeTxt = findViewById(R.id.text_home);
        TextView searchTxt = findViewById(R.id.text_search);
        TextView gridTxt = findViewById(R.id.text_grid);

        // Reset all buttons to default state
        resetButtonStates(buttonHome, buttonSearch, buttonSaved, buttonPlan, homeTxt, searchTxt, gridTxt);

        // Set the selected button state using if-else
        if (selectedButtonId == R.id.navigation_home) {
            buttonHome.setImageResource(R.drawable.home_color);
            homeTxt.setTextColor(getResources().getColor(R.color.pink));
            homeTxt.setText("Home");
            homeTxt.setVisibility(View.VISIBLE);
        } else if (selectedButtonId == R.id.navigation_search) {
            buttonSearch.setImageResource(R.drawable.search_color);
            searchTxt.setTextColor(getResources().getColor(R.color.pink));
            searchTxt.setText("Search");
            searchTxt.setVisibility(View.VISIBLE);
        } else if (selectedButtonId == R.id.navigation_save) {
            buttonSaved.setImageResource(R.drawable.saved);
            // You may want to handle text visibility here too
        } else if (selectedButtonId == R.id.navigation_grid) {
            buttonPlan.setImageResource(R.drawable.planner_color);
            gridTxt.setTextColor(getResources().getColor(R.color.pink));
            gridTxt.setText("Planner");
            gridTxt.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    @Override
    public void onNetworkChanged(boolean isConnected) {
        if (isConnected) {
            // Network is available, automatically switch to HomeFragment
            switchFragment(
                    findViewById(R.id.navigation_home),
                    findViewById(R.id.navigation_search),
                    findViewById(R.id.navigation_save),
                    findViewById(R.id.navigation_grid),
                    findViewById(R.id.text_home),
                    findViewById(R.id.text_search),
                    findViewById(R.id.text_grid),
                    new HomeFragment(),
                    "Home"
            );
            Snackbar.make(findViewById(android.R.id.content), "Connected to network. Loading data...", Snackbar.LENGTH_SHORT).show();
        } else {
            showOfflineDialog(); // Handle when there's no network
        }
    }
}
