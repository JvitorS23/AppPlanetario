package com.example.appplanetario;

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

public class ActSistema extends AppCompatActivity {

    private TextView txtID;
    private TextView txtNome;
    private TextView txtQtdePlanetas;
    private TextView txtQtdeEstrelas;
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
        txtQtdePlanetas = findViewById(R.id.txt_qtde_planetas);
        txtQtdeEstrelas = findViewById(R.id.txt_qtde_estrelas);
        txtIdade = findViewById(R.id.txt_idade);
        btn = findViewById(R.id.btn_remover);

        sistema = (SistemaPlanetario) getIntent().getSerializableExtra("sistema");

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
        boolean achou = true;
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("Tem certeza que deseja remover ?");
        dlg.setNegativeButton("Cancelar", null);
        dlg.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which){
                boolean removeu = true;
                //removeu = removerPlaneta(id); remove o planeta do banco
                if(removeu){
                    Toast.makeText(getApplicationContext(), "Sistema Planetário removido!", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(ActSistema.this, Act_Inicio.class);
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
