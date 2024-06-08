package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class sign_up extends AppCompatActivity {


    EditText username,address,contact;
    Button sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.username);
        address = findViewById(R.id.address);
        contact = findViewById(R.id.contact_number);
        sign_up = findViewById(R.id.get_up);

        sign_up.setEnabled(false);
        ButtonDisabler(username,address,contact,sign_up);


    }

    public void ButtonDisabler(EditText email, EditText password,EditText contact, Button log) {

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
                               !password.getText().toString().trim().isEmpty() &&
                               !contact.getText().toString().trim().isEmpty());
            }
        };

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        contact.addTextChangedListener(textWatcher);
    }



}