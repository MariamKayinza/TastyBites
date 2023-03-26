package com.example.tastybites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrderHistoryAdapter extends ArrayAdapter<Order> {

    public OrderHistoryAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup
            parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_item, parent, false);
        }

        Order order = getItem(position);
        TextView foodNameTextView = convertView.findViewById(R.id.food_name);
        TextView restaurantNameTextView = convertView.findViewById(R.id.restaurant_name);
        TextView totalPriceTextView = convertView.findViewById(R.id.total_price);
        TextView statusTextView = convertView.findViewById(R.id.status);

        // set the food name, restaurant name, total price, and status for the current order
        foodNameTextView.setText(order.getFoodName());
        restaurantNameTextView.setText(order.getRestaurantName());
        totalPriceTextView.setText(order.getTotalPrice());
        // change text color based on status
        if (order.getStatus().equals("pending")) {
            statusTextView.setTextColor(getContext().getResources().getColor(R.color.colorPending));
        } else if (order.getStatus().equals("processing")) {
            statusTextView.setTextColor(getContext().getResources().getColor(R.color.colorProcessing));
        } else if (order.getStatus().equals("completed")) {
            statusTextView.setTextColor(getContext().getResources().getColor(R.color.colorCompleted));
        }
        statusTextView.setText(order.getStatus());

        return convertView;
    }
}