package com.example.appplanetario;

import androidx.annotation.StringDef;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActListarBuracosNegros extends AppCompatActivity implements  ListarBackground.OnListarCompletedListener{

    private String tipo;
    private ListView lista_buracos;
    private ArrayList<Estrela> buracos;
    private ArrayAdapter<Estrela> arrayAdapter;
    private Estrela estrela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_buracos_negros);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow);
//        tipo = "Entidade";
//        tipo = (String) getIntent().getSerializableExtra("tipo");
//        toolbar.setTitle("Listar " + tipo);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        lista_buracos = (ListView) findViewById(R.id.lista_buracos);

        buracos = new ArrayList<Estrela>();
        arrayAdapter = new ArrayAdapter<Estrela>(this, android.R.layout.simple_list_item_1, buracos);

        lista_buracos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO Fazer tela da estrela que é buraco negro
                Intent it = new Intent(ActListarBuracosNegros.this, ActBuracoNegro.class);
                it.putExtra("buraco", buracos.get(position));
                startActivity(it);
            }
        });

        //Sempre que essa tela é chamada uma consulta ao banco é feita
        ListarBackground listarBackground = new ListarBackground(this);
        listarBackground.setOnListarCompletedListener(ActListarBuracosNegros.this);
        listarBackground.execute("Estrela");
    }

    @Override
    public void onListarCompleted(ResultSet resultado, String erro) {
        if(erro.equals("ERRO-CONEXAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha na conexão!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(erro.equals("ERRO-CONSULTA")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha na consulta!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(erro.equals("OK")){
            try{
                while(true){
                    if(!resultado.next())
                        break;

                    this.estrela = new Estrela(resultado.getInt("id_estrela"), resultado.getString("nome_estrela"),
                            resultado.getInt("idade_estrela"), resultado.getFloat("dist_terra"),
                            resultado.getFloat("gravidade_estrela"), resultado.getFloat("tam_estrela"),
                            resultado.getString("tipo_estrela"), resultado.getBoolean("morte"));
                    System.out.println("Antes: " + estrela.getNome());
                    if(this.estrela.isMorte() && this.estrela != null){
                        System.out.println("Depois: " + estrela.getNome());
                        Estrela aux = estrela;
                        System.out.println(buracos.add(aux));
                        lista_buracos.setAdapter(arrayAdapter);
                    }
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
