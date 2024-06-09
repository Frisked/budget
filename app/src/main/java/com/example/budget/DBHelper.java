package com.example.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String BudgetigApp = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table AccountDetail (userid integer primary key autoincrement not null, email Text not null, password Text not null, username Text not null, address Text not null, contact_number Text not null)");
        MyDB.execSQL("create Table PlanDetail (userid integer, plan_id integer primary key autoincrement not null, plan_name Text not null, budget_amount integer not null, foreign key(userid) references AccountDetail(userid))");
        MyDB.execSQL("create Table ExpensesDetail(plan_id integer, expenses_name Text not null, expenses_amount integer not null,foreign key(plan_id) references PlanDetail(plan_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists AccountDetail");
    }
    public boolean insertAccountData(Integer userid, String email, String password, String username, String address, String contact_number){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserID", userid);
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        contentValues.put("Username", username);
        contentValues.put("Address", address);
        contentValues.put("Contact_number", contact_number);
        long result = MyDB.insert("AccountDetail", null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean insertPlanData(Integer userid, Integer plan_id, String plan_name, Integer budget_amount){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("UserID", userid);
        contentValues1.put("Plan_ID", plan_id);
        contentValues1.put("Plan_name", plan_name);
        contentValues1.put("Budget_Amount", budget_amount);
        long result1 = MyDB.insert("PlanDetail", null, contentValues1);
        if(result1==-1)
            return false;
        else
            return true;

    }

    public boolean insertExpensesDetail(Integer plan_id, String expenses_name, Integer expenses_amount){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("Plant_ID", plan_id);
        contentValues2.put("Expenses_Name", expenses_name);
        contentValues2.put("Expenses_amount", expenses_amount);
        long result2 = MyDB.insert("ExpensesDetail", null, contentValues2);
        if(result2==-1)
            return false;
        else
            return true;
    }

    public boolean Checkemail(String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from AccountDetail where email = ?", new String[] {email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkaccount(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from AccountDetail where email = ? and password = ?", new String[] {email, password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}

