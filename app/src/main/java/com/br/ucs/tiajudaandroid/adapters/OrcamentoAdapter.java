package com.br.ucs.tiajudaandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable; // <-- Importante
import android.content.Context;
import android.util.TypedValue;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.model.Orcamento;

import java.util.ArrayList;
import java.util.List;

public class OrcamentoAdapter extends RecyclerView.Adapter<OrcamentoAdapter.OrcamentoViewHolder>  implements Filterable {

    private List<Orcamento> listaOrcamentos;
    private int posicaoSelecionada = RecyclerView.NO_POSITION;
    private List<Orcamento> listaOrcamentosCompleta;

    public OrcamentoAdapter(List<Orcamento> listaOrcamentos) {
        this.listaOrcamentos = listaOrcamentos;
        // 2. Guarda uma cópia da lista original
        this.listaOrcamentosCompleta = new ArrayList<>(listaOrcamentos);
    }

    public void setOrcamentos(List<Orcamento> novosOrcamentos) {
        this.listaOrcamentos = novosOrcamentos;
        this.listaOrcamentosCompleta = new ArrayList<>(novosOrcamentos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrcamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_orcamento, parent, false);
        return new OrcamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrcamentoViewHolder holder, int position) {
        Orcamento orcamento = listaOrcamentos.get(position);
        holder.textValor.setText("Valor R$ " + (orcamento.getStatus() == "C" ? "--" : orcamento.getValor()));
        holder.textObservacao.setText(orcamento.getObservacao());
        holder.textNomeCliente.setText("Cliente: " + orcamento.getNomeCliente());
        holder.textTituloServico.setText("Serviço: " + orcamento.getTituloServico());
        holder.textStatus.setText("Status: " + orcamento.getStatus());
    
        // Aplica a cor de fundo conforme a seleção
        if (position == posicaoSelecionada) {
            holder.itemView.setBackgroundColor(
                holder.itemView.getContext().getResources().getColor(R.color.itemSelecionado) // use um recurso de cor
            );
        } else {
        TypedValue typedValue = new TypedValue();
        Context context = holder.itemView.getContext();
        context.getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);
        holder.itemView.setBackgroundColor(typedValue.data);
    }
    
        holder.itemView.setOnClickListener(v -> {
            int posicaoAnterior = posicaoSelecionada;
            posicaoSelecionada = holder.getAdapterPosition();
    
            notifyItemChanged(posicaoAnterior);  // Atualiza visualmente o item antigo
            notifyItemChanged(posicaoSelecionada); // Atualiza o novo
    
            if (itemClickListener != null && posicaoSelecionada != RecyclerView.NO_POSITION) {
                itemClickListener.onItemClick(orcamento);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaOrcamentos != null ? listaOrcamentos.size() : 0;
    }

    public interface OnEditarClickListener {
      void onEditarClick(Orcamento orcamento);
  }
  
    private OnEditarClickListener editarClickListener;
  
    public void setOnEditarClickListener(OnEditarClickListener listener) {
      this.editarClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Orcamento orcamento);
    }
    
    private OnItemClickListener itemClickListener;
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

        // 3. Implemente o método getFilter()
        @Override
        public Filter getFilter() {
            return filtroDeOrcamentos;
        }
    
        // 4. Crie o objeto de filtro
        private Filter filtroDeOrcamentos = new Filter() {
            // Roda em uma thread de background (para não travar a UI)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Orcamento> listaFiltrada = new ArrayList<>();
    
                if (constraint == null || constraint.length() == 0) {
                    // Se a busca estiver vazia, mostra a lista completa
                    listaFiltrada.addAll(listaOrcamentosCompleta);
                } else {
                    String padraoDeBusca = constraint.toString().toLowerCase().trim();
    
                    for (Orcamento orcamento : listaOrcamentosCompleta) {
                        // Filtra por título ou descrição
                        if (orcamento.getObservacao().toLowerCase().contains(padraoDeBusca)) {
                            listaFiltrada.add(orcamento);
                        }
                    }
                }
    
                FilterResults resultados = new FilterResults();
                resultados.values = listaFiltrada;
                return resultados;
            }
    
            // Roda na UI thread (para atualizar a lista visual)
            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaOrcamentos.clear();
                listaOrcamentos.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

        static class OrcamentoViewHolder extends RecyclerView.ViewHolder {
            TextView textValor, textObservacao, textNomeCliente, textTituloServico, textStatus;

            public OrcamentoViewHolder(@NonNull View itemView) {
                super(itemView);
                textValor = itemView.findViewById(R.id.textValor);
                textObservacao = itemView.findViewById(R.id.textObservacao);
                textNomeCliente = itemView.findViewById(R.id.textNomeCliente);
                textTituloServico = itemView.findViewById(R.id.textTituloServico);
                textStatus = itemView.findViewById(R.id.textStatus);
            }
        }

    }

  
    

