package com.rapo.goalgetter.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.rapo.goalgetter.data.database.AppDatabase;
import com.rapo.goalgetter.data.dao.UserDao;
import com.rapo.goalgetter.data.model.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;
    private final ExecutorService executor;

    public UserRepository(Application application) {
        // Используем правильный метод для получения экземпляра базы данных
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<User> getUserByCredentials(String email, String password) {
        return userDao.getUserByCredentials(email, password);
    }

    public LiveData<Boolean> isEmailExists(String email) {
        return userDao.isEmailExists(email);
    }

    public void registerUser(User user) {
        executor.execute(() -> userDao.insert(user));
    }

    // Добавим метод для получения ID пользователя
    public LiveData<User> getUserById(String userId) {
        return userDao.getUserById(userId);
    }
}
