package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText firstName, lastName, marks, id;
    TextView results;
    DatabaseHelper studentDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = (EditText) findViewById(R.id.firstname);
        lastName = (EditText) findViewById(R.id.lastname);
        marks = (EditText) findViewById(R.id.marks);
        id = (EditText) findViewById(R.id.ed_id);
        results = (TextView) findViewById(R.id.tv_results);
        studentDb = new DatabaseHelper(this);

    }

    public void addRecord(View view) {
        boolean isInserted = studentDb.insertData(firstName.getText().toString(),
                lastName.getText().toString(), marks.getText().toString());
        if (isInserted)
            results.setText("A new record is created.");
        else
            results.setText("Data cannot be inserted.");
    }

    public void viewAllRecords(View view) {
        Cursor res = studentDb.getAllData();
        if (res.getCount() == 0) {
            results.setText("No record in the Student Database.");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("First Name :"+ res.getString(1)+"\n");
            buffer.append("Last Name :"+ res.getString(2)+"\n");
            buffer.append("Marks :"+ res.getString(3)+"\n\n");
        }
        results.setText(buffer.toString());
    }

    public void updateRecord(View view) {
        boolean isUpdate = studentDb.updateData(id.getText().toString(),
                                                firstName.getText().toString(),
                                                lastName.getText().toString(),
                                                marks.getText().toString());
        if (isUpdate)
            results.setText("The record is updated.");
        else
            results.setText("The record cannot be updated.");
    }

    public void deleteRecord(View view) {
        Integer deletedRows = studentDb.deleteData(id.getText().toString());
        if (deletedRows > 0)
            results.setText("The record is deleted.");
        else
            results.setText("The record cannot be deleted.");
    }

    public void clearText(View view) {
        firstName.setText("");
        lastName.setText("");
        marks.setText("");
        id.setText("");
    }
}