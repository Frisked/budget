package com.example.budget;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class main_plan extends AppCompatActivity {

    TextView title;
    Intent any;

    String title_name,TransV,FoodV,BillsV,MiscV,HouseV,Income;
    TextView trans_bgt,food_bgt,bills_bgt,misc_bgt,house_bgt,income;
    DBHelper DB;
    String login;
    Integer PlanID;
    float  transport_value,food_value,bills_value,misc_value,house_value,income_value;
    TextView edit,delete;
    Drawable icon_warn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_plan);
        icon_warn = ContextCompat.getDrawable(this, R.drawable.warning_red);

        title = findViewById(R.id.budget_title);
        edit = findViewById(R.id.edit_btn);
        delete = findViewById(R.id.delete_btn);


        any = getIntent();
        title_name= any.getStringExtra("Title");
        TransV= any.getStringExtra("TranV");
        FoodV= any.getStringExtra("FoodV");
        BillsV= any.getStringExtra("BillsV");
        HouseV= any.getStringExtra("HouseV");
        MiscV= any.getStringExtra("MiscV");
        Income= any.getStringExtra("Income");
        login= any.getStringExtra("Login");


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


        DB = new DBHelper(this);
         PlanID=DB.getPlanID(title_name);
        title.setText(title_name);
      //  DB.deleteExpense(PlanID);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_plan.this, update_value.class);
                intent.putExtra("PlanID",String.valueOf(PlanID));
                intent.putExtra("Login",login);
                intent.putExtra("Income",Income);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(main_plan.this)
                        .setTitle("Delete Plan")
                        .setMessage("Are you sure you want to delete this plan?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                Intent in = new Intent(main_plan.this,planning.class);
                                in.putExtra("Login",login);
                                DB.deleteExpense(PlanID);
                                Toast.makeText(main_plan.this, "Plan deleted successfully", Toast.LENGTH_SHORT).show();
                                startActivity(in);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .setIcon(icon_warn)
                        .show();


            }
        });
    }
}