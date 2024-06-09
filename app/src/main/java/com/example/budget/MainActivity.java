package com.example.budget;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    int click = 1;
    private boolean keep = true;
    private final int DELAY = 1000;
    EditText email,password;
    LinearLayout linear_layout;
    SplashScreen screen;
    ImageButton view;
    Button sign, log;
    String email_input,password_input;


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
        log = findViewById(R.id.Log);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        email_input = email.getText().toString();

        log.setEnabled(false);




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

       ButtonDisabler(email,password,log);


    }

    public void Plan(View view) {

        email_input = email.getText().toString();
        password_input = password.getText().toString();
        if (isValidEmail(email_input)) {
            Intent i = new Intent(MainActivity.this, profile.class);
            startActivity(i);
            finish();

        } else {
            email.setHintTextColor(getColor(R.color.Red));
            password.setHintTextColor(getColor(R.color.Red));
        }
    }

    public void sign_up_menu(View view) {
        Intent i = new Intent(MainActivity.this,sign_up2.class);
        startActivity(i);
    }



    private void SplashSceen() {
        screen.setKeepOnScreenCondition(() -> keep);
        Handler handler = new Handler();
        handler.postDelayed(() -> keep = false, DELAY);
    }


    public static boolean isValidEmail(String email) {
        String EMAILREGEX = "^[a-zA-Z0-9.%+-]+@[a-zA-Z0-9]+\\.{1}[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(EMAILREGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }




        public void ButtonDisabler(EditText email,EditText password,Button log) {
    
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
    
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
    
                @Override
                public void afterTextChanged(Editable s) {
                    log.setEnabled(!email.getText().toString().trim().isEmpty() &&
                            !password.getText().toString().trim().isEmpty());
                }
            };
    
            email.addTextChangedListener(textWatcher);
            password.addTextChangedListener(textWatcher);
        }
}