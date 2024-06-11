package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_expenses extends AppCompatActivity {

    int total,income,transportation,food,bills,housing,misc;
    EditText Itranspo,Ifood,Ibills,Ihousing,Imisc;
    Button add_exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        Itranspo = findViewById(R.id.transpo_field);
        Ifood = findViewById(R.id.food_field);
        Ibills =  findViewById(R.id.bills_field);
        Ihousing = findViewById(R.id.housing_field);
        Imisc = findViewById(R.id.misc_field);
        add_exp = findViewById(R.id.add_expenses);

        transportation = Integer.parseInt(Itranspo.getText().toString());
        food = Integer.parseInt(Ifood.getText().toString());
        bills = Integer.parseInt(Ibills.getText().toString());
        housing = Integer.parseInt(Ihousing.getText().toString());
        misc = Integer.parseInt(Imisc.getText().toString());

        Intent intent = getIntent();
        income = Integer.parseInt(intent.getStringExtra("Income"));



        total = transportation+food+bills+housing+misc;

        add_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (income-total <= -1) {
                    Toast.makeText(add_expenses.this, "Insufficient Income", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });




    }
}