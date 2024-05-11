package com.example.lostnfound.helper;

// DbHelper.java
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lostnfound.model.Advert;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "advert_database";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    public static final String TABLE_ADVERTS = "adverts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_POST_TYPE = "post_type";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LOCATION = "location";

    // Create table SQL query
    private static final String CREATE_TABLE_ADVERTS = "CREATE TABLE " + TABLE_ADVERTS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_POST_TYPE + " TEXT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_PHONE + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_LOCATION + " TEXT"
            + ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database table
        db.execSQL(CREATE_TABLE_ADVERTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade database (if needed)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADVERTS);
        onCreate(db);
    }
// DbHelper.java

    @SuppressLint("Range")
    public Advert getAdvertById(String advertId) {
        Advert advert = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_POST_TYPE, COLUMN_NAME, COLUMN_PHONE, COLUMN_DESCRIPTION, COLUMN_DATE, COLUMN_LOCATION};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {advertId};

        Cursor cursor = db.query(TABLE_ADVERTS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            advert = new Advert();
            advert.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            advert.setPostType(cursor.getString(cursor.getColumnIndex(COLUMN_POST_TYPE)));
            advert.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            advert.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
            advert.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            advert.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            advert.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));

            cursor.close();
        }

        return advert;
    }
    public boolean deleteAdvert(String advertId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { advertId };

        int deletedRows = db.delete(TABLE_ADVERTS, selection, selectionArgs);
        db.close();

        return deletedRows > 0;
    }

}

