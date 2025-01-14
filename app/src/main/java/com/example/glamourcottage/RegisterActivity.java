package com.example.glamourcottage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, phoneEditText;
    private TextInputEditText passEditText;
    private Spinner genderSpinner;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    private String name, email, phone, pass, gender;

    private final Pattern namePattern = Pattern.compile("[a-zA-Z ._]+");
   // private final Pattern emailPattern = Pattern.compile("[a-z]+@(gmail)\\.com");

   private final Pattern emailPattern = Pattern.compile("^(cse_)\\d{15}(@lus.ac.bd)$");
    private final Pattern phonePattern = Pattern.compile("01[578][0-9]{8}");
    private final Pattern passPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$%^&+=!]).{8,}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        nameEditText = findViewById(R.id.et_register_username);
        emailEditText = findViewById(R.id.et_register_email);
        phoneEditText = findViewById(R.id.et_register_mobile);
        passEditText = findViewById(R.id.pass);
        genderSpinner = findViewById(R.id.spinner);
        Button sign = findViewById(R.id.btn_signRegister);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Set up gender spinner
        String[] items = new String[]{"Gender :", "MALE", "FEMALE"};
        genderSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items));
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = genderSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        // Set up sign-up button
        sign.setOnClickListener(v -> {
            // Get user inputs
            name = nameEditText.getText().toString();
            email = emailEditText.getText().toString();
            phone = phoneEditText.getText().toString();
            pass = passEditText.getText().toString();
            genderSpinner = findViewById(R.id.spinner);

            // Validate inputs
            if (name.isEmpty()) {
                nameEditText.setError("Name cannot be empty");
                nameEditText.requestFocus();
            } else if (!namePattern.matcher(name).matches()) {
                nameEditText.setError("Name must contain only letters and spaces");
                nameEditText.requestFocus();
            } else if (email.isEmpty()) {
                emailEditText.setError("Email cannot be empty");
                emailEditText.requestFocus();
            } else if (!emailPattern.matcher(email).matches()) {
                emailEditText.setError("Invalid email format");
                emailEditText.requestFocus();
            } else if (phone.isEmpty()) {
                phoneEditText.setError("Phone number cannot be empty");
                phoneEditText.requestFocus();
            } else if (!phonePattern.matcher(phone).matches()) {
                phoneEditText.setError("Invalid phone number");
                phoneEditText.requestFocus();
            } else if (pass.isEmpty()) {
                passEditText.setError("Password cannot be empty");
                passEditText.requestFocus();
            } else if (!passPattern.matcher(pass).matches()) {
                passEditText.setError("Password must include uppercase, lowercase, number, and special character");
                passEditText.requestFocus();
            } else if ("Gender :".equals(gender)) {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            sendEmailVerification(user); // Send email verification

                            assert user != null;
                            DocumentReference df = firestore.collection("Users").document(user.getUid());
                            Map<String, String> userInfo = new HashMap<>();
                            userInfo.put("email", user.getEmail());
                            userInfo.put("name", name);
                            userInfo.put("phone", phone);
                            userInfo.put("gender",gender);

                            userInfo.put("uid", user.getUid());
                            df.set(userInfo);

                            Toast.makeText(getApplicationContext(), "Successfully Registered!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "User Already Exist!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    private void sendEmailVerification(FirebaseUser user) {
        if (user != null && !user.isEmailVerified()) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Verification email sent.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error sending verification email.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
