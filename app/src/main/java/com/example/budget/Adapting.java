package com.example.budget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapting extends RecyclerView.Adapter<Adapting.MyViewHolder> {
    Context context;
    DBHelper DB;
    ArrayList<String> title,TranV,FoodV,BillsV,MiscV,HouseV,Income;
    int userid;
    String login;

    Adapting(Context context,ArrayList title,int userid,String login) {
        DB = new DBHelper(context);
        this.context = context;
        this.title = title;
        this.userid=userid;
        this.login = login;
    }
    @NonNull
    @Override
    public Adapting.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapting_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapting.MyViewHolder holder, int position) {


        holder.title.setText(String.valueOf(title.get(position)));




        holder.lay_out.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                int planId = DB.getPlanID(title.get(position));
                TranV = DB.getValue(planId,"Transportation");
                FoodV = DB.getValue(planId,"Food");
                BillsV = DB.getValue(planId,"Bills");
                HouseV = DB.getValue(planId,"Housing");
                MiscV = DB.getValue(planId,"Miscellaneous");
                Income = DB.getIncome(userid,String.valueOf(title.get(position)));

                Intent i = new Intent(context,main_plan.class);
                i.putExtra("Title",String.valueOf(title.get(position)));
                i.putExtra("TranV",String.valueOf(TranV.get(0)));
                i.putExtra("FoodV",String.valueOf(FoodV.get(0)));
                i.putExtra("BillsV",String.valueOf(BillsV.get(0)));
                i.putExtra("HouseV",String.valueOf(HouseV.get(0)));
                i.putExtra("MiscV",String.valueOf(MiscV.get(0)));
                i.putExtra("Income",String.valueOf(Income.get(0)));
                i.putExtra("Login",login);


                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        LinearLayout lay_out;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_budget);
            lay_out = itemView.findViewById(R.id.adapt_linear);
        }
    }
}
