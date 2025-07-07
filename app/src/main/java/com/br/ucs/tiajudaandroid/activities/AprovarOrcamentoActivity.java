package com.br.ucs.tiajudaandroid.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.data.OrcamentoData;
import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.utils.SessionManager;
import androidx.appcompat.app.AlertDialog;

public class AprovarOrcamentoActivity extends AppCompatActivity {

    private TextView textValorTotal;
    private RadioGroup radioGroupPagamento;
    private Button btnAprovarPagamento;
    private TextView textoEmailConfirmacao;
    private View loadingOverlay;

    private int orcamentoId;
    private double valorTotal = 0.0; // Você pode obter esse valor da API ou da Intent extra

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovar_orcamento);

        textValorTotal = findViewById(R.id.textValorTotal);
        radioGroupPagamento = findViewById(R.id.radioGroupPagamento);
        btnAprovarPagamento = findViewById(R.id.btnAprovarPagamento);
        textoEmailConfirmacao = findViewById(R.id.textoEmailConfirmacao);
        loadingOverlay = findViewById(R.id.loadingOverlay);


        // Recupera dados da intent
        orcamentoId = getIntent().getIntExtra("orcamento_id", -1);

        // Simulando o valor total
        valorTotal = 350.00; // <- Substitua pelo valor real do orçamento

        textValorTotal.setText("Valor Total: R$ " + String.format("%.2f", valorTotal));

        btnAprovarPagamento.setOnClickListener(v -> {
            int selectedId = radioGroupPagamento.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Selecione uma forma de pagamento", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                .setTitle("Aprovar Orçamento")
                .setMessage("Deseja realmente aprovar este orçamento?")
                .setPositiveButton("Sim", (dialog, which) -> {
                  String formaPagamento = "";

                  switch (selectedId) {
                      case R.id.radioPix:
                          formaPagamento = "PIX";
                          break;
                      case R.id.radioCredito:
                          formaPagamento = "Cartão de Crédito (1x)";
                          break;
                      case R.id.radioCreditoParcelado:
                          formaPagamento = "Cartão de Crédito (3x)";
                          break;
                      case R.id.radioDebito:
                          formaPagamento = "Cartão de Débito";
                          break;
                      case R.id.radioBoleto:
                          formaPagamento = "Boleto Bancário";
                          break;
                  }

                  loadingOverlay.setVisibility(View.VISIBLE);

                  OrcamentoData.aprovarOrcamento(formaPagamento,  orcamentoId,
            new DataCallback<String>() {
                @Override
                public void onSuccess(String resposta) {
                    // Aqui você pode usar runOnUiThread() se precisar atualizar a UI
                    runOnUiThread(() -> {
                        loadingOverlay.setVisibility(View.GONE);
                        Toast.makeText(AprovarOrcamentoActivity.this, "Orçamento aprovado com sucesso!", Toast.LENGTH_SHORT).show();
                        // exibir resposta ou navegar
                        setResult(RESULT_OK);
                        finish();
                    });
                }
            
                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        loadingOverlay.setVisibility(View.GONE);
                        Toast.makeText(AprovarOrcamentoActivity.this, "Erro ao aprovar orçamento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
                })
                .setNegativeButton("Cancelar", null)
                .show();


        });
    }
}
