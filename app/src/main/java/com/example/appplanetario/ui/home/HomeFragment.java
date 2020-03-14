package com.example.appplanetario.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appplanetario.R;

public class HomeFragment extends Fragment {
    public TextView user;

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    // desenha interface
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

}