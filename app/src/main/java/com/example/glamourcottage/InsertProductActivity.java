package com.example.glamourcottage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;



public  class InsertProductActivity extends AppCompatActivity {

    //private static final int REQUEST_IMAGE_PICK = 1;

    private EditText productNameEditText;
    private EditText productPriceEditText;
    private ImageView selectedImageView;
    private DatabaseHelper database;
    private byte[] imageByteArray;

    private ActivityResultLauncher<Intent> imagePickerLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);

        productNameEditText = findViewById(R.id.et_product_name);
        productPriceEditText = findViewById(R.id.et_product_price);
        selectedImageView = findViewById(R.id.iv_selected_image);
        Button selectImageButton = findViewById(R.id.btn_select_image);
        Button insertProductButton = findViewById(R.id.btn_insert_product);


        database = new DatabaseHelper(this);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onActivityResult);

        selectImageButton.setOnClickListener(view -> showImageSelectionDialog());
        insertProductButton.setOnClickListener(view -> insertProduct());
    }



    @SuppressLint("IntentReset")
    private void showImageSelectionDialog() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        imagePickerLauncher.launch(pickIntent);
    }



    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



    private void insertProduct() {
        String name = productNameEditText.getText().toString();
        double price = Double.parseDouble(productPriceEditText.getText().toString());

        if (name.isEmpty() || imageByteArray == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        database.insertProduct(name, price, imageByteArray);
        Toast.makeText(this, "Product inserted successfully", Toast.LENGTH_SHORT).show();
    }



    private void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            Uri imageUri = result.getData().getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                selectedImageView.setImageBitmap(imageBitmap);
                imageByteArray = bitmapToByteArray(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



















