package com.br.ucs.tiajudaandroid.model;

public class Tecnico extends Usuario {
    public Tecnico(int id, String nome, String email, String senha) {
        super(id, nome, email, senha, "tecnico");
    }
}
