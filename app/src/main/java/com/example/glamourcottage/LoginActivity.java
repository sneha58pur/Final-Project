package com.example.glamourcottage;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        EditText etUsername = findViewById(R.id.et_username);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnSign = findViewById(R.id.btn_sign);


        btnLogin.setOnClickListener(v->{





            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            }


            else{

                if(username.equals("admin") && password.equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class); // Assuming HomeActivity is the activity after login
                    startActivity(intent);
                }

                else{

                    DatabaseHelper database = new DatabaseHelper(LoginActivity.this);
                    boolean result = database.checkUserByUsername(username, password);
                    if (result) {
                        Toast.makeText(LoginActivity.this, "Welcome valid user!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, ProductDisplayActivity.class); // Assuming HomeActivity is the activity after login
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Username and password!", Toast.LENGTH_SHORT).show();


                    }
                }
            }
        });

        btnSign.setOnClickListener(v -> {
            // Implement your sign up logic here
            Toast.makeText(LoginActivity.this, "Sign up clicked", Toast.LENGTH_SHORT).show();
            // Navigate to sign up activity if needed
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}





