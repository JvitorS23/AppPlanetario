package com.example.appplanetario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Act_Modificar extends AppCompatActivity implements ConsultaBanco.OnConsultaCompletedListener{
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
        System.out.println(tipo);
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
    public void botaoModificar(View view){
        if(edtId.getText().toString().trim().length()<1) {
            edtId.requestFocus();
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }else{
            if(tipo.equals("Planeta") && edtId.getText().toString().trim().length() > 0) {
                //Procurar planeta no banco
                Intent it = new Intent(Act_Modificar.this, ActAdicionarPlaneta.class);

                //Se encontrar instanciar planeta e passar para a activity modificar planeta
                ArrayList<String> composicao = new ArrayList();
                composicao.add("Carbono");
                composicao.add("Hidrogênio");
                Planeta planeta = new Planeta(Integer.parseInt(edtId.getText().toString()), "Terra",
                        1000.0f, 50.0f, 9.8f, composicao);
                it.putExtra("planeta", planeta);
                it.putExtra("operacao", "Modificar");
                startActivity(it);
            }
            if (tipo.equals("Estrela") && edtId.getText().toString().trim().length() > 0) {
                //Procurar planeta no banco
                //Se encontrar instanciar estrela e passar para a activity modificar estrela
                ConsultaBanco consultaBanco = new ConsultaBanco(this, Integer.parseInt(edtId.getText().toString()), tipo);
                consultaBanco.setOnConsultaCompletedListener(Act_Modificar.this);
                consultaBanco.execute();

            }

            if (tipo.equals("Galáxia") && edtId.getText().toString().trim().length() > 0) {
                //Procurar satélite no banco
                //Se encontrar instanciar galaxia e passar para a activity modificar galaxia
                Intent it = new Intent(Act_Modificar.this, ActAdicionarGalaxia.class);
                Galaxia galaxia = new Galaxia(Integer.parseInt(edtId.getText().toString()), "Via-láctea",
                        1000, 100000f);
                it.putExtra("galaxia", galaxia);
                it.putExtra("operacao", "Modificar");
                startActivity(it);
            }

            if (tipo.equals("Satélite Natural") && edtId.getText().toString().trim().length() > 0) {
                //Procurar satélite no banco
                //Se encontrar instanciar satélite e passar para a activity modificar satelite
                Intent it = new Intent(Act_Modificar.this, ActAdicionarSatelite.class);
                ArrayList<String> composicao = new ArrayList();
                composicao.add("Carbono");
                composicao.add("Hidrogênio");
                SateliteNatural satelite = new SateliteNatural(Integer.parseInt(edtId.getText().toString()), "Lua",
                        1000.0f, 100000f, composicao);
                it.putExtra("satelite", satelite);
                it.putExtra("operacao", "Modificar");
                startActivity(it);
            }

            if (tipo.equals("Sistema Planetário") && edtId.getText().toString().trim().length() > 0) {
                //Procurar sistema no banco
                //Se encontrar instanciar sistema e passar para a activity modificar sistema
                Intent it = new Intent(Act_Modificar.this, ActAdicionarSistema.class);
                SistemaPlanetario sistema = new SistemaPlanetario(Integer.parseInt(edtId.getText().toString()), "Sistema Solar",
                        8, 100000, 90);
                it.putExtra("sistema", sistema);
                it.putExtra("operacao", "Modificar");
                startActivity(it);
            }
        }
    }


    @Override
    public void onConsultaCompleted(String result, ResultSet resultado) throws SQLException {
        if(result.equals("OK")){
            switch (tipo){
                case "Estrela":
                    Intent it = new Intent(Act_Modificar.this, ActAdicionarEstrela.class);
                    Estrela aux = null;
                    while(true){
                        if(!resultado.next())
                            break;

                        aux = new Estrela(resultado.getInt("id_estrela"), resultado.getString("nome_estrela"),
                                resultado.getInt("idade_estrela") , resultado.getFloat("dist_terra"),
                                resultado.getFloat("gravidade_estrela"), resultado.getFloat("tam_estrela"),
                                resultado.getString("tipo_estrela"), resultado.getBoolean("morte"));
                    }

                    Estrela estrela = aux;
                    it.putExtra("estrela", estrela);
                    it.putExtra("operacao", "Modificar");
                    startActivity(it);
            }
        }
    }
}
