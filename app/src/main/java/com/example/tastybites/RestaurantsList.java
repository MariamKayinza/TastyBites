package com.example.tastybites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantsList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView response_recycler_view;
    private MaterialButton retrieve_data_button;
    String endpoint = MainActivity.Constants.API_ENDPOINT;

    public RestaurantsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantsList.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantsList newInstance(String param1, String param2) {
        RestaurantsList fragment = new RestaurantsList();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurants_list, container, false);
        response_recycler_view = view.findViewById(R.id.response_recycler_view);
//        retrieve_data_button = findViewById(R.id.retrieve_data_button);

        response_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
//        volleyGet();
        VolleyMyGet();

        return view;
    }
    public void VolleyMyGet(){
        String url =  endpoint + "/shopping/all-restaurants/12003";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        List<MyRepo> jsonResponses = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

//                Toast.makeText(getActivity(), "response"+ response, Toast.LENGTH_SHORT).show()
//                jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < response.length(); i++) {

//                            Toast.makeText(getActivity(), "i"+i, Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = response.getJSONObject(i);

                                String  address = jsonObject.getString("address");
                                String id = jsonObject.getString("_id");
                                int rating  = jsonObject.getInt("rating");
                                String name = jsonObject.getString("name");
//                                String image = jsonObject.getString("coverImages");
                                 JSONArray array_image = jsonObject.getJSONArray("coverImages");
                                 JSONArray array_food = jsonObject.getJSONArray("foods");
                                 // get the first image
                                String image = array_image.getString(0);
//                        ArrayList food = jsonObject.getJSONArray('food')
//                        JSONArray foodType = jsonObject.getJSONArray("foodType");
                                jsonResponses.add(new MyRepo(id, name, rating, address,image,array_food));

                                response_recycler_view.setAdapter(new RecyclerViewAdapter(jsonResponses, getActivity()));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
//
                        }


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