package com.example.thecoffeehouse.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.entities.Order;

import java.util.List;

public class HistoryRewardAdapter extends RecyclerView.Adapter<HistoryRewardAdapter.OrderViewHolder>{

    private final Context context;
    private List<Order> orderList;


    public HistoryRewardAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }
    public void updateData(List<Order> orders) {
        this.orderList = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gift_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderDateTextView;
        private final TextView orderNameTextView;

        private final TextView orderPointTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDateTextView = itemView.findViewById(R.id.giftItem_date);
            orderNameTextView = itemView.findViewById(R.id.giftItem_name);
            orderPointTextView = itemView.findViewById(R.id.giftItem_point);
        }

        public void bind(Order order) {
            // Display order details
            orderDateTextView.setText(order.getDate());
            orderNameTextView.setText(order.getCoffeeType());
            orderPointTextView.setText("+ " + order.getQuantity()*12 + "Pts");
        }
    }

}
