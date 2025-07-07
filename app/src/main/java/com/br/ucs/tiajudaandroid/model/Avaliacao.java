package com.br.ucs.tiajudaandroid.model;

public class Avaliacao {
    private long idServico;
    private int nota;
    private String comentario;

    public Avaliacao(long idServico, int nota, String comentario) {
        this.idServico = idServico;
        this.nota = nota;
        this.comentario = comentario;
    }

    public long getIdServico() {
        return idServico;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }
}
