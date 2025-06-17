package com.br.ucs.tiajudaandroid.activities; // Ou seu pacote de activities

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.model.Servico; 
import com.br.ucs.tiajudaandroid.data.ServicoData;
import com.br.ucs.tiajudaandroid.data.DataCallback;

public class CadastroServicoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    // Removemos as variáveis para os EditTexts de ID
    private TextInputEditText editTextTitulo, editTextDescricao;
    private Button buttonSalvar;
    private boolean modoEdicao = false;
    private long idServicoEditando = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);

        
        // Configura a Toolbar
        toolbar = findViewById(R.id.toolbar_cadastro);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Cadastrar Novo Serviço");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Conecta apenas as variáveis que ainda existem
        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        
        if (getIntent().hasExtra("servico_id")) {
            modoEdicao = true;
            idServicoEditando = getIntent().getLongExtra("servico_id", -1);
        
            String titulo = getIntent().getStringExtra("servico_titulo");
            String descricao = getIntent().getStringExtra("servico_descricao");
        
            // Preenche os campos com os dados do serviço
            editTextTitulo.setText(titulo);
            editTextDescricao.setText(descricao);
        
            // Atualiza o título da tela e o texto do botão
            getSupportActionBar().setTitle("Editar Serviço");
            buttonSalvar.setText("Salvar Alterações");
        }

        buttonSalvar.setOnClickListener(v -> {
            cadastrarServico();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cadastrarServico() {
        String titulo = editTextTitulo.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();

        if (titulo.isEmpty()) {
            editTextTitulo.setError("Título é obrigatório");
            editTextTitulo.requestFocus();
            return;
        }

        // --- LÓGICA DE CHAMADA DA API IRIA AQUI ---
        // Agora, ao montar o objeto para enviar para a API, você passaria
        // null ou omitiria os campos id_tecnico e id_cliente.

        // Exemplo:
        // Servico novoServico = new Servico();
        // novoServico.setTitulo(titulo);
        // novoServico.setDescricao(descricao);
        // Seu ApiClient/ViewModel enviaria este objeto, que seria serializado
        // para um JSON como: { "titulo": "...", "descricao": "..." }

        if(idServicoEditando != -1){
            ServicoData.alterarServico(getApplication().getApplicationContext(), new Servico(idServicoEditando, titulo, descricao, 0, 0, null),
            new DataCallback<String>() {
                @Override
                public void onSuccess(String resposta) {
                    // Aqui você pode usar runOnUiThread() se precisar atualizar a UI
                    runOnUiThread(() -> {
                        Toast.makeText(CadastroServicoActivity.this, "Cadastro editado com sucesso!", Toast.LENGTH_SHORT).show();
                        // exibir resposta ou navegar
                        setResult(RESULT_OK);
                        finish();
                    });
                }
            
                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(CadastroServicoActivity.this, "Erro ao salvar edição: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }
            else{
            ServicoData.adicionarServico(getApplication().getApplicationContext(), new Servico(0, titulo, descricao, 0, 0, null),
                    new DataCallback<String>() {
                        @Override
                        public void onSuccess(String resposta) {
                            // Aqui você pode usar runOnUiThread() se precisar atualizar a UI
                            runOnUiThread(() -> {
                                Toast.makeText(CadastroServicoActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                // exibir resposta ou navegar
                                setResult(RESULT_OK);
                                finish();
                            });
                        }
                    
                        @Override
                        public void onFailure(Exception e) {
                            e.printStackTrace();
                            runOnUiThread(() -> {
                                Toast.makeText(CadastroServicoActivity.this, "Erro ao cadastrar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }
                    });

        }


        
    }
}