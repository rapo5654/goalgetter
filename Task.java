package com.rapo.goalgetter.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String title;

    private String description;

    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;

    @ColumnInfo(name = "due_date")
    private String dueDate;

    private int priority;

    private int color;

    @ColumnInfo(name = "created_at")
    private long createdAt;

    @ColumnInfo(name = "user_id")
    private long userId;

    // Конструктор
    public Task(@NonNull String title,
                String description,
                boolean isCompleted,
                String dueDate,
                int priority,
                int color,
                long createdAt,
                long userId) {
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.dueDate = dueDate;
        this.priority = priority;
        this.color = color;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    public String getTitle() { return title; }
    public void setTitle(@NonNull String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
}