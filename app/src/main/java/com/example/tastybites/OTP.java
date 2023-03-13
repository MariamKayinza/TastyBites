package com.example.tastybites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OTP extends AppCompatActivity {

    Button otpButton;
    private EditText otp1, otp2, otp3, otp4, otp5, otp6;

    private String otp;

    SharedPreferences prefs;
    SharedPreferences.Editor edit;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);
        otpButton = findViewById(R.id.otpButton);

        otp1.addTextChangedListener(new OTPListener(otp1, otp2));
        otp2.addTextChangedListener(new OTPListener(otp2, otp3));
        otp3.addTextChangedListener(new OTPListener(otp3, otp4));
        otp4.addTextChangedListener(new OTPListener(otp4, otp5));
        otp5.addTextChangedListener(new OTPListener(otp5, otp6));
        otp6.addTextChangedListener(new OTPListener(otp6, null));

        otpButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String url = "https://tastybites-1.onrender.com/customer/verify";
                        String endpoint = MainActivity.Constants.API_ENDPOINT;

                        String url = endpoint + "/customer/verify";
                        // get signature from shared preferences


                        try {
                            prefs = getSharedPreferences("user", MODE_PRIVATE);
                            String signature = prefs.getString("signature", null);
                            // check if signature is not null
                            Toast.makeText(OTP.this, signature, Toast.LENGTH_SHORT).show();
                            if (signature != null) {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString() + otp5.getText().toString() + otp6.getText().toString();
                                    jsonObject.put("otp", otp);

                                    Toast.makeText(OTP.this, "OTP is" + jsonObject, Toast.LENGTH_SHORT).show();
                                    RequestQueue queue = Volley.newRequestQueue(OTP.this);
//
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, jsonObject,
                                            // headers
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    String status = null;
                                                    try {
                                                        status = response.getString("verified");
                                                    } catch (JSONException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                    Toast.makeText(OTP.this, ""+ response, Toast.LENGTH_SHORT).show();
                                                        if (status.equals("true")) {
                                                            // move to home activity
                                                            Toast.makeText(OTP.this, "OTP is correct", Toast.LENGTH_SHORT).show();
                                                            // set  firstName in shared preferences
                                                            try {
                                                                String firstName = response.getString("firstName");
                                                                edit = prefs.edit();
                                                                edit.putString("firstName", firstName);
                                                                edit.apply();
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                            Intent intent = new Intent(OTP.this, Home.class);
                                                            startActivity(intent);
                                                        } else {
                                                            Toast.makeText(OTP.this, "OTP is incorrect", Toast.LENGTH_SHORT).show();
                                                        }
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(OTP.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

                                        }


                                    }){
                                        @Override
                                        public Map<String, String> getHeaders() throws AuthFailureError {
                                            Map<String, String> headers = new HashMap<>();
                                            String token = "Bearer " + signature;
                                            headers.put("Authorization", token);
                                            headers.put("Content-Type", "application/json");
                                            return headers;
                                        }
                                    };

                                        // Add the request to the RequestQueue.

                                    queue.add(jsonObjectRequest);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                            // Add the request to the RequestQueue.
//                            queue.add(jsonObjectRequest);
//                        }


                    }


                });

    }

        private class OTPListener implements TextWatcher {

        private EditText currentBox;
        private EditText nextBox;

        OTPListener(EditText currentBox, EditText nextBox) {
            this.currentBox = currentBox;
            this.nextBox = nextBox;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override

        public void afterTextChanged(Editable s) {
            if (s.length() == 1) {
                if (nextBox != null) {
                    nextBox.requestFocus();
                } else {

                    otp = otp1.getText().toString() + otp2.getText().toString() +
                            otp3.getText().toString() + otp4.getText().toString() +
                            otp5.getText().toString() + otp6.getText().toString();


                    Toast.makeText(OTP.this, "Your OTP is: " + otp, Toast.LENGTH_SHORT).show();

                    // Send the OTP to Backend and verify it


                }
}
            };
        }
    }

