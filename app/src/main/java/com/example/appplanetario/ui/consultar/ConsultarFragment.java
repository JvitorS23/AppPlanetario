package com.example.appplanetario.ui.consultar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appplanetario.R;
import com.example.appplanetario.ui.consultar.ConsultarViewModel;

public class ConsultarFragment extends Fragment {

    private ConsultarViewModel consultarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        consultarViewModel =
                ViewModelProviders.of(this).get(ConsultarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_consultar, container, false);
        final TextView textView = root.findViewById(R.id.text_consultar);
        consultarViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}