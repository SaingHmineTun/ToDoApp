package com.example.todoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.adapters.Adapter;
import com.example.todoapp.R;
import com.example.todoapp.models.TaskModel;
import com.example.todoapp.utilities.TaskDataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return timeFormat.format(new Date());
    }
}