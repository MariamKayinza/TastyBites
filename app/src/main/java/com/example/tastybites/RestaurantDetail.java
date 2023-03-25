package com.example.tastybites;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetail extends AppCompatActivity {

    public TextView restaurant_name;
    public TextView restaurant_address;
    public ImageView header_cover_image;
    String endpoint = MainActivity.Constants.API_ENDPOINT;

    RecyclerView response_recycler_view;





    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        restaurant_name = findViewById(R.id.restaurant_name);
        restaurant_address = findViewById(R.id.restaurant_address);
        // get the restaurant name from the intent
           String   restaurantName = getIntent().getStringExtra("name");
        // set the restaurant name to the text view
        restaurant_name.setText(restaurantName);
        // resturaunt  image header
        header_cover_image = findViewById(R.id.header_cover_image);
        // get the restaurant image from the intent
        String restaurantImage = getIntent().getStringExtra("image");
        String restaurantAddress = getIntent().getStringExtra("address");
        restaurant_address.setText(restaurantAddress);
        // set the restaurant image to the image view
        Glide.with(this).load(restaurantImage).into(header_cover_image);

        response_recycler_view = findViewById(R.id.myresponse_recycler_view1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(RestaurantDetail.this, 2);
        response_recycler_view.setLayoutManager(gridLayoutManager);
        // got back to the HOME FRAGMENT


        VolleyMyGet();


    }
    public void VolleyMyGet(){
        String url =  endpoint + "/shopping/restaurant-name/"+ restaurant_name.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        List<Repo> jsonResponses = new ArrayList<>();

        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // get food arrary from response
                            // first loop through the response and get the food array
//                            for (int i = 0; i < response.length(); i++) {
//                                Toast.makeText(getActivity(), "i"+i, Toast.LENGTH_SHORT).show();

                                JSONArray food = response.getJSONArray("foods");
//                                String description = jsonObject.getString("description");

                                // loop through the food array
                                for (int j = 0; j < food.length(); j++) {
                                    JSONObject foodObject = food.getJSONObject(j);
                                    String id = foodObject.getString("_id");
                                    String name = foodObject.getString("name");
                                    int price = foodObject.getInt("price");
                                    JSONArray images = foodObject.getJSONArray("images");
                                    int quantity = 1;
                                    // convert price to string
                                    String priceString = Integer.toString(price);
                                    String totalprice = priceString;
//                                    priceString = "UGX " + priceString;

                                    String image = images.getString(0);
                                    jsonResponses.add(new Repo(id, name, priceString, image, quantity, totalprice));
                                    response_recycler_view.setAdapter(new RecylerViewRestaurantDetailAdapter(jsonResponses, RestaurantDetail.this));

                                }
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RestaurantDetail.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(JsonObjectRequest);
    }
}