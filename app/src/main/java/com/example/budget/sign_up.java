package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Interpolator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_up extends AppCompatActivity {
    sign_up2 sign_visibility;

    EditText username,address,contact;
    Button sign_up;
    TextView alert_username,alert_contact,alert_address;


    String username_input,address_input,contact_input, email_input, password_input;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sign_visibility = new sign_up2();

        username     = findViewById(R.id.username);
        address = findViewById(R.id.address);
        contact = findViewById(R.id.contact_number);
        sign_up = findViewById(R.id.get_up);

        alert_address = findViewById(R.id.alert_address);
        alert_contact  = findViewById(R.id.alert_contact);
        alert_username = findViewById(R.id.alert_username);


        DB = new DBHelper(this);

        Intent intent = getIntent();
        email_input = intent.getStringExtra("EMAIL");
        password_input = intent.getStringExtra("PASSWORD");



        sign_up.setEnabled(false);
        ButtonDisabler(username,address,contact,sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username_input = username.getText().toString();
                contact_input = contact.getText().toString();
                address_input = address.getText().toString();


                sign_visibility.visibility(isString(username_input),alert_username);
                sign_visibility.visibility(isNumberValid(contact_input),alert_contact);

                if (isString(username_input) && isNumberValid(contact_input)) {

                    Intent main_page = new Intent(sign_up.this,MainActivity.class);
                    Boolean checkuser =DB.Checkemail(email_input);
                    if(checkuser == false){
                        DB.insertAccountData(email_input, password_input, username_input, address_input, contact_input);
                            Toast.makeText(com.example.budget.sign_up.this, "Registered Successful", Toast.LENGTH_SHORT).show();
                            main_page.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(main_page);
                    }
                    else{
                        Toast.makeText(com.example.budget.sign_up.this, "Email already exist", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


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

    public  boolean hasNoWhiteSpaces(String input) {
        String regex = "^\\S+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();


    }
    
    public boolean isNumberValid(String number) {
        String input = String.valueOf(number);
        String regex = "^09[0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);


        return  matcher.matches();
    }

    public static boolean isString(String input) {
        String regex = "^[a-zA-Z]{1,}+[0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }


    



}