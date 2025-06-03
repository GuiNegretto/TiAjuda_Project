package com.br.ucs.tiajudaandroid.storage;

import com.br.ucs.tiajudaandroid.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    private static List<Usuario> usuarios = new ArrayList<>();

    public static void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public static boolean validarLogin(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
}