package com.example.glamourcottage;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewOrderActivity extends AppCompatActivity {

    private ListView listViewOrder;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        // Initialize views and database helper
        listViewOrder = findViewById(R.id.orderListView);
        databaseHelper = new DatabaseHelper(this);

        // Display orders when the activity is created
        displayOrder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the displayed orders when the activity is resumed
        displayOrder();
    }

    private void displayOrder() {
        // Fetch all orders from the database
        Cursor cursor = databaseHelper.getAllOrders();
        // Create and set the adapter to display orders in the ListView
        OrderAdapter adapter = new OrderAdapter(this, cursor, 0);
        listViewOrder.setAdapter(adapter);
    }
}













