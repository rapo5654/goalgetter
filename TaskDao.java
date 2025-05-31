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

    // Исправлено: используем существующие колонки
    @Query("SELECT * FROM tasks ORDER BY date DESC")
    LiveData<List<Task>> getAllTasks();

    // Исправлено: используем правильное имя колонки completed
    @Query("SELECT * FROM tasks WHERE completed = 0")
    LiveData<List<Task>> getActiveTasks();

    @Query("SELECT * FROM tasks WHERE title LIKE :searchQuery")
    LiveData<List<Task>> searchTasks(String searchQuery);

    // Добавляем метод для получения задачи по ID
    @Query("SELECT * FROM tasks WHERE taskId = :taskId")
    LiveData<Task> getTaskById(long taskId);

    // Добавляем метод для получения задач пользователя
    @Query("SELECT * FROM tasks WHERE userId = :userId")
    LiveData<List<Task>> getTasksByUserId(long userId);
}
