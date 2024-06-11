package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class setting extends AppCompatActivity {
    Intent profile, planner;
    String login;
    TextView logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        BottomNavigationView bottom_nav = findViewById(R.id.navigation);
        bottom_nav.setSelectedItemId(R.id.setting);
        profile = new Intent(getApplicationContext(), profile.class);
        planner = new Intent(getApplicationContext(), planning.class);

        Intent intent = getIntent();
        login = intent.getStringExtra("Login");



        bottom_nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.profile) {
                profile.putExtra("Login", login);
                startActivity(profile);
                overridePendingTransition(0, 0);
                finish();
                return  true;
            } else if (item.getItemId() ==R.id.planner) {
                planner.putExtra("Login", login);
                startActivity(planner);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() ==R.id.setting) {
                return true;
            }

            return false;
        });

        logout = findViewById(R.id.title7);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(setting.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}