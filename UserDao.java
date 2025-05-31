package com.rapo.goalgetter.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rapo.goalgetter.data.model.Task;
import com.rapo.goalgetter.data.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private final TaskRepository repository;
    private LiveData<List<Task>> tasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    // Изменяем параметр на long
    public LiveData<List<Task>> getTasksByUserId(long userId) {
        if (tasks == null) {
            tasks = repository.getTasksByUserId(userId);
        }
        return tasks;
    }

    public LiveData<Task> getTaskById(long taskId) {
        return repository.getTaskById(taskId);
    }
//    // Изменяем параметр на long
//    public void refreshTasks(long userId) {
//        tasks = repository.getTasksByUserId(userId);
//    }

    public void refreshTasks(long userId) {
        repository.refreshTasks(userId);
    }

    public void insert(Task task) {
        repository.insert(task);
    }

    public void update(Task task) {
        repository.update(task);
    }

    public void delete(Task task) {
        repository.delete(task);
    }
}
