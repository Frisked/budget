package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class planning extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        BottomNavigationView bottom_nav = findViewById(R.id.navigation);
        bottom_nav.setSelectedItemId(R.id.planner);
        ImageView add = findViewById(R.id.add_btn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(planning.this,main_plan.class);
                startActivity(i);
            }
        });

        bottom_nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), profile.class));
                overridePendingTransition(0, 0);
                finish();
                return  true;
            } else if (item.getItemId() ==R.id.planner) {
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