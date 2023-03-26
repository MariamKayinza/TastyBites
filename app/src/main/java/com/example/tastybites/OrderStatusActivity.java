package com.example.tastybites;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderStatusActivity extends AppCompatActivity {
    String endpoint = MainActivity.Constants.API_ENDPOINT;
    public SharedPreferences prefs;
    public SharedPreferences.Editor edit;
    private ListView orderHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        orderHistoryList = findViewById(R.id.order_history_list);


        VolleyMyGet();

    }

    public void VolleyMyGet() {
        String url = endpoint + "/customer/orders";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        List<Repo> jsonResponses = new ArrayList<>();
        prefs = getSharedPreferences("user", MODE_PRIVATE);
        String signature = prefs.getString("signature", null);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            // get food arrary from response
                            // first loop through the response and get the food array
                            List<Order> orders = new ArrayList<>();
//                            Toast.makeText(OrderStatusActivity.this, "" + response, Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < response.length(); i++) {
//                                Toast.makeText(OrderStatusActivity.this, "i"+i, Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = response.getJSONObject(i);
//                                String id = jsonObject.getString("_id");
                                String status = jsonObject.getString("status");
                                String vendorName = jsonObject.getString("vendorName");
                                String totalPrice = jsonObject.getString("totalprice");
//                                String createdAt = jsonObject.getString("createdAt");
//                                String updatedAt = jsonObject.getString("updatedAt");
//                                String quantity = jsonObject.getString("quantity");
                                String foodName = jsonObject.getString("foodName");
//                                Toast.makeText(OrderStatusActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(OrderStatusActivity.this, ""+foodName, Toast.LENGTH_SHORT).show();
                                Order order = new Order(foodName, vendorName, totalPrice, status);

                                orders.add(order);
                            }
                            OrderHistoryAdapter adapter = new OrderHistoryAdapter(OrderStatusActivity.this, orders);
                            orderHistoryList.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(OrderStatusActivity.this, "error" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = "Bearer " + signature;
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }
}