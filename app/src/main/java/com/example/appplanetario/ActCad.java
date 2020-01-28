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

public class ActCad extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtPass1;
    private EditText edtPass2;
    private Button btnCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnCad   = (Button)findViewById(R.id.btn_cadastrar);
        edtUser  = (EditText)findViewById(R.id.userInputCad);
        edtPass1 = (EditText)findViewById(R.id.passInput2);
        edtPass2 = (EditText)findViewById(R.id.passInput1);


    }
    @Override
    public void onBackPressed() {
        finish();
    }

    public void clickBtnCadastrar(View view) {
        int  valid = validaCampos();


        if(valid==0){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Parabéns!");
            dlg.setMessage("Cadastro Realizado");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActCad.this, ActLogin.class);
                    startActivity(it);
                    finish();
                }
            });
//
            dlg.show();

        }else{
            if(valid==1){

                edtUser.requestFocus();
                Toast.makeText(this, "Campo usuário vazio", Toast.LENGTH_SHORT).show();
            }else{
                if(valid==2){

                    edtPass1.requestFocus();
                    Toast.makeText(this, "Campo senha vazio", Toast.LENGTH_SHORT).show();
                }else{
                    if(valid==3){

                        edtPass2.requestFocus();
                        Toast.makeText(this, "Campo senha vazio", Toast.LENGTH_SHORT).show();
                    }else{
                        if(valid==4){

                            edtPass1.requestFocus();
                            Toast.makeText(this, "As senhas não conferem", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

        }


    }

    public int validaCampos(){
        String usuario = edtUser.getText().toString();
        String senha1  = edtPass1.getText().toString();
        String senha2  = edtPass2.getText().toString();
        if(isCampoVazio(usuario)){
            return 1;
        }else{
            if(isCampoVazio(senha1)){
                return 2;
            }else{
                if(isCampoVazio(senha2)){
                    return 3;
                }else{
                    if(!senha1.equals(senha2)){
                        return 4;
                    }

                }
            }
        }
        return 0;
    }

    public boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }



}
