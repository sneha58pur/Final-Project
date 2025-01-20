package com.example.glamourcottage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String TABLE_ORDERS = "order_table";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_PRODUCT_NAME = "product_name";
    public static final String COLUMN_ORDER_PRICE = "o_price";


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
        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_PRODUCT_NAME + " TEXT, " +
                COLUMN_ORDER_PRICE + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop all existing tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + SIGNUP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 1, DATABASE_VERSION);
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


    public void insertOrders(String productName, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ORDER_PRODUCT_NAME, productName);
        values.put(COLUMN_ORDER_PRICE, price);


        db.insert(TABLE_ORDERS, null, values);
    }



    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT product_name, o_price FROM " + TABLE_ORDERS, null);
    }


}
