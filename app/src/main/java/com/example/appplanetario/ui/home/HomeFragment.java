package com.example.appplanetario.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.appplanetario.ui.add.ActAddOrbita;
import com.example.appplanetario.ui.add.ActAddPertence;
import com.example.appplanetario.ui.remover.ActRemoverOrbita;
import com.example.appplanetario.ui.remover.ActRemoverPertence;
import com.example.appplanetario.R;

public class HomeFragment extends Fragment {
    private Button btnAddPertence;
    private Button btnRemovePertence;
    private Button btnAddOrbita;
    private Button btnRemoverOrita;

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    // desenha interface
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btnAddPertence = root.findViewById(R.id.btn_add_pertencimento);
        btnAddPertence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, ActAddPertence.class);
                    startActivity(it);
                }
            }
        });

        btnRemovePertence = root.findViewById(R.id.btn_remover_pertencimento);
        btnRemovePertence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, ActRemoverPertence.class);
                    startActivity(it);
                }
            }
        });

        btnAddOrbita = root.findViewById(R.id.btn_add_orbita);
        btnAddOrbita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, ActAddOrbita.class);

                    startActivity(it);
                }
            }
        });

        btnRemoverOrita = root.findViewById(R.id.btn_remover_orbita);
        btnRemoverOrita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, ActRemoverOrbita.class);

                    startActivity(it);
                }
            }
        });

        return root;
    }

}