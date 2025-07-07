package com.br.ucs.tiajudaandroid.data;

import android.content.Context;

import com.br.ucs.tiajudaandroid.model.Orcamento;
import com.br.ucs.tiajudaandroid.model.Usuario;
import com.br.ucs.tiajudaandroid.network.ApiClient;
import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.network.HttpMethod;
import com.br.ucs.tiajudaandroid.utils.SessionManager;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Type;


public class OrcamentoData {

    private static ApiClient apiClient = new ApiClient();
    private static Gson gson = new Gson();

    private SessionManager sessionManager;


    // Método que a Activity chama para iniciar a busca de dados
    public static void buscarOrcamentos(Context context, DataCallback<List<Orcamento>> callback) {
        SessionManager sessionManager = new SessionManager(context);

      new Thread(() -> {
        try {
            
            Usuario user = sessionManager.getUserDetails();
            
            String resposta = apiClient.requestApiSync(HttpMethod.GET , "orcamentos/id_" + user.getTipo() + "/" + sessionManager.getUserId() );

            Type listType = new TypeToken<List<Orcamento>>() {}.getType();
            List<Orcamento> orcamentos = gson.fromJson(resposta, listType);

            if (callback != null) {
                callback.onSuccess(orcamentos);
            }
        } catch (IOException e) {
            if (callback != null) {
                callback.onFailure(e);
            }
        }
}).start();
  }

  public static void adicionarOrcamento(Context context, Orcamento orcamento, DataCallback<String> callback) {
    SessionManager sessionManager = new SessionManager(context);

    new Thread(() -> {
            try {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("id_tecnico", sessionManager.getUserId());
                requestBody.put("valor", orcamento.getValor());
                requestBody.put("observacao", orcamento.getObservacao());
                requestBody.put("id_servico", orcamento.getIdServico());

                String resposta = apiClient.requestApiSync(HttpMethod.POST, "orcamentos", requestBody);
    
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

public static void alterarOrcamento(Context context, Orcamento orcamento, DataCallback<String> callback) {
    new Thread(() -> {
            try {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("titulo", orcamento.getValor());
                requestBody.put("descricao", orcamento.getObservacao());
                //requestBody.put("id_cliente", sessionManager.getUserId());

                String resposta = apiClient.requestApiSync(HttpMethod.PUT, "orcamentos/" + orcamento.getId() , requestBody);
    
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

public static void aprovarOrcamento(String formaPagamento, int id, DataCallback<String> callback) {
    new Thread(() -> {
            try {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("form_pag", formaPagamento);
                //requestBody.put("id_cliente", sessionManager.getUserId());

                String resposta = apiClient.requestApiSync(HttpMethod.PUT, "orcamentos/" + id + "/aprovado", requestBody);
    
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
