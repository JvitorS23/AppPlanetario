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
import android.widget.Toast;

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
        toolbar.setTitle("Consultar " + tipo);
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
        if(edtId.getText().toString().trim().length()<1) {
            edtId.requestFocus();
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }else{
            switch (tipo) {
                case "Planeta":
                    //Procurar planeta no banco
                    //Se encontrar instanciar planeta e passar para a activity planeta
                    Intent it = new Intent(Act_Consulta.this, ActPlaneta.class);
                    ArrayList<String> composicao = new ArrayList();
                    composicao.add("Carbono");
                    composicao.add("Hidrogênio");
                    Planeta planeta = new Planeta(Integer.parseInt(edtId.getText().toString()), "Terra",
                            1000.0f, 50.0f, 9.8f, composicao);
                    it.putExtra("planeta", planeta);
                    it.putExtra("operacao", "Consultar");
                    startActivity(it);
                    break;
                case "Galáxia":
                    //Procurar galxia no banco
                    //Se encontrar instanciar galaxia e passar para a activity modificar galaxia
                    Intent it2 = new Intent(Act_Consulta.this, ActGalaxia.class);
                    Galaxia galaxia = new Galaxia(Integer.parseInt(edtId.getText().toString()), "Via-láctea",
                            1000, 100000f);
                    it2.putExtra("galaxia", galaxia);
                    it2.putExtra("operacao", "Consultar");
                    startActivity(it2);
                    break;
                case "Estrela":
                    //Procurar estrela no banco
                    // Se encontrar instanciar estrela e passar para a activity modificar estrela
                    Intent it3 = new Intent(Act_Consulta.this, ActEstrela.class);
                    Estrela estrela = new Estrela(Integer.parseInt(edtId.getText().toString()), "Sol",
                            1000, 50000f, 900.8f, 500, 41010, "Gigante Azul");
                    it3.putExtra("estrela", estrela);
                    it3.putExtra("operacao", "Consultar");
                    startActivity(it3);
                    break;
                case "Sistema Planetário":
                    //Procurar sistema no banco
                    //Se encontrar instanciar sistema e passar para a activity modificar sistema
                    Intent it4 = new Intent(Act_Consulta.this, ActSistema.class);
                    SistemaPlanetario sistema = new SistemaPlanetario(Integer.parseInt(edtId.getText().toString()), "Sistema Solar",
                            8, 100000, 90);
                    it4.putExtra("sistema", sistema);
                    it4.putExtra("operacao", "Consultar");
                    startActivity(it4);

                    break;
                case "Satélite Natural":
                    //Procurar satélite no banco
                    //Se encontrar instanciar satélite e passar para a activity modificar satelite
                    Intent it5 = new Intent(Act_Consulta.this, ActSatelite.class);
                    ArrayList<String> composicao2 = new ArrayList();
                    composicao2.add("Carbono");
                    composicao2.add("Hidrogênio");
                    SateliteNatural satelite = new SateliteNatural(Integer.parseInt(edtId.getText().toString()), "Lua",
                            1000.0f, 100000f, composicao2);
                    it5.putExtra("satelite", satelite);
                    it5.putExtra("operacao", "Consultar");
                    startActivity(it5);
                    break;
            }
        }



    }

}
