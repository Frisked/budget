package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;



public class sign_up2 extends AppCompatActivity {
    int click = 1;
    ImageButton next_button;
    sign_up sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        LinearLayout linear_layout = findViewById(R.id.lay_lin);
        LinearLayout linear_layout2 = findViewById(R.id.lay_lin2);
        EditText email = findViewById(R.id.username);
        EditText password = findViewById(R.id.pass);
        EditText password2 = findViewById(R.id.pass2);
        ImageButton view = findViewById(R.id.visibile_pass);
        ImageButton view2 = findViewById(R.id.visibile_pass2);

        Clicking(view,password,linear_layout);
        Clicking(view2,password2,linear_layout2);

        next_button = findViewById(R.id.next_but);
        next_button.setEnabled(false);

        ButtonDisabler(email,password,password2,next_button);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(sign_up2.this,sign_up.class);
                startActivity(i);
            }
        });


    }

    public void Clicking(ImageButton view, EditText password, LinearLayout linear_layout) {
        Drawable background = ContextCompat.getDrawable(this, R.drawable.textfield_selected);
        Drawable background2 = ContextCompat.getDrawable(this, R.drawable.circle);
        click = 1;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selection = password.getSelectionEnd();
                if (click == 1) {
                    view.setImageResource(R.drawable.visis);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    click++;
                } else if (click == 2) {
                    view.setImageResource(R.drawable.visis_off);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    click--;
                }
                password.setSelection(selection);
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


    public void ButtonDisabler(EditText email, EditText password,EditText contact, ImageButton log) {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action needed here
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Enable the button if both EditTexts have input, disable if not
                log.setEnabled(!email.getText().toString().trim().isEmpty() &&
                        !password.getText().toString().trim().isEmpty() &&
                        !contact.getText().toString().trim().isEmpty());
            }
        };

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        contact.addTextChangedListener(textWatcher);
    }
}