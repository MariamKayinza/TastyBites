package com.example.tastybites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewCartAdapter extends RecyclerView.Adapter<RecyclerViewCartAdapter.ViewHolder> implements SelectListener {
    private List<Repo> retrievedResponses;
    private Context context;
    private SelectListener listenerinterface;


    public RecyclerViewCartAdapter(List<Repo> products, Context context) {
        this.retrievedResponses = products;
        this.listenerinterface = listenerinterface;
        this.context = context;



    }


    @NonNull
    @Override
    public RecyclerViewCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycler_view, parent, false);

        return new RecyclerViewCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setName_field(retrievedResponses.get(position).getName());
        holder.setPrice_field(retrievedResponses.get(position).getPrice());
        holder.setQuantity_field(retrievedResponses.get(position).getQuantity());
        holder.setTotalprice_field(retrievedResponses.get(position).getTotalprice());
        Glide.with(holder.itemView.getContext()).load(retrievedResponses.get(position).getImage()).into(holder.image_field);
        // set onclick listener using the listener in viewholder
//        holder.image_field.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(v.getContext(), StoryActivity.class);
//
//                if (v.getContext() != null) {
//                    Intent intent = new Intent(v.getContext(), FoodDetail.class);
//                    intent.putExtra("foodid",retrievedResponses.get(position).getId());
//                    intent.putExtra("foodname",retrievedResponses.get(position).getName());
//                    intent.putExtra("foodimage",retrievedResponses.get(position).getImage());
//                    intent.putExtra("foodprice",retrievedResponses.get(position).getPrice());
//                    intent.putExtra("foodquantity",retrievedResponses.get(position).getQuantity());
////
//
//                    v.getContext().startActivity(intent);
//                }
////
// Intent intent=new Intent(context,Main2Activity.class);
//                 intent.putExtra("imagename",temp.getImgname());
//                 intent.putExtra("header",temp.getHeader());
//                 intent.putExtra("desc",temp.getDesc());
//                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                 context.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return retrievedResponses.size();
    }

    @Override
    public void onItemClicked(int position) {

    }

//    @Override
//    public void onItemClicked(int position) {
//        Intent intent = new Intent(context,RestaurantDetail.class);
//        context.startActivity(intent);
//    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id_field;
        private TextView name_field;
        private TextView price_field;
        private ImageView image_field;
        private TextView totalprice_field;

        private TextView numItems;
//        private ImageView plusBtnCart;
//        private ImageView minusBtnCart;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            id_field = itemView.findViewById(R.id.id_field);
            name_field = itemView.findViewById(R.id.txtTitleCart);
            price_field = itemView.findViewById(R.id.feeEachItem);
            numItems = itemView.findViewById(R.id.numItems);
            totalprice_field = itemView.findViewById(R.id.totalEachItem);
            image_field = itemView.findViewById(R.id.picCart);
//            plusBtnCart = itemView.findViewById(R.id.plusBtnCart);
//            minusBtnCart = itemView.findViewById(R.id.minBtnCart);
//            card_view = itemView.findViewById(R.id.card_view);

            image_field.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (v.getContext() != null) {
                    Intent intent = new Intent(v.getContext(), FoodDetail.class);
                    intent.putExtra("foodid",retrievedResponses.get(getAdapterPosition()).getId());
                    intent.putExtra("foodname",retrievedResponses.get(getAdapterPosition()).getName());
                    intent.putExtra("foodimage",retrievedResponses.get(getAdapterPosition()).getImage());
                    intent.putExtra("foodprice",retrievedResponses.get(getAdapterPosition()).getPrice());
                    intent.putExtra("foodquantity",retrievedResponses.get(getAdapterPosition()).getQuantity());
                    intent.putExtra("foodtotalprice",retrievedResponses.get(getAdapterPosition()).getTotalprice());
                    v.getContext().startActivity(intent);
                    // onclick of the persons  back button go back to home fragment


//                    Navigation.findNavController(fragment.getView()).popBackStack(R.id.homeFragment, false);


                    // kill the fragment
//                    ((Activity)context).finish();
                }
            }});



        }

        public void setName_field(String name) {
            name_field.setText(name);
        }

        public void setPrice_field(String price) {
            price_field.setText(price);
        }

        public void setImage_field(String image) {
            Glide.with(itemView.getContext()).load(image).into(image_field);
        }

        public void setQuantity_field(Integer quantity) {
            numItems.setText(quantity.toString());
        }

        public void setTotalprice_field(String totalprice) {
            totalprice_field.setText(totalprice);
        }


    }


}
