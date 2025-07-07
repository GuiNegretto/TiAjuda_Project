package com.br.ucs.tiajudaandroid.data;

import android.content.Context;

import com.br.ucs.tiajudaandroid.model.Orcamento;
import com.br.ucs.tiajudaandroid.model.Usuario;
import com.br.ucs.tiajudaandroid.network.ApiClient;
import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.network.HttpMethod;
import com.br.ucs.tiajudaandroid.utils.SessionManager;
import com.br.ucs.tiajudaandroid.model.Avaliacao;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Type;


public class AvaliacaoData {

    private static ApiClient apiClient = new ApiClient();
    private static Gson gson = new Gson();

    private SessionManager sessionManager;



    public static void enviarAvaliacao(Context context, Avaliacao avaliacao, DataCallback<String> callback) {
      SessionManager sessionManager = new SessionManager(context);

      new Thread(() -> {
          try {
              Map<String, Object> requestBody = new HashMap<>();
              requestBody.put("id_servico", avaliacao.getIdServico());
              requestBody.put("nota", avaliacao.getNota());
              requestBody.put("comentario", avaliacao.getComentario());

              String resposta = apiClient.requestApiSync(HttpMethod.POST, "avaliacoes", requestBody);

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
