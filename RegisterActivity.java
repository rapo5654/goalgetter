package com.rapo.goalgetter.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.rapo.goalgetter.R;
import com.rapo.goalgetter.data.model.User;
import com.rapo.goalgetter.ui.viewmodels.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setupViewModel();
        setupRegisterButton();
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
    }

    private void setupViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void setupRegisterButton() {
        findViewById(R.id.btnRegister).setOnClickListener(v -> attemptRegistration());
    }

    private void attemptRegistration() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!validateInputs(username, email, password)) return;

        userViewModel.isEmailExists(email).observe(this, emailExists -> {
            if (emailExists) {
                showToast("Email уже зарегистрирован");
            } else {
                registerNewUser(username, email, password);
            }
        });
    }

    private boolean validateInputs(String username, String email, String password) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showToast("Заполните все поля");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Некорректный email");
            return false;
        }

        if (password.length() < 6) {
            showToast("Пароль должен содержать минимум 6 символов");
            return false;
        }

        return true;
    }

    private void registerNewUser(String username, String email, String password) {
        User newUser = new User(username, email, password);
        userViewModel.registerUser(newUser);
        showToast("Регистрация успешна");
        navigateToLogin();
    }

    private void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}