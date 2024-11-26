
package com.example.glamourcottage;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnEnter = findViewById(R.id.btn_welButton);

        btnEnter.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        });


    }
}