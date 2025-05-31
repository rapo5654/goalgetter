package com.rapo.goalgetter.ui.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.rapo.goalgetter.data.model.User;
import com.rapo.goalgetter.data.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    // Добавлен недостающий метод
    public LiveData<User> getUserByCredentials(String email, String password) {
        return repository.getUserByCredentials(email, password);
    }

    public LiveData<Boolean> isEmailExists(String email) {
        return repository.isEmailExists(email);
    }

    public void registerUser(User user) {
        repository.registerUser(user);
    }
}
