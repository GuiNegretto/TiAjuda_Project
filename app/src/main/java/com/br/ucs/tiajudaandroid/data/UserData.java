package com.br.ucs.tiajudaandroid.data;

import com.br.ucs.tiajudaandroid.model.Usuario;
import com.br.ucs.tiajudaandroid.network.ApiClient;
import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.network.HttpMethod;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class UserData {
    private static ApiClient apiClient = new ApiClient();
 
    public static void adicionarUsuario(Usuario usuario, DataCallback<String> callback) {


            new Thread(() -> {
                    try {

                        Map<String, Object> requestBody = new HashMap<>();
                        requestBody.put("nome", usuario.getNome());
                        requestBody.put("email", usuario.getEmail());
                        requestBody.put("senha", usuario.getSenha());
                        requestBody.put("tipo", usuario.getSenha());

                    String resposta = apiClient.requestApiSync(HttpMethod.POST, "usuarios", requestBody);
            
                    if (callback != null) {
                        callback.onSuccess(resposta);
                    }
                } catch (IOException e) {
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }
        }).start();
    }

    public static void alterarUsuario(Usuario usuario, DataCallback<String> callback) {


        new Thread(() -> {
                try {

                    Map<String, Object> requestBody = new HashMap<>();
                    requestBody.put("nome", usuario.getNome());
                    requestBody.put("email", usuario.getEmail());
                    requestBody.put("senha", usuario.getSenha());
                    requestBody.put("tipo", usuario.getTipo());

                String resposta = apiClient.requestApiSync(HttpMethod.PUT, "usuarios/" + usuario.getId(), requestBody);
        
                if (callback != null) {
                    callback.onSuccess(resposta);
                }
            } catch (IOException e) {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
    }).start();
}

    public static void validarLogin(String email, String senha, DataCallback<String> callback) {

        new Thread(() -> {
            try {

                        Map<String, Object> requestBody = new HashMap<>();
                        requestBody.put("email", email);
                        requestBody.put("senha", senha);

                        String resposta = apiClient.requestApiSync(HttpMethod.POST, "usuarios/login", requestBody);

                        if (callback != null) {
                            callback.onSuccess(resposta);
                        }
                    } catch (IOException e) {
                        if (callback != null) {
                            callback.onFailure(e);
                        }
                    }
        }).start();


        
    }

    
}
