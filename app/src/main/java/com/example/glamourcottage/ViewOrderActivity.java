package com.example.glamourcottage;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewOrderActivity extends AppCompatActivity {

    private TextView productNameTextView, productPriceTextView, quantityTextView, sizeTextView, totalTextView;
    private ImageView productImageView;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        // Initialize views
        productNameTextView = findViewById(R.id.orderSummaryProductName);
        productPriceTextView = findViewById(R.id.orderSummaryPrice);
        quantityTextView = findViewById(R.id.orderSummaryQuantity);
        sizeTextView = findViewById(R.id.orderSummarySize);
        totalTextView = findViewById(R.id.orderSummaryTotal);
        productImageView = findViewById(R.id.orderSummaryImage);

        // Get order details from Intent
        String productName = getIntent().getStringExtra("productName");
        double productPrice = getIntent().getDoubleExtra("productPrice", 0.0);
        int quantity = getIntent().getIntExtra("quantity", 1);
        String productSize = getIntent().getStringExtra("productSize");
        byte[] productImage = getIntent().getByteArrayExtra("productImage");

        // Set the order details to the views
        productNameTextView.setText("Product Name: " + productName);
        productPriceTextView.setText("Price: $" + String.format("%.2f", productPrice));
        quantityTextView.setText("Quantity: " + quantity);
        sizeTextView.setText("Size: " + productSize);
        totalTextView.setText("Total: $" + String.format("%.2f", productPrice * quantity));

        // If product image is available, set it to ImageView
        if (productImage != null) {
            Bitmap productBitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
            productImageView.setImageBitmap(productBitmap);
        }
    }
}
