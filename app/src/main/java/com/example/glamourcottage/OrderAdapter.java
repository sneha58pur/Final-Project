
package com.example.glamourcottage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        super(context, R.layout.order_list_item, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.productName = convertView.findViewById(R.id.orderProductNameTextView);
            viewHolder.productPrice = convertView.findViewById(R.id.orderPriceTextView);
            viewHolder.orderQuantity = convertView.findViewById(R.id.orderQuantityTextView);
            viewHolder.productSize = convertView.findViewById(R.id.orderSizeTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Order order = orderList.get(position);

        viewHolder.productName.setText(order.getProductName());
        viewHolder.productPrice.setText(String.format("Price: $%.2f", order.getProductPrice()));
        viewHolder.orderQuantity.setText("Quantity: " + order.getQuantity());
        viewHolder.productSize.setText("Size: " + order.getProductSize());

        return convertView;
    }

    private static class ViewHolder {
        TextView productName, productPrice, orderQuantity, productSize;
    }
}
