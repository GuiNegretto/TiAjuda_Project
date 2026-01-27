package com.br.ucs.tiajudaandroid.activities;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.activities.ServicoActivity;
import com.br.ucs.tiajudaandroid.utils.SessionManager;
import com.br.ucs.tiajudaandroid.model.Usuario;
import android.view.View;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        TextView TextMainMenu = findViewById(R.id.TextMainMenu);
        Button btnEditar = findViewById(R.id.btnEditarCadastro);
        Button btnNovaSolicitacao = findViewById(R.id.btnNovaSolicitacao);
        Button btnConsultar = findViewById(R.id.btnConsultarOrcamentos);
        Button btnAtenderServico = findViewById(R.id.btnAtenderServico);
        Button btnAvaliarServico = findViewById(R.id.btnAvaliarServico);
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

        btnAtenderServico.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, AtendimentoServicoActivity.class);
                        startActivity(intent);
        });

        SessionManager sessionManager = new SessionManager(getApplication().getApplicationContext());
        Usuario usuarioExistente = sessionManager.getUserDetails();

        if("cliente".equals(usuarioExistente.getTipo())){
            btnAtenderServico.setVisibility(View.GONE);
            btnNovaSolicitacao.setVisibility(View.VISIBLE);
            //btnAvaliarServico.setVisibility(View.VISIBLE);
        }
        else{
            btnNovaSolicitacao.setVisibility(View.GONE);
            //btnAvaliarServico.setVisibility(View.GONE);
            btnAtenderServico.setVisibility(View.VISIBLE);
        }

        btnAvaliarServico.setVisibility(View.GONE);

        btnSair.setOnClickListener(v -> {
            Toast.makeText(this, "Voltando para o login...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            sessionManager.logoutUser();
            finish();
        });

        TextMainMenu.setText("Bem-vindo ao Menu Principal (" + usuarioExistente.getTipo() + ")");

    }
}
