package com.example.appplanetario;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class ActPlaneta extends AppCompatActivity {
    private TextView txtID;
    private TextView txtNome;
    private TextView txtMassa;
    private TextView txtTamanho;
    private TextView txtGravidade;
    private TextView txtComposicao;

    private Planeta planeta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_planeta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setTitle("Planeta");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtID = findViewById(R.id.txt_id);
        txtNome = findViewById(R.id.txt_nome);
        txtTamanho = findViewById(R.id.txt_tamanho);
        txtMassa = findViewById(R.id.txt_massa);
        txtGravidade = findViewById(R.id.txt_gravidade);
        txtComposicao = findViewById(R.id.txt_comp);

        planeta = (Planeta) getIntent().getSerializableExtra("planeta");

        txtID.setText("ID: "+String.valueOf(planeta.getId()));
        txtNome.setText("Nome: "+planeta.getNome());
        txtMassa.setText("Massa: "+String.valueOf(planeta.getMassa()));
        txtTamanho.setText("Tamanho: "+String.valueOf(planeta.getTamanho()));
        txtGravidade.setText("Gravidade: "+String.valueOf(planeta.getGravidade()));
        String compos = "";
        for(int i=0; i<planeta.composicao.size(); i++){
            compos = compos + " "+planeta.composicao.get(i);
        }
        txtComposicao.setText("Composição:"+compos);

    }
}
