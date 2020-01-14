package com.example.appplanetario.ui.consultar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConsultarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConsultarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tela de consulta");
    }

    public LiveData<String> getText() {
        return mText;
    }
}