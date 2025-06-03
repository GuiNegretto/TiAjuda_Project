package com.br.ucs.tiajudaandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.storage.UserStorage;

public class MainActivity extends Activity {
    EditText editEmail, editSenha;
    Button btnEntrar;
    TextView linkCadastro;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.emailLogin);
        editSenha = findViewById(R.id.senhaLogin);
        btnEntrar = findViewById(R.id.btnEntrar);
        linkCadastro = findViewById(R.id.linkCadastro);

        btnEntrar.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            if (UserStorage.validarLogin(email, senha)) {
                Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                // Aqui você pode redirecionar para outra tela
                // startActivity(new Intent(this, TelaPrincipalActivity.class));
            } else {
                Toast.makeText(this, "Email ou senha inválidos.", Toast.LENGTH_SHORT).show();
            }
        });

        linkCadastro.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });
    }
}
