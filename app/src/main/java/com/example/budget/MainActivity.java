package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    int click = 1;
    private boolean keep = true;
    private final int DELAY = 2000;
    EditText password;
    LinearLayout linear_layout;
    SplashScreen screen;
    ImageButton view;
    Button sign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);
        SplashSceen();
        
         view = findViewById(R.id.visibile_pass);
        password = findViewById(R.id.pass);
        linear_layout = findViewById(R.id.lay_lin);
        sign = findViewById(R.id.Sign);

        Drawable background = ContextCompat.getDrawable(this, R.drawable.textfield_selected);
        Drawable background2 = ContextCompat.getDrawable(this, R.drawable.circle);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText passfield = findViewById(R.id.pass);
                int selection = passfield.getSelectionEnd();
                if (click == 1) {
                    view.setImageResource(R.drawable.visis);
                    passfield.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    click++;
                } else if (click == 2) {
                    view.setImageResource(R.drawable.visis_off);
                    passfield.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    click--;
                }
                passfield.setSelection(selection);
            }
        });

        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                linear_layout.setBackground(background);
            } else {
                linear_layout.setBackground(background2);
            }
        });


    }

    public void Plan(View view) {
        Intent i = new Intent(MainActivity.this,profile.class);
        startActivity(i);
        finish();
    }

    public void sign_up_menu(View view) {
        Intent i = new Intent(MainActivity.this,sign_up2.class);
        startActivity(i);
        finish();
    }



    private void SplashSceen() {
        screen.setKeepOnScreenCondition(() -> keep);
        Handler handler = new Handler();
        handler.postDelayed(() -> keep = false, DELAY);;
    }





}