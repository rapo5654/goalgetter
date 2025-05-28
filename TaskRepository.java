// app/src/main/java/com/yourdomain/goalgetter/data/repository/TaskRepository.java
package com.rapo.goalgetter.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.rapo.goalgetter.data.database.AppDatabase;
import com.rapo.goalgetter.data.dao.TaskDao;
import com.rapo.goalgetter.data.model.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private final TaskDao taskDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        taskDao = db.taskDao();
    }

    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
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

    public void deleteAll() {
        executor.execute(taskDao::deleteAll);
    }
}
