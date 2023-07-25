package com.example.thecoffeehouse.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.entities.CartItem;
import com.example.thecoffeehouse.entities.Order;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.OrderViewHolder> {

    private final Context context;
    private List<Order> orderList;

    private OnOrderClickListener onOrderClickListener;

    public MyOrderAdapter(Context context, List<Order> orderList, OnOrderClickListener listener) {
        this.context = context;
        this.orderList = orderList;
        this.onOrderClickListener = listener;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public interface OnOrderClickListener {
        void onOrderClicked(int position);
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

    public void deleteItem(int position) {
        Order order = orderList.get(position);
        orderList.remove(position);
        notifyItemRemoved(position);

        // Notify the listener that an item has been deleted
        if (onOrderClickListener != null) {
            onOrderClickListener.onOrderClicked(position);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                order.setStatus(Order.STATUS_HISTORY);
                AppDatabase appDatabase = AppDatabase.getInstance(context);
                appDatabase.orderDao().updateOrder(order);
            }
        }).start();
    }

    public Order getItemAtPosition(int position) {
        if (position >= 0 && position < orderList.size()) {
            return orderList.get(position);
        }
        return null;
    }
}
