package com.br.ucs.tiajudaandroid.model;

import com.google.gson.annotations.SerializedName;

public class Servico {

    private long id;
    private String titulo;
    private String descricao;
    private long idTecnico;
    private long idCliente;
    private String status;

    @SerializedName("data_cadastro")
    private String dataCadastro;

    // Construtor vazio (necessário para algumas bibliotecas de desserialização)
    public Servico() {
    }

    // Construtor com todos os campos
    public Servico(long id, String titulo, String descricao, long idTecnico, long idCliente, String dataCadastro) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.idTecnico = idTecnico;
        this.idCliente = idCliente;
        this.dataCadastro = dataCadastro;
    }

    public Servico(long id, String titulo, String descricao, long idTecnico, long idCliente, String dataCadastro, String status) {
        super();
        this.status = status;
    }

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public long getIdTecnico() { return idTecnico; }
    public void setIdTecnico(long idTecnico) { this.idTecnico = idTecnico; }

    public long getIdCliente() { return idCliente; }
    public void setIdCliente(long idCliente) { this.idCliente = idCliente; }

    public String getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro = dataCadastro; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}