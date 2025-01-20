package com.example.glamourcottage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private TextView productNameTextView, productPriceTextView, quantityTextView, priceTextView;


    private RadioGroup sizeRadioGroup;
    private Button incrementButton, decrementButton, placeOrderButton;
    private int quantity = 1;
    private double productPrice;

    private String productName, productSize;
    private ImageView productImageView;
    private byte[] productImage;

    @SuppressLint({"DefaultLocale", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Initialize views

        productNameTextView = findViewById(R.id.productName);
        productPriceTextView = findViewById(R.id.productPrice);
        quantityTextView = findViewById(R.id.quantityTextView);
        priceTextView = findViewById(R.id.priceTextView);
        productImageView = findViewById(R.id.productImage);



        sizeRadioGroup = findViewById(R.id.radioGroup);
        incrementButton = findViewById(R.id.increment);
        decrementButton = findViewById(R.id.decrement);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Get product details from Intent

        productName = getIntent().getStringExtra("productName");
        productPrice = getIntent().getDoubleExtra("productPrice", 0.0);

        productImage = getIntent().getByteArrayExtra("productImage");



        // Set the product details to views
        productNameTextView.setText(productName);
        productPriceTextView.setText(String.format("$%.2f", productPrice));

        priceTextView.setText(String.format("Total: $%.2f", productPrice*quantity));

        // If you want to display an image from the byte array, you can use the following:
        if (productImage != null) {
            // Convert byte[] to Bitmap and set to ImageView
            Bitmap productBitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
            productImageView.setImageBitmap(productBitmap);
        }

        // Increment button click listener
        incrementButton.setOnClickListener(v -> {
            quantity++;
            quantityTextView.setText(String.valueOf(quantity));
            priceTextView.setText(String.format("Total: $%.2f", productPrice * quantity));
        });

        // Decrement button click listener
        decrementButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityTextView.setText(String.valueOf(quantity));
                priceTextView.setText(String.format("Total: $%.2f", productPrice * quantity));
            }
        });

        // Place Order button click listener



        placeOrderButton.setOnClickListener(v -> {
            // Get selected size
            int selectedSizeId = sizeRadioGroup.getCheckedRadioButtonId();
            RadioButton selectedSizeButton = findViewById(selectedSizeId);

            if (selectedSizeButton != null) {
                productSize = selectedSizeButton.getText().toString();

                // Save order to the database
                dbHelper.insertOrders(productName,productPrice*quantity);
                Toast.makeText(OrderActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();

                // Pass order details to OrderSummaryActivity
                Intent intent = new Intent(OrderActivity.this, OrderSummeryActivity.class);
                intent.putExtra("productName", productName);
                intent.putExtra("productPrice", productPrice);
                intent.putExtra("quantity", quantity);
                intent.putExtra("productSize", productSize);
                intent.putExtra("productImage", productImage); // Pass image as byte array
                startActivity(intent);
            } else {
               Toast.makeText(OrderActivity.this, "Please select a size", Toast.LENGTH_SHORT).show();
            }

        });








    }


}












