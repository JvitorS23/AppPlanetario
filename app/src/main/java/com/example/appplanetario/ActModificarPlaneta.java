package com.example.appplanetario;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class ActModificarPlaneta extends AppCompatActivity {
    private TextView txtID;
    private TextView txtNome;
    private Planeta planeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_modificar_planeta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setTitle("Modificar Planeta");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtID = findViewById(R.id.txt_id);
        txtNome = findViewById(R.id.txt_nome);

        planeta = (Planeta) getIntent().getSerializableExtra("planeta");


        txtID.setText("ID: "+String.valueOf(planeta.getId()));
        txtNome.setText("Nome: "+planeta.getNome());



    }

}
