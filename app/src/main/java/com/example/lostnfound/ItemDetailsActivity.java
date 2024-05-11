package com.example.lostnfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostnfound.helper.DbHelper;
import com.example.lostnfound.model.Advert;

public class ItemDetailsActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewPostType;
    private TextView textViewPhone;
    private TextView textViewDescription;
    private TextView textViewDate;
    private TextView textViewLocation;
    private Button btnDeleteItem;
    private DbHelper dbHelper;
    private String advertId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        // Initialize views
        textViewName = findViewById(R.id.textViewName);
        textViewPostType = findViewById(R.id.textViewPostType);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewDate = findViewById(R.id.textViewDate);
        textViewLocation = findViewById(R.id.textViewLocation);
        btnDeleteItem = findViewById(R.id.btnDeleteItem);

        // Initialize DbHelper
        dbHelper = new DbHelper(this);

        // Get advert ID from intent extras
        advertId = getIntent().getStringExtra("advertId");

        // Fetch advert details based on advertId
        Advert advert = dbHelper.getAdvertById(advertId);

        if (advert != null) {
            // Display advert details in TextViews
            textViewName.setText("Name: " + advert.getName());
            textViewPostType.setText("Post Type: " + advert.getPostType());
            textViewPhone.setText("Phone: " + advert.getPhone());
            textViewDescription.setText("Description: " + advert.getDescription());
            textViewDate.setText("Date: " + advert.getDate());
            textViewLocation.setText("Location: " + advert.getLocation());
        }

        // Set click listener for delete button
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Attempt to delete the advert
                boolean isDeleted = dbHelper.deleteAdvert(advertId);

                if (isDeleted) {
                    Toast.makeText(ItemDetailsActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                    // Redirect to AllItemsActivity
                    redirectToAllItemsActivity();

                } else {
                    Toast.makeText(ItemDetailsActivity.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Method to redirect to AllItemsActivity
    private void redirectToAllItemsActivity() {
        Intent intent = new Intent(ItemDetailsActivity.this, AllItemsActivity.class);
        startActivity(intent);
        finish(); // Finish current activity to prevent back navigation
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connection when the activity is destroyed
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
