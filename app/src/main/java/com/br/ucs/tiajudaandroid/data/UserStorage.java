package com.br.ucs.tiajudaandroid.data;

import com.br.ucs.tiajudaandroid.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    private static List<Usuario> usuarios = new ArrayList<>();

    public static void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public static boolean validarLogin(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }
}
