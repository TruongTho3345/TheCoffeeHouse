package com.example.thecoffeehouse.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecoffeehouse.models.Cups;
import com.example.thecoffeehouse.R;

import java.util.ArrayList;

public class CupsAdapter extends RecyclerView.Adapter<CupsAdapter.MyViewHolder>{
    private ArrayList<Cups> cupsList;
    private int orderSize;

    public CupsAdapter(ArrayList<Cups> cupsList) {
        this.cupsList = cupsList;
    }

    public void setOrderSize(int orderSize) {
        this.orderSize = orderSize;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    // This method creates a new ViewHolder object for each item in the RecyclerView
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item and return a new ViewHolder object
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cups_item, parent, false);
        return new MyViewHolder(itemView);
    }

    // This method binds the data to the ViewHolder object
    // for each item in the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cups currentCup = cupsList.get(position);


        if (position < orderSize) {
            holder.cups.setImageResource(R.drawable.coffee_cup_1);
        } else {
            // Clear background tint for non-order items
            holder.cups.setImageResource(R.drawable.coffee_cup_blur);
        }
    }

    @Override
    public int getItemCount() {
        return cupsList.size();
    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView cups;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cups = itemView.findViewById(R.id.cupImg);
        }
    }
}
