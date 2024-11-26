package com.example.glamourcottage;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.glamourcottage.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameEditText,  emailEditText, phoneEditText;
    private TextInputEditText passEditText;
    private Spinner genderSpinner;
    private String name,  email, phone,pass, gender;
    private Button sign;
    private Pattern namePattern = Pattern.compile("[a-z A-Z._]+");
    private Pattern emailPattern = Pattern.compile("[a-z]+@(gmail|yahoo).com" );
    private Pattern phonePattern= Pattern.compile("01[578][0-9]{8}");
    private Pattern passPattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$%^&+=!]).{8,}");



    LinearLayout inputLayout, outputLayout, inputLayoutC;
    TextView outputText;
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

        nameEditText = findViewById(R.id.et_register_username);

        emailEditText = findViewById(R.id.et_register_email);
        phoneEditText = findViewById(R.id.et_register_mobile);
        passEditText=findViewById(R.id.pass);

        genderSpinner = findViewById(R.id.spinner);
        sign = findViewById(R.id.btn_signRegister);

        inputLayout = findViewById(R.id.inputLayout);
        inputLayoutC = findViewById(R.id.inputLayoutCreate);
        outputLayout = findViewById(R.id.outputLayout);
        outputText = findViewById(R.id.outputText);

        String[] items = new String[]{"Gender : ", "MALE", "FEMALE"};
        genderSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items));
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = genderSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();

                email = emailEditText.getText().toString();
                phone = phoneEditText.getText().toString();
                pass= passEditText.getText().toString();

                if (name.isEmpty()){
                    nameEditText.setError("Empty!!");
                    nameEditText.requestFocus();
                }

                else if (!namePattern.matcher(name).matches()){
                    nameEditText.setError("Name can be only Alphabet");
                    nameEditText.requestFocus();
                }



                else if (email.isEmpty()){
                    emailEditText.setError("Empty!!");
                    emailEditText.requestFocus();
                }
                else if (!emailPattern.matcher(email).matches()){
                    emailEditText.setError("Email is not accepted");
                    emailEditText.requestFocus();
                }



                else if (phone.isEmpty()){
                    phoneEditText.setError("Empty!!");
                    phoneEditText.requestFocus();
                }

                else if (!phonePattern.matcher(phone).matches()){
                    phoneEditText.setError("Invalid");
                    phoneEditText.requestFocus();
                }

                else if (pass.isEmpty()){
                    passEditText.setError("Empty!!");
                    passEditText.requestFocus();
                }

                else if (!passPattern.matcher(pass).matches()){
                    passEditText.setError("Invalid");
                    passEditText.requestFocus();
                }



                else if (Objects.equals(gender, "Select ")){
                    Toast.makeText(getApplicationContext(), "Please Select ", Toast.LENGTH_SHORT).show();
                }

                else {
                    inputLayout.setVisibility(View.GONE);
                    inputLayoutC.setVisibility(View.GONE);
                    outputLayout.setVisibility(View.VISIBLE);
                    String s = "Name: " + name + "\nEmail: " + email + "\nMobile Number: "+phone+ "\nGender: "+gender ;
                    outputText.setText(s);
                }
            }
        });

    }
}