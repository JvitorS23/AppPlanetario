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

public class ActAdicionarSatelite extends AppCompatActivity {

    private String operacao;
    private Button btn;
    private EditText form_nome;
    private EditText form_id;
    private EditText form_massa;
    private EditText form_tamanho;
    private EditText form_composicao;
    private SateliteNatural satelite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adicionar_satelite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        operacao = "";
        operacao = (String) getIntent().getSerializableExtra("operacao");

        toolbar.setTitle(operacao + " Satélite Natural");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        form_nome = findViewById(R.id.edtNome);
        form_id = findViewById(R.id.edtID);
        form_tamanho = findViewById(R.id.edt_tamanho);
        form_massa = findViewById(R.id.edt_massa);
        form_composicao = findViewById(R.id.edt_composicao);

        if(operacao.equals("Adicionar")){
            findViewById(R.id.btn_modificar).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_adicionar);
            btn.setVisibility(View.VISIBLE);

        }
        if(operacao.equals("Modificar")) {
            findViewById(R.id.btn_adicionar).setVisibility(View.INVISIBLE);
            btn = (Button) findViewById(R.id.btn_modificar);
            btn.setVisibility(View.VISIBLE);

            satelite = (SateliteNatural) getIntent().getExtras().getSerializable("satelite");
            form_nome.setText(satelite.getNome());
            form_id.setText(""+satelite.getId());
            form_massa.setText(""+satelite.getMassa());
            form_tamanho.setText(""+satelite.getTamanho());
            String compos = "";
            for(int i=0; i<satelite.composicao.size(); i++){

                compos = compos +satelite.composicao.get(i)+", ";
            }
            form_composicao.setText(compos);

        }
    }

    public void clickBtnAdicionarSatelite(View view){
        if(validaCampos()){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Satélite Natural Adicionado");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarSatelite.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }

    }

    public void clickBtnModificarSatelite(View view){

        if(validaCampos()){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Satélite Natural Modificado");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarSatelite.this, Act_Inicio.class);
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
                        if(isCampoVazio(form_composicao.getText().toString())){
                            form_composicao.requestFocus();
                            Toast.makeText(this, "Campo composição vazio", Toast.LENGTH_SHORT).show();
                            valid = false;
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
