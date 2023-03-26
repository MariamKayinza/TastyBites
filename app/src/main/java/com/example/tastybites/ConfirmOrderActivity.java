package com.example.tastybites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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