package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class update_value extends AppCompatActivity {
    EditText transportation_update,food_update,bills_update,housing_update,misc_update;
    String  new_transpo,new_food,new_bills,new_housing,new_misc;

    Button update;
    String PlanID;
    int TransV,FoodV,BillsV,MiscV,HouseV,total;
    int IDplan  ;
    DBHelper DB;
    final String expense_name1 = "Transportation";
    final String expense_name2 = "Food";
    final String expense_name3 = "Bills";

    final String expense_name4 = "Housing";
    final String expense_name5 = "Miscellaneous";
    String login;
    String in;
    int come;




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

        Log.d("jet","Hello4");

        Intent i  = getIntent();
        PlanID = i.getStringExtra("PlanID");
        login = i.getStringExtra("Login");
        IDplan = Integer.parseInt(PlanID);
        in  = i.getStringExtra("Income");
        come = Integer.parseInt(in);

        Log.d("jet","Hello5");


        update.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                new_transpo = transportation_update.getText().toString();
                new_food = food_update.getText().toString();
                new_bills = bills_update.getText().toString();
                new_housing = housing_update.getText().toString();
                new_misc = misc_update.getText().toString();

                TransV = Integer.parseInt(new_transpo);
                FoodV = Integer.parseInt(new_food);
                BillsV = Integer.parseInt(new_bills);
                MiscV = Integer.parseInt(new_housing);
                HouseV = Integer.parseInt(new_misc);

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
}