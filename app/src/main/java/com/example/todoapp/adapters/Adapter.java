package com.example.todoapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.activities.DetailActivity;
import com.example.todoapp.R;
import com.example.todoapp.models.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>  {
    LayoutInflater inflater;
    List<TaskModel> taskModels;
    List<TaskModel> fullLists;
    public Adapter(Context context, List<TaskModel> taskModels){
        this.inflater = LayoutInflater.from(context);
        this.taskModels = taskModels;
        this.fullLists = new ArrayList<>(taskModels);
    }
    public void filterList(List<TaskModel> filteredList) {
        taskModels = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            String title = taskModels.get(position).getTaskTitle();
            String date = taskModels.get(position).getTaskDate();
            String time = taskModels.get(position).getTaskTime();
            holder.tTitle.setText(title);
            holder.tDate.setText(date);
            holder.tTime.setText(time);
    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }
            public  class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView tTitle,tDate,tTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           tTitle = itemView.findViewById(R.id.tTitle);
            tTime = itemView.findViewById(R.id.tTime);
            tDate = itemView.findViewById(R.id.tDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailActivity.class);
                    i.putExtra("ID",taskModels.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(i);
                }
            });
        }
    }



}
