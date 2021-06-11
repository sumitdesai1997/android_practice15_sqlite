package com.example.android_practice15_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.android_practice15_sqlite.model.Employee;
import com.example.android_practice15_sqlite.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {

    //SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;

    List<Employee> employeeList;
    ListView employeeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        employeeListView = findViewById(R.id.lv_employees);
        employeeList = new ArrayList<>();

        //sqLiteDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);

        databaseHelper = new DatabaseHelper(this);
        loadEmployees();
    }

    private void loadEmployees() {
        /*String sql = "SELECT * FROM employee";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);*/

        Cursor cursor = databaseHelper.getAllEmployees();
        if (cursor.moveToFirst()) {
            do {
                // create an employee instance
                employeeList.add(new Employee(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }

        // create an adapter to display the employees
        /*EmployeeAdapter employeeAdapter = new EmployeeAdapter(this,
                R.layout.list_layout_employee,
                employeeList, sqLiteDatabase);*/

        EmployeeAdapter employeeAdapter = new EmployeeAdapter(this,
                R.layout.list_layout_employee,
                employeeList, databaseHelper);
        employeeListView.setAdapter(employeeAdapter);

    }
}