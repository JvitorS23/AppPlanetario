package com.example.appplanetario.ui.remover;

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


public class RemoverFragment extends Fragment {

    private RemoverViewModel removerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        removerViewModel =
                ViewModelProviders.of(this).get(RemoverViewModel.class);
        View root = inflater.inflate(R.layout.fragment_remover, container, false);
        final TextView textView = root.findViewById(R.id.text_remover);
        removerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}