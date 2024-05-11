package com.example.lostnfound;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostnfound.helper.DbHelper;

public class NewAdvertActivity extends AppCompatActivity {

    private RadioGroup radioGroupPostType;
    private EditText editTextName, editTextPhone, editTextDescription, editTextDate, editTextLocation;
    private Button btnSubmitAdvert;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);
        dbHelper = new DbHelper(this);

        // Initialize views
        radioGroupPostType = findViewById(R.id.radioGroupPostType);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDate = findViewById(R.id.editTextDate);
        editTextLocation = findViewById(R.id.editTextLocation);
        btnSubmitAdvert = findViewById(R.id.btnSubmitAdvert);

        // Set click listener for Submit button
        btnSubmitAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected post type (Lost/Found)
                RadioButton selectedRadioButton = findViewById(radioGroupPostType.getCheckedRadioButtonId());
                String postType = selectedRadioButton.getText().toString();

                // Get input values
                String name = editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String date = editTextDate.getText().toString().trim();
                String location = editTextLocation.getText().toString().trim();

                // Validate input (e.g., ensure required fields are not empty)
                if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
                    Toast.makeText(NewAdvertActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the advert to the database
                    long advertId = saveAdvertToDatabase(postType, name, phone, description, date, location);

                    if (advertId != -1) {
                        // Show success message
                        Toast.makeText(NewAdvertActivity.this, "Advert submitted successfully", Toast.LENGTH_SHORT).show();
                        // Redirect to AllItemsActivity
                        Intent intent = new Intent(NewAdvertActivity.this, AllItemsActivity.class);
                        startActivity(intent);

                        // Finish the current activity
                        finish();
                    } else {
                        // Show error message if saving failed
                        Toast.makeText(NewAdvertActivity.this, "Failed to submit advert", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private long saveAdvertToDatabase(String postType, String name, String phone, String description, String date, String location) {
        // Get writable database instance
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create ContentValues object to store advert data
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_POST_TYPE, postType);
        values.put(DbHelper.COLUMN_NAME, name);
        values.put(DbHelper.COLUMN_PHONE, phone);
        values.put(DbHelper.COLUMN_DESCRIPTION, description);
        values.put(DbHelper.COLUMN_DATE, date);
        values.put(DbHelper.COLUMN_LOCATION, location);

        // Insert advert data into database
        long advertId = db.insert(DbHelper.TABLE_ADVERTS, null, values);

        // Close the database connection
        db.close();

        return advertId;
    }
}
