package com.example.glamourcottage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Constants
    public static final String DATABASE_NAME = "My_DB";
    public static final int DATABASE_VERSION = 2;

    // User Table
    public static final String SIGNUP_TABLE = "register";
    public static final String COL_ID = "_id";
    public static final String USERNAME_COL = "name";
    public static final String EMAIL_COL = "email";
    public static final String PASS_COL = "pass";
    public static final String PHONE_COL = "phone";

    // Product Table
    public static final String TABLE_PRODUCTS = "products";
    public static final String PRODUCT_ID = "_id";
    public static final String PRODUCT_NAME_COL = "productName";
    public static final String PRODUCT_PRICE_COL = "productPrice";
    public static final String PRODUCT_IMAGE_URI_COL = "productImageUri";

    // Order Table
    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_ID = "id";
    public static final String ORDER_PRODUCT_NAME = "productName";
    public static final String ORDER_PRODUCT_PRICE = "productPrice";
    public static final String ORDER_QUANTITY = "quantity";
    public static final String ORDER_SIZE = "productSize";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User Table
        db.execSQL("CREATE TABLE " + SIGNUP_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME_COL + " TEXT, " +
                EMAIL_COL + " TEXT, " +
                PASS_COL + " TEXT, " +
                PHONE_COL + " TEXT)");

        // Create Product Table
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " (" +
                PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT_NAME_COL + " TEXT, " +
                PRODUCT_PRICE_COL + " REAL, " +
                PRODUCT_IMAGE_URI_COL + " BLOB)");

        // Create Order Table
        db.execSQL("CREATE TABLE " + ORDER_TABLE + " (" +
                ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ORDER_PRODUCT_NAME + " TEXT, " +
                ORDER_PRODUCT_PRICE + " REAL, " +
                ORDER_QUANTITY + " INTEGER, " +
                ORDER_SIZE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop all existing tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + SIGNUP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE);
        onCreate(db);
    }

    // User Methods
    public boolean insertUser(String name, String email, String phone, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_COL, name);
        contentValues.put(EMAIL_COL, email);
        contentValues.put(PHONE_COL, phone);
        contentValues.put(PASS_COL, pass);

        long result = db.insert(SIGNUP_TABLE, null, contentValues);
        return result != -1;
    }

    public boolean checkUserByUsername(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SIGNUP_TABLE + " WHERE " +
                USERNAME_COL + " = ? AND " + PASS_COL + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Product Methods
    public void insertProduct(String name, double price, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME_COL, name);
        values.put(PRODUCT_PRICE_COL, price);
        values.put(PRODUCT_IMAGE_URI_COL, imageByteArray);
        db.insert(TABLE_PRODUCTS, null, values);
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }

    public Cursor getProductByName(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " +
                PRODUCT_NAME_COL + " = ?", new String[]{productName});
    }

    public void updateProduct(int productId, String productName, double price, byte[] productImageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME_COL, productName);
        values.put(PRODUCT_PRICE_COL, price);
        values.put(PRODUCT_IMAGE_URI_COL, productImageByteArray);
        db.update(TABLE_PRODUCTS, values, PRODUCT_ID + " = ?", new String[]{String.valueOf(productId)});
    }

    public void deleteProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, PRODUCT_NAME_COL + " = ?", new String[]{productName});
    }

    // Order Methods
    public long insertOrder(String productName, double productPrice, int quantity, String productSize) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_PRODUCT_NAME, productName);
        values.put(ORDER_PRODUCT_PRICE, productPrice);
        values.put(ORDER_QUANTITY, quantity);
        values.put(ORDER_SIZE, productSize);
        return db.insert(ORDER_TABLE, null, values);
    }

//    public Cursor getAllOrders() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("SELECT * FROM " + ORDER_TABLE, null);
//    }


    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ORDER_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("productName"));
                @SuppressLint("Range") double productPrice = cursor.getDouble(cursor.getColumnIndex("productPrice"));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                @SuppressLint("Range") String productSize = cursor.getString(cursor.getColumnIndex("productSize"));

                Order order = new Order(productName, productPrice, quantity, productSize);
                orderList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return orderList;
    }
}





