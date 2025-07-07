package com.br.ucs.tiajudaandroid.activities;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.model.Usuario;
import com.br.ucs.tiajudaandroid.data.UserData;
import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.utils.SessionManager;

import android.app.Application;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.*;
import com.google.android.material.textfield.TextInputEditText;



public class CadastroActivity extends AppCompatActivity {
    EditText editNome, editEmail, editSenha;
    RadioGroup radioGroupTipo;
    Button btnCadastrar;
    private View loadingOverlay;
    
    @Override
    protected void onCreate(Bundle b) {
        SessionManager sessionManager = new SessionManager(getApplication().getApplicationContext());
        super.onCreate(b);
        setContentView(R.layout.activity_cadastro);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        radioGroupTipo = findViewById(R.id.radioGroupTipo);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        loadingOverlay = findViewById(R.id.loadingOverlay);

        // Verifica se foi passado um usuário para edição
        
        if (sessionManager.isLoggedIn()) {
           Usuario usuarioExistente = sessionManager.getUserDetails();

            if (usuarioExistente != null) {
                editNome.setText(usuarioExistente.getNome());
                editEmail.setText(usuarioExistente.getEmail());
                editSenha.setText(usuarioExistente.getSenha());

                // if ("cliente".equals(usuarioExistente.getTipo())) {
                //     radioGroupTipo.check(R.id.radioCliente);
                // } else if ("tecnico".equals(usuarioExistente.getTipo())) {
                //     radioGroupTipo.check(R.id.radioTecnico);
                // }
                radioGroupTipo.setVisibility(View.GONE);

                btnCadastrar.setText("Atualizar");
            }
        }


        btnCadastrar.setOnClickListener(v -> {
            String nome = editNome.getText().toString();
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            loadingOverlay.setVisibility(View.VISIBLE);

            String tipo = "";
            int checkedId = radioGroupTipo.getCheckedRadioButtonId();
            if (checkedId == R.id.radioCliente) tipo = "cliente";
            else if (checkedId == R.id.radioTecnico) tipo = "tecnico";

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || (tipo.isEmpty() && !sessionManager.isLoggedIn())) {
                Toast.makeText(CadastroActivity.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            } else {

                if(sessionManager.isLoggedIn()){

                    UserData.alterarUsuario(new Usuario(sessionManager.getUserId(), nome, email, senha, tipo),
                new DataCallback<String>() {
                    @Override
                    public void onSuccess(String resposta) {
                        // Aqui você pode usar runOnUiThread() se precisar atualizar a UI
                        runOnUiThread(() -> {
                            loadingOverlay.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this, "Cadastro alterado com sucesso!", Toast.LENGTH_SHORT).show();
                            // exibir resposta ou navegar
                            finish();
                        });
                    }
                
                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> {
                            loadingOverlay.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this, "Erro ao editar cadastro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });
                }
                else{
                    
                UserData.adicionarUsuario(new Usuario(0, nome, email, senha, tipo),
                new DataCallback<String>() {
                    @Override
                    public void onSuccess(String resposta) {
                        // Aqui você pode usar runOnUiThread() se precisar atualizar a UI
                        runOnUiThread(() -> {
                            loadingOverlay.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            // exibir resposta ou navegar
                            finish();
                        });
                    }
                
                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> {
                            loadingOverlay.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this, "Erro ao cadastrar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });
                }
                
                
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}