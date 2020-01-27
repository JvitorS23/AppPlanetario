package com.example.appplanetario;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Act_Consulta extends AppCompatActivity {
    private TextView txtID;
    private EditText edtId;
    public String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_consulta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        tipo = "Entidade";
        tipo = (String) getIntent().getSerializableExtra("tipo");
        toolbar.setTitle("Consulta " + tipo);
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

    public void clickBtnConsultar(View view){
        if(tipo.equals("Planeta") && edtId.getText().toString().trim().length()>0){
            Intent it = new Intent(Act_Consulta.this, ActPlaneta.class);

            //validar campo id
            //buscar dados do planeta no banco
            //Se não encontrar criar dialog box

            //Se encontrar instanciar planeta e passar para a activity planeta
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
