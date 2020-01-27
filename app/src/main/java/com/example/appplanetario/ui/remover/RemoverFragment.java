package com.example.appplanetario.ui.remover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appplanetario.R;


public class RemoverFragment extends Fragment {

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // desenha interface
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_remover, container, false);
        return root;
    }
}