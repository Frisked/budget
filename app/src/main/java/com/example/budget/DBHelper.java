package com.example.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
        MyDB.execSQL("create Table PlanDetail (userid integer, plan_id integer primary key autoincrement not null, plan_name Text not null, budget_amount integer not null, foreign key(userid) references AccountDetail(userid) on delete cascade)");
        MyDB.execSQL("create Table ExpensesDetail(plan_id integer, expenses_name Text not null, expenses_amount integer not null,foreign key(plan_id) references PlanDetail(plan_id) on delete cascade)");
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

    public void insertPlanData(Integer userid, Integer plan_id, String plan_name, Integer budget_amount){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("userID", userid);
        contentValues1.put("plan_ID", plan_id);
        contentValues1.put("plan_name", plan_name);
        contentValues1.put("budget_Amount", budget_amount);
        long result1 = MyDB.insert("PlanDetail", null, contentValues1);
        if(result1==-1)
            Toast.makeText(context, userid + plan_id + plan_name + budget_amount, Toast.LENGTH_SHORT).show();

    }

    public void insertExpensesDetail(Integer plan_id, String expenses_name, Integer expenses_amount){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("plant_ID", plan_id);
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
        SQLiteDatabase db = this.getReadableDatabase();
        String[] userDetails = new String[5];

        Cursor cursor = db.rawQuery("SELECT username, address, email, contact_number, userid FROM AccountDetail WHERE email = ?", new String[]{email});
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

}


