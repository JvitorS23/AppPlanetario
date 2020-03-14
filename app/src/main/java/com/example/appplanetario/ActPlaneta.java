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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActPlaneta extends AppCompatActivity {
    private TextView txtID;
    private TextView txtNome;
    private TextView txtMassa;
    private TextView txtTamanho;
    private TextView txtGravidade;
    private TextView txtComposicao;
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
        btn = findViewById(R.id.btn_remover);

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
                    Toast.makeText(getApplicationContext(), "Planeta removido!", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(ActPlaneta.this, Act_Inicio.class);
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
