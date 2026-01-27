package com.br.ucs.tiajudaandroid.viewmodel;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel; // <-- Mude para AndroidViewModel
import androidx.annotation.NonNull;

import com.br.ucs.tiajudaandroid.data.DataCallback;
import com.br.ucs.tiajudaandroid.data.OrcamentoData;
import com.br.ucs.tiajudaandroid.model.Orcamento;

import java.util.List;

public class OrcamentoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Orcamento>> orcamentos = new MutableLiveData<>();
    private MutableLiveData<String> erro = new MutableLiveData<>();

    public OrcamentoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Orcamento>> getOrcamentos() {
        return orcamentos;
    }

    public LiveData<String> getErro() {
        return erro;
    }

    public void fetchOrcamentosFromApi() {
        OrcamentoData.buscarOrcamentos(getApplication().getApplicationContext(), new DataCallback<List<Orcamento>>() {
            @Override
            public void onSuccess(List<Orcamento> data) {
                orcamentos.postValue(data);
            }

            @Override
            public void onFailure(Exception e) {
                erro.postValue("Erro ao buscar orçamentos: " + e.getMessage());
            }
        });
    }
}
