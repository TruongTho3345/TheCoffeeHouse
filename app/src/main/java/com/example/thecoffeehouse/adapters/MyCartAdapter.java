package com.example.thecoffeehouse.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.entities.CartItem;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    List<CartItem> list;

    public MyCartAdapter(List<CartItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getCoffeeImg());
        holder.name.setText(list.get(position).getCoffeeName());
        holder.info.setText(list.get(position).getShot() + " | " + list.get(position).getTemp() + " | " +
                list.get(position).getSize() + " | "+ list.get(position).getIceLevel() + " | ");
        holder.quantity.setText(list.get(position).getQuantity());
        holder.price.setText("$" + String.format("%.2f", list.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, info, quantity, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.myCart_coffeeImg);
            name = itemView.findViewById(R.id.myCart_coffeeName);
            info = itemView.findViewById(R.id.myCart_coffeeInfo);
            quantity = itemView.findViewById(R.id.myCart_coffeeQuantity);
            price = itemView.findViewById(R.id.myCart_coffeePrice);
        }
    }
}
