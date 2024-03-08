package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    EditText title,details;
    Button addTaskBtn;
    String toDayDate,currentTime;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        title = findViewById(R.id.noteTitle);
        details = findViewById(R.id.noteDetail);
        addTaskBtn = findViewById(R.id.addBtn);
        calendar = Calendar.getInstance();
        toDayDate = calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
        currentTime = pad(calendar.get(Calendar.HOUR))+":"+pad(calendar.get(Calendar.MINUTE));
        Log.d("Calender","Date and Time"+toDayDate +" and "+ currentTime);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskModel taskModel = new TaskModel(title.getText().toString(),details.getText().toString(),toDayDate,currentTime);
                TaskDataBaseHelper db = new TaskDataBaseHelper(AddTaskActivity.this);
                db.addTasks(taskModel);
                Intent intent = new Intent(AddTaskActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Task Saved",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String pad(int i) {
        if(i<0)
            return  "0"+1;
        return  String.valueOf(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return  true;
        }
        return  super.onOptionsItemSelected(item);
    }
}