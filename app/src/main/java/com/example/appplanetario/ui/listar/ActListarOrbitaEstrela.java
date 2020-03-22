package com.example.appplanetario.ui.listar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.appplanetario.Planeta;
import com.example.appplanetario.R;
import com.example.appplanetario.banco.OrbitaBackground;
import com.example.appplanetario.ui.ActPlaneta;

public class ActListarOrbitaEstrela extends AppCompatActivity implements OrbitaBackground.OnOrbitaCompletedListener{
    //TODO fazer tela de planetas que orbitam uma estrela que vai ser basicamente replicar o que tem lá na órbita de um planeta
    private String tipo;
    private ListView lista_planetas;
    private ArrayList<Planeta> planetas;
    private ArrayAdapter<Planeta> arrayAdapter;
    private Planeta planeta;
    private EditText edtIdEstrela;
    private ImageButton btnBuscaOrbitaEstrela;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_orbita_estrela);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setTitle("Listar Órbita de Estrela");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lista_planetas = (ListView) findViewById(R.id.lista_orbita_estrela);
        edtIdEstrela = (EditText) findViewById(R.id.edtIdEstrelaToOrbita);
        btnBuscaOrbitaEstrela = (ImageButton) findViewById(R.id.btnBuscaOrbitaEstrela);

        planetas = new ArrayList<Planeta>();
        arrayAdapter = new ArrayAdapter<Planeta>(this, android.R.layout.simple_list_item_1, planetas);

        lista_planetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(ActListarOrbitaEstrela.this, ActPlaneta.class);
                it.putExtra("operacao", "Listar");
                it.putExtra("planeta", planetas.get(position));
                startActivity(it);
            }
        });
    }

    public void buscaOrbitaEstrela(View view){
        planetas.clear();
        OrbitaBackground orbitaBackground = new OrbitaBackground(this, "Consultar-Orbita-Estrela");
        orbitaBackground.setOnOrbitaCompletedListener(ActListarOrbitaEstrela.this);
        orbitaBackground.execute(edtIdEstrela.getText().toString());
    }

    @Override
    public void onOrbitaCompleted(String result, ResultSet resultado) {
        if(result.equals("ERRO-CONEXAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha na conexão!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        if(result.equals("ERRO-CONSULTA")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha ao consultar órbita!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        if(result.equals("OK")) {
            try{
                while(true){
                    if(!resultado.next())
                        break;

                    String comp = resultado.getArray("comp_planeta").toString();
                    System.out.println(comp);

                    comp = comp.substring(1);
                    comp = comp.replace('}', ' ');

                    String[] composicao = comp.split(",");

                    this.planeta = new Planeta(resultado.getInt("id_planeta"), resultado.getString("nome_planeta"),
                            resultado.getFloat("tam_planeta"), resultado.getFloat("peso_planeta"),
                            resultado.getFloat("gravidade_planeta"), resultado.getFloat("vel_rotacao"),
                            composicao);
                    planetas.add(planeta);
                    lista_planetas.setAdapter(arrayAdapter);
                }
            }catch (SQLException e){
                System.out.println("EXCEPTION7");
                e.printStackTrace();
            }
        }
    }
}
