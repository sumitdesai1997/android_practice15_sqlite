package com.example.android_practice15_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android_practice15_sqlite.util.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etName, etSalary;
    Spinner spinnerDept;

    public static final String DATABASE_NAME = "employee_database";

    // 1st step: declare an instance of SQLite database
    //SQLiteDatabase sqLiteDatabase;

    // This commit is for using SQLiteOpenHelper instead of SQLiteDatabase, so we will comment code that is used for SQLiteDatabase and write code for SQLiteOpenHelper

    // 1st step: Create Database helper class that extends SQLiteOpenHelper, and all the methods that can be used to perform the database operation

    // 2nd step: declare an instance of DatabaseHelper
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        etSalary = findViewById(R.id.et_salary);
        spinnerDept = findViewById(R.id.spinner_dept);

        findViewById(R.id.btn_add_employee).setOnClickListener(this);
        findViewById(R.id.tv_display_employees).setOnClickListener(this);

        // 2nd step: create database and create table
        /*sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createTable();*/

        // 3rd step: perform CRUD operations in project as per project requirement

        // 3nd step: initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        //4th step: perform CRUD operations using  database helper class methods in project as per project requirement
    }

    /*private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employee (" +
                "id INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(20) NOT NULL, " +
                "department VARCHAR(20) NOT NULL, " +
                "joining_date DATETIME NOT NULL, " +
                "salary DOUBLE NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_employee:
                addEmployee();
                break;
            case R.id.tv_display_employees:
                // navigate to Employees activity
                startActivity(new Intent(this, EmployeeActivity.class));
                break;
        }
    }

    private void addEmployee() {
        String name = etName.getText().toString().trim();
        String salary = etSalary.getText().toString().trim();
        String department = spinnerDept.getSelectedItem().toString();

        // getting the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(calendar.getTime());

        if (name.isEmpty()) {
            etName.setError("name field cannot be empty");
            etName.requestFocus();
            return;
        }

        if (salary.isEmpty()) {
            etName.setError("salary field cannot be empty");
            etName.requestFocus();
            return;
        }

        /*String sql = "INSERT INTO employee (name, department, joining_date, salary) " +
                "VALUES (?, ?, ?, ?);";
        sqLiteDatabase.execSQL(sql, new String[]{name, department, joiningDate, salary});*/

        if(databaseHelper.addEmployee(name, department, joiningDate, Double.parseDouble(salary))) {
            Toast.makeText(MainActivity.this, "Employee (" + name + ") is added", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(MainActivity.this, "Employee is not added", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(MainActivity.this, "Employee (" + name + ") is added", Toast.LENGTH_SHORT).show();
        Log.d("TAG", "addEmployee: ");

        clearFields();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        clearFields();
    }

    private void clearFields() {
        etName.setText("");
        etSalary.setText("");
        spinnerDept.setSelection(0);
        etName.clearFocus();
        etSalary.clearFocus();
    }
}