package com.example.appplanetario;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActGalaxia extends AppCompatActivity {
    private TextView txtID;
    private TextView txtNome;
    private TextView txtQtdeSistemas;
    private TextView txtDistTerra;
    private Button btn;
    private String operacao;
    private Galaxia galaxia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_galaxia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        operacao = "Operação";
        operacao = (String) getIntent().getSerializableExtra("operacao");
        toolbar.setTitle("Galáxia");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn = findViewById(R.id.btn_remover);
        txtID = findViewById(R.id.txt_id);
        txtNome = findViewById(R.id.txt_nome);
        txtQtdeSistemas = findViewById(R.id.txt_qtde_sistemas);
        txtDistTerra = findViewById(R.id.txt_dist_terra);
        galaxia = (Galaxia) getIntent().getSerializableExtra("galaxia");

        txtID.setText("ID: "+galaxia.getId());
        txtNome.setText("Nome: "+galaxia.getNome());
        txtQtdeSistemas.setText("Quantidade de Sistemas: "+galaxia.getQtd_sistemas());
        txtDistTerra.setText("Distância da Terra: "+galaxia.getDist_terra());

        if(operacao.equals("Consultar")){
            btn.setVisibility(View.INVISIBLE);
        }

        if(operacao.equals("Remover")){
            btn.setVisibility(View.VISIBLE);
        }

    }

    public void clickBtnRemover(View view){
        boolean achou = true;
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("Tem certeza que deseja remover ?");
        dlg.setNegativeButton("Cancelar", null);
        dlg.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which){
                boolean removeu = true;
                //removeu = removerPlaneta(id); remove o planeta do banco
                if(removeu){
                    Toast.makeText(getApplicationContext(), "Estrela removida!", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(ActGalaxia.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Falha ao remover!", Toast.LENGTH_LONG).show();
                }
            }
        });
        dlg.show();
    }


}
