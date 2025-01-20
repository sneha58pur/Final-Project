package com.example.glamourcottage;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminViewOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrder;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_order);

        // Initialize views and database helper
        recyclerViewOrder = findViewById(R.id.recyclerViewOrders);
        databaseHelper = new DatabaseHelper(this);



        // Set RecyclerView properties
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));

        // Display orders when the activity is created
        displayOrders();
    }

    private void displayOrders() {
        // Fetch all orders from the database
        Cursor cursor = databaseHelper.getAllOrders();

        // Check if the cursor is valid and has data
        if (cursor != null && cursor.getCount() > 0) {
            // Create and set the adapter to display orders in RecyclerView
            OrderAdapter adapter = new OrderAdapter(this, cursor);
            recyclerViewOrder.setAdapter(adapter);
        } else {
            // Show a message if no orders are found
            Toast.makeText(this, "No orders available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the displayed orders when the activity is resumed
        displayOrders();
    }
}
