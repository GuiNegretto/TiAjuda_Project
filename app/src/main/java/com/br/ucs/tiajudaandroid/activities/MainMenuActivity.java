package com.br.ucs.tiajudaandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.activities.ServicoActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btnEditar = findViewById(R.id.btnEditarCadastro);
        Button btnNovaSolicitacao = findViewById(R.id.btnNovaSolicitacao);
        Button btnConsultar = findViewById(R.id.btnConsultarOrcamentos);
        Button btnSair = findViewById(R.id.btnSair);

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        });

        btnNovaSolicitacao.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, ServicoActivity.class);
                        startActivity(intent);
        });

        btnConsultar.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, OrcamentoActivity.class);
                        startActivity(intent);
        });

        btnSair.setOnClickListener(v -> {
            Toast.makeText(this, "Voltando para o login...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
