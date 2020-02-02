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

public class ActEstrela extends AppCompatActivity {
    private String  operacao;
    private TextView txtID;
    private TextView txtNome;
    private TextView txtDistTerra;
    private TextView txtTamanho;
    private TextView txtGravidade;
    private TextView txtMassa;
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
        txtMassa = findViewById(R.id.txt_massa);
        txtIdade = findViewById(R.id.txt_idade);
        txtTipo = findViewById(R.id.txt_tipo);
        btn = findViewById(R.id.btn_remover);

        estrela = (Estrela) getIntent().getSerializableExtra("estrela");

        txtID.setText("ID: "+estrela.getId()+"");
        txtNome.setText("Nome: "+estrela.getNome());
        txtTamanho.setText("Tamanho "+estrela.getTamanho());
        txtDistTerra.setText("Distância da Terra: "+estrela.getDist_terra());
        txtGravidade.setText("Gravidade: "+estrela.getGravidade()+"");
        txtMassa.setText("Massa: "+estrela.getMassa()+"");
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
                    Intent it = new Intent(ActEstrela.this, Act_Inicio.class);
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
