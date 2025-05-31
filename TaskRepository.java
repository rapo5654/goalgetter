package com.rapo.goalgetter.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rapo.goalgetter.data.dao.TaskDao;
import com.rapo.goalgetter.data.database.AppDatabase;
import com.rapo.goalgetter.data.model.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao taskDao;
    private final ExecutorService executor;

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDao = db.taskDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Task>> getTasksByUserId(long userId) {
        return taskDao.getTasksByUserId(userId);
    }

    public LiveData<Task> getTaskById(long taskId) {
        return taskDao.getTaskById(taskId);
    } // Добавлена закрывающая скобка

    public void refreshTasks(long userId) {
        // Обновление происходит автоматически через LiveData
    }

    public void insert(Task task) {
        executor.execute(() -> taskDao.insert(task));
    }

    public void update(Task task) {
        executor.execute(() -> taskDao.update(task));
    }

    public void delete(Task task) {
        executor.execute(() -> taskDao.delete(task));
    }
}
