package com.rapo.goalgetter.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private long taskId;
    private String title;
    private String description;
    private String date;
    private int priority;
    private int color;
    private boolean completed;
    private long userId; // Изменяем с int на long

    // Геттеры и сеттеры
    public long getTaskId() { return taskId; }
    public void setTaskId(long taskId) { this.taskId = taskId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public long getUserId() { return userId; } // long вместо int
    public void setUserId(long userId) { this.userId = userId; } // long вместо int
}
