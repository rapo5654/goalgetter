package com.rapo.goalgetter.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;

import com.rapo.goalgetter.R;
import com.rapo.goalgetter.data.model.User;
import com.rapo.goalgetter.ui.viewmodels.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setupViewModel();
        setupListeners();
    }

    private void initializeViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);
    }

    private void setupViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void setupListeners() {
        findViewById(R.id.tvRegister).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        findViewById(R.id.btnLogin).setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!isInputValid(email, password)) return;

        userViewModel.getUserByCredentials(email, password)
                .observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (user != null) {
                            handleSuccessfulLogin(user);
                        } else {
                            showErrorMessage();
                        }
                    }
                });
    }

    private boolean isInputValid(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Все поля должны быть заполнены");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Некорректный формат email");
            return false;
        }

        if (password.length() < 6) {
            showToast("Пароль должен содержать минимум 6 символов");
            return false;
        }

        return true;
    }

    private void handleSuccessfulLogin(User user) {
        saveUserSession(user.getId());
        navigateToMainActivity();
    }

    private void saveUserSession(long userId) {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        prefs.edit()
                .putLong("user_id", userId)
                .apply();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showErrorMessage() {
        Toast.makeText(this,
                "Неверные учетные данные",
                Toast.LENGTH_SHORT).show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}