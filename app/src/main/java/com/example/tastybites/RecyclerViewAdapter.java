package com.example.tastybites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<MyRepo> retrievedResponses;
    private Context context;
    private ArrayList<String> dataList;
    private AdapterView.OnItemClickListener  listener;
    public RecyclerViewAdapter(List<MyRepo> retrievedResponses, Context context) {
        this.retrievedResponses = retrievedResponses;
        this.context = context;

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id_field;
        private TextView name_field;
        private TextView address_field;
        private TextView rating_field;
        private ImageView image_field;
        private AdapterView.OnItemClickListener listener;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            id_field = itemView.findViewById(R.id.id_field);
            name_field = itemView.findViewById(R.id.name_field);
            address_field = itemView.findViewById(R.id.address_field);
            rating_field = itemView.findViewById(R.id.rating_field);
            image_field = itemView.findViewById(R.id.image_field);
        }

        public void setId_field(String id) {
            id_field.setText(id);
        }

        public void setName_field(String name){
            name_field.setText(name);
        }

        public void setAddress_field(String address){
            address_field.setText(address);
        }
        public void setRating_field(String rating){
            rating_field.setText(rating);
        }
        public void setImage_field(int image){
            image_field.setImageResource(image);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_recycler_view_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.setId_field(String.valueOf(retrievedResponses.get(position).getId()));
        holder.setAddress_field(String.valueOf(retrievedResponses.get(position).getAddress()));
        holder.setRating_field(String.valueOf(retrievedResponses.get(position).getRating()));
        holder.setName_field(String.valueOf(retrievedResponses.get(position).getName()));
        //GET IMAGE FROM URL
        Glide.with(context).load(retrievedResponses.get(position).getImage()).into(holder.image_field);
        holder.image_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                Intent intent = new Intent(context,RestaurantDetail.class);
                intent.putExtra("name",retrievedResponses.get(position).getName());
                intent.putExtra("address",retrievedResponses.get(position).getAddress());
                intent.putExtra("rating",retrievedResponses.get(position).getRating());
                intent.putExtra("image",retrievedResponses.get(position).getImage());


                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return retrievedResponses.size();
    }


}