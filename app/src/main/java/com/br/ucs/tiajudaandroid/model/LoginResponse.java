package com.br.ucs.tiajudaandroid.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    @JsonProperty("usuario")
    private Usuario usuario;
    @JsonProperty("token")
    private String token;

    // Getters
    public Usuario getUsuario() { return usuario; }
    public String getToken() { return token; }
}