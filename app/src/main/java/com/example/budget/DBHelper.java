package com.example.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public final Context context;
    public static final String BudgetingApp = "Login.db";

    public DBHelper(Context context) {

        super(context, "Login.db", null, 1);
        this.context = context;
    }

    @Override
        public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table AccountDetail (userid integer primary key autoincrement not null, email Text not null, password Text not null, username Text not null, address Text not null, contact_number Text not null)");
        MyDB.execSQL("create Table PlanDetail (userid integer, plan_id integer primary key autoincrement not null, plan_name Text not null, budget_amount decimal(11,2) not null, foreign key(userid) references AccountDetail(userid) on delete cascade)");
        MyDB.execSQL("create Table ExpensesDetail(plan_id integer, expenses_name Text not null, expenses_amount decimal(11,2) not null,foreign key(plan_id) references PlanDetail(plan_id) on delete cascade)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists AccountDetail");
        MyDB.execSQL("drop Table if exists PlanDetail");
        MyDB.execSQL("drop Table if exists ExpensesDetail");
        onCreate(MyDB);

    }

    public void insertAccountData(String email, String password, String username, String address, String contact_number){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("username", username);
        contentValues.put("address", address);
        contentValues.put("contact_number", contact_number);

        long result = MyDB.insert("AccountDetail", null, contentValues);
        if(result==-1)
            Toast.makeText(context, email + password + username + address + contact_number, Toast.LENGTH_SHORT).show();
    }

    public void insertPlanData(Integer userid, String plan_name, Float budget_amount){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("userID", userid);
        contentValues1.put("plan_name", plan_name);
        contentValues1.put("budget_amount", budget_amount);
        long result1 = MyDB.insert("PlanDetail", null, contentValues1);
        if(result1==-1)
            Toast.makeText(context, userid + plan_name + budget_amount, Toast.LENGTH_SHORT).show();

    }

    public void insertExpensesDetail(Integer plan_id, String expenses_name, Integer expenses_amount){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("plan_ID", plan_id);
        contentValues2.put("expenses_Name", expenses_name);
        contentValues2.put("expenses_amount", expenses_amount);
        long result2 = MyDB.insert("ExpensesDetail", null, contentValues2);
        if(result2==-1)
            Toast.makeText(context, plan_id + expenses_name + expenses_amount, Toast.LENGTH_SHORT).show();
    }

    public boolean Checkemail(String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from AccountDetail where email = ?", new String[] {email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean Checkaccount(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from AccountDetail where email = ? and password = ?", new String[] {email, password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    public String[] getUserDetailsByEmail(String email) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String[] userDetails = new String[5];

        Cursor cursor = MyDB.rawQuery("SELECT username, address, email, contact_number, userid FROM AccountDetail WHERE email = ?", new String[]{email});
        if (cursor != null && cursor.moveToFirst()) {
            userDetails[0] = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            userDetails[1] = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            userDetails[2] = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            userDetails[3] = cursor.getString(cursor.getColumnIndexOrThrow("contact_number"));
            userDetails[4] = cursor.getString(cursor.getColumnIndexOrThrow("userid"));
            cursor.close();
        }
        return userDetails;

    }
    public Integer[] getUserID(String login) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Integer[] userID = new Integer[1];
        Cursor cursor = MyDB.rawQuery("SELECT userid FROM AccountDetail WHERE email = ?", new String[]{login});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                userID[0] = cursor.getInt(cursor.getColumnIndexOrThrow("userid"));
            }
            cursor.close();
        }


        return userID;
    }



    public Integer getPlanID(String plan) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer plan_id = null;
        Cursor cursor = db.rawQuery("SELECT plan_id FROM PlanDetail WHERE plan_name = ?", new String[]{plan});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                plan_id = cursor.getInt(cursor.getColumnIndexOrThrow("plan_id"));
            }
            cursor.close();
        }

        return plan_id;
    }

    public ArrayList<String> getPlan_name(Integer userid) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        ArrayList<String> userPlans = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("SELECT plan_name FROM PlanDetail WHERE userid = ?", new String[]{String.valueOf(userid)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String planName = cursor.getString(cursor.getColumnIndexOrThrow("plan_name"));
                    userPlans.add(planName);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return userPlans;
    }

    public ArrayList<String> getIncome(Integer userid,String plan_name) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        ArrayList<String> income = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("SELECT budget_amount FROM PlanDetail WHERE userid = ? AND plan_name=?", new String[]{String.valueOf(userid),plan_name});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String budget_amount = cursor.getString(cursor.getColumnIndexOrThrow("budget_amount"));
                    income.add(budget_amount);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return income;
    }

    public ArrayList<String> getValue(Integer planid,String expense_name) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        ArrayList<String> Vtranspo = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("SELECT expenses_amount FROM ExpensesDetail WHERE plan_id = ? AND expenses_Name = ?", new String[]{String.valueOf(planid),expense_name});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    String budget_Amount = cursor.getString(cursor.getColumnIndexOrThrow("expenses_amount"));
                    Vtranspo.add(budget_Amount);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return Vtranspo;
    }





    public boolean doesExpenseNameExist(String expenses_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT plan_name FROM PlanDetail WHERE plan_name = ?";
        Cursor cursor = db.rawQuery(query, new String[]{expenses_name});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public void updateExpense(int planId, String expenseName, int newAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("expenses_amount", newAmount);

        int result = db.update("ExpensesDetail", contentValues, "plan_id = ? AND expenses_name = ?", new String[]{String.valueOf(planId), expenseName});

        if (result > 0) {
            Log.d("DBHelper", "Expense updated successfully!");
        } else {
            Log.d("DBHelper", "Failed to update expense.");
        }
    }

    public void deleteExpense(int planId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete("PlanDetail", "plan_id = ? ", new String[]{String.valueOf(planId)});
        db.delete("ExpensesDetail", "plan_id = ? ", new String[]{String.valueOf(planId)});

        if (result > 0) {
            Log.d("DBHelper", "Expense deleted successfully!");
        } else {
            Log.d("DBHelper", "Failed to delete expense.");
        }
    }

}
