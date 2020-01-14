package com.example.appplanetario.ui.sobre;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SobreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SobreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Versão: 1.0\n" +
                "Desenvolvido por:\n" +
                "Filipe Nascimento e José Vitor");

    }

    public LiveData<String> getText() {
        return mText;
    }
}