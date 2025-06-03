package com.br.ucs.tiajudaandroid.activities;
import com.br.ucs.tiajudaandroid.model.Usuario;
import com.br.ucs.tiajudaandroid.storage.UserStorage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.br.ucs.tiajudaandroid.R;

public class CadastroActivity extends Activity {
    EditText editNome, editEmail, editSenha;
    RadioGroup radioGroupTipo;
    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        radioGroupTipo = findViewById(R.id.radioGroupTipo);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(v -> {
            String nome = editNome.getText().toString();
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            String tipo = "";
            int checkedId = radioGroupTipo.getCheckedRadioButtonId();
            if (checkedId == R.id.radioCliente) tipo = "cliente";
            else if (checkedId == R.id.radioTecnico) tipo = "tecnico";

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || tipo.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            } else {
                UserStorage.adicionarUsuario(new Usuario(nome, email, senha, tipo));
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}