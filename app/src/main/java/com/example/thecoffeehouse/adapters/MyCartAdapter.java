package com.example.thecoffeehouse.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.entities.CartItem;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private List<CartItem> cartItemList;

    private Context context;
    private CartItemDeletedListener cartItemDeletedListener;

    public interface CartItemDeletedListener {
        void onCartItemDeleted(int position);
    }



    public MyCartAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(cartItemList.get(position).getCoffeeImg());
        holder.name.setText(cartItemList.get(position).getCoffeeName());
        holder.info.setText(cartItemList.get(position).getShot() + " | " + cartItemList.get(position).getTemp() + " | " +
                cartItemList.get(position).getSize() + " | "+ cartItemList.get(position).getIceLevel() + " | ");
        holder.quantity.setText("x " + String.valueOf(cartItemList.get(position).getQuantity()));
        holder.price.setText("$" + String.format("%.2f", cartItemList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItemList != null ? cartItemList.size() : 0;
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

    public List<CartItem> getCartItems() {
        return cartItemList;
    }

    public CartItem getItemAtPosition(int position) {
        if (position >= 0 && position < cartItemList.size()) {
            return cartItemList.get(position);
        }
        return null;
    }

    // New method to update the data in the adapter
    public void updateData(List<CartItem> newData) {
        cartItemList.clear();
        cartItemList.addAll(newData);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        CartItem itemToDelete = cartItemList.get(position);
        cartItemList.remove(position);
        notifyItemRemoved(position);

        // Notify the listener that an item has been deleted
        if (cartItemDeletedListener != null) {
            cartItemDeletedListener.onCartItemDeleted(position);
        }

        // Delete the item from the Room database using DAO
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase appDatabase = AppDatabase.getInstance(context);
                appDatabase.cartItemDao().deleteCartItem(itemToDelete);
            }
        }).start();
    }
}
