package com.example.appplanetario;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActAdicionarPlaneta extends AppCompatActivity {

    private String operacao;
    private Button btn;
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
        setContentView(R.layout.act_adicionarplaneta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);

        operacao = "Operação";
        operacao = (String) getIntent().getSerializableExtra("operacao");

        toolbar.setTitle(operacao +" Planeta");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        form_nome = findViewById(R.id.edt_nome);
        form_id = findViewById(R.id.edt_ID);
        form_massa = findViewById(R.id.edt_massa);
        form_tamanho = findViewById(R.id.edt_tamanho);
        form_gravidade = findViewById(R.id.edt_gravidade);
        form_composicao = findViewById(R.id.edt_composicao);

        if(operacao.equals("Adicionar")){
            findViewById(R.id.btn_modificar).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_adicionar);
            btn.setVisibility(View.VISIBLE);
        }
        if(operacao.equals("Modificar")){
            findViewById(R.id.btn_adicionar).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_modificar);
            btn.setVisibility(View.VISIBLE);
            planeta = (Planeta) getIntent().getExtras().getSerializable("planeta");
            form_nome.setText(planeta.getNome());
            form_id.setText(""+planeta.getId());
            form_massa.setText(""+planeta.getMassa());
            form_tamanho.setText(""+planeta.getTamanho());
            form_gravidade.setText(""+planeta.getGravidade());
            String compos = "";
            for(int i=0; i<planeta.composicao.size(); i++){

                compos = compos +planeta.composicao.get(i)+", ";
            }
            form_composicao.setText(compos);
        }

    }
    public void clickBtnAdicionarPlaneta(View view){
       if(validaCampos()){
          //add planeta no banco
           AlertDialog.Builder dlg = new AlertDialog.Builder(this);
           dlg.setMessage("Planeta Adicionado");
           dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   Intent it = new Intent(ActAdicionarPlaneta.this, Act_Inicio.class);
                   startActivity(it);
                   finish();
               }
           });
           dlg.show();
       }
    }

    public void clickBtnModificarPlaneta(View view){
        if(validaCampos()){
            //modificar o planeta no banco
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Planeta Modificado");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarPlaneta.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }
    }

    public boolean validaCampos(){
        boolean valid=true;
        if(isCampoVazio(form_nome.getText().toString())){
            form_nome.requestFocus();
            Toast.makeText(this, "Campo nome vazio", Toast.LENGTH_SHORT).show();
            valid = false;
        }else {
            if (isCampoVazio(form_id.getText().toString())) {
                form_id.requestFocus();
                Toast.makeText(this, "Campo id vazio", Toast.LENGTH_SHORT).show();
                valid = false;
            }else{
                if(isCampoVazio(form_tamanho.getText().toString())){
                    form_tamanho.requestFocus();
                    Toast.makeText(this, "Campo tamanho vazio", Toast.LENGTH_SHORT).show();
                    valid = false;
                }else{
                    if(isCampoVazio(form_massa.getText().toString())){
                        form_massa.requestFocus();
                        Toast.makeText(this, "Campo massa vazio", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }else{
                        if(isCampoVazio(form_gravidade.getText().toString())){
                            form_gravidade.requestFocus();
                            Toast.makeText(this, "Campo gravidade vazio", Toast.LENGTH_SHORT).show();
                            valid = false;
                        }else{
                            if(isCampoVazio(form_composicao.getText().toString())){
                                form_composicao.requestFocus();
                                Toast.makeText(this, "Campo composição vazio", Toast.LENGTH_SHORT).show();
                                valid = false;
                            }
                        }

                    }
                }
            }
        }
        return valid;
    }
    public boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }
}
