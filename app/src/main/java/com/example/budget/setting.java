package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class setting extends AppCompatActivity {
    Intent profile, planner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        BottomNavigationView bottom_nav = findViewById(R.id.navigation);
        bottom_nav.setSelectedItemId(R.id.setting);
        profile = new Intent(getApplicationContext(), profile.class);
        planner = new Intent(getApplicationContext(), planning.class);

        bottom_nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.profile) {
                startActivity(profile);
                overridePendingTransition(0, 0);
                finish();
                return  true;
            } else if (item.getItemId() ==R.id.planner) {
                startActivity(planner);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() ==R.id.setting) {
                return true;
            }

            return false;
        });
    }
}