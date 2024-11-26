package com.example.glamourcottage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, phoneEditText;
    private TextInputEditText passEditText;
    private Spinner genderSpinner;
    private String name, email, phone, pass, gender;
    private Button sign;

    private final Pattern namePattern = Pattern.compile("[a-zA-Z ._]+");
    private final Pattern emailPattern = Pattern.compile("[a-z]+@(gmail|yahoo)\\.com");
    private final Pattern phonePattern = Pattern.compile("01[578][0-9]{8}");
    private final Pattern passPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$%^&+=!]).{8,}");

    private LinearLayout inputLayout, outputLayout, inputLayoutC;
    private TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
        sign = findViewById(R.id.btn_signRegister);
        inputLayout = findViewById(R.id.inputLayout);
        inputLayoutC = findViewById(R.id.inputLayoutCreate);
        outputLayout = findViewById(R.id.outputLayout);
        outputText = findViewById(R.id.outputText);

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
            name = nameEditText.getText().toString().trim();
            email = emailEditText.getText().toString().trim();
            phone = phoneEditText.getText().toString().trim();
            pass = passEditText.getText().toString().trim();

            // Validate inputs
            if (name.isEmpty()) {
                nameEditText.setError("Name cannot be empty");
                nameEditText.requestFocus();
                return;
            }
            if (!namePattern.matcher(name).matches()) {
                nameEditText.setError("Name must contain only letters and spaces");
                nameEditText.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                emailEditText.setError("Email cannot be empty");
                emailEditText.requestFocus();
                return;
            }
            if (!emailPattern.matcher(email).matches()) {
                emailEditText.setError("Invalid email format");
                emailEditText.requestFocus();
                return;
            }
            if (phone.isEmpty()) {
                phoneEditText.setError("Phone number cannot be empty");
                phoneEditText.requestFocus();
                return;
            }
            if (!phonePattern.matcher(phone).matches()) {
                phoneEditText.setError("Invalid phone number");
                phoneEditText.requestFocus();
                return;
            }
            if (pass.isEmpty()) {
                passEditText.setError("Password cannot be empty");
                passEditText.requestFocus();
                return;
            }
            if (!passPattern.matcher(pass).matches()) {
                passEditText.setError("Password must include uppercase, lowercase, number, and special character");
                passEditText.requestFocus();
                return;
            }
            if ("Gender :".equals(gender)) {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
                return;
            }

            // Display output
            inputLayout.setVisibility(View.GONE);
            inputLayoutC.setVisibility(View.GONE);
            outputLayout.setVisibility(View.VISIBLE);
            String output = "Name: " + name + "\nEmail: " + email + "\nMobile Number: " + phone + "\nGender: " + gender;
            outputText.setText(output);

            // Save to database
            boolean isInserted;
            try (DatabaseHelper db = new DatabaseHelper(RegisterActivity.this)) {
                isInserted = db.insertUser(name, email, phone, pass);
            }

            if (isInserted) {
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, ProductDisplayActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
