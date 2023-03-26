package com.example.tastybites;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;
    private Button plusBtnCart, minusBtnCart, removeBtnCart;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        RecyclerView recycler_view_cart = view.findViewById(R.id.recycler_view_cart);
//        prefs =getActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE);
//        String productsJson = prefs.getString("cartProducts", null);


//        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view_cart.setLayoutManager(linearLayoutManager);
//        response_recycler_view.setLayoutManager(gridLayoutManager);
//        Gson gson = new Gson();

        try {
            sharedPreferences = getActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE);
            String productsJson = sharedPreferences.getString("cartProducts", null);
            if (productsJson == null) {
                // No signature found, replace the linear layout with another layout
                // Inflate the new layout
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentlayout, new CartEmptyFragment());
                fragmentTransaction.commit();
            }
            else{
                Gson gson = new Gson();
//                products = gson.fromJson(productsJson, new TypeToken<List<Repo>>() {
//                }.getType());
                List<Repo> products = new Gson().fromJson(productsJson, new TypeToken<List<Repo>>() {
                }.getType());
                RecyclerView recyclerView = view.findViewById(R.id.recycler_view_cart);
                Button checkout = view.findViewById(R.id.CHECKOUT);
                Button cancel = view.findViewById(R.id.CANCEL);
                plusBtnCart = view.findViewById(R.id.plusBtnCart);
                minusBtnCart = view.findViewById(R.id.minBtnCart);
                TextView totalPrice = view.findViewById(R.id.totalPrice);

                // total price for all products in cart
                totalPrice.setText(String.valueOf(getTotalPrice(products)));
                // store total price in shared preferences

                // set adapter



                // on click listener clear  shared preferences
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        // back to  home activity
                        Intent intent = new Intent(getActivity(), Home.class);
                        startActivity(intent);
                        // FINISH
                        getActivity().finish();

                    }
                });
                prefs = requireContext().getSharedPreferences("user", MODE_PRIVATE);
                String restoredText = prefs.getString("signature", null);
                String firstName = prefs.getString("firstName", null);


                checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        if (firstName == null) {
                            // got login activity


                            intent = new Intent(getActivity(), Login.class);


                            intent.putExtra("from", "cart");
                            startActivity(intent);


                        } else {

                            intent = new Intent(getActivity(), ShippingAdressActivity.class);
                            // send total price to shipping activity
                            intent.putExtra("total_price", String.valueOf(getTotalPrice(products)));

                            startActivity(intent);

                        }

//                if (firstName == null) {
//                    // got login activity
//                    intent = new Intent(getActivity(), ShippingAdressActivity.class);
//                    startActivity(intent);
//                    return;
//                }
//                if (restoredText != null) {
//                    // got checkout activity
//                    intent = new Intent(getActivity(), ShippingAdressActivity.class);
//                    startActivity(intent);
//                    return;
//                }
                        // got checkout activity
//                intent = new Intent(getActivity(), CheckoutActivity.class);


//                startActivity(intent);


//                try {
//                    SharedPreferences prefs = getActivity().getSharedPreferences("signature", Context.MODE_PRIVATE);
//                    String restoredText = prefs.getString( "signature", null);
////
//
//                    if (restoredText != null) {
//                        Toast toast = Toast.makeText(getActivity(), restoredText, Toast.LENGTH_SHORT);
//                        Intent intent = new Intent(getActivity(), FoodDetail.class);
//                        startActivity(intent);
//                        return;
//
//                    }else {
//                        Intent intent = new Intent(getActivity(), Login.class);
//                        startActivity(intent);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                    }
                });
//        for (int j = 0; j < products.size(); j++) {
//
//            String id = products.get(j).getId();
//            String name = products.get(j).getName();
//            String priceString = products.get(j).getPrice();
//            String image = products.get(j).getImage();
//            int quantity = products.get(j).getQuantity();
//
//
//            products.add(new Repo(id, name, priceString, image, quantity));
//            Toast.makeText(getActivity(), ""+products, Toast.LENGTH_SHORT).show();
//
//
//
//        }
                recyclerView.setAdapter(new RecyclerViewCartAdapter(products, getActivity()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        Type type = new TypeToken<List<Repo>>() {}.getType();
//
        return view;
    }

    private char[] getTotalPrice(List<Repo> products) {
        int totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            int price = Integer.parseInt(products.get(i).getPrice());
            int quantity = products.get(i).getQuantity();
            totalPrice += price * quantity;
        }
        return String.valueOf(totalPrice).toCharArray();
    }
}