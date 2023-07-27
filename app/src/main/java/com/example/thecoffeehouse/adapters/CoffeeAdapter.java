package com.example.thecoffeehouse.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecoffeehouse.models.Coffee;
import com.example.thecoffeehouse.R;

import java.util.ArrayList;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.RecyclerViewHolder>{
    private ArrayList<Coffee> coffeeList;
    private OnClickListener onClickListener;

    public CoffeeAdapter(ArrayList<Coffee> coffeeList) {
        this.coffeeList = coffeeList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Coffee coffeePosition);
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coffee_item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Coffee coffeePosition = coffeeList.get(position);
        holder.coffeeText.setText(coffeePosition.getCoffeeName());
        holder.coffeeImg.setImageResource(coffeePosition.getCoffeeImgId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, coffeePosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return coffeeList.size();
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView coffeeImg;
        private TextView coffeeText;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImg = itemView.findViewById(R.id.coffeeImg);
            coffeeText = itemView.findViewById(R.id.coffeeText);
        }
    }
}
