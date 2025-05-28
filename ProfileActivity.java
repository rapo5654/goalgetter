package com.rapo.goalgetter.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView; // Импорт добавлен
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rapo.goalgetter.R;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivAvatar; // Теперь класс распознается
    private TextView tvUsername, tvEmail, tvStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Инициализация элементов
        ivAvatar = findViewById(R.id.ivAvatar); // Убедитесь, что ID совпадает с XML
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvStats = findViewById(R.id.tvStats);

        // Загрузка аватарки
        ivAvatar.setOnClickListener(v -> openImagePicker());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            ivAvatar.setImageURI(imageUri); // Обновление аватарки
        }
    }
}