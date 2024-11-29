package com.example.glamourcottage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class ProductDisplayActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    ListView listView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);


        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        displayProducts();

    }

    private void displayProducts() {
        Cursor cursor = dbHelper.getAllProducts();
        List<Product> productList = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCT_NAME_COL));
            @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.PRODUCT_PRICE_COL));
            @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.PRODUCT_IMAGE_URI_COL));

            productList.add(new Product(name, price, image));
        }
        cursor.close();


        ProductAdapter adapter = new ProductAdapter(this, productList);

//        ProductAdapter adapter = new ProductAdapter(this, productList, product -> {
//             //Handle the image click event here
//            Intent intent = new Intent(ProductDisplayActivity.this, OrderActivity.class);
//            intent.putExtra("productName", product.getName());
//            intent.putExtra("productPrice", product.getPrice());
//            intent.putExtra("productImage", product.getImage());
//            startActivity(intent);
//        });










        listView.setAdapter(adapter) ;
    }








}