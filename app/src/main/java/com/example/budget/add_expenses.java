package com.example.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_expenses extends AppCompatActivity {

    int total,income,transportation,food,bills,housing,misc;
    EditText Itranspo,Ifood,Ibills,Ihousing,Imisc;
    final String expense_name1 = "Transportation";
    final String expense_name2 = "Food";
    final String expense_name3 = "Bills";

    final String expense_name4 = "Housing";
    final String expense_name5 = "Miscellaneous";
    Button add_exp;
    DBHelper DB;
    Integer[] getPlanID ;
    String plan_title,initial_income,Login;
    int plan_id;


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


        Intent intent = getIntent();
        plan_title=intent.getStringExtra("budget_title");
        initial_income = intent.getStringExtra("income");
        Login = intent.getStringExtra("Login");



        DB = new DBHelper(this);

        plan_id = DB.getPlanID(plan_title);


        add_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                transportation = Integer.parseInt(Itranspo.getText().toString());
                food = Integer.parseInt(Ifood.getText().toString());
                bills = Integer.parseInt(Ibills.getText().toString());
                housing = Integer.parseInt(Ihousing.getText().toString());
                misc = Integer.parseInt(Imisc.getText().toString());

                income = Integer.parseInt(initial_income);
                total = transportation+food+bills+housing+misc;

                if (income-total <= -1) {
                    Toast.makeText(add_expenses.this, "Insufficient Income", Toast.LENGTH_SHORT).show();
                } else {
                    DB.insertExpensesDetail(plan_id,expense_name1,transportation);
                    DB.insertExpensesDetail(plan_id,expense_name2,food);
                    DB.insertExpensesDetail(plan_id,expense_name3,bills);
                    DB.insertExpensesDetail(plan_id,expense_name4,housing);
                    DB.insertExpensesDetail(plan_id,expense_name5,misc);

                    Intent i = new Intent(add_expenses.this,planning.class);
                    i.putExtra("Login",Login);
                    startActivity(i);
                    finish();
                }
            }
        });




    }
}