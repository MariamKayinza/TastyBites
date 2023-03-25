package com.example.tastybites;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    SearchView searchView;
    ImageButton imageButton;

    private HomeFragement homeFragement;
    private RestaurantsList restaurantsListFragement;
    private ProfileFragment profileFragment;

    private static final int REQUEST_ENABLE_LOCATION = 1;


    TextView home_welcome_text;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // change the status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        //? Check if location services are enabled, and show a popup to enable them if not


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FrameLayout fragmentLayout = findViewById(R.id.fragmentlayout);
        homeFragement = new HomeFragement();
        restaurantsListFragement = new RestaurantsList();
        profileFragment = new ProfileFragment();
        InitializerFragement(homeFragement);
        String from = getIntent().getStringExtra("from");
        if (from != null) {
            if (from.equals("cart")) {
                InitializerFragement(new CartFragment());
                bottomNavigationView.setSelectedItemId(R.id.cart_menu);
            }
        } else {
            InitializerFragement(homeFragement);
        }


        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_menu:
                        InitializerFragement(homeFragement);
                        return true;
                    case R.id.list_menu:
                        InitializerFragement(restaurantsListFragement);
                        return true;
                    case R.id.profile_menu:
                        InitializerFragement(profileFragment);
                        return true;
                    case R.id.cart_menu:
                        InitializerFragement(new CartFragment());
                        return true;
//                        return true;
//                    case R.id.cart_menu:
//                        startActivity(new Intent(getApplicationContext(),Cart.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.list_menu:
//                        startActivity(new Intent(getApplicationContext(),Category.class));
//                        overridePendingTransition(0,0);
//                        return true;

                }
                return false;
            }
        });
        imageButton = findViewById(R.id.account_status_login_or_register);

        // Setting onClick behavior to the button
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(Home.this, imageButton);
                ;

                //? CHECK IF USER IS LOGGED IN OR NOT
                //? GET USER DATA FROM SHARED PREFERENCES
                try {
                    prefs = getSharedPreferences("user", MODE_PRIVATE);
                    String restoredText = prefs.getString("signature", null);
                    Toast toast = Toast.makeText(Home.this, restoredText, Toast.LENGTH_SHORT);

                    if (restoredText != null) {
                        //? IF USER IS LOGGED IN THEN SHOW LOGOUT BUTTON
                        popupMenu.getMenuInflater().inflate(R.menu.account_logout, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.logout:
                                        //? CLEAR SHARED PREFERENCES
                                        prefs = getSharedPreferences("user", MODE_PRIVATE);
                                        SharedPreferences sharedPreferences = getSharedPreferences("shopping_cart", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = prefs.edit();
                                        SharedPreferences.Editor editor_Cart = sharedPreferences.edit();

                                        editor.remove("signature");
                                        editor.remove("firstName");
                                        editor_Cart.remove("cartProducts");


                                        editor.commit();
                                        prefs.edit().clear().apply();
                                        sharedPreferences.edit().clear().apply();
                                        String productsJson = sharedPreferences.getString("cartProducts", null);
                                        Toast.makeText(Home.this, ""+productsJson, Toast.LENGTH_SHORT).show();



                                        //? RESTART ACTIVITY
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                        finish();
                                        overridePendingTransition(0, 0);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        // Showing the popup menu
                        popupMenu.show();
                    } else {
                        //? IF USER IS NOT LOGGED IN THEN SHOW LOGIN AND REGISTER BUTTON
                        popupMenu.getMenuInflater().inflate(R.menu.account, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.login:
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                        overridePendingTransition(0, 0);
                                        return true;
                                    case R.id.register:
                                        startActivity(new Intent(getApplicationContext(), Register.class));
                                        overridePendingTransition(0, 0);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        // Showing the popup menu
                        popupMenu.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
        //! Prompot to turn on location
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showLocationSettingsPopup();
        }
        //! ALLOW TASTY BITES ACCESS LOCATION (How to request Location permission at run time in Android?)
        if (ContextCompat.checkSelfPermission(Home.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(Home.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(Home.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    //! Prompot to turn on location
    private void showLocationSettingsPopup() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Enable Location Services");
        dialogBuilder.setMessage("Location services need to be enabled to use this feature. Please enable location services in your device settings.");
        dialogBuilder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(settingsIntent, REQUEST_ENABLE_LOCATION);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Handle cancel button click
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_LOCATION) {
            // Check if the user enabled location services, and show a popup if they didn't
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showLocationSettingsPopup();
            }
        }
    }

    //!    Navigation for Fragements
    private void InitializerFragement(Fragment fragment) {
        FragmentTransaction fragementTransaction = getSupportFragmentManager().beginTransaction();
        fragementTransaction.replace(R.id.fragmentlayout, fragment);
        fragementTransaction.commit();
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentlayout, fragment)
                .commit();
    }

    //! ALLOW TASTY BITES ACCESS LOCATION (How to request Location permission at run time in Android?)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Home.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}