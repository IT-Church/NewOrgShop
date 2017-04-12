package com.paramonod.kikos.pack;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "my_tag";

    public static final String TABLE_NAME = "friends";

    public static final String KEY_ID = "idShops";
    public static final String KEY_NAME = "ShopName";
    public static final String KEY_COORDX = "CoordX";
    public static final String KEY_COORDY = "CoordY";
    public static final String KEY_DESC = "Description";
    public static final String KEY_UPDATE = "Update";

    private static final String DATABASE_NAME = "ShopsDB";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " ("
                + KEY_ID + " integer primary key autoincrement,"
                + KEY_NAME + " text,"
                + KEY_COORDX + " double,"
                + KEY_COORDY + " double,"
                + KEY_DESC + "text,"
                + KEY_UPDATE + "integer" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
}