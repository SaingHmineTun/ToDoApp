package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    TextView showTitle,showDetail;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        showTitle = findViewById(R.id.showTitle);
        showDetail = findViewById(R.id.showDetail);
        TaskDataBaseHelper taskDataBaseHelper = new TaskDataBaseHelper(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",0);
        TaskModel taskModel = taskDataBaseHelper.getTasksByID(id);
        showTitle.setText(taskModel.getTaskTitle());
        showDetail.setText(taskModel.getTaskDetail());
        Toast.makeText(getApplicationContext(),"id"+taskModel.getId(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_menu,menu);
        inflater.inflate(R.menu.edit_menu,menu);
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
        if(item.getItemId() == R.id.edit){
            Intent intent = new Intent(DetailActivity.this,EditActivity.class);
            intent.putExtra("ID",id);
            startActivity(intent);
            return  true;
        }
        return  super.onOptionsItemSelected(item);
    }
}