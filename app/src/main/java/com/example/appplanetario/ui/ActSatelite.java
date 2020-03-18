package com.example.appplanetario.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.appplanetario.R;
import com.example.appplanetario.banco.RemoverAstrosBackground;
import com.example.appplanetario.SateliteNatural;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;

public class ActSatelite extends AppCompatActivity implements RemoverAstrosBackground.OnRemoverCompletedListener {
    private String operacao;
    private TextView txtID;
    private TextView txtNome;
    private TextView txtMassa;
    private TextView txtTamanho;
    private TextView txtComposicao;
    private Button btn;
    private SateliteNatural satelite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_satelite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        operacao = "Operação";
        operacao = (String) getIntent().getSerializableExtra("operacao");
        toolbar.setTitle("Satélite Natural");
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
        txtComposicao = findViewById(R.id.txt_comp);
        btn = findViewById(R.id.btn_remover);

        satelite = (SateliteNatural) getIntent().getSerializableExtra("satelite");

        txtID.setText("ID: "+String.valueOf(satelite.getId()));
        txtNome.setText("Nome: "+satelite.getNome());
        txtMassa.setText("Massa: "+String.valueOf(satelite.getPeso()));
        txtTamanho.setText("Tamanho: "+String.valueOf(satelite.getTamanho()));
        String compos = "";
        for(int i=0; i<satelite.composicao.length; i++){
            if(operacao.equals("Listar")){
                if(i == 0){
                    compos = compos + " "+satelite.composicao[i];
                }else{
                    compos = compos + ", "+satelite.composicao[i];
                }
            }else{
                compos = compos + " "+satelite.composicao[i];
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
                RemoverAstrosBackground remover = new RemoverAstrosBackground(a, "Satélite Natural");
                remover.setOnRemoverCompletedListener((RemoverAstrosBackground.OnRemoverCompletedListener) a);
                remover.execute(satelite.getId()+"");
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
            dlg.setMessage("Falha ao remover Satélite Natural!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Satélite Natural Removido!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActSatelite.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }


    }


}
