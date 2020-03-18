package com.example.appplanetario.ui.listar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.appplanetario.R;

public class ListarFragment extends Fragment {
    private Button btnPlaneta;
    private Button btnSistema;
    private Button btnGalaxia;
    private Button btnSatelite;
    private Button btnEstrela;
    private Button btnSistemasDeUmaGalaxia;
    private Button btnBuracosNegros;
    private Button btnPlanetaSistema;
    private Button btnEstrelaSistema;
    private Button btnOrbitaPlaneta;
    private Button btnOrbitaSatelite;
    private Button btnOrbitaEstrela;

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // desenha interface
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listar, container, false);

        btnPlaneta = root.findViewById(R.id.btn_planeta);
        btnPlaneta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, ActListar.class);
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
                    Intent it = new Intent(act, ActListar.class);
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
                    Intent it = new Intent(act, ActListar.class);
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
                    Intent it = new Intent(act, ActListar.class);
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
                    Intent it = new Intent(act, ActListar.class);
                    it.putExtra("tipo", "Estrela");
                    startActivity(it);
                }
            }
        });


        btnSistemasDeUmaGalaxia = root.findViewById(R.id.btn_sistemas_de_uma_galaxia);
        btnSistemasDeUmaGalaxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, ActListarSistemasDeUmaGalaxia.class);
                    it.putExtra("tipo", "Sistemas de uma Galáxia");
                    startActivity(it);
                }
            }
        });

        btnBuracosNegros = root.findViewById(R.id.btn_buracos_negros);
        btnBuracosNegros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, ActListarBuracosNegros.class);
                    it.putExtra("tipo", "Buracos Negros");
                    startActivity(it);
                }
            }
        });

        btnPlanetaSistema = root.findViewById(R.id.btn_planetas_sistema);
        btnPlanetaSistema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, com.example.appplanetario.ui.listar.ActListarPlanetasDeSistema.class);
                    it.putExtra("tipo", "Planetas-Sistema");
                    startActivity(it);
                }
            }
        });

        btnEstrelaSistema = root.findViewById(R.id.btn_estrela_sistema);
        btnEstrelaSistema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, ActListarEstrelasDeSistema.class);
                    it.putExtra("tipo", "Buracos Negros");
                    startActivity(it);
                }
            }
        });

        btnOrbitaPlaneta = root.findViewById(R.id.btn_orbita_planeta);
        btnOrbitaPlaneta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, ActListarOrbitaPlaneta.class);
                    it.putExtra("tipo", "Buracos Negros");
                    startActivity(it);
                }
            }
        });

        btnOrbitaEstrela = root.findViewById(R.id.btn_orbita_estrela);
        btnOrbitaEstrela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = getActivity();

                if (act != null) {
                    Intent it = new Intent(act, ActListarOrbitaEstrela.class);
                    it.putExtra("tipo", "Buracos Negros");
                    startActivity(it);
                }
            }
        });


        return root;
    }

}