package com.rapo.goalgetter.ui.activities;

import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rapo.goalgetter.R;
import com.rapo.goalgetter.data.model.Task;
import com.rapo.goalgetter.ui.adapters.TaskAdapter;
import com.rapo.goalgetter.ui.viewmodels.TaskViewModel;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {

    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupViewModel();
    }

    private void initViews() {
        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new TaskAdapter(this);
        recyclerView.setAdapter(adapter);

        // Floating Action Button
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> showAddTaskDialog());
    }

    private void setupViewModel() {
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks ->
                adapter.submitList(tasks));
    }

    private void showAddTaskDialog() {
        Task newTask = new Task(
                "Новая задача",
                "Описание задачи",
                false,
                "2023-12-31",
                1,
                getColor(R.color.blue),
                System.currentTimeMillis(),
                getCurrentUserId()
        );
        taskViewModel.insert(newTask);
    }

    private long getCurrentUserId() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return prefs.getLong("user_id", -1);
    }

    @Override
    public void onTaskClick(Task task) {
        // Реализация перехода к редактированию задачи
    }

    @Override
    public void onCheckboxChanged(Task task, boolean isChecked) {
        task.setCompleted(isChecked);
        taskViewModel.update(task);
    }
}