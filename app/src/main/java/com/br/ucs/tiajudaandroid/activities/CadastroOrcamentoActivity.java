package com.br.ucs.tiajudaandroid.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.data.OrcamentoData;
import com.br.ucs.tiajudaandroid.model.Orcamento;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroOrcamentoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText editTextValor, editTextObservacao;
    private Button buttonSalvar;

    private long idServicoVinculado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_orcamento);

        toolbar = findViewById(R.id.toolbar_cadastro_orcamento);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Cadastrar Orçamento");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editTextValor = findViewById(R.id.editTextValor);
        editTextObservacao = findViewById(R.id.editTextObservacao);
        buttonSalvar = findViewById(R.id.buttonSalvarOrcamento);

        // Recupera o id do serviço enviado pela intent
        // idServicoVinculado = getIntent().getLongExtra("servico_id", -1);
        // if (idServicoVinculado == -1) {
        //     Toast.makeText(this, "Serviço não informado!", Toast.LENGTH_SHORT).show();
        //     finish();
        //     return;
        // }

        buttonSalvar.setOnClickListener(v -> salvarOrcamento());
    }

    private void salvarOrcamento() {
        String valorTexto = editTextValor.getText().toString().trim();
        String observacao = editTextObservacao.getText().toString().trim();

        if (valorTexto.isEmpty()) {
            editTextValor.setError("Valor obrigatório");
            return;
        }

        try {
            double valor = Double.parseDouble(valorTexto);

            Orcamento novo = new Orcamento(0, valor, observacao, idServicoVinculado);
            OrcamentoData.adicionarOrcamento(this, novo, new DataCallback<String>() {
                @Override
                public void onSuccess(String resposta) {
                    runOnUiThread(() -> {
                        Toast.makeText(CadastroOrcamentoActivity.this, "Orçamento salvo!", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    });
                }

                @Override
                public void onFailure(Exception e) {
                    runOnUiThread(() -> Toast.makeText(CadastroOrcamentoActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            });

        } catch (NumberFormatException e) {
            editTextValor.setError("Formato inválido");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
