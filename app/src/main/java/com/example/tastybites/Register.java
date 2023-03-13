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

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    TextView already_have_account;
    Button register_button;
    RequestQueue queue;
    TextView emailEdit;
    TextView phoneEdit;
    TextView firstNameEdit;
    TextView lastNameEdit;
    TextView passwordEdit;
    TextView confirmPasswordEdit;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
                Intent intent = new Intent( Register.this, Home.class);
                startActivity(intent);
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        setContentView(R.layout.activity_register);

        emailEdit = findViewById(R.id.email_edit_text);
        phoneEdit = findViewById(R.id.phone_edit_text);
        firstNameEdit = findViewById(R.id.first_name_edit_text);
        lastNameEdit = findViewById(R.id.last_name_edit_text);
        passwordEdit = findViewById(R.id.password_edit_text);
        confirmPasswordEdit = findViewById(R.id.confirm_password_edit_text);
        already_have_account = findViewById(R.id.already_have_an_account_text_view);


        already_have_account.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                    }
                }
        );
        register_button = findViewById(R.id.register_button);
        register_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(Register.this, OTP.class);
//                        startActivity(intent);
                            String email = emailEdit.getText().toString();
                            String password = passwordEdit.getText().toString();
                            // Instantiate the RequestQueue.
                            RequestQueue queue = Volley.newRequestQueue(Register.this);
//                            String url = "https://tastybites-1.onrender.com/customer/signup";
                        String endpoint = MainActivity.Constants.API_ENDPOINT;
                        String url = endpoint + "/customer/signup";

                            // send in a POST REQUEST
                            JSONObject jsonBody = new JSONObject();
                            try {
                                jsonBody.put("email", email);
                                jsonBody.put("firstName", firstNameEdit.getText().toString());
                                jsonBody.put("lastName", lastNameEdit.getText().toString());
                                jsonBody.put("phone", phoneEdit.getText().toString());
                                jsonBody.put("password", confirmPasswordEdit.getText().toString());

                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        Toast.makeText(Register.this, ""+ jsonBody, Toast.LENGTH_SHORT).show();
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                       String signature = null;
                                       Toast.makeText(Register.this, "Response is: "+ response, Toast.LENGTH_SHORT).show();

                                       try {
                                           signature = response.getString("signature");
                                           // remove all the shared preferences
                                            prefs = getSharedPreferences("user", MODE_PRIVATE);
                                            edit = prefs.edit();
                                            edit.clear();
                                            edit.apply();
                                            // store the signature in shared preferences
                                            edit.putString("signature", signature);
                                            edit.apply();
                                            // move to the OTP activity
                                            Intent intent = new Intent(Register.this, OTP.class);
                                            startActivity(intent);

                                       }catch (JSONException e){
                                           e.printStackTrace();
                                            Toast.makeText(Register.this, "this is the" +e, Toast.LENGTH_SHORT).show();
                                            throw new RuntimeException(e);

                                       }
//


                                        //! STORE SIGNATURE IN SHARED PREFERENCES
//                                        prefs = getSharedPreferences("user", MODE_PRIVATE);
//                                        edit = prefs.edit();
//                                        String signature = null;
//                                        try {
//                                            signature = response.getString("signature");
//                                            edit.putString("signature", signature);
//                                            edit.apply();
//                                            // MOVE TO HOME ACTIVITY
//                                            Intent intent = new Intent(Login.this, Home.class);
//                                            startActivity(intent);
//
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                            Toast.makeText(Login.this, "this is the" +e, Toast.LENGTH_SHORT).show();
//                                            throw new RuntimeException(e);
//
//                                        }




                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Register.this, "this is the" +error, Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue.add(jsonObjectRequest);


                    }
                }
        );
    }
}