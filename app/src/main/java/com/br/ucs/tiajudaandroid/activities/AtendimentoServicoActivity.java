package com.br.ucs.tiajudaandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.adapters.AtendimentoAdapter;
import com.br.ucs.tiajudaandroid.viewmodel.ServicoViewModel;
import com.br.ucs.tiajudaandroid.model.Servico;
import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.data.ServicoData;

import java.util.ArrayList;
import java.util.List;

public class AtendimentoServicoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAtendimento;
    private AtendimentoAdapter adapter;
    private View loadingOverlay;
    private ServicoViewModel servicoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atendimento_servico);

        Toolbar toolbar = findViewById(R.id.toolbar_atendimento);
        setSupportActionBar(toolbar);

        recyclerViewAtendimento = findViewById(R.id.recyclerViewAtendimento);
        loadingOverlay = findViewById(R.id.loadingOverlay);

        adapter = new AtendimentoAdapter(new ArrayList<>());
        recyclerViewAtendimento.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAtendimento.setAdapter(adapter);

        adapter.setOnAtenderClickListener(servico -> {
            Intent intent = new Intent(this, DetalheAtendimentoActivity.class);
            intent.putExtra("servico_id", servico.getId());
            startActivity(intent);
        });

        servicoViewModel = new ViewModelProvider(this).get(ServicoViewModel.class);
        observarDados();

        loadingOverlay.setVisibility(View.VISIBLE);
        servicoViewModel.fetchServicosFromApi();
    }

    private void observarDados() {
        loadingOverlay.setVisibility(View.VISIBLE);

    ServicoData.buscarServicosTecnico(this, new DataCallback<List<Servico>>() {
        @Override
        public void onSuccess(List<Servico> servicos) {
            runOnUiThread(() -> {
                loadingOverlay.setVisibility(View.GONE);
                if (servicos != null && !servicos.isEmpty()) {
                    adapter.setServicos(servicos);
                } else {
                    Toast.makeText(getApplicationContext(), "Nenhum serviço encontrado.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onFailure(Exception e) {
            runOnUiThread(() -> {
                loadingOverlay.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            });
        }
    });
    }
}
