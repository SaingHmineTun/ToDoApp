package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    int id;
    EditText editTextTitle,editTextDetail;
    TextView date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
       editTextTitle = findViewById(R.id.editTextTitle);
       editTextDetail = findViewById(R.id.editTextDetail);
       editTextDetail.setHint("Start typing");
        editTextTitle.setHint("Title");
        Toolbar toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        Drawable backArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24);
        backArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
        TaskDataBaseHelper taskDataBaseHelper = new TaskDataBaseHelper(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",0);
        TaskModel taskModel = taskDataBaseHelper.getTasksByID(id);
        date.setText(taskModel.getTaskDate());
        time.setText(taskModel.getTaskTime());
         editTextTitle.setText(taskModel.getTaskTitle());
        editTextDetail.setText(taskModel.getTaskDetail());
        Toast.makeText(getApplicationContext(),"id"+taskModel.getId(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_menu,menu);
        inflater.inflate(R.menu.save_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return  true;
        } if(item.getItemId() == R.id.delete){
            TaskDataBaseHelper db = new TaskDataBaseHelper(this);
            Intent intent = getIntent();
            id = intent.getIntExtra("ID",0);
            db.deleteNote(id);
            Toast.makeText(getApplicationContext(),"Task Deleted",Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent1);
        }
        if(item.getItemId() == R.id.save){

            String date = getCurrentDate();
            String time = getCurrentTime();
            String newTitle = editTextTitle.getText().toString();
            String newDetail = editTextDetail.getText().toString();
            TaskModel updatedTask = new TaskModel(id, newTitle, newDetail, date, time);
            if (!newTitle.isEmpty() && !newDetail.isEmpty()) {
                TaskDataBaseHelper db = new TaskDataBaseHelper(DetailActivity.this);
                db.updateTask(updatedTask);
                Toast.makeText(getApplicationContext(), "Task updated", Toast.LENGTH_SHORT).show();
                hideKeyboard();
               editTextTitle.clearFocus();
               editTextDetail.clearFocus();

            } else {
                Toast.makeText(getApplicationContext(), "Title and Detail cannot be empty", Toast.LENGTH_SHORT).show();
            }
            return  true;
        }
        return  super.onOptionsItemSelected(item);
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
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}