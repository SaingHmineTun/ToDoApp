package com.apphyo.todoapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.apphyo.todoapp.R;
import com.apphyo.todoapp.models.TaskModel;
import com.apphyo.todoapp.utilities.TaskDataBaseHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    EditText title, details;
    Button addTaskBtn;
    String toDayDate, currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = findViewById(R.id.toolbarAddTask);
        Drawable backArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24);
        backArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);

        title = findViewById(R.id.noteTitle);
        details = findViewById(R.id.noteDetail);
        addTaskBtn = findViewById(R.id.addBtn);
        toDayDate = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.ENGLISH).format(LocalDate.now());
        currentTime = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH).format(LocalTime.now());
        addTaskBtn.setOnClickListener(v -> {
            TaskModel taskModel = new TaskModel(title.getText().toString(), details.getText().toString(), toDayDate, currentTime);
            TaskDataBaseHelper db = new TaskDataBaseHelper(AddTaskActivity.this);
            db.addTasks(taskModel);
            Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Task Saved", Toast.LENGTH_SHORT).show();
        });
    }

    private String pad(int i) {
        if (i < 0)
            return "0" + 1;
        return String.valueOf(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}