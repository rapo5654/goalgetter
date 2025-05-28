package com.rapo.goalgetter.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rapo.goalgetter.data.model.User;
import java.util.List;

@Dao
public interface UserDao {

    // Создание нового пользователя
    @Insert
    void insert(User user);

    // Обновление данных пользователя
    @Update
    void update(User user);

    // Получение пользователя по ID
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    LiveData<User> getUserById(long userId);

    // Авторизация пользователя
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    LiveData<User> getUserByCredentials(String email, String password);

    // Проверка существования email
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email LIMIT 1)")
    LiveData<Boolean> isEmailExists(String email);

    // Получение всех пользователей
    @Query("SELECT * FROM users ORDER BY id DESC")
    LiveData<List<User>> getAllUsers();

    // Удаление пользователя
    @Query("DELETE FROM users WHERE id = :userId")
    void deleteUser(long userId);

    // Обновление пароля
    @Query("UPDATE users SET password = :newPassword WHERE id = :userId")
    void updatePassword(long userId, String newPassword);
}