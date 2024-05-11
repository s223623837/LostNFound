package com.example.lostnfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnCreateAdvert;
    private Button btnShowAllItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateAdvert = findViewById(R.id.btnCreateAdvert);
        btnShowAllItems = findViewById(R.id.btnShowAllItems);

        // Set click listener for the "Create a New Advert" button
        btnCreateAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open NewAdvertActivity when the button is clicked
                startActivity(new Intent(MainActivity.this, NewAdvertActivity.class));
            }
        });

        // Set click listener for the "Show All Lost & Found Items" button
        btnShowAllItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open AllItemsActivity to show all items
                startActivity(new Intent(MainActivity.this, AllItemsActivity.class));
            }
        });
    }
}
