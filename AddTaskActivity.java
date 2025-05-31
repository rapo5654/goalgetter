package com.rapo.goalgetter.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rapo.goalgetter.R;
import com.rapo.goalgetter.data.model.Task;
import com.rapo.goalgetter.ui.viewmodels.TaskViewModel;

public class AddTaskActivity extends AppCompatActivity { // Или TaskActivity

    private EditText editTextTitle, editTextDescription;
    private Button buttonSave;
    private TaskViewModel taskViewModel;
    private Task currentTask;
    private long userId;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task); // Убедитесь, что это правильный layout

        // Инициализация ViewModel
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Получение UI элементов
        editTextTitle = findViewById(R.id.etTitle);
        editTextDescription = findViewById(R.id.etDescription);
        buttonSave = findViewById(R.id.btnSave);

        // Получение ID пользователя
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        userId = prefs.getLong("user_id", -1L);

        // Проверяем, редактируем ли существующую задачу
        long taskId = getIntent().getLongExtra("taskId", -1L);
        if (taskId != -1L) {
            isEditing = true;
            loadTask(taskId);
        } else {
            currentTask = new Task();
            currentTask.setUserId(userId);
            currentTask.setCompleted(false);
        }

        buttonSave.setOnClickListener(v -> saveTask());
    }

    private void loadTask(long taskId) {
        taskViewModel.getTaskById(taskId).observe(this, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                if (task != null) {
                    currentTask = task;
                    editTextTitle.setText(task.getTitle());
                    editTextDescription.setText(task.getDescription());
                }
            }
        });
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty()) {
            editTextTitle.setError("Title cannot be empty");
            return;
        }

        currentTask.setTitle(title);
        currentTask.setDescription(description);

        if (isEditing) {
            taskViewModel.update(currentTask);
            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        } else {
            taskViewModel.insert(currentTask);
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
