package com.example.appplanetario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActLogin extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtPass;
    private Button btnCad;
    private Button  btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        getSupportActionBar().hide(); //Esta linha contém o código necessário  para não aparece toolbar.

        edtUser = (EditText) findViewById(R.id.inputUser);
        edtPass = (EditText) findViewById(R.id.inputPass);
        btnCad  = (Button)findViewById(R.id.btnCad);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        edtUser.clearFocus();
        edtPass.clearFocus();
    }

    public void telaCad(View view) {
        Intent it = new Intent(ActLogin.this, ActCad.class);
        startActivity(it);

    }

    public void clickBtnLogin(View view) {
        int i = validaCampos();
        if(i==0) {
            Intent it = new Intent(ActLogin.this, Act_Inicio.class);
            startActivity(it);
            finish();

        }else {
            if(i==1) {
                edtUser.requestFocus();
                Toast.makeText(this, "Campo usuário vazio", Toast.LENGTH_SHORT).show();
            }else{
                if(i==2){
                    edtPass.requestFocus();
                    Toast.makeText(this, "Campo senha vazio", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);

    }
    public int validaCampos(){
        String usuario = edtUser.getText().toString();
        String senha  = edtPass.getText().toString();

        if(isCampoVazio(usuario)){
            return 1;
        }else{
            if(isCampoVazio(senha)){
                return 2;
            }
        }
        return 0;
    }

    public boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

}

