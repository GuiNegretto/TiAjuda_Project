package com.br.ucs.tiajudaandroid.model;

public class Cliente extends Usuario {
    public Cliente(String nome, String email, String senha) {
        super(nome, email, senha, "cliente");
    }
}