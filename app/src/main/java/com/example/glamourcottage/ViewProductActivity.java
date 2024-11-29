package com.example.glamourcottage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity extends AppCompatActivity {
    private ListView listViewProducts;
    private DatabaseHelper database;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        listViewProducts = findViewById(R.id.list_view_products);
        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonDelete = findViewById(R.id.button_delete);

        database = new DatabaseHelper(this);

        // Display products in the ListView
        displayProducts();

        buttonUpdate.setOnClickListener(v -> handleUpdate());
        buttonDelete.setOnClickListener(v -> handleDelete());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the displayed products when returning to this activity
        displayProducts();
    }

    private void displayProducts() {
        Cursor cursor = database.getAllProducts();
        List<Product> productList = new ArrayList<>();

        // Extract data from the Cursor and populate the product list
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_NAME_COL));
            @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.PRODUCT_PRICE_COL));
            @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.PRODUCT_IMAGE_URI_COL));
            productList.add(new Product(name, price, image));
        }
        cursor.close();

        // Set up the ProductAdapter
        adapter = new ProductAdapter(this, productList);
        listViewProducts.setAdapter(adapter);
    }

    private void handleUpdate() {
        // Navigate to the UpdateProductActivity
        Intent intent = new Intent(ViewProductActivity.this, UpdateProductActivity.class);
        startActivity(intent);
    }

    private void handleDelete() {
        // Navigate to the DeleteProductActivity
        Intent intent = new Intent(ViewProductActivity.this, DeleteProductActivity.class);
        startActivity(intent);

        // Optional: Show a toast message
        Toast.makeText(this, "Navigate to delete screen", Toast.LENGTH_SHORT).show();
    }
}
