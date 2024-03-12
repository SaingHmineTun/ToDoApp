package com.apphyo.todoapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.apphyo.todoapp.adapters.Adapter;
import com.apphyo.todoapp.R;
import com.apphyo.todoapp.models.TaskModel;
import com.apphyo.todoapp.utilities.TaskDataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


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
        myCalendar = Calendar.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.floatingActionButton);
        TaskDataBaseHelper taskDataBaseHelper = new TaskDataBaseHelper(this);
        taskModels = taskDataBaseHelper.getTasks();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Adapter(this, taskModels);
        recyclerView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(i);
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


    private void filterByDate(String dateToFilter) {

        List<TaskModel> filteredList = new ArrayList<>();
        for (TaskModel task : taskModels) {
            if (task.getTaskDate().equals(dateToFilter)) {
                filteredList.add(task);
            }
        }
        adapter.filterList(filteredList);
    }


    private Calendar myCalendar;

    private DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.ENGLISH);
        String dateToFilter = LocalDate.of(year, monthOfYear + 1, dayOfMonth).format(dateFormatter);
        filterByDate(dateToFilter);

        /*
        Filter List Here!!!
         */
    };

    final View.OnClickListener dateClickListener = v -> {
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.calendar) {
            dateClickListener.onClick(item.getActionView());
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
