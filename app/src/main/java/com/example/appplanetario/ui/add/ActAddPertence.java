package com.example.appplanetario.ui.add;

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

import com.example.appplanetario.banco.AddPertencimentoBackground;
import com.example.appplanetario.banco.ConsultaAstrosBackground;
import com.example.appplanetario.R;
import com.example.appplanetario.ui.Act_Inicio;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActAddPertence extends AppCompatActivity implements ConsultaAstrosBackground.OnConsultaCompletedListener, AddPertencimentoBackground.OnAddPertencimentoCompletedListener {

    private EditText edt_id_pertencido;
    private EditText edt_id_sistema;
    private Button btnAdd;
    private RadioGroup radio_group;
    private RadioButton escolhido;
    public boolean controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_add_pertence);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setTitle("Adicionar Pertencimento");
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

    public void clickBtnAddPertencimento(View view){

        String id_pertencido = edt_id_pertencido.getText().toString();
        String id_sistema = edt_id_sistema.getText().toString();
        if(id_pertencido.trim().length()>0 || id_sistema.trim().length()>0){
            escolhido = (RadioButton) findViewById(radio_group.getCheckedRadioButtonId());

            if(escolhido.getText().toString().equals("Planeta")){
                controller = true;
                ConsultaAstrosBackground consulta = null;
                consulta = new ConsultaAstrosBackground(this, "Planeta");
                consulta.setOnConsultaCompletedListener(this);
                consulta.execute(id_pertencido);
            }else{
                controller = true;
                ConsultaAstrosBackground consulta = null;
                consulta = new ConsultaAstrosBackground(this, "Estrela");
                consulta.setOnConsultaCompletedListener(this);
                consulta.execute(id_pertencido);

            }



        }else{
            Toast.makeText(this, "Algum campo está inválido!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onConsultaCompleted(String result, ResultSet resultado) throws SQLException {

        if(result.equals("ERRO-CONEXAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha na conexão!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("ERRO-CONSULTA")){
            if(controller){
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Erro!");
                dlg.setMessage("Planeta ou estrela não encontrado!");
                dlg.setNeutralButton("OK", null);
                dlg.show();
                this.edt_id_pertencido.requestFocus();

            }else{
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Erro!");
                dlg.setMessage("Sistema Planetário não encontrado!");
                dlg.setNeutralButton("OK", null);
                dlg.show();
                this.edt_id_sistema.requestFocus();
            }

        }
        if(result.equals("OK")) {
            if(controller){
                controller = false;
                ConsultaAstrosBackground consulta = null;
                consulta = new ConsultaAstrosBackground(this, "Sistema Planetário");
                consulta.setOnConsultaCompletedListener(this);
                consulta.execute(this.edt_id_sistema.getText().toString());


            }else{
                AddPertencimentoBackground add_pertence = new AddPertencimentoBackground(this);
                add_pertence.setOnAddPertencimentoCompletedListener(this);
                add_pertence.execute(escolhido.getText().toString(), edt_id_sistema.getText().toString(), edt_id_pertencido.getText().toString());

            }
        }

    }

    @Override
    public void onAddPertencimentoCompleted(String result) {

        if(result.equals("ERRO-CONEXAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha na conexão!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("ERRO-INSERCAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha ao inserir!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
          
        }

        if(result.equals("OK")) {

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Pertencimento adicionado!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAddPertence.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();

        }



    }
}
