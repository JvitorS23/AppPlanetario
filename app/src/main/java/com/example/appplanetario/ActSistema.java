package com.example.appplanetario;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class ActSistema extends AppCompatActivity implements RemoverAstrosBackground.OnRemoverCompletedListener{

    private TextView txtID;
    private TextView txtNome;
    private TextView txtQtdePlanetas;
    private TextView txtQtdeEstrelas;
    private TextView txtIDGalaxia;
    private TextView txtIdade;
    private Button btn;
    private String operacao;
    private SistemaPlanetario sistema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sistema);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        operacao = "Operação";
        operacao = (String) getIntent().getSerializableExtra("operacao");
        toolbar.setTitle("Sistema Planetário");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtID = findViewById(R.id.txt_id);
        txtNome = findViewById(R.id.txt_nome);
        txtIDGalaxia = findViewById(R.id.txt_id_galaxia);
        txtQtdePlanetas = findViewById(R.id.txt_qtde_planetas);
        txtQtdeEstrelas = findViewById(R.id.txt_qtde_estrelas);
        txtIdade = findViewById(R.id.txt_idade);
        btn = findViewById(R.id.btn_remover);

        sistema = (SistemaPlanetario) getIntent().getSerializableExtra("sistema");

        txtIDGalaxia.setText("ID Galáxia: "+sistema.getId_galaxia());
        txtID.setText("ID: "+sistema.getId());
        txtNome.setText("Nome: "+sistema.getNome()+"");
        txtQtdePlanetas.setText("Quantidade de Planetas: " +sistema.getQtde_planetas());
        txtQtdeEstrelas.setText("Quantidade de estrelas: "+sistema.getQtde_estrelas());
        txtIdade.setText("Idade: "+sistema.getIdade());

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
                RemoverAstrosBackground remover = new RemoverAstrosBackground(a, "Sistema Planetário");
                remover.setOnRemoverCompletedListener((RemoverAstrosBackground.OnRemoverCompletedListener) a);
                remover.execute(sistema.getId()+"");
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
            dlg.setMessage("Falha ao remover Sistema Planetário!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Sistema Planetário Removido!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActSistema.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }


    }

}
