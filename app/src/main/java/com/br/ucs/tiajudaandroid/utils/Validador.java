package com.br.ucs.tiajudaandroid.utils;
public class Validador{
    public static boolean validarEmail(String e){
        return e.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    }
    public static boolean validarCpf(String cpf){
        return cpf.matches("\\d{11}");
    }
}
