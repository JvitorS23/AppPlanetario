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

public class ActRemover extends AppCompatActivity {

    private TextView txtID;
    private EditText edtId;
    public String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_remover);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        tipo = "Entidade";
        tipo = (String) getIntent().getSerializableExtra("tipo");
        toolbar.setTitle("Remover " + tipo);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtID = findViewById(R.id.txt_id);
        edtId = findViewById(R.id.edt_id);
        txtID.setText("ID "+tipo);


    }

    public void clickBtnRemover(View view){
        //buscar astro no banco


        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setMessage(tipo + " Removido");
        if(tipo.equals("Estrela")){
            dlg.setMessage(tipo + " Removida");
        }
        dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent it = new Intent(ActRemover.this, Act_Inicio.class);
                startActivity(it);
                finish();
            }
        });
        dlg.show();


    }


}
