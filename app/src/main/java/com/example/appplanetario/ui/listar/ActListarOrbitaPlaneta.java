package com.example.appplanetario.ui.listar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.appplanetario.R;
import com.example.appplanetario.SateliteNatural;
import com.example.appplanetario.banco.OrbitaBackground;
import com.example.appplanetario.ui.ActSatelite;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActListarOrbitaPlaneta extends AppCompatActivity implements OrbitaBackground.OnOrbitaCompletedListener{
    //TODO fazer tela de satélites que orbitam um planeta
    private String tipo;
    private ListView lista_sn;
    private ArrayList<SateliteNatural> satelites;
    private ArrayAdapter<SateliteNatural> arrayAdapter;
    private SateliteNatural satelite;
    private EditText edtIdPlaneta;
    private ImageButton btnBuscaOrbita;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_orbita_planeta);

        lista_sn = (ListView) findViewById(R.id.lista_orbita_planeta);
        edtIdPlaneta = (EditText) findViewById(R.id.edtIdPlanetaToOrbita);
        btnBuscaOrbita = (ImageButton) findViewById(R.id.btnBuscaOrbitaPlaneta);

        satelites = new ArrayList<SateliteNatural>();
        arrayAdapter = new ArrayAdapter<SateliteNatural>(this, android.R.layout.simple_list_item_1, satelites);

        lista_sn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(ActListarOrbitaPlaneta.this, ActSatelite.class);
                it.putExtra("operacao", "Listar");
                it.putExtra("satelite", satelites.get(position));
                startActivity(it);
            }
        });
    }

    public void buscaOrbita(View view){
        satelites.clear();
        OrbitaBackground orbitaBackground = new OrbitaBackground(this, "Consultar-Orbita-Planeta");
        orbitaBackground.setOnOrbitaCompletedListener(ActListarOrbitaPlaneta.this);
        orbitaBackground.execute(edtIdPlaneta.getText().toString());
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

                    String comp = resultado.getArray("comp_sn").toString();
                    System.out.println(comp);

                    comp = comp.substring(1);
                    comp = comp.replace('}', ' ');
                    System.out.println("NOVO " + comp);
                    String[] composicao = comp.split(",");
                    System.out.println("NOVO2 " + comp);
                    this.satelite = new SateliteNatural(resultado.getInt("id_sn"), resultado.getString("nome_sn"),
                            resultado.getFloat("tam_sn"), resultado.getFloat("peso_sn"),
                            composicao);
                    satelites.add(satelite);
                    lista_sn.setAdapter(arrayAdapter);
                }
            }catch (SQLException e){
                System.out.println("EXCEPTION7");
                e.printStackTrace();
            }
        }
    }
}
