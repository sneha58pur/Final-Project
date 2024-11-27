package com.example.glamourcottage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        Button btnInsertProduct =findViewById(R.id.btn_insert_product);
         Button btnViewProduct =findViewById(R.id.btn_view_product);

        btnInsertProduct.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, InsertProductActivity.class);
            startActivity(intent);
        });

        btnViewProduct.setOnClickListener(v -> {
         Intent intent = new Intent(AdminHomeActivity.this,ViewProductActivity.class);
        startActivity(intent);
           });

    }
}