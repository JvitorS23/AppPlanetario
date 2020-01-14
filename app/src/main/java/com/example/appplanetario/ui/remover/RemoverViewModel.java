package com.example.appplanetario.ui.remover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RemoverViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RemoverViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tela de Remover Astro");
    }

    public LiveData<String> getText() {
        return mText;
    }
}