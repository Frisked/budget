package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class sign_up2 extends AppCompatActivity {
    int click = 1;
    ImageButton next_button;
    sign_up sign;
    Drawable background,background2,error_textfield;
    String email_input,password_input,password_input2;
    EditText email,password,password2;
    TextView alert_email,alert_pass,alert_pass2;
    LinearLayout linear_layout,linear_layout2;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
         linear_layout = findViewById(R.id.lay_lin);
         linear_layout2 = findViewById(R.id.lay_lin2);
         email = findViewById(R.id.username);
         password = findViewById(R.id.pass);
         password2 = findViewById(R.id.pass2);
        ImageButton view = findViewById(R.id.visibile_pass);
        ImageButton view2 = findViewById(R.id.visibile_pass2);
        background = ContextCompat.getDrawable(this, R.drawable.textfield_selected);
        background2 = ContextCompat.getDrawable(this, R.drawable.circle);
        error_textfield = ContextCompat.getDrawable(this,R.drawable.error_textfield);
        alert_email  = findViewById(R.id.alert_email);
        alert_pass = findViewById(R.id.alert_password);
        alert_pass2 = findViewById(R.id.alert_password2);
        DB = new DBHelper(this);


        Clicking(view,password,linear_layout);
        Clicking(view2,password2,linear_layout2);

        next_button = findViewById(R.id.next_but);
        next_button.setEnabled(false);

        ButtonDisabler(email,password,password2,next_button);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    email_input = email.getText().toString();
                    password_input = password.getText().toString();
                    password_input2 = password2.getText().toString();



                    visibility(isValidEmail(email_input),alert_email);
                    visibility(isMatch(password_input,password_input2),alert_pass2);
                    visibility(isValidPass(password_input,alert_pass),alert_pass);

                if (isValidEmail(email_input) && isMatch(password_input,password_input2) && isValidPass(password_input,alert_pass) ) {
                    Intent i = new Intent(sign_up2.this,sign_up.class);
                    i.putExtra("EMAIL", email_input);
                    i.putExtra("PASSWORD", password_input);
                    startActivity(i);
                } else {
                    Toast.makeText(sign_up2.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    public void Clicking(ImageButton view, EditText password, LinearLayout linear_layout) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selection = password.getSelectionEnd();
                if (click == 1)  {
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
                Log.d("Ako", background2.toString());

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
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                log.setEnabled(!email.getText().toString().trim().isEmpty() &&
                        !password.getText().toString().trim().isEmpty() &&
                        !contact.getText().toString().trim().isEmpty()
                        );
            }
        };

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        contact.addTextChangedListener(textWatcher);
    }

    public boolean isValidEmail(String email) {
        String EMAILREGEX = "^[a-zA-Z0-9.%+-]+@[a-zA-Z0-9]+\\.{1}[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(EMAILREGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean isValidPass(String password, TextView warning) {

        String lengthRegex = "^.{10,}$";
        String letterRegex = ".*[A-Za-z].*";
        String specialCharRegex = "^(?=.*\\d|.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).+$";

        Pattern lengthPattern = Pattern.compile(lengthRegex);
        Pattern letterPattern = Pattern.compile(letterRegex);
        Pattern specialCharPattern = Pattern.compile(specialCharRegex);

        Matcher lengthMatcher = lengthPattern.matcher(password);
        Matcher letterMatcher = letterPattern.matcher(password);
        Matcher specialCharMatcher = specialCharPattern.matcher(password);

        boolean isLengthValid = lengthMatcher.matches();
        boolean hasLetter = letterMatcher.matches();
        boolean hasSpecialChar = specialCharMatcher.matches();

        if (isLengthValid && hasLetter && hasSpecialChar) {
                 return true;
        } else {

            if (!isLengthValid) {
                warning.setText("Password must be atleast 10 characters long");
                return false;
            }
            if (!hasLetter) {
                warning.setText("Password must contain at least one letter");
                return false;
            }
            if (!hasSpecialChar) {
                warning.setText("Password must contain at least one special character or one number");
                return false;
            }
        }
        return true;
    }


  public void visibility(Boolean validating,TextView alert) {
      if (!validating) {
          alert.setVisibility(View.VISIBLE);
      } else {
          alert.setVisibility(View.INVISIBLE);

      }
  }



  public boolean isMatch(String pass,String pass2) {
        boolean ismatching = pass.equals(pass2);
        return ismatching;
  }




}