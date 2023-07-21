package com.example.thecoffeehouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CupsAdapter extends RecyclerView.Adapter<CupsAdapter.MyViewHolder>{
    private ArrayList<Cups> cupsList;

    public CupsAdapter(ArrayList<Cups> cupsList) {
        this.cupsList = cupsList;
    }

    // This method creates a new ViewHolder object for each item in the RecyclerView
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item and return a new ViewHolder object
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cups_list, parent, false);
        return new MyViewHolder(itemView);
    }

    // This method binds the data to the ViewHolder object
    // for each item in the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cups currentCup = cupsList.get(position);
        holder.cups.setImageResource(currentCup.getCupsResourceId());
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
