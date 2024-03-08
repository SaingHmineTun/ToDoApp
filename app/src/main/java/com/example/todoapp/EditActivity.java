package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    EditText editTitle, editDetails;
    Button updateBtn;
    Adapter adapter;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editTitle = findViewById(R.id.editTitle);
        editDetails = findViewById(R.id.editDetail);
        updateBtn = findViewById(R.id.updateBtn);

        TaskDataBaseHelper taskDataBaseHelper = new TaskDataBaseHelper(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        TaskModel taskModel = taskDataBaseHelper.getTasksByID(id);
        editTitle.setText(taskModel.getTaskTitle());
        editDetails.setText(taskModel.getTaskDetail());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = editTitle.getText().toString();
                String newDetails = editDetails.getText().toString();
                String date = getCurrentDate();
                String time = getCurrentTime();
                TaskModel updatedTask = new TaskModel(id, newTitle, newDetails, date, time); // You may need to adjust this constructor
                taskDataBaseHelper.updateTask(updatedTask);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = dateFormat.format(new Date());
        return formattedDate;
    }

    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String formattedTime = timeFormat.format(new Date());
        return formattedTime;
    }
}