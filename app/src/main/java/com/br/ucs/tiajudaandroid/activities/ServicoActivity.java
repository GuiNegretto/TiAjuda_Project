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
import com.br.ucs.tiajudaandroid.adapters.ServicoAdapter;
import com.br.ucs.tiajudaandroid.viewmodel.ServicoViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

// O nome da sua classe aqui deve ser ServicoActivity, como no arquivo.
public class ServicoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewServicos;
    private ServicoAdapter servicoAdapter;
    private FloatingActionButton fabAdicionarServico;

    // 1. A Activity só precisa conhecer o seu ViewModel.
    private ServicoViewModel servicoViewModel;

    private ActivityResultLauncher<Intent> cadastroServicoLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servico);

        // --- Configuração da UI ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Lista de Serviços");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerViewServicos = findViewById(R.id.recyclerViewServicos);
        fabAdicionarServico = findViewById(R.id.fabAdicionarServico);
        setupRecyclerView();

        // 2. INICIALIZAÇÃO CORRETA DO VIEWMODEL
        servicoViewModel = new ViewModelProvider(this).get(ServicoViewModel.class);

        // Configuração do launcher para a tela de cadastro
        setupCadastroLauncher();
        
        // 3. OBSERVAÇÃO DOS DADOS
        // Diz à Activity para "escutar" as mudanças nos dados do ViewModel.
        observeViewModel();

        // 4. INÍCIO DA BUSCA
        // Pede ao ViewModel para buscar os dados da primeira vez.
        servicoViewModel.fetchServicosFromApi();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setupRecyclerView() {
        servicoAdapter = new ServicoAdapter(new ArrayList<>());
        recyclerViewServicos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewServicos.setAdapter(servicoAdapter);

        servicoAdapter.setOnEditarClickListener(servico -> {
            // Aqui você trata a ação de edição, por exemplo:
            // abrir uma nova Activity/Fragment para editar o serviço selecionado
            Intent intent = new Intent(ServicoActivity.this, CadastroServicoActivity.class);
    intent.putExtra("servico_id", servico.getId());
    intent.putExtra("servico_titulo", servico.getTitulo());
    intent.putExtra("servico_descricao", servico.getDescricao());
    cadastroServicoLauncher.launch(intent);
        });
    }

    private void setupCadastroLauncher() {
        cadastroServicoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Se um novo serviço foi cadastrado, pede para o ViewModel buscar os dados novamente.
                        Toast.makeText(this, "Atualizando lista...", Toast.LENGTH_SHORT).show();
                        servicoViewModel.fetchServicosFromApi();
                    }
                });

        fabAdicionarServico.setOnClickListener(view -> {
            Intent intent = new Intent(ServicoActivity.this, CadastroServicoActivity.class);
            cadastroServicoLauncher.launch(intent);
        });
    }

    // Este é o único lugar onde a Activity reage aos dados.
    private void observeViewModel() {
        // Observa a lista de serviços. Quando ela mudar, o código aqui dentro será executado.
        servicoViewModel.getServicos().observe(this, servicos -> {
            if (servicos != null) {
                // Entrega a nova lista para o adapter, que atualiza a UI.
                servicoAdapter.setServicos(servicos);
            }
        });

        // Observa possíveis mensagens de erro.
        servicoViewModel.getErro().observe(this, erro -> {
            if (erro != null) {
                Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_servico, menu);
        MenuItem itemDeBusca = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) itemDeBusca.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (servicoAdapter != null) {
                    servicoAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}