package com.br.ucs.tiajudaandroid.model;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String tipo; // "cliente" ou "tecnico"

    public Usuario(int id, String nome, String email, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getTipo() { return tipo; }
}
