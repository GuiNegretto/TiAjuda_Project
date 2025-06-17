package com.br.ucs.tiajudaandroid.data;

public interface DataCallback<T> {
    void onSuccess(T result);
    void onFailure(Exception e);
}