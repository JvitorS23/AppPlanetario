package com.example.appplanetario;

import android.content.Context;
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

import java.sql.SQLException;

public class ActGalaxia extends AppCompatActivity implements RemoverAstrosBackground.OnRemoverCompletedListener{
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
        final Context a = this;
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("Tem certeza que deseja remover ?");
        dlg.setNegativeButton("Cancelar", null);
        dlg.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which){
                RemoverAstrosBackground remover = new RemoverAstrosBackground(a, "Galáxia");
                remover.setOnRemoverCompletedListener((RemoverAstrosBackground.OnRemoverCompletedListener) a);
                remover.execute(galaxia.getId()+"");
            }
        });
        dlg.show();
    }

    @Override
    public void onRemoverCompleted(String result) throws SQLException {

        if(result.equals("ERRO-CONEXAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha na conexão!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        if(result.equals("ERRO-REMOVER")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha ao remover Galáxia!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Galáxia Removida!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActGalaxia.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }


    }


}
