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
import android.widget.EditText;
import android.widget.TextView;

public class ActModificarPlaneta extends AppCompatActivity {
    private TextView txtID;
    private TextView txtNome;
    private EditText form_nome;
    private EditText form_id;
    private EditText form_massa;
    private EditText form_tamanho;
    private EditText form_gravidade;
    private EditText form_composicao;
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
        form_nome = findViewById(R.id.edtNome);
        form_id = findViewById(R.id.edtID);
        form_massa = findViewById(R.id.edtMassa);
        form_tamanho = findViewById(R.id.edtTamanho);
        form_gravidade = findViewById(R.id.edtGravidade);
        form_composicao = findViewById(R.id.edtComposicao);

        planeta = (Planeta) getIntent().getExtras().getSerializable("planeta");
        System.out.println(planeta.getMassa());


        txtID.setText("ID: "+String.valueOf(planeta.getId()));
        txtNome.setText("Nome: "+planeta.getNome());
        form_nome.setText(planeta.getNome());
        form_id.setText(""+planeta.getId());
        form_massa.setText(""+planeta.getMassa());
        form_tamanho.setText(""+planeta.getTamanho());
        form_gravidade.setText(""+planeta.getGravidade());
        String compos = "";
        for(int i=0; i<planeta.composicao.size(); i++){

            compos = compos +planeta.composicao.get(i)+" ";
        }
        form_composicao.setText(compos);
    }

    public void clickBtnModificarPlaneta(View view){
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage("Planeta Modificado");
        dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent it = new Intent(ActModificarPlaneta.this, Act_Inicio.class);
                startActivity(it);
                finish();
            }
        });
        dlg.show();

    }

}
