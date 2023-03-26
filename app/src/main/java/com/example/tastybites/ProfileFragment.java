package com.example.tastybites;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

    TextView fullnameText;
    TextView phoneText;
    TextView emailText;

    Button orderstatus;

    String endpoint = MainActivity.Constants.API_ENDPOINT;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prefs = requireContext().getSharedPreferences("user", MODE_PRIVATE);
        String signature = prefs.getString( "signature", null);

        String restoredText = prefs.getString( "signature", null);
        String firstName = prefs.getString("firstName", null);

        try {

            if (firstName==null) {
                // No signature found, replace the linear layout with another layout
                // Inflate the new layout
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentlayout, new NoLoginUserFragment());
                fragmentTransaction.commit();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView textView = view.findViewById(R.id.user_profile_name);

        phoneText = view.findViewById(R.id.phonenumber);
        emailText = view.findViewById(R.id.email);
        fullnameText = view.findViewById(R.id.fullname);
        orderstatus = view.findViewById(R.id.orderstatus);
        orderstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderStatusActivity.class);
                startActivity(intent);

            }
        });

        edit = prefs.edit();
        textView.setText("Welcome " + firstName);

        VolleyMyGet();

        return view;
    }
    public void VolleyMyGet(){
        String url =  endpoint + "/customer/profile";

        try {
            prefs =requireContext().getSharedPreferences("user", MODE_PRIVATE);
            String signature = prefs.getString("signature", null);
            // check if signature is not null

            if (signature != null) {


                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                List<Repo> jsonResponses = new ArrayList<>();

                JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(Request.Method.GET, url, null,
                        // headers
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

//                                Toast.makeText(getActivity(), ""+ response, Toast.LENGTH_SHORT).show();

                                try {
                                    String firstName = response.getString("firstName").substring(0, 1).toUpperCase() + response.getString("firstName").substring(1);
                                    String lastName = response.getString("lastName").substring(0, 1).toUpperCase() + response.getString("lastName").substring(1);
                                    String phone = response.getString("phone");
                                    String email = response.getString("email");
                                    String fullname = firstName +" " + lastName;
                                    emailText.setText(email);
                                    phoneText.setText(phone);
                                    fullnameText.setText(fullname);


                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
//                                String name = foodObject.getString("name");
//                                int price = foodObject.getInt("price");
//                                JSONArray images = foodObject.getJSONArray("images");
//                                int quantity = 1;



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

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

                requestQueue.add(jsonObjectRequest);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}



