package com.br.ucs.tiajudaandroid.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable; // <-- Importante
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.br.ucs.tiajudaandroid.R; // Substitua pelo seu pacote
import com.br.ucs.tiajudaandroid.model.Servico; // Substitua pelo seu pacote

import java.util.ArrayList;
import java.util.List;

// 1. Implemente a interface Filterable
public class ServicoAdapter extends RecyclerView.Adapter<ServicoAdapter.ServicoViewHolder> implements Filterable {

    private List<Servico> listaServicos;
    private List<Servico> listaServicosCompleta; // <-- Lista com todos os itens originais

    public interface OnEditarClickListener {
        void onEditarClick(Servico servico);
    }

    private OnEditarClickListener editarClickListener;

    // 2. Setter para atribuir o listener
    public void setOnEditarClickListener(OnEditarClickListener listener) {
        this.editarClickListener = listener;
    }

    public interface OnAvaliarClickListener {
        void onAvaliarClick(Servico servico);
    }
    
    private OnAvaliarClickListener avaliarClickListener;
    
    public void setOnAvaliarClickListener(OnAvaliarClickListener listener) {
        this.avaliarClickListener = listener;
    }

    public ServicoAdapter(List<Servico> listaServicos) {
        this.listaServicos = listaServicos;
        // 2. Guarda uma cópia da lista original
        this.listaServicosCompleta = new ArrayList<>(listaServicos);
    }

    @NonNull
    @Override
    public ServicoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servico, parent, false);
        return new ServicoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicoViewHolder holder, int position) {
        Servico servico = listaServicos.get(position);
        holder.textViewTitulo.setText(servico.getTitulo());
        holder.textViewDescricao.setText(servico.getDescricao());
        holder.textViewData.setText("Cadastrado em " + servico.getDataCadastro());

        //if("F".equals(servico.getStatus()))
            holder.buttonAvaliar.setEnabled(true);     
        //else
        //    holder.buttonAvaliar.setEnabled(false);
        
        holder.buttonEditar.setOnClickListener(v -> {
            if (editarClickListener != null) {
                editarClickListener.onEditarClick(servico);
            }
        });

        holder.buttonAvaliar.setOnClickListener(v -> {
            if (avaliarClickListener != null) {
                avaliarClickListener.onAvaliarClick(servico);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaServicos != null ? listaServicos.size() : 0;
    }
    
    public void setServicos(List<Servico> novosServicos) {
        this.listaServicos = novosServicos;
        this.listaServicosCompleta = new ArrayList<>(novosServicos);
        notifyDataSetChanged();
    }

    // 3. Implemente o método getFilter()
    @Override
    public Filter getFilter() {
        return filtroDeServicos;
    }

    // 4. Crie o objeto de filtro
    private Filter filtroDeServicos = new Filter() {
        // Roda em uma thread de background (para não travar a UI)
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Servico> listaFiltrada = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                // Se a busca estiver vazia, mostra a lista completa
                listaFiltrada.addAll(listaServicosCompleta);
            } else {
                String padraoDeBusca = constraint.toString().toLowerCase().trim();

                for (Servico servico : listaServicosCompleta) {
                    // Filtra por título ou descrição
                    if (servico.getTitulo().toLowerCase().contains(padraoDeBusca) ||
                        servico.getDescricao().toLowerCase().contains(padraoDeBusca)) {
                        listaFiltrada.add(servico);
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
            listaServicos.clear();
            listaServicos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    static class ServicoViewHolder extends RecyclerView.ViewHolder {
      // 1. Declare as variáveis de View aqui
      TextView textViewTitulo;
      TextView textViewDescricao;
      TextView textViewData;
      TextView textViewIdServico;
      TextView textViewStatus;
      Button buttonEditar, buttonAvaliar;

      // 2. Crie o construtor que recebe a View do item
      public ServicoViewHolder(@NonNull View itemView) {
          // 3. Chame o construtor da classe pai (obrigatório)
          super(itemView);

          // 4. Conecte as variáveis com os IDs do XML "item_servico.xml"
          textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
          textViewDescricao = itemView.findViewById(R.id.textViewDescricao);
          textViewData = itemView.findViewById(R.id.textViewData);
          textViewIdServico = itemView.findViewById(R.id.textViewIdServico);
          textViewStatus = itemView.findViewById(R.id.textViewStatus);
          buttonEditar = itemView.findViewById(R.id.buttonEditar);
          buttonAvaliar = itemView.findViewById(R.id.buttonAvaliar);
      }
  }
}