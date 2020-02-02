package com.example.appplanetario.ui.modificar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.appplanetario.Act_Modificar;
import com.example.appplanetario.R;

public class ModificarFragment extends Fragment {
    private Button btnPlaneta;
    private Button btnSistema;
    private Button btnGalaxia;
    private Button btnSatelite;
    private Button btnEstrela;

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // desenha interface
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_modificar, container, false);

        btnPlaneta = root.findViewById(R.id.btn_planeta);
        btnPlaneta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();
                if (act != null) {
                    Intent it = new Intent(act, Act_Modificar.class);
                    it.putExtra("tipo", "Planeta");
                    startActivity(it);
                }
            }
        });

        btnSistema = root.findViewById(R.id.btn_sistema);
        btnSistema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, Act_Modificar.class);
                    it.putExtra("tipo", "Sistema Planetário");
                    startActivity(it);
                }
            }
        });

        btnGalaxia = root.findViewById(R.id.btn_galaxia);
        btnGalaxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, Act_Modificar.class);
                    it.putExtra("tipo", "Galáxia");
                    startActivity(it);
                }
            }
        });

        btnSatelite = root.findViewById(R.id.btn_satelite);
        btnSatelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, Act_Modificar.class);
                    it.putExtra("tipo", "Satélite Natural");
                    startActivity(it);
                }
            }
        });

        btnEstrela = root.findViewById(R.id.btn_estrela);
        btnEstrela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, Act_Modificar.class);
                    it.putExtra("tipo", "Estrela");
                    startActivity(it);
                }
            }
        });
        return root;
    }
}