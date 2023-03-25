package com.example.tastybites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    Button login_button;
    TextInputEditText emailEdit;
    TextInputEditText passwordEdit;
    TextView donot_have_account;

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    private String BASE_URL="https://tastybites-1.onrender.com";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //? get the shared preferences
        try {
            prefs = getSharedPreferences("user", MODE_PRIVATE);
            edit = prefs.edit();
            //? get signature from shared preferences
            String signature = prefs.getString("signature", null);
            //? check if signature is not null
            Toast.makeText(this, signature, Toast.LENGTH_SHORT).show();
            if (signature != null) {
                // move to home activity
                Intent intent = new Intent( Login.this, Home.class);
                startActivity(intent);
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }





         emailEdit= findViewById(R.id.email_edit_text);
         passwordEdit = findViewById(R.id.password_edit_text);

         donot_have_account = findViewById(R.id.login_donot_have_account_text_view);
            donot_have_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                   Move to Register Activity
                    Intent intent = new Intent(Login.this, Register.class);
                    startActivity(intent);

                }
            });

        login_button = findViewById(R.id.login_button);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Login.this);
//                String url = "https://tastybites-1.onrender.com/customer/login";
                String endpoint = MainActivity.Constants.API_ENDPOINT;
                String url =  endpoint + "/customer/login";

                // send in a POST REQUEST
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("email", email);
                    jsonBody.put("password", password);
                } catch (Exception e) {
                    e.printStackTrace();

                }

                // print the json body
//                Toast.makeText(Login.this, "this is the" + jsonBody, Toast.LENGTH_SHORT).show();
                // send in a POST REQUEST in a JSON format
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(Login.this, "Response is: "+ response, Toast.LENGTH_SHORT).show();

                        // STORE SIGNATURE IN SHARED PREFERENCES
                        String signature = null;
                        String first_name = null;
                        try {

                            prefs = getSharedPreferences("user", MODE_PRIVATE);
                            signature = response.getString("signature");
                            first_name = response.getString("firstName");
                            edit = prefs.edit();
                            edit.putString("signature", signature);
                            edit.putString("firstName",first_name);
                            edit.putString("email",email);
                            edit.commit();

                            // MOVE TO HOME ACTIVITY
                            String name = prefs.getString("firstName", "");
//                            Toast.makeText(Login.this, ">" +name, Toast.LENGTH_SHORT).show();


//                            Intent intent = new Intent(Login.this, Home.class);
//                            startActivity(intent);
//                            finish();
                            String from = getIntent().getStringExtra("from");
                            if (from != null) {
                                if (from.equals("cart")) {
                                    // move to cart Fragment
                                    Intent intent1 = new Intent(Login.this, Home.class);
                                    intent1.putExtra("from", "cart");
                                    startActivity(intent1);
                                    finish();
                                }
                            }else {
                                // move to home activity
                                Intent intent1 = new Intent(Login.this, Home.class);
                                startActivity(intent1);
                                finish();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "this is the" +e, Toast.LENGTH_SHORT).show();
                            throw new RuntimeException(e);

                        }




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "this is the" +error, Toast.LENGTH_SHORT).show();
                    }
                });


                 // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                Toast.makeText(Login.this, "Response is: "+ response.substring(0,5), Toast.LENGTH_SHORT).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(Login.this, "That didn't work!", Toast.LENGTH_SHORT).show();
//                    }
//                });

                // Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);
                // Add the request to the RequestQueue.
//                queue.add(stringRequest);
//                    Toast.makeText(Login.this, "Please enter all the details", Toast.LENGTH_SHORT).show();

            }
        });
        }

    }

