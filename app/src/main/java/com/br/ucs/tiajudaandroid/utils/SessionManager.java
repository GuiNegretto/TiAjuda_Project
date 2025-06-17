package com.br.ucs.tiajudaandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.br.ucs.tiajudaandroid.model.Usuario; // Supondo que você tenha uma classe Usuario
import com.google.gson.Gson;

public class SessionManager {

    private static final String PREF_NAME = "AppSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_TOKEN = "userToken";
    private static final String KEY_USER_DETAILS = "userDetails";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private Gson gson = new Gson();

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Salva a sessão do usuário após o login bem-sucedido.
     */
    public void createLoginSession(String token, Usuario usuario) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_TOKEN, token);

        // Converte o objeto Usuario para uma String JSON para salvar
        String userJson = gson.toJson(usuario);
        editor.putString(KEY_USER_DETAILS, userJson);

        editor.apply(); // Usa apply() em vez de commit() para rodar em background
    }

    /**
     * Retorna o token do usuário salvo.
     */
    public String getToken() {
        return pref.getString(KEY_USER_TOKEN, null);
    }

    /**
     * Retorna os detalhes do usuário salvo.
     */
    public Usuario getUserDetails() {
        String userJson = pref.getString(KEY_USER_DETAILS, null);
        if (userJson != null) {
            return gson.fromJson(userJson, Usuario.class);
        }
        return null;
    }

    public int getUserId() {
      return getUserDetails().getId();
    }

    /**
     * Verifica se o usuário está logado.
     */
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Limpa os dados da sessão (logout).
     */
    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}