package com.example.glamourcottage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        LayoutInflater inflater = LayoutInflater.from(this);

        while (cursor.moveToNext()) {
            @SuppressLint("InflateParams") View productView = inflater.inflate(R.layout.product_list_item, null);

            TextView nameTextView = productView.findViewById(R.id.productName);
            TextView priceTextView = ((View) productView).findViewById(R.id.productPrice);

            ImageView productImageView = productView.findViewById(R.id.productImage);

            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.product_name_col));
            @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.product_price_col));
      ;
            @SuppressLint("Range") byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.product_image_URI_col));

            nameTextView.setText(name);
            priceTextView.setText(String.valueOf(price));


            if (imageByteArray != null && imageByteArray.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                productImageView.setImageBitmap(bitmap);
            } else {
                productImageView.setImageResource(R.drawable.ic_launcher_background); // Set a default image if no image exists
            }

            // Make the image view clickable
            final String productName = name;
            final double productPrice = price;

            productImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductDisplayActivity.this, OrderActivity.class);
                    intent.putExtra("productName", productName);
                    intent.putExtra("productPrice", productPrice);

                    startActivity(intent);
                }
            });


            listView.addView(productView);
        }

        cursor.close();
    }
}










