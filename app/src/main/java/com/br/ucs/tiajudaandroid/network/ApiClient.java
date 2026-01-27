package com.br.ucs.tiajudaandroid.network;

import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.Map;
import java.util.HashMap;
import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException; // <-- IMPORTANTE!
import com.br.ucs.tiajudaandroid.network.HttpMethod;
// Remova os imports antigos de java.net.http

public class ApiClient {

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json");
    private static final String BASE_URL = "https://tiajuda-api-nodejs.onrender.com/"; // <-- COLOQUE A URL BASE DA SUA API AQUI


    public interface LoginCallback {
        void onResult(String resposta);
        void onError(Exception e);
    }

    public String requestApiSync(HttpMethod method, String endpoint) throws IOException {
        return requestApiSync(method, endpoint, null);
    }
    // Método para fazer a requisição
    public String requestApiSync(HttpMethod method, String endpoint, Map<String, Object> requestData) throws IOException {
        String url = BASE_URL + endpoint;
        Request.Builder requestBuilder = new Request.Builder().url(url);

        String jsonBody = gson.toJson(requestData);
        RequestBody body = null;

        if (jsonBody != null && (method == HttpMethod.POST || method == HttpMethod.PUT)) {
            body = RequestBody.create(jsonBody, JSON);
        }



        // Adiciona o verbo HTTP correto à requisição
        switch (method) {
            case GET:
                requestBuilder.get();
                break;
            case POST:
                requestBuilder.post(body);
                break;
            case PUT:
                requestBuilder.put(body);
                break;
            case DELETE:
                requestBuilder.delete();
                break;
        }

        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) 
            {
                throw new IOException("HTTP: " + response.code() + "\nErro: " + response.body().string());
            }

            return response.body().string();
        }
    }
}