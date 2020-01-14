package com.example.appplanetario.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdicionarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AdicionarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tela de adicionar Astro");
    }

    public LiveData<String> getText() {
        return mText;
    }
}