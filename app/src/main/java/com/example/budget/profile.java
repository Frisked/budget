package com.example.budget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile extends AppCompatActivity {

    ImageView profile_picture;
    TextView PUsername, PAddress, PContact_number, PEmail;
    String username, address, contact_number, email, userid;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView bottom_nav = findViewById(R.id.navigation);
        bottom_nav.setSelectedItemId(R.id.profile);
        profile_picture = findViewById(R.id.title2);
        DB = new DBHelper(this);

        Intent intent = getIntent();
        String login = intent.getStringExtra(MainActivity.LOGIN);

        String[] userDetails = DB.getUserDetailsByEmail(login);
        username = userDetails[0];
        address = userDetails[1];
        email = userDetails[2];
        contact_number = userDetails[3];
        userid = userDetails[4];

        PUsername = findViewById(R.id.title4);
        PAddress = findViewById(R.id.title5);
        PContact_number = findViewById(R.id.title6);
        PEmail = findViewById(R.id.title7);

        PUsername.append("\n" + username);
        PAddress.append("\n" + address);
        PContact_number.append("\n" + contact_number);
        PEmail.append("\n" + email);



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

        TextView df = findViewById(R.id.title3);

        df.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePick();
            }
        });


    }

    public void ImagePick() {
        ImagePicker.with(profile.this)
                .galleryOnly()
                .cropSquare()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        profile_picture.setImageURI(uri);
    }
}