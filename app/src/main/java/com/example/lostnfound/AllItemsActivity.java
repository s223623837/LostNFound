package com.example.lostnfound;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnfound.adapter.AdvertAdapter;
import com.example.lostnfound.helper.DbHelper;
import com.example.lostnfound.model.Advert;

import java.util.ArrayList;
import java.util.List;

public class AllItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdvertAdapter advertAdapter;
    private List<Advert> advertList;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database helper
        dbHelper = new DbHelper(this);

        // Load adverts from database
        loadAdvertsFromDatabase();
    }

    private void loadAdvertsFromDatabase() {
        // Get readable database instance
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define projection (columns to retrieve)
        String[] projection = {
                DbHelper.COLUMN_ID,
                DbHelper.COLUMN_POST_TYPE,
                DbHelper.COLUMN_NAME,
                DbHelper.COLUMN_PHONE,
                DbHelper.COLUMN_DESCRIPTION,
                DbHelper.COLUMN_DATE,
                DbHelper.COLUMN_LOCATION
        };

        // Query the database to retrieve all adverts
        Cursor cursor = db.query(
                DbHelper.TABLE_ADVERTS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Initialize advertList to store retrieved adverts
        advertList = new ArrayList<>();

        // Process the cursor and populate advertList
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_ID));
                String postType = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_POST_TYPE));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_PHONE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_DATE));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LOCATION));

                // Create new Advert object and add to advertList
                Advert advert = new Advert(postType, name, phone, description, date, location);
                advert.setId(id);
                advertList.add(advert);
            } while (cursor.moveToNext());

            // Close the cursor
            cursor.close();
        }

        // Set up RecyclerView adapter with loaded advertList
        advertAdapter = new AdvertAdapter(this, advertList);
        recyclerView.setAdapter(advertAdapter);
    }
}
