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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class add_expenses extends AppCompatActivity {

    float total,income,transportation,food,bills,housing,misc;
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
    int plan_id,userid;
    String id;


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
        id = intent.getStringExtra("ID");
        userid = Integer.parseInt(id);




        DB = new DBHelper(this);

                        add_exp.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text1 = Itranspo.getText().toString();
                String text2 = Ifood.getText().toString();
                String text3 = Ibills.getText().toString();
                String text4 = Ihousing.getText().toString();
                String text5 = Imisc.getText().toString();
                boolean isInputValid =  !text1.isEmpty()
                 && !text2.isEmpty()
                 && !text3.isEmpty()
                 && !text4.isEmpty()
                 && !text5.isEmpty()  ;

                add_exp.setEnabled(isInputValid);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        Itranspo.addTextChangedListener(textWatcher);
        Ifood.addTextChangedListener(textWatcher);
        Ibills.addTextChangedListener(textWatcher);
        Ihousing.addTextChangedListener(textWatcher);
        Imisc.addTextChangedListener(textWatcher);


        add_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("ako","ano3");

                transportation = Format(Itranspo.getText().toString());
                food = Format(Ifood.getText().toString());
                bills = Format(Ibills.getText().toString());
                housing = Format(Ihousing.getText().toString());
                misc = Format(Imisc.getText().toString());
                Log.d("ako","ano4");

                income = Format(initial_income);
                total = transportation+food+bills+housing+misc;
                Log.d("ako","ano7");
                String tota1= String.valueOf(total);
                Log.d("ako","ano8");
                float total2= Format(tota1);
                Log.d("ako",String.valueOf(misc));


                if (income-total2 <= -1) {
                    Toast.makeText(add_expenses.this, "Insufficient Income", Toast.LENGTH_SHORT).show();
                } else {
                    DB.insertPlanData(userid, plan_title, income);
                    plan_id = DB.getPlanID(plan_title);
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



    public static float Format(String numberString) {
        double number = Double.parseDouble(numberString);
        DecimalFormat df = new DecimalFormat("#.00");
        return Float.parseFloat(df.format(number));
    }

}