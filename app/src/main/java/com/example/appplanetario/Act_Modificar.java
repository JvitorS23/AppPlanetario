package com.example.appplanetario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;


public class Act_Modificar extends AppCompatActivity {
    private TextView txtID;
    private EditText edtId;
    public String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_modificar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        tipo = "Entidade";
        tipo = (String) getIntent().getSerializableExtra("tipo");
        toolbar.setTitle("Modificar " + tipo);
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
    public void clickBtnModificar(View view){
        if(tipo.equals("Planeta") && edtId.getText().toString().trim().length()>0){
        //Procurar planeta no banco
        Intent it = new Intent(Act_Modificar.this, ActModificarPlaneta.class);
        //Se encontrar instanciar planeta e passar para a activity modificar planeta
        ArrayList<String> composicao = new ArrayList();
        composicao.add("Carbono");
        composicao.add("Hidrogênio");
        Planeta planeta = new Planeta(Integer.parseInt(edtId.getText().toString()), "Terra",
                1000.0f, 50.0f, 9.8f, composicao );
        it.putExtra("planeta", planeta);
        startActivity(it);

        }
    }



}
