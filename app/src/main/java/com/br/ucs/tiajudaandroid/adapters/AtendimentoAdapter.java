package com.br.ucs.tiajudaandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.br.ucs.tiajudaandroid.R;
import com.br.ucs.tiajudaandroid.model.Servico;

import java.util.List;

public class AtendimentoAdapter extends RecyclerView.Adapter<AtendimentoAdapter.ViewHolder> {

    private List<Servico> servicos;
    private OnAtenderClickListener listener;

    public interface OnAtenderClickListener {
        void onAtenderClick(Servico servico);
    }

    public void setOnAtenderClickListener(OnAtenderClickListener listener) {
        this.listener = listener;
    }

    public AtendimentoAdapter(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public void setServicos(List<Servico> novos) {
        this.servicos = novos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AtendimentoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_servico_atendimento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AtendimentoAdapter.ViewHolder holder, int position) {
        Servico s = servicos.get(position);
        holder.titulo.setText(s.getTitulo());
        holder.descricao.setText(s.getDescricao());

        holder.buttonAtender.setOnClickListener(v -> {
            if (listener != null) listener.onAtenderClick(s);
        });
    }

    @Override
    public int getItemCount() {
        return servicos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, descricao;
        Button buttonAtender;

        ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textViewTitulo);
            descricao = itemView.findViewById(R.id.textViewDescricao);
            buttonAtender = itemView.findViewById(R.id.buttonAtender);
        }
    }
}
