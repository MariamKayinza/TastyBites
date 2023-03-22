package com.example.tastybites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartEmptyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartEmptyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

    TextView back_to_shopping;

    public CartEmptyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartEmptyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartEmptyFragment newInstance(String param1, String param2) {
        CartEmptyFragment fragment = new CartEmptyFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_empty, container, false);
        // Inflate the layout for this fragment

        back_to_shopping = view.findViewById(R.id.back_to_shopping);

        back_to_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getActivity(), Home.class);
                startActivity(intent);


                // get the NavController
//                NavController navController = Navigation.findNavController(v);
//
//                // navigate to the destination fragment
//                navController.navigate(R.id.destination_fragment);


            }
        }

        );

        return view;
    }
}