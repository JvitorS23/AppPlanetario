package com.example.appplanetario;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActAdicionarGalaxia extends AppCompatActivity {

    private String operacao;
    private Button btn;
    private EditText form_nome;
    private EditText form_id;
    private EditText form_qtde_sistemas;
    private EditText form_dist_terra;
    private Galaxia galaxia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adicionar_galaxia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);

        operacao = "Operação";
        operacao = (String) getIntent().getSerializableExtra("operacao");

        toolbar.setTitle(operacao+" Galáxia");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        form_nome = findViewById(R.id.edtNome);
        form_id = findViewById(R.id.edtID);
        form_qtde_sistemas = findViewById(R.id.edt_qtde_sistemas);
        form_dist_terra = findViewById(R.id.edt_dist_terra);

        if(operacao.equals("Adicionar")){
            findViewById(R.id.btn_modificar).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_adicionar);
            btn.setVisibility(View.VISIBLE);

        }

        if(operacao.equals("Modificar")){
            findViewById(R.id.btn_adicionar).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_modificar);
            btn.setVisibility(View.VISIBLE);

            galaxia = (Galaxia) getIntent().getExtras().getSerializable("galaxia");
            form_nome.setText(galaxia.getNome());
            form_id.setText(""+galaxia.getId());
            form_qtde_sistemas.setText(""+galaxia.getQtd_sistemas());
            form_dist_terra.setText(""+galaxia.getDist_terra());


        }


    }

    public void clickBtnAdicionarGalaxia(View view){
        if(validaCampos()){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Galáxia Adicionada");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarGalaxia.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();

        }

    }

    public void clickBtnModificarGalaxia(View view){

        if(validaCampos()){
            //modificar galáxia no banco
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Galáxia Modificada");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarGalaxia.this, Act_Inicio.class);
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
                if(isCampoVazio(form_qtde_sistemas.getText().toString())){
                    form_qtde_sistemas.requestFocus();
                    Toast.makeText(this, "Campo Quantidade de Sistemas vazio", Toast.LENGTH_SHORT).show();
                    valid = false;
                }else{
                    if(isCampoVazio(form_dist_terra.getText().toString())){
                        form_dist_terra.requestFocus();
                        Toast.makeText(this, "Campo Distância da terra vazio", Toast.LENGTH_SHORT).show();
                        valid = false;
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
