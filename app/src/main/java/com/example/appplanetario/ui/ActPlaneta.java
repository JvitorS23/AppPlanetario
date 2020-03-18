package com.example.appplanetario.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.appplanetario.Planeta;
import com.example.appplanetario.R;
import com.example.appplanetario.banco.RemoverAstrosBackground;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;

public class ActPlaneta extends AppCompatActivity implements RemoverAstrosBackground.OnRemoverCompletedListener {
    private TextView txtID;
    private TextView txtNome;
    private TextView txtMassa;
    private TextView txtTamanho;
    private TextView txtGravidade;
    private TextView txtComposicao;
    private TextView txtVelocidade;
    private String operacao;
    private Button btn;
    private Planeta planeta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_planeta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        operacao = "Operação";
        operacao = (String) getIntent().getSerializableExtra("operacao");
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
        txtVelocidade = findViewById(R.id.txt_velocidade);

        btn = findViewById(R.id.btn_remover);

        planeta = (Planeta) getIntent().getSerializableExtra("planeta");

        txtID.setText("ID: "+planeta.getId());
        txtNome.setText("Nome: "+planeta.getNome());
        txtMassa.setText("Massa: "+planeta.getPeso());
        txtTamanho.setText("Tamanho: "+planeta.getTamanho());
        txtGravidade.setText("Gravidade: "+planeta.getGravidade());
        txtVelocidade.setText("Velocidade de Rotação: "+planeta.getVel_rotacao());

        String compos = "";
        for(int i=0; i<planeta.composicao.length; i++){
            if(operacao.equals("Listar")){
                if(i == 0){
                    compos = compos + " "+planeta.composicao[i];
                }else{
                    compos = compos + ", "+planeta.composicao[i];
                }
            }else{//Esse else apenas server para deixar como antes, caso alguma outra classe use essa linhas abaixo
                compos = compos + " "+planeta.composicao[i];
            }
        }
        txtComposicao.setText("Composição:"+compos);

        if(operacao.equals("Consultar")){
            btn.setVisibility(View.INVISIBLE);

        }

        if(operacao.equals("Remover")){
           btn.setVisibility(View.VISIBLE);

        }

        if(operacao.equals("Listar")){
            btn.setVisibility(View.INVISIBLE);
        }

    }

    public void clickBtnRemover(View view){

        final Context a = this;
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("Tem certeza que deseja remover ?");
        dlg.setNegativeButton("Cancelar", null);
        dlg.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which){
                RemoverAstrosBackground remover = new RemoverAstrosBackground(a, "Planeta");
                remover.setOnRemoverCompletedListener((RemoverAstrosBackground.OnRemoverCompletedListener) a);
                remover.execute(planeta.getId()+"");
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
            dlg.setMessage("Falha ao remover Planeta!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Planeta Removido!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActPlaneta.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }


    }
}
