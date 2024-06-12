package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class main_plan extends AppCompatActivity {

    TextView title;
    Intent any;

    String title_name,TransV,FoodV,BillsV,MiscV,HouseV,Income;
    TextView trans_bgt,food_bgt,bills_bgt,misc_bgt,house_bgt,income;

    float  transport_value,food_value,bills_value,misc_value,house_value,income_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_plan);

        title = findViewById(R.id.budget_title);

        any = getIntent();
        title_name= any.getStringExtra("Title");
        TransV= any.getStringExtra("TranV");
        FoodV= any.getStringExtra("FoodV");
        BillsV= any.getStringExtra("BillsV");
        HouseV= any.getStringExtra("HouseV");
        MiscV= any.getStringExtra("MiscV");
        Income= any.getStringExtra("Income");

        trans_bgt = findViewById(R.id.transport_budgt);
        food_bgt = findViewById(R.id.food_budgt);
        bills_bgt = findViewById(R.id.bills_budgt);
        house_bgt = findViewById(R.id.housing_budgt);
        misc_bgt = findViewById(R.id.miscellaneous_budgt);
        income = findViewById(R.id.total_budget);

        trans_bgt.append(TransV);
        food_bgt.append(FoodV);
        bills_bgt.append(BillsV);
        house_bgt.append(HouseV);
        misc_bgt.append(MiscV);
        income.append(Income);


        title.setText(title_name);
    }
}