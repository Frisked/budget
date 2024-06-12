package com.example.budget;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class setting extends AppCompatActivity {
    Intent profile, planner;
    String login;
    TextView logout,username_change,password_change;
    DBHelper DB;
    Integer userid;
    EditText editText1;
    Drawable bg_dialog;
String input1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        BottomNavigationView bottom_nav = findViewById(R.id.navigation);
        bottom_nav.setSelectedItemId(R.id.setting);
        profile = new Intent(getApplicationContext(), profile.class);
        planner = new Intent(getApplicationContext(), planning.class);
        bg_dialog = ContextCompat.getDrawable(this,R.drawable.circle);

        username_change = findViewById(R.id.username_change);
        password_change = findViewById(R.id.password_change);
        Intent intent = getIntent();
        login = intent.getStringExtra("Login");

        DB = new DBHelper(this);
        Integer[] getUserID = DB.getUserID(login);
        userid = getUserID[0];


        username_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog("username");


            }
        });

        password_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("password");
            }
        });





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

                AlertDialog.Builder dialog = new AlertDialog.Builder(setting.this);
                dialog.setTitle("Logout");
                dialog.setMessage("Are you sure you want to Logout?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(setting.this, MainActivity.class);
                                startActivity(intent1);
                                finish();
                                Toast.makeText(setting.this, "Successfully Logout", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No", null)
                        .setCancelable(false);

                AlertDialog dialog1 = dialog.create();

                dialog1.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog1.getWindow().setBackgroundDrawable(bg_dialog);
                    }
                });

                dialog1.show();

            }
        });
    }

    private void showDialog(String changeW) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.change_user, null);

        editText1 = dialogView.findViewById(R.id.editText1);
        editText1.setHint("Enter new "+changeW);

        DB = new DBHelper(this);
        Integer[] getUserID = DB.getUserID(login);
        userid = getUserID[0];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Enter new "+changeW);
        builder.setCancelable(false);


        builder.setPositiveButton("OK", (dialog, which) -> {
            String[] userDetails = DB.getUserDetailsByEmail(login);
            String username = userDetails[0];

            input1 = editText1.getText().toString();
            if (username.equals(input1) ) {
                Toast.makeText(this,"Already the same "+changeW,Toast.LENGTH_SHORT).show();

            }



            else {
                if (changeW.equals("username")) {
                DB.changeUsername(userid,input1);
                }
                else {
                    if(isValidPass(input1)) {
                        DB.changePassword(userid, input1);
                    }
                }
            }

        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            dialog.getWindow().setBackgroundDrawable(bg_dialog);
        });

        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text1 = editText1.getText().toString();
                boolean isInputValid = !text1.isEmpty();
                positiveButton.setEnabled(isInputValid);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editText1.addTextChangedListener(textWatcher);
    }
    public boolean isValidPass(String password) {

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
                Toast.makeText(setting.this,"Password must be atleast 10 characters long",Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!hasLetter) {
                Toast.makeText(setting.this,"Password must contain at least one letter",Toast.LENGTH_SHORT).show();

                return false;
            }
            if (!hasSpecialChar) {
                Toast.makeText(setting.this,"Password must contain at least one special character or one number",Toast.LENGTH_SHORT).show();

                return false;
            }
        }
        return true;
    }
}