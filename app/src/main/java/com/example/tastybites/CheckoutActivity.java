package com.example.tastybites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

//
public class CheckoutActivity extends AppCompatActivity implements PaymentResultListener {

    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    RadioGroup radioGroup;
    Button confirmButton;
    RadioButton mastercard_radio_button;

    String endpoint = MainActivity.Constants.API_ENDPOINT;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout); // replace with your layout file
//        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        radioGroup = findViewById(R.id.radio_group);
        confirmButton = findViewById(R.id.button);
        mastercard_radio_button = findViewById(R.id.mastercard_radio_button);
        prefs = getSharedPreferences("shopping_cart", Context.MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String email = prefs.getString("email", null);

        Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();
        // get products from shared preferences
        String productsJson = prefs.getString("cartProducts", null);

        // get total price from intent
        String total_price = getIntent().getStringExtra("total_price");
        // convert total price to double
        double total_price_double = Double.parseDouble(total_price);
        // convert total price to int
        int total_price_int = (int) total_price_double;
        // convert total price to to USD
        double total_price_usd = total_price_int / 3700;
        // convert total price to to USD
        int total_price_usd_int = (int) total_price_usd;
//        remove points from total price
//        int total_price_usd_int = total_price_usd_int - 10;
        Toast.makeText(this, "Total Price: " + total_price_usd_int, Toast.LENGTH_SHORT).show();


        confirmButton.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == mastercard_radio_button.getId()) {
                startPayment(1000);
            } else {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            }
        });
        Checkout.preload(CheckoutActivity.this);

    }

    public void startPayment(int Amount) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_1DP5mmOlF5G5ag");
//        checkout.setKeyID("rzp_test_FXb5XmLEY2ZBQ6");
//        checkout.setImage(R.drawable.logo);
//        final Activity activity = this;
        try {
            JSONObject jsonObject = new JSONObject();
            // image of the payment  from drawable
            jsonObject.put("image", R.drawable.tasty2);
            jsonObject.put("name", "Tasty Bites");
            jsonObject.put("description", "Order Payment");

            // currency should be in USD
            jsonObject.put("currency", "USD");
            jsonObject.put("amount", Amount);
            //color of the payment button
            jsonObject.put("theme.color", "#F9813A");

            JSONObject retryObject = new JSONObject();
            retryObject.put("enabled", true);
            retryObject.put("max_count", 4);
            jsonObject.put("retry", retryObject);
            checkout.open(CheckoutActivity.this, jsonObject);
        } catch (JSONException e) {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
//        checkout.open(CheckoutActivity.this,jsonObject);
    }


    @Override
    public void onPaymentSuccess(String s) {
//        txtPaymentStatus.setText("Payment Successful");
        prefs = getSharedPreferences("shopping_cart", Context.MODE_PRIVATE);
        // get products from shared preferences
        String productsJson = prefs.getString("cartProducts", null);


//        Intent intent = new Intent(CheckoutActivity.this, OrderStatusActivity.class);
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("order", productsJson);
            // put email
            // get email from local storage

            SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
            String email = prefs.getString("email", null);
            jsonObject.put("email", email);
            Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(CheckoutActivity.this);

        String url = endpoint + "/shopping/confirmorder/";

        // sending a post using Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(CheckoutActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        // check if status is 200


                        if (response.has("status")) {
                            try {
                                String status = response.getString("status");
                                if (status.equals("Order created!")) {
                                    // move to home activity
                                    Toast.makeText(CheckoutActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CheckoutActivity.this, OrderStatusActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(CheckoutActivity.this, "Order not Placed", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//
//
                    }

                }, error -> {
            Toast.makeText(CheckoutActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        });
        queue.add(jsonObjectRequest);


    }

    @Override
    public void onPaymentError(int i, String s) {
//        txtPaymentStatus.setText("Payment Failed");
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();

    }
}




