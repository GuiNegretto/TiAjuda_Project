package com.br.ucs.tiajudaandroid.model;

import com.google.gson.annotations.SerializedName;

public class Orcamento {
    private long id;
    private double valor;
    private String observacao;
    @SerializedName("id_servico")
    private long idServico;
    @SerializedName("nome_cliente")
    private String nomeCliente;
    @SerializedName("titulo_servico")
    private String tituloServico;
    private String status;


    public Orcamento() {}

    public Orcamento(long id, double valor, String observacao, long idServico) {
        this.id = id;
        this.valor = valor;
        this.observacao = observacao;
        this.idServico = idServico;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public long getIdServico() { return idServico; }
    public void setIdServico(long idServico) { this.idServico = idServico; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getTituloServico() { return tituloServico; }
    public void setTituloServico(String tituloServico) { this.tituloServico = tituloServico; }

    public String getStatus() { 
        String result = null;
        switch(status){
            case "C" :
                result = "Pendente criação";
                break;
            case "D":
                result = "Orçamento criado, aguardando aprovação";
                break;
            case "A":
                result = "Orçamento aprovado pelo cliente";
                break;
            case "R":
                result = "Orçamento rejeitado pelo cliente";
                break;
            case "F":
                result = "Faturado";
                break;
        }
        return result;
     }
     public String getStatusChar() { 
        return status;
     }
     
    public void setStatus(String status) { this.status = status; }
}
