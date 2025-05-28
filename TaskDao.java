package com.rapo.goalgetter.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rapo.goalgetter.data.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM tasks")
    void deleteAll();

    @Query("SELECT * FROM tasks ORDER BY created_at DESC") // ← Исправлено!
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE is_completed = 0")
    LiveData<List<Task>> getActiveTasks();

    @Query("SELECT * FROM tasks WHERE title LIKE :searchQuery")
    LiveData<List<Task>> searchTasks(String searchQuery);
}