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

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.OrderViewHolder> {

    private final Context context;
    private List<Order> orderList;

    public MyOrderAdapter(Context context, List<Order> orderList) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.myorder_item, parent, false);
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
        private final TextView orderPriceTextView;
        private final TextView orderAddressTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDateTextView = itemView.findViewById(R.id.myOrderItem_date);
            orderNameTextView = itemView.findViewById(R.id.myOrderItem_coffeeType);
            orderPriceTextView = itemView.findViewById(R.id.myOrderItem_price);
            orderAddressTextView = itemView.findViewById(R.id.myOrderItem_address);
        }

        public void bind(Order order) {
            // Display order details
            orderDateTextView.setText(order.getDate());
            orderNameTextView.setText(order.getCoffeeType());
            orderPriceTextView.setText("$" + String.format("%.2f", order.getPrice()));
            orderAddressTextView.setText(order.getAddress());
        }
    }
}
