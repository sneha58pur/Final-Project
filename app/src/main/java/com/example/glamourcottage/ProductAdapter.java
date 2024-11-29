package com.example.glamourcottage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private final Context context;
    private final List<Product> productList;

    // Constructor to initialize the context and product list
    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // Inflate the custom layout
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
            holder = new ViewHolder();
            holder.productName = convertView.findViewById(R.id.productName);
            holder.productPrice = convertView.findViewById(R.id.productPrice);
            holder.productImage = convertView.findViewById(R.id.productImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the current product
        Product product = productList.get(position);

        // Set data to the views
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("$%.2f", product.getPrice()));

        if (product.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
            holder.productImage.setImageBitmap(bitmap);
        } else {
            holder.productImage.setImageResource(R.drawable.ic_launcher_background); // Default image
        }





//        holder.productImage.setOnClickListener(v -> {
//            // Navigate to OrderActivity and pass product details
//            Intent intent = new Intent(context, OrderActivity.class);
//            intent.putExtra("productName", product.getName());
//            intent.putExtra("productPrice", product.getPrice());
//            intent.putExtra("productImage", product.getImage());
//            context.startActivity(intent);
//        });


        // Set OnClickListener on the CardView
//        holder.cardView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, OrderActivity.class);
//            intent.putExtra("productName", product.getName());
//            intent.putExtra("productPrice", product.getPrice());
//            intent.putExtra("productImage", product.getImage());
//            context.startActivity(intent);
//        });

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderActivity.class);
            intent.putExtra("productName", product.getName());
            intent.putExtra("productPrice", product.getPrice());
            intent.putExtra("productImage", product.getImage());
            context.startActivity(intent);
        });


        return convertView;
    }

    // ViewHolder to optimize view reuse
    private static class ViewHolder {

        TextView productName;
        TextView productPrice;
        ImageView productImage;
        androidx.cardview.widget.CardView cardView;
    }
}
