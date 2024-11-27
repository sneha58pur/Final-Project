package com.example.glamourcottage;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



    public class DatabaseHelper extends SQLiteOpenHelper {
        public static final String Database_name = "My_DB";
        public static final int version = 2;
        //user table
        public static final String SignUp_table = "register";
        static final String col_id = "_id";
        public static final String username_col = "name";
        public static final String email_col = "email";
        public static final String pass_col = "pass";
        public static final String phone_col = "phone";
      //product table
        public static final String TABLE_PRODUCTS = "products";
        public static final String product_name_col = "productName";
        public static final String product_price_col = "productPrice";
        public static final String product_image_URI_col = "productImageUri";
//order table
//        public static final String TABLE_ORDERS = "orders";
//        public static final String name_col = "name";
//        public static final String address_col = "address";
//        public static final String item_col = "item";




        public DatabaseHelper(Context context) {
        super(context, Database_name, null, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SignUp_table);
        onCreate(db);
    }
    @Override


    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SignUp_table + " (" +
                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                username_col + " TEXT, " +
                email_col + " TEXT, " +
                pass_col + " TEXT, " +
                phone_col + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " (" +
                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                product_name_col + " TEXT, " +
                product_price_col + " REAL, " +
                product_image_URI_col + " BLOB)");

         //        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " (" +
//                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                name_col + " TEXT, " +
//                address_col + " TEXT, " +
//                item_col + " TEXT)");







    }





    public boolean insertUser(String name, String email, String phone, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(username_col, name);
        contentValues.put(email_col, email);

        contentValues.put(phone_col, phone);
        contentValues.put(pass_col, pass);


        long result = db.insert(SignUp_table, null, contentValues);
        //result value, if inserted, then "row number"
        //result value, if not inserted, then -1

        return result != -1;
    }


    public boolean checkUserByUsername(String username, String password) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SignUp_table + " WHERE " + username_col + " = ? AND " + pass_col + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;

    }

    public void insertProduct(String name, double price, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(product_name_col, name);
        values.put(product_price_col, price);
        values.put(product_image_URI_col, imageByteArray);
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
    ////insert complete


    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }

    public Cursor getProductByName(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + product_name_col + " = ?", new String[]{productName});

    }
    //}//getting product from



    public void updateProduct(int productId, String productName, double price, byte[] productImageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(product_name_col, productName);
        values.put(product_price_col, price);

        values.put(product_image_URI_col, productImageByteArray);

        db.update(TABLE_PRODUCTS, values, col_id + " = ?", new String[]{String.valueOf(productId)});
        db.close();


    }

    public void deleteProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, product_name_col + " = ?", new String[]{productName});
        db.close();
    }





    }







