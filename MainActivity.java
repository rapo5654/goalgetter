package com.rapo.goalgetter.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rapo.goalgetter.R;
import com.rapo.goalgetter.data.model.Task;
import com.rapo.goalgetter.ui.adapters.TaskAdapter;
import com.rapo.goalgetter.ui.viewmodels.TaskViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private TaskAdapter adapter;
    private TaskViewModel taskViewModel;
    private long userId;
    private Toolbar toolbar;
    private TextView textTotalTasks;
    private TextView textCompletedTasks;
    private FloatingActionButton fabAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // Получение ID пользователя
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        userId = prefs.getLong("user_id", -1L);

        // Инициализация UI элементов
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        textTotalTasks = findViewById(R.id.textTotalTasks);
        textCompletedTasks = findViewById(R.id.textCompletedTasks);
        fabAddTask = findViewById(R.id.fabAddTask);

        // Настройка RecyclerView
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter();
        recyclerViewTasks.setAdapter(adapter);

        // Инициализация ViewModel
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getTasksByUserId(userId).observe(this, tasks -> {
            adapter.setTasks(tasks);
            updateStatistics(tasks);
        });

        // Обработчик нажатия на кнопку добавления
        fabAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        // Обработчик клика по задаче
        adapter.setOnItemClickListener(task -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            intent.putExtra("taskId", task.getTaskId());
            startActivity(intent);
        });
    }

    private void updateStatistics(List<Task> tasks) {
        int total = tasks.size();
        int completed = 0;
        for (Task task : tasks) {
            if (task.isCompleted()) completed++;
        }
        textTotalTasks.setText("Total: " + total);
        textCompletedTasks.setText("Completed: " + completed);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Обновляем задачи при возвращении на экран
        if (taskViewModel != null) {
            taskViewModel.refreshTasks(userId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        prefs.edit().clear().apply();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
