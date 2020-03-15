package com.example.appplanetario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ActRemoverPertence extends AppCompatActivity implements RemoverPertenceBackground.OnRemoverPertenceCompletedListener {

    private EditText edt_id_pertencido;
    private EditText edt_id_sistema;
    private Button btnAdd;
    private RadioGroup radio_group;
    private RadioButton escolhido;
    public boolean controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_remover_pertence);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setTitle("Remover Pertencimento");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_id_pertencido = (EditText)findViewById(R.id.edt_id_pertencido);
        edt_id_sistema = (EditText) findViewById(R.id.edt_id_sistema);
        btnAdd = (Button) findViewById(R.id.btn_remover_pertencimento);
        radio_group = (RadioGroup) findViewById(R.id.radio_group_pertence);
        escolhido = findViewById(R.id.radio_planeta);
        escolhido.setChecked(true);
    }


    public void clickBtnRemoverPertencimento(View view){
        String id_pertencido = edt_id_pertencido.getText().toString();
        String id_sistema = edt_id_sistema.getText().toString();
        if(id_pertencido.trim().length()>0 || id_sistema.trim().length()>0){
            escolhido = (RadioButton) findViewById(radio_group.getCheckedRadioButtonId());
            if(escolhido.getText().toString().equals("Planeta")){
                RemoverPertenceBackground remover_pertence = new RemoverPertenceBackground(this, escolhido.getText().toString());
                remover_pertence.setOnRemoverPertenceCompletedListener(this);
                remover_pertence.execute(id_sistema, id_pertencido);
            }else{
                RemoverPertenceBackground remover_pertence = new RemoverPertenceBackground(this, escolhido.getText().toString());
                remover_pertence.setOnRemoverPertenceCompletedListener(this);
                remover_pertence.execute(id_sistema, id_pertencido);
            }
        }else{
            Toast.makeText(this, "Algum campo está inválido!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRemoverPertenceCompleted(String result) {
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
            dlg.setMessage("Falha ao remover!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Pertencimento Removido!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActRemoverPertence.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }
    }
}
