package com.br.ucs.tiajudaandroid.model;

public class Cliente extends Usuario {
    public Cliente(int id, String nome, String email, String senha) {
        super(id, nome, email, senha, "cliente");
    }
}