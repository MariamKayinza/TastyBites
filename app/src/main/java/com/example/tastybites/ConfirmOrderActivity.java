package com.example.tastybites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {
    public RecyclerView recycler_view_cart;
    public SharedPreferences sharedPreferences;
    public Button checkout;
    public Button cancel;
    public RecyclerView recyclerView;
    String endpoint = MainActivity.Constants.API_ENDPOINT;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = endpoint + "/shopping/confirmorder/";

        setContentView(R.layout.activity_confirm_order);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_view_cart = findViewById(R.id.recycler_view_cart1);
        recycler_view_cart.setLayoutManager(linearLayoutManager);
//        response_recycler_view.setLayoutManager(gridLayoutManager);
        Gson gson = new Gson();
        sharedPreferences = getSharedPreferences("shopping_cart", Context.MODE_PRIVATE);
        String productsJson = sharedPreferences.getString("cartProducts", null);

        List<Repo> products = new Gson().fromJson(productsJson, new TypeToken<List<Repo>>() {
        }.getType());
        recyclerView = findViewById(R.id.recycler_view_cart1);
        checkout = findViewById(R.id.CONFIRM_ORDER);
        cancel = findViewById(R.id.CANCEL1);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonObject = new JSONObject();
                Toast.makeText(ConfirmOrderActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();

                try {
                    jsonObject.put("order", productsJson);
                    // put email
                    // get email from local storage

                    SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
                    String email = prefs.getString("email", null);
                    jsonObject.put("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestQueue queue = Volley.newRequestQueue(ConfirmOrderActivity.this);

                // sending a post using Volley
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Toast.makeText(ConfirmOrderActivity.this, "" + response, Toast.LENGTH_SHORT).show();
//                                if (status.equals("true")) {
//                                    // move to home activity
//                                    Toast.makeText(OTP.this, "OTP is correct", Toast.LENGTH_SHORT).show();
//                                    // set  firstName in shared preferences
//                                    try {
//                                        String firstName = response.getString("firstName");
//                                        edit = prefs.edit();
//                                        edit.putString("firstName", firstName);
//                                        edit.apply();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    Intent intent = new Intent(OTP.this, Home.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    Toast.makeText(OTP.this, "OTP is incorrect", Toast.LENGTH_SHORT).show();
//                                }
                            }

                        }, error -> {
                    Toast.makeText(ConfirmOrderActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });
                queue.add(jsonObjectRequest);

            }
        });




//            }
//        });

//        Button checkout = findViewById(R.id.CHECKOUT1);
//        Button cancel = findViewById(R.id.CANCEL1);
//        Button checkout = findViewById(R.id.CHECKOUT1);
        // on click listener clear  shared preferences
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.clear();
//                editor.apply();
//                // back to  home activity
//                Intent intent = new Intent(getActivity(), Home.class);
//                startActivity(intent);
//                // FINISH
//                getActivity().finish();
//
//            }
//        });
        SharedPreferences prefs = getSharedPreferences("signature", Context.MODE_PRIVATE);
        String restoredText = prefs.getString("signature", null);
        String firstName = prefs.getString("firstName", null);



        recyclerView.setAdapter(new RecyclerViewCartAdapter(products, this));
    }
}