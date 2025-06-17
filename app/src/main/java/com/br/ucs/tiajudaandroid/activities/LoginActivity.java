package com.br.ucs.tiajudaandroid.activities;

import android.app.Activity;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.data.UserData;
import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.utils.SessionManager;
import com.br.ucs.tiajudaandroid.model.LoginResponse;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity{

    EditText editEmail, editSenha;
    Button btnEntrar;
    TextView linkCadastro;

    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.emailLogin);
        editSenha = findViewById(R.id.senhaLogin);
        btnEntrar = findViewById(R.id.btnEntrar);
        linkCadastro = findViewById(R.id.linkCadastro);

        btnEntrar.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            UserData.validarLogin(email, senha,     
            new DataCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    // Aqui você pode usar runOnUiThread() se precisar atualizar a UI
                    runOnUiThread(() -> {

                        // 1. Converte a resposta JSON para o nosso objeto LoginResponse
                        Gson gson = new Gson();
                        LoginResponse response = gson.fromJson(result, LoginResponse.class);

                        // 2. Cria uma instância do SessionManager
                        SessionManager sessionManager = new SessionManager(getApplicationContext());

                        // 3. Salva a sessão com o token e os dados do usuário
                        sessionManager.createLoginSession(response.getToken(), response.getUsuario());

                        Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                        // exibir resposta ou navegar

                        // Abrir a MainMenuActivity
                        Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                        startActivity(intent);

                        // Finaliza a tela de login para que o usuário não volte ao pressionar "voltar"
                        finish();
                    });
                }
            
                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        new AlertDialog.Builder(LoginActivity.this)
        .setTitle("Erro ao logar")
        .setMessage(e.getMessage())
        .setPositiveButton("OK", null)
        .show();
                        //Toast.makeText(MainActivity.this, "Erro ao logar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });

        linkCadastro.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });
    }
}
