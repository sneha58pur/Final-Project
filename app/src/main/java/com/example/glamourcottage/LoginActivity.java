package com.example.glamourcottage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText  etEmail, etPassword;
    private Button btnLogin, btnSign;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize views

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSign = findViewById(R.id.btn_sign);
        auth = FirebaseAuth.getInstance();



        // Set up login button
        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();



            if (email.isEmpty()) {
                etEmail.setError("Email cannot be empty!");
                etEmail.requestFocus();

            }
           else if (password.isEmpty()) {
                etPassword.setError("Password cannot be empty!");
                etPassword.requestFocus();

            }

            // Admin login
            if (email.equals("admin@gmail.com") && password.equals("admin")) {
                Toast.makeText(this, "Admin Login Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                finish();
                return;
            }

            // Firebase authentication for regular users


            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    FirebaseUser user = auth.getCurrentUser();
                    if (task.isSuccessful()) {
                        if (user != null && user.isEmailVerified()) {
                            Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ProductDisplayActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        }else {
                            // If Email not verified
                            Toast.makeText(getApplicationContext(), "Please verify your email.", Toast.LENGTH_SHORT).show();
                            auth.signOut();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        });

        btnSign.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });

    }
}




