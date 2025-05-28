package com.rapo.goalgetter.ui.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.rapo.goalgetter.R;
import com.rapo.goalgetter.data.model.Task;
import com.rapo.goalgetter.ui.viewmodels.TaskViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    private EditText etTitle, etDescription, etDate;
    private Spinner spinnerPriority, spinnerColor;
    private Calendar calendar;
    private TaskViewModel taskViewModel;
    private long currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        initViews();
        setupDatePicker();
        loadCurrentUserId();
    }

    private void initViews() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        spinnerColor = findViewById(R.id.spinnerColor);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> saveTask());
    }

    private void setupDatePicker() {
        calendar = Calendar.getInstance();
        etDate.setOnClickListener(v -> showDatePicker());
    }

    private void loadCurrentUserId() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        currentUserId = prefs.getLong("user_id", -1);
    }

    private void showDatePicker() {
        new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    updateDateLabel();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void updateDateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        etDate.setText(sdf.format(calendar.getTime()));
    }

    private int getSelectedColor() {
        int position = spinnerColor.getSelectedItemPosition();
        switch (position) {
            case 0: return getColor(R.color.red);
            case 1: return getColor(R.color.green);
            case 2: return getColor(R.color.blue);
            default: return getColor(R.color.white);
        }
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        int priority = spinnerPriority.getSelectedItemPosition() + 1;
        int color = getSelectedColor();

        if (title.isEmpty()) {
            showToast("Введите название задачи");
            return;
        }

        if (date.isEmpty()) {
            showToast("Выберите дату");
            return;
        }

        Task newTask = new Task(
                title,
                description,
                false,  // isCompleted
                date,    // dueDate
                priority,
                color,
                System.currentTimeMillis(), // createdAt
                currentUserId
        );

        taskViewModel.insert(newTask);
        showToast("Задача сохранена");
        setResult(RESULT_OK);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}