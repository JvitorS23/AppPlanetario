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

public class ActEstrela extends AppCompatActivity implements RemoverAstrosBackground.OnRemoverCompletedListener{
    private String  operacao;
    private TextView txtID;
    private TextView txtNome;
    private TextView txtDistTerra;
    private TextView txtTamanho;
    private TextView txtGravidade;

    private TextView txtIdade;
    private TextView txtTipo;
    private Button btn;
    private Estrela estrela;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_estrela);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        operacao = "Operação";
        operacao = (String) getIntent().getSerializableExtra("operacao");
        toolbar.setTitle("Estrela");
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
        txtDistTerra = findViewById(R.id.txt_dist_terra);
        txtGravidade = findViewById(R.id.txt_gravidade);

        txtIdade = findViewById(R.id.txt_idade);
        txtTipo = findViewById(R.id.txt_tipo);
        btn = findViewById(R.id.btn_remover);

        estrela = (Estrela) getIntent().getSerializableExtra("estrela");

        txtID.setText("ID: "+estrela.getId()+"");
        txtNome.setText("Nome: "+estrela.getNome());
        txtTamanho.setText("Tamanho "+estrela.getTamanho());
        txtDistTerra.setText("Distância da Terra: "+estrela.getDist_terra());
        txtGravidade.setText("Gravidade: "+estrela.getGravidade()+"");

        txtIdade.setText("Idade: "+estrela.getIdade()+"");
        txtTipo.setText("Tipo: "+estrela.getTipo_estrela());

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
                RemoverAstrosBackground remover = new RemoverAstrosBackground(a, "Estrela");
                remover.setOnRemoverCompletedListener((RemoverAstrosBackground.OnRemoverCompletedListener) a);
                remover.execute(estrela.getId()+"");
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
            dlg.setMessage("Falha ao remover Estrela!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Estrela Removida!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActEstrela.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }


    }


}
