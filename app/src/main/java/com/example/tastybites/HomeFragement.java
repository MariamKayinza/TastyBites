package com.example.tastybites;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragement extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView home_welcome_text;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;
    private RecyclerView response_recycler_view;

    String endpoint = MainActivity.Constants.API_ENDPOINT;

    public HomeFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragement newInstance(String param1, String param2) {
        HomeFragement fragment = new HomeFragement();
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


//        if (first_name != null) {
//            Toast.makeText(this, "prefs is not null", Toast.LENGTH_SHORT).show();
////                String signature = prefs.getString("signature", null);
//            Toast toast = Toast.makeText(this, signature, Toast.LENGTH_SHORT);
//            toast.show();
//            if (signature != null) {
//
//            }
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home_fragement, null);
        View view = inflater.inflate(R.layout.fragment_home_fragement, container, false);

        TextView textView = view.findViewById(R.id.home_welcome);
        response_recycler_view = view.findViewById(R.id.myresponse_recycler_view);
        int numberOfColumns = 2;
//        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        response_recycler_view.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));




        prefs = requireContext().getSharedPreferences("user", MODE_PRIVATE);
        String restoredText = prefs.getString( "signature", null);
        String firstName = prefs.getString("firstName", null);

        edit = prefs.edit();


        if (restoredText != null) {
            String signature = prefs.getString("signature", null);
            //? IF USER IS LOGGED IN THEN SHOW LOGOUT BUTTON

            // captialized first letter of first name
//                    String firstName = prefs.getString("firstName", null);
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            Toast.makeText(getActivity(), "Text!" + firstName, Toast.LENGTH_SHORT).show();
            textView.setText("Welcome " + firstName);

        }

        VolleyMyGet();

        return view;
    }
    public void VolleyMyGet(){
        String url =  endpoint + "/shopping/all-restaurants/12003";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        List<Repo> jsonResponses = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            // get food arrary from response
                            // first loop through the response and get the food array
                            for (int i = 0; i < response.length(); i++) {
//                                Toast.makeText(getActivity(), "i"+i, Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONArray food = jsonObject.getJSONArray("foods");

                                // loop through the food array
                                for (int j = 0; j < food.length(); j++) {
                                    JSONObject foodObject = food.getJSONObject(j);
                                    String id = foodObject.getString("_id");
                                    String name = foodObject.getString("name");
                                    int price = foodObject.getInt("price");
                                    JSONArray images = foodObject.getJSONArray("images");
                                    // convert price to string
                                    String priceString = Integer.toString(price);
                                    priceString = "UGX " + priceString;

                                    String image = images.getString(0);
                                    jsonResponses.add(new Repo(id, name, priceString, image));
//                                    Toast.makeText(getActivity(), "name"+price, Toast.LENGTH_SHORT).show();
                                    response_recycler_view.setAdapter(new RecyclerViewHomeAdapter(jsonResponses, getActivity()));

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        Toast.makeText(getActivity(), "jsonArray"+jsonArray, Toast.LENGTH_SHORT).show();
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            Toast.makeText(getActivity(), "i"+i, Toast.LENGTH_SHORT).show();
//
//
////                                jsonObject = response.getJSONObject(i);
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);


//                                String id = jsonObject.getString("_id");
//                                int rating  = jsonObject.getInt("price");
//                                String name = jsonObject.getString("name");
//                                JSONArray array_image = jsonObject.getJSONArray("images");
//                                // get the first image
//                                String image = array_image.getString(0);
//                                Toast.makeText(getActivity(), "name"+name, Toast.LENGTH_SHORT).show();
//                        ArrayList food = jsonObject.getJSONArray('food')
//                        JSONArray foodType = jsonObject.getJSONArray("foodType");
//                                jsonResponses.add(new MyRepo(id, name, rating, address,image,array_food));

//
////
//                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), "error"+error, Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }


}