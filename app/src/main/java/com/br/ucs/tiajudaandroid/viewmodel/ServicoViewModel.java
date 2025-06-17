package com.br.ucs.tiajudaandroid.viewmodel;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel; // <-- Mude para AndroidViewModel
import androidx.annotation.NonNull;

import com.br.ucs.tiajudaandroid.data.ServicoData;
import com.br.ucs.tiajudaandroid.model.Servico;
import com.br.ucs.tiajudaandroid.data.DataCallback;

import java.util.List;

public class ServicoViewModel extends AndroidViewModel  {

    private MutableLiveData<List<Servico>> servicosLiveData = new MutableLiveData<>();
    private MutableLiveData<String> erroLiveData = new MutableLiveData<>();

    public ServicoViewModel(@NonNull Application application) {
      super(application);
  }

    public LiveData<List<Servico>> getServicos() {
        return servicosLiveData;
    }

    public LiveData<String> getErro() {
        return erroLiveData;
    }

    // O método agora é muito mais simples!
    public void fetchServicosFromApi() {
        // Pede os dados ao repositório ServicoData
        ServicoData.BuscaServicosCliente(getApplication().getApplicationContext(), new DataCallback<List<Servico>>() {
            @Override
            public void onSuccess(List<Servico> servicos) {
                // A lista já vem pronta! Apenas atualiza o LiveData.
                servicosLiveData.postValue(servicos);
            }

            @Override
            public void onFailure(Exception e) {
                // Repassa o erro para o LiveData de erro.
                erroLiveData.postValue("Falha ao buscar dados: " + e.getMessage());
            }
        });
    }
}