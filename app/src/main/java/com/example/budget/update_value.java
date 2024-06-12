package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class update_value extends AppCompatActivity {
    EditText transportation_update,food_update,bills_update,housing_update,misc_update;
    String  new_transpo,new_food,new_bills,new_housing,new_misc;

    Button update;
    String PlanID;
    float TransV,FoodV,BillsV,MiscV,HouseV,total;
    int IDplan  ;
    DBHelper DB;
    final String expense_name1 = "Transportation";
    final String expense_name2 = "Food";
    final String expense_name3 = "Bills";

    final String expense_name4 = "Housing";
    final String expense_name5 = "Miscellaneous";
    String login;
    String in;
    float come;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_value);

        Log.d("jet","Hello3");
        transportation_update = findViewById(R.id.transpo_field);
        food_update = findViewById(R.id.food_field);
        bills_update  = findViewById(R.id.bills_field);
        housing_update = findViewById(R.id.housing_field);
        misc_update = findViewById(R.id.misc_field);
        update=  findViewById(R.id.update_expenses);
        DB = new DBHelper(this);


        Intent i  = getIntent();
        PlanID = i.getStringExtra("PlanID");
        login = i.getStringExtra("Login");
        IDplan = Integer.parseInt(PlanID);
        in  = i.getStringExtra("Income");
        come = Format(in);
        update.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text1 = transportation_update.getText().toString();
                String text2 = food_update.getText().toString();
                String text3 = bills_update.getText().toString();
                String text4 = housing_update.getText().toString();
                String text5 = misc_update.getText().toString();
                boolean isInputValid = !text1.startsWith("0") && !text1.isEmpty()
                        && !text2.startsWith("0") && !text2.isEmpty()
                        && !text3.startsWith("0") && !text3.isEmpty()
                        && !text4.startsWith("0") && !text4.isEmpty()
                        && !text5.startsWith("0") && !text5.isEmpty()  ;

                update.setEnabled(isInputValid);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        transportation_update.addTextChangedListener(textWatcher);
        food_update.addTextChangedListener(textWatcher);
        bills_update.addTextChangedListener(textWatcher);
        housing_update.addTextChangedListener(textWatcher);
        misc_update.addTextChangedListener(textWatcher);

        update.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                new_transpo = transportation_update.getText().toString();
                new_food = food_update.getText().toString();
                new_bills = bills_update.getText().toString();
                new_housing = housing_update.getText().toString();
                new_misc = misc_update.getText().toString();

                TransV = Format(new_transpo);
                FoodV = Format(new_food);
                BillsV = Format(new_bills);
                MiscV = Format(new_misc);
                HouseV = Format(new_housing);

                total = TransV+FoodV+BillsV+MiscV+HouseV;

                if (come-total <= -1) {
                    Toast.makeText(update_value.this, "Insufficient Income", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(update_value.this, "Update Success", Toast.LENGTH_SHORT).show();
                    DB.updateExpense(IDplan,expense_name1,TransV);
                    DB.updateExpense(IDplan,expense_name2,FoodV);
                    DB.updateExpense(IDplan,expense_name3,BillsV);
                    DB.updateExpense(IDplan,expense_name4,MiscV);
                    DB.updateExpense(IDplan,expense_name5,HouseV);

                    Intent i = new Intent(update_value.this,planning.class);
                    i.putExtra("Login",login);
                    startActivity(i);
                    finish();
                }

            }
        });



    }

    public static float Format(String numberString) {
        double number = Double.parseDouble(numberString);
        DecimalFormat df = new DecimalFormat("#.00");
        return Float.parseFloat(df.format(number));
    }
}