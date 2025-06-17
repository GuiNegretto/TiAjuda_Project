package com.br.ucs.tiajudaandroid.data;

import android.content.Context;

import com.br.ucs.tiajudaandroid.model.Servico;
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


public class ServicoData {

    private static ApiClient apiClient = new ApiClient();
    private static Gson gson = new Gson();

    private SessionManager sessionManager;


    // Método que a Activity chama para iniciar a busca de dados
    public static void BuscaServicosCliente(Context context, DataCallback<List<Servico>> callback) {
        SessionManager sessionManager = new SessionManager(context);

      new Thread(() -> {
        try {

            String resposta = apiClient.requestApiSync(HttpMethod.GET , "servicos/id_cliente/" + sessionManager.getUserId() );

            Type listType = new TypeToken<List<Servico>>() {}.getType();
            List<Servico> servicos = gson.fromJson(resposta, listType);

            if (callback != null) {
                callback.onSuccess(servicos);
            }
        } catch (IOException e) {
            if (callback != null) {
                callback.onFailure(e);
            }
        }
}).start();
  }

  public static void adicionarServico(Context context, Servico servico, DataCallback<String> callback) {
    SessionManager sessionManager = new SessionManager(context);

    new Thread(() -> {
            try {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("id_cliente", sessionManager.getUserId());
                requestBody.put("titulo", servico.getTitulo());
                requestBody.put("descricao", servico.getDescricao());

                String resposta = apiClient.requestApiSync(HttpMethod.POST, "servicos", requestBody);
    
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

public static void alterarServico(Context context, Servico servico, DataCallback<String> callback) {
    new Thread(() -> {
            try {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("titulo", servico.getTitulo());
                requestBody.put("descricao", servico.getDescricao());
                //requestBody.put("id_cliente", sessionManager.getUserId());

                String resposta = apiClient.requestApiSync(HttpMethod.PUT, "servicos/" + servico.getId() , requestBody);
    
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
