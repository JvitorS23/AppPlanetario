package com.example.appplanetario;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActListar extends AppCompatActivity implements ListarBackground.OnListarCompletedListener{
    public String tipo;
    public ListView lista;
    public ArrayList<Planeta> planetas  = new ArrayList<Planeta>();
    public ArrayList<Galaxia> galaxias = new ArrayList<Galaxia>();
    public ArrayList<SistemaPlanetario> sistemas = new ArrayList<SistemaPlanetario>();
    public ArrayList<SateliteNatural> satelites = new ArrayList<SateliteNatural>();
    public ArrayList<Estrela> estrelas = new ArrayList<Estrela>();
    public String[] itens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        tipo = "Entidade";
        tipo = (String) getIntent().getSerializableExtra("tipo");
        toolbar.setTitle("Listar " + tipo);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lista = findViewById(R.id.lista);
        ListarBackground listar = new ListarBackground(this);
        listar.setOnListarCompletedListener(this);
        listar.execute(tipo);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (tipo) {
                    case "Planeta":
                        Intent it = new Intent(ActListar.this, ActPlaneta.class);
                        it.putExtra("planeta", planetas.get(position));
                        it.putExtra("operacao", "Remover");
                        startActivity(it);
                        break;
                    case "Galáxia":
                        Intent it2 = new Intent(ActListar.this, ActGalaxia.class);
                        it2.putExtra("galaxia", galaxias.get(position));
                        it2.putExtra("operacao", "Remover");
                        startActivity(it2);
                        break;
                    case "Estrela":

                        Intent it3 = new Intent(ActListar.this, ActEstrela.class);
                        it3.putExtra("estrela", estrelas.get(position));
                        it3.putExtra("operacao", "Remover");
                        startActivity(it3);
                        break;
                    case "Sistema Planetário":
                        Intent it4 = new Intent(ActListar.this, ActSistema.class);
                        it4.putExtra("sistema", sistemas.get(position));
                        it4.putExtra("operacao", "Remover");
                        startActivity(it4);
                        break;
                    case "Satélite Natural":
                        Intent it5 = new Intent(ActListar.this, ActSatelite.class);
                        it5.putExtra("satelite", satelites.get(position));
                        it5.putExtra("operacao", "Remover");
                        startActivity(it5);
                        break;
                }
            }
        });
    }

    @Override
    public void onListarCompleted(ResultSet resultado, String result) {

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
            dlg.setMessage("Falha na consulta!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("OK")) {
            int i = 0;
            while(true){
                try {
                    if (!resultado.next()) break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                switch (tipo) {
                    case "Planeta":
                        Planeta planeta = null;
                        String[] composicao = null;
                        try {
                            Array comp =  resultado.getArray("comp_planeta");
                            String str_comp = comp.toString();
                            str_comp = str_comp.replace("{", "");
                            str_comp = str_comp.replace("}", "");
                            composicao = str_comp.split(",");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            planeta = new Planeta(resultado.getInt("id_planeta"),
                                    resultado.getString("nome_planeta"), resultado.getFloat("tam_planeta"),
                                    resultado.getFloat("peso_planeta"), resultado.getFloat("gravidade_planeta"),
                                    resultado.getFloat("vel_rotacao"), composicao);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        System.out.println(planeta);
                        this.planetas.add(planeta);
                        break;
                    case "Galáxia":
                        Galaxia galaxia = null;
                        try {
                            galaxia = new Galaxia(resultado.getInt("id_galaxia"), resultado.getString("nome_galaxia"),
                                    resultado.getInt("qtd_sistemas"), resultado.getFloat("dist_terra"));

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        this.galaxias.add(galaxia);
                        break;
                    case "Estrela":
                        Estrela estrela = null;
                        try {
                            estrela = new Estrela(resultado.getInt("id_estrela"), resultado.getString("nome_estrela"),
                                    resultado.getInt("idade_estrela"), resultado.getFloat("dist_terra"),
                                    resultado.getFloat("gravidade_estrela"), resultado.getFloat("tam_estrela"),
                                    resultado.getString("tipo_estrela"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        this.estrelas.add(estrela);
                        break;
                    case "Sistema Planetário":
                        SistemaPlanetario sistema = null;
                        try {
                            sistema = new SistemaPlanetario(resultado.getInt("id_sistema"),
                                    resultado.getString("nome_sistema"), resultado.getInt("qtd_planetas"),
                                    resultado.getInt("qtd_estrelas"), resultado.getInt("idade_sistema"),
                                    resultado.getInt("id_galaxia"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        this.sistemas.add(sistema);
                        break;
                    case "Satélite Natural":
                        SateliteNatural satelite = null;
                        String[] composicao2 = null;
                        Array comp = null;
                        try {
                            comp = resultado.getArray("comp_sn");
                            String str_comp = comp.toString();
                            str_comp = str_comp.replace("{", "");
                            str_comp = str_comp.replace("}", "");
                            composicao2 = str_comp.split(",");
                            satelite = new SateliteNatural(resultado.getInt("id_sn"), resultado.getString("nome_sn"),
                                    resultado.getFloat("tam_sn"), resultado.getFloat("peso_sn"), composicao2);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        this.satelites.add(satelite);
                        break;
                }
                i++;
            }
            if(i==0){
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Erro!");
                dlg.setMessage("Astros não cadastrados!");
                dlg.setNeutralButton("OK", null);
                dlg.show();

            }else{
                switch (tipo) {
                    case "Planeta":
                        itens = new String[this.planetas.size()];
                        for (int j=0; j<itens.length;j++){
                            itens[j] = "ID: "+planetas.get(j).getId()+ "\nNome: "+planetas.get(j).getNome();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
                        this.lista.setAdapter(adapter);
                        break;
                    case "Galáxia":
                        itens = new String[this.galaxias.size()];
                        for (int j=0; j<itens.length;j++){
                            itens[j] = "ID: "+galaxias.get(j).getId()+ "\nNome: "+galaxias.get(j).getNome();
                        }
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
                        this.lista.setAdapter(adapter2);
                        break;
                    case "Estrela":
                        itens = new String[this.estrelas.size()];
                        for (int j=0; j<itens.length;j++){
                            itens[j] = "ID: "+estrelas.get(j).getId()+ "\nNome: "+estrelas.get(j).getNome();
                        }
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
                        this.lista.setAdapter(adapter3);
                        break;
                    case "Sistema Planetário":
                        this.itens = new String[this.sistemas.size()];
                        for (int j=0; j<itens.length;j++){
                            itens[j] = "ID: "+sistemas.get(j).getId()+ "\nNome: "+sistemas.get(j).getNome();
                        }
                        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
                        this.lista.setAdapter(adapter4);
                        for (int j=0; j<itens.length;j++){
                            System.out.println(itens[j]);
                        }
                        break;
                    case "Satélite Natural":
                        itens = new String[this.satelites.size()];
                        for (int j=0; j<itens.length;j++){
                            itens[j] = "ID: "+satelites.get(j).getId()+ "\nNome: "+satelites.get(j).getNome();
                        }
                        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
                        this.lista.setAdapter(adapter5);
                        break;
                }
            }
        }
    }
}
