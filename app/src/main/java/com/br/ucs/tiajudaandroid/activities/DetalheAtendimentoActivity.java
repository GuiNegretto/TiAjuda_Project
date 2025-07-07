package com.br.ucs.tiajudaandroid.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.data.ServicoData;
import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.model.Servico;

public class DetalheAtendimentoActivity extends AppCompatActivity {

    private TextView textTituloServico, textDescricaoServico, textCliente, textData, textStatus;
    private Button buttonConcluir;
    private View loadingOverlay;

    private Servico servico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_atendimento);

        Toolbar toolbar = findViewById(R.id.toolbar_detalhe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inicializa componentes
        textTituloServico = findViewById(R.id.textTituloServico);
        textDescricaoServico = findViewById(R.id.textDescricaoServico);
        textCliente = findViewById(R.id.textCliente);
        textData = findViewById(R.id.textData);
        textStatus = findViewById(R.id.textStatus);
        buttonConcluir = findViewById(R.id.buttonConcluir);
        loadingOverlay = findViewById(R.id.loadingOverlay);

        // Recebe o serviço via intent (simulação)
        servico = (Servico) getIntent().getSerializableExtra("servico");

        if (servico != null) {
            textTituloServico.setText(servico.getTitulo());
            textDescricaoServico.setText(servico.getDescricao());
            textCliente.setText("Cliente ID: " + servico.getIdCliente());
            textData.setText("Data: " + (servico.getDataCadastro() != null ? servico.getDataCadastro() : "Indefinido"));
            textStatus.setText("Status: Em aberto");
        }

        buttonConcluir.setOnClickListener(v -> concluirAtendimento());
    }

    private void concluirAtendimento() {
        loadingOverlay.setVisibility(View.VISIBLE);

        // Aqui você pode implementar a lógica para atualizar o status do serviço
        ServicoData.marcarComoConcluido(this, servico.getId(), new DataCallback<String>() {
            @Override
            public void onSuccess(String result) {
                runOnUiThread(() -> {
                    loadingOverlay.setVisibility(View.GONE);
                    Toast.makeText(DetalheAtendimentoActivity.this, "Atendimento concluído com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Fecha a tela
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    loadingOverlay.setVisibility(View.GONE);
                    Toast.makeText(DetalheAtendimentoActivity.this, "Erro ao concluir: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
