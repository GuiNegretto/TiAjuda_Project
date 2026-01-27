package com.br.ucs.tiajudaandroid.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.br.ucs.tiajudaandroid.data.AvaliacaoData;
import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.model.Avaliacao;

import com.br.ucs.tiajudaandroid.R;

public class AvaliacaoServicoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textViewTituloServico;
    private RatingBar ratingBar;
    private EditText editTextComentario;
    private Button buttonEnviarAvaliacao;
    private View loadingOverlay;

    private long servicoId = -1; // Será passado via intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_servico);

        toolbar = findViewById(R.id.toolbar_avaliacao);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        textViewTituloServico = findViewById(R.id.textViewTituloServico);
        ratingBar = findViewById(R.id.ratingBar);
        editTextComentario = findViewById(R.id.editTextComentario);
        buttonEnviarAvaliacao = findViewById(R.id.buttonEnviarAvaliacao);
        loadingOverlay = findViewById(R.id.loadingOverlay);

        // Recupera o ID e título do serviço (se foi passado)
        servicoId = getIntent().getLongExtra("servico_id", -1);
        String titulo = getIntent().getStringExtra("servico_titulo");
        textViewTituloServico.setText(titulo != null ? titulo : "Serviço");

        buttonEnviarAvaliacao.setOnClickListener(v -> enviarAvaliacao());
    }

    private void enviarAvaliacao() {
        int nota = Math.round(ratingBar.getRating());
        String comentario = editTextComentario.getText().toString().trim();

        if (nota == 0) {
            Toast.makeText(this, "Por favor, dê uma nota.", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingOverlay.setVisibility(View.VISIBLE);
        Avaliacao avaliacao = new Avaliacao(servicoId, nota, comentario);

AvaliacaoData.enviarAvaliacao(this, avaliacao, new DataCallback<String>() {
    @Override
    public void onSuccess(String result) {
        runOnUiThread(() -> {
            loadingOverlay.setVisibility(View.GONE);
            Toast.makeText(AvaliacaoServicoActivity.this, "Avaliação enviada com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
    });
    }

    @Override
    public void onFailure(Exception e) {
        runOnUiThread(() -> {
            loadingOverlay.setVisibility(View.GONE);
            Toast.makeText(AvaliacaoServicoActivity.this, "Erro ao enviar avaliação: " + e.getMessage(), Toast.LENGTH_LONG).show();
    });
    }
});

        // Finaliza a tela
        setResult(RESULT_OK);
        
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
