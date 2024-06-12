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
import android.widget.ProgressBar;
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
    Drawable bg_dialog;
    ProgressBar transport_bar,food_bar,bills_bar,house_bar,misc_bar;
    TextView total_spent,left_spend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_plan);
        icon_warn = ContextCompat.getDrawable(this, R.drawable.warning_red);

        title = findViewById(R.id.budget_title);
        edit = findViewById(R.id.edit_btn);
        delete = findViewById(R.id.delete_btn);
        bg_dialog = ContextCompat.getDrawable(this,R.drawable.circle);


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

        transport_bar = findViewById(R.id.transport_progressBar);
        food_bar = findViewById(R.id.food_progressBar);
        bills_bar = findViewById(R.id.bills_progressBar);
        misc_bar = findViewById(R.id.miscellaneous_progressBar);
        house_bar = findViewById(R.id.housing_progressBar);

        total_spent = findViewById(R.id.total_spent);
        left_spend = findViewById(R.id.left_budget);

        Log.d("vug","hel11");


        trans_bgt.append(format(TransV));
        food_bgt.append(format(FoodV));
        bills_bgt.append(format(BillsV));
        house_bgt.append(format(HouseV));
        misc_bgt.append(format(MiscV));
        income.append(format(Income));
        Log.d("vug","hel1");
        Log.d("vug",TransV);

        int total = Math.round(Float.parseFloat(TransV))+
                Math.round(Float.parseFloat(FoodV))+
                Math.round(Float.parseFloat(BillsV))+
                Math.round(Float.parseFloat(HouseV))+
                Math.round(Float.parseFloat(MiscV));

        float total2 = Float.parseFloat(TransV) +
                Float.parseFloat(FoodV) +
                Float.parseFloat(BillsV) +
                Float.parseFloat(HouseV) +
                Float.parseFloat(MiscV);

        Float i = Float.parseFloat(Income);

        total_spent.append(format(String.valueOf(total2)));
        left_spend.append(format(String.valueOf(i-total2)));
        Log.d("vug","hel2");
        Log.d("vug",String.valueOf(total));
        transport_bar.setMax(total);
        food_bar.setMax(total);
        bills_bar.setMax(total);
        house_bar.setMax(total);
        misc_bar.setMax(total);
        Log.d("vug","hel3");

        transport_bar.setProgress(Math.round(Float.parseFloat(TransV)));
        food_bar.setProgress(Math.round(Float.parseFloat(FoodV)));
        bills_bar.setProgress( Math.round(Float.parseFloat(BillsV)));
        house_bar.setProgress( Math.round(Float.parseFloat(HouseV)));
        misc_bar.setProgress(Math.round(Float.parseFloat(MiscV)));




        DB = new DBHelper(this);
         PlanID=DB.getPlanID(title_name);
        title.setText(title_name);
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(main_plan.this);
                        dialog.setTitle("Delete Plan");
                        dialog.setMessage("Are you sure you want to delete this plan?");
                        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

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

    public static String format(String numberString) {
        double number = Double.parseDouble(numberString);
        String formattedNumber = String.format("%.2f", number);
        return formattedNumber;
    }


}