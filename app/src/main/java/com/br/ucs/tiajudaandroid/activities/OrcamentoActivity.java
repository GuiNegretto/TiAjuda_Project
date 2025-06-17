package com.br.ucs.tiajudaandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.model.Orcamento;
import com.br.ucs.tiajudaandroid.adapters.OrcamentoAdapter;
import com.br.ucs.tiajudaandroid.viewmodel.OrcamentoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class OrcamentoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrcamentos;
    private OrcamentoAdapter orcamentoAdapter;
    private FloatingActionButton fabAdicionarOrcamento;
    private OrcamentoViewModel orcamentoViewModel;
    private ActivityResultLauncher<Intent> cadastroOrcamentoLauncher;

    private Orcamento orcamentoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orcamento);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Lista de Orçamentos");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerViewOrcamentos = findViewById(R.id.recyclerViewOrcamentos);
        fabAdicionarOrcamento = findViewById(R.id.fabAdicionarOrcamento);

        setupRecyclerView();

        orcamentoViewModel = new ViewModelProvider(this).get(OrcamentoViewModel.class);

        setupCadastroLauncher();

        observeViewModel();

        orcamentoViewModel.fetchOrcamentosFromApi();
    }

    private void setupRecyclerView() {
        orcamentoAdapter = new OrcamentoAdapter(new ArrayList<>());
        recyclerViewOrcamentos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrcamentos.setAdapter(orcamentoAdapter);

        orcamentoAdapter.setOnEditarClickListener(orcamento -> {
            Intent intent = new Intent(OrcamentoActivity.this, CadastroOrcamentoActivity.class);
            intent.putExtra("orcamento_id", orcamento.getId());
            cadastroOrcamentoLauncher.launch(intent);
        });

        orcamentoAdapter.setOnItemClickListener(orcamento -> {
            orcamentoSelecionado = orcamento;
        });
    }

    private void setupCadastroLauncher() {
        cadastroOrcamentoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        orcamentoViewModel.fetchOrcamentosFromApi();
                    }
                });

                fabAdicionarOrcamento.setOnClickListener(view -> {
                    if (orcamentoSelecionado == null) {
                        Toast.makeText(this, "Selecione um orçamento primeiro!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                
                    Intent intent = new Intent(OrcamentoActivity.this, CadastroOrcamentoActivity.class);
                    intent.putExtra("orcamento_id", orcamentoSelecionado.getId());
                    cadastroOrcamentoLauncher.launch(intent);
                });
    }

    private void observeViewModel() {
        orcamentoViewModel.getOrcamentos().observe(this, orcamentos -> {
            if (orcamentos != null) {
                orcamentoAdapter.setOrcamentos(orcamentos);
            }
        });

        orcamentoViewModel.getErro().observe(this, erro -> {
            if (erro != null) {
                Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_orcamento, menu);
        MenuItem itemDeBusca = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) itemDeBusca.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (orcamentoAdapter != null) {
                    orcamentoAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
