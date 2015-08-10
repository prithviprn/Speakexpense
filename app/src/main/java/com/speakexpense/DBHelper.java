package com.speakexpense;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Expenses";


    // Contacts table name
    private static final String TABLE_NAME = "Mydata";
    DBHelper dbh;
    int rows, cols;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(id INTEGER PRIMARY KEY,DATE DATETIME,PRODUCT VARCHAR,COST REAL,PLACE VARCHAR,SP2TXT VARCHAR);");

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void insertdata(String DATE, String PRODUCT, String COST, String PLACE, String SP2TXT) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_NAME + " Values (null,'" + DATE + "','" + PRODUCT.toLowerCase() + "','" + COST + "','" + PLACE.toLowerCase() + "','" + SP2TXT + "');");
    }

    public void updatedata(String OLDPRODUCT, String DATE, String NEWPRODUCT, String COST, String PLACE, String SP2TXT) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET PLACE='" + PLACE + "',PRODUCT='" + NEWPRODUCT.toLowerCase()  + "',COST='" + COST + "',SP2TXT='" + SP2TXT + "' WHERE PRODUCT='" + OLDPRODUCT.toLowerCase() + "';");
    }

    public void updatedata1(Integer ROWID, String DATE, String PRODUCT, String COST, String PLACE, String SP2TXT) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET PRODUCT='" + PRODUCT.toLowerCase() + "',COST='" + COST + "',PLACE='" + PLACE.toLowerCase() + "' WHERE ID='" + ROWID + "';");
    }

    public void updateall(Integer ROWID, String PRODUCT, String COST, String PLACE) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET PRODUCT='" + PRODUCT.toLowerCase() + "',COST='" + COST + "',PLACE='" + PLACE.toLowerCase() + "' WHERE ID='" + ROWID + "';");
    }

    public void updateproduct(Integer ROWID, String PRODUCT) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET PRODUCT='" + PRODUCT.toLowerCase() + "' WHERE ID='" + ROWID + "';");
    }

    public void updatecost(Integer ROWID, String cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET COST='" + cost + "' WHERE ID='" + ROWID + "';");
    }

    public void updateplace(Integer ROWID, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET PLACE='" + place.toLowerCase() + "' WHERE ID='" + ROWID + "';");
    }

    public void updatecostplace(Integer ROWID, String cost, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET COST='" + cost + "',PLACE='" + place.toLowerCase() + "' WHERE ID='" + ROWID + "';");
    }

    public void updateproductplace(Integer ROWID, String product, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET PRODUCT='" + product.toLowerCase() + "',PLACE='" + place.toLowerCase() + "' WHERE ID='" + ROWID + "';");
    }

    public void updateproductcost(Integer ROWID, String product, String cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET PRODUCT='" + product.toLowerCase() + "',COST='" + cost + "' WHERE ID='" + ROWID + "';");
    }

    public void deletedata(String PRODUCT, String COST, String PLACE, String DATE) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE PRODUCT='" + PRODUCT.toLowerCase() + "' OR COST='" + COST + "' OR DATE='" + DATE + "' OR PLACE='" + PLACE + "';");
    }

    public void deletedata1(Integer ROWID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ID='" + ROWID + "';");
    }

}