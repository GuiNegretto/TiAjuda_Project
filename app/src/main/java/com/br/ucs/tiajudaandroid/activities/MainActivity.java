package com.br.ucs.tiajudaandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager sessionManager = new SessionManager(getApplicationContext());

    if (sessionManager.isLoggedIn()) {
        // Usuário já está logado, vai direto para a tela de serviços
        Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
        startActivity(intent);
    } else {
        // Usuário não está logado, vai para a tela de login
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    finish();
    }
}
