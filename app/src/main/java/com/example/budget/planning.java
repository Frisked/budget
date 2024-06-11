package com.example.budget;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class planning extends AppCompatActivity {
    ImageView add;
    String input1;
    String login;
    Integer input2, userid;
    EditText editText1;

    EditText editText2;
    Drawable bg_dialog;

    Intent profile,setting;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        BottomNavigationView bottom_nav = findViewById(R.id.navigation);
        bottom_nav.setSelectedItemId(R.id.planner);
        bg_dialog = ContextCompat.getDrawable(this,R.drawable.circle);
        add = findViewById(R.id.add_btn);

        Intent intent = getIntent();
        login = intent.getStringExtra("Login");

        profile = new Intent(getApplicationContext(), profile.class);
        setting = new Intent(getApplicationContext(), setting.class);



         add.setOnClickListener(v -> showDialog());


        bottom_nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.profile) {
                profile.putExtra("Login", login);
                startActivity(profile);
                overridePendingTransition(0, 0);
                finish();
                return  true;
            } else if (item.getItemId() ==R.id.planner) {
                return true;
            } else if (item.getItemId() ==R.id.setting) {;
                setting.putExtra("Login", login);
                startActivity(setting);
                overridePendingTransition(0, 0);
                finish();
                return true;
            }

            return false;
        });











    }

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.interface_dialog, null);

        editText1 = dialogView.findViewById(R.id.editText1);
        editText2 = dialogView.findViewById(R.id.editText2);

        // Initialize DB object
        DB = new DBHelper(this);
        Integer[] getUserID = DB.getUserID(login);
        userid = getUserID[0];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Enter Details");

        builder.setPositiveButton("OK", (dialog, which) -> {
            input1 = editText1.getText().toString();
            input2 = Integer.parseInt(editText2.getText().toString());


            Intent Budget = new Intent(planning.this, add_expenses.class);
            Budget.putExtra("Budget_name",input1);
            Budget.putExtra("Income",input2);
            Budget.putExtra("UserID",userid);



            startActivity(Budget);
            finish();

            DB.insertPlanData(userid, input1, input2);
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
                String text2 = editText2.getText().toString();
                boolean isInputValid = !text2.startsWith("0") && !text2.isEmpty() && !text1.isEmpty();
                positiveButton.setEnabled(isInputValid);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        editText1.addTextChangedListener(textWatcher);
        editText2.addTextChangedListener(textWatcher);
    }


}