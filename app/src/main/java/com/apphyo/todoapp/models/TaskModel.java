package com.apphyo.todoapp.models;

public class TaskModel {
    int id;
    private String taskTitle,taskDetail,taskDate,taskTime;
    public TaskModel(){

    }

    public TaskModel(String taskTitle, String taskDetail, String taskDate, String taskTime) {
        this.taskTitle = taskTitle;
        this.taskDetail = taskDetail;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
    }
    public TaskModel(int id,String taskTitle, String taskDetail, String taskDate, String taskTime) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDetail = taskDetail;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}
