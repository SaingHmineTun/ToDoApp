package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.provider.FontsContractCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    List<TaskModel> taskModels;
    FloatingActionButton fab;
    TaskDataBaseHelper taskDataBaseHelper = new TaskDataBaseHelper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.floatingActionButton);
        TaskDataBaseHelper taskDataBaseHelper = new TaskDataBaseHelper(this);
        taskModels = taskDataBaseHelper.getTasks();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Adapter(this,taskModels);
        recyclerView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_menu, menu);
    inflater.inflate(R.menu.calendar_menu, menu);
    MenuItem searchItem = menu.findItem(R.id.search);
    SearchView searchView = (SearchView) searchItem.getActionView();
    EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
    ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);

    searchEditText.setTextColor(Color.WHITE);
        if (closeButton != null) {
            // Apply color filter
            closeButton.setColorFilter(Color.WHITE);
        } else {
            Log.e("CloseButton", "Close button not found");
        }

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            filter(newText);
            return true;
        }
    });
    return true;
}

    private void filter(String text) {
        List<TaskModel> filteredList = new ArrayList<>();
        for (TaskModel task : taskModels) {
            if (task.getTaskTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(task);
            }
        }
        adapter.filterList(filteredList);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.calendar){
            Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    protected void onResume() {
        super.onResume();
        taskModels.clear();
        taskModels.addAll(taskDataBaseHelper.getTasks());
        adapter.notifyDataSetChanged();
    }
}
