package com.example.tastybites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.Locale;

public class ShippingAdressActivity extends AppCompatActivity {
    public SearchView searchView;
    public SharedPreferences sharedPreferences;
    public Button addNewAddressButton;
    public Button useCurrentLocation;

    private LocationManager locationManager;
    private Button locationButton;


    private static final int REQUEST_MAP_LOCATION = 100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_adress);

//        Toolbar toolbar = findViewById(R.id.toolbar);


        // Initialize the SearchView
        // Initialize SharedPreferences
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        addNewAddressButton = findViewById(R.id.addNewAddress);
        addNewAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapAndPickLocation(v);
            }
        });
        useCurrentLocation = findViewById(R.id.useCurrentLocation);
        useCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                // got to checkout activity
                Intent intent = new Intent(ShippingAdressActivity.this, CheckoutActivity.class);
                startActivity(intent);

//                if (ActivityCompat.checkSelfPermission(ShippingAdressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                        ActivityCompat.checkSelfPermission(ShippingAdressActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(ShippingAdressActivity.this, "Location permission not granted", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, (float) 0, ShippingAdressActivity.this);
            }
        });
    }

    public void openMapAndPickLocation(View view) {
        // Create a Uri object with the location data
        Uri locationUri = Uri.parse("geo:0,0?q=");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:0,0?q="));
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_MAP_LOCATION);
        } else {
            Toast.makeText(ShippingAdressActivity.this, "Google Maps is not installed on this device", Toast.LENGTH_SHORT).show();
        }
        // Start the activity with the intent
//        startActivityForResult(mapIntent, REQUEST_MAP_LOCATION);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                // Get the selected location's coordinates from the returned intent
//                String location = data.getData().toString();
//
//                // Store the location in SharedPreferences
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("location", location);
//                editor.apply();
//            }
//        }
//    }

    public void onLocationChanged(Location location) {
        Toast.makeText(this, "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(ShippingAdressActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);

//            textView_location.setText(address);
            Toast.makeText(this, ""+address, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Current location: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        locationManager.removeUpdates((LocationListener) ShippingAdressActivity.this);
    }

    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, (LocationListener) ShippingAdressActivity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the activity result is from the Google Maps intent
        if (requestCode == REQUEST_MAP_LOCATION && resultCode == RESULT_OK) {
            // Get the location data from the Intent
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);

            // Store the location data in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("latitude", (float) latitude);
            editor.putFloat("longitude", (float) longitude);
            editor.apply();

            // Show a Toast message with the location data
            String message = String.format("Location set to: %f, %f", latitude, longitude);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }


}