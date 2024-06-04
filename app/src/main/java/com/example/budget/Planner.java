package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Planner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        BottomNavigationView bottom_nav = findViewById(R.id.navigation);
        bottom_nav.setSelectedItemId(R.id.profile);

        bottom_nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.profile) {
                return  true;
            } else if (item.getItemId() ==R.id.planner) {
                startActivity(new Intent(getApplicationContext(), planning.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() ==R.id.setting) {
                startActivity(new Intent(getApplicationContext(), setting.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }

            return false;
        });
    }


}