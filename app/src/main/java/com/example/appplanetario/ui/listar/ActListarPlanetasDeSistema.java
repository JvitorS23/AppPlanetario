package com.example.appplanetario.ui.listar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.example.appplanetario.Planeta;
import com.example.appplanetario.R;
import com.example.appplanetario.banco.ConsultaAstrosBackground;
import com.example.appplanetario.banco.ListarBackground;
import com.example.appplanetario.ui.ActPlaneta;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActListarPlanetasDeSistema extends AppCompatActivity implements ConsultaAstrosBackground.OnConsultaCompletedListener, ListarBackground.OnListarCompletedListener {

    public String tipo;
    public EditText edt_id_sistema;
    public ImageButton btn_busca;
    public ArrayList<Planeta> planetas = new ArrayList<Planeta>();
    public String[] itens;
    public ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_planetas_de_sistema);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setTitle("Listar Planetas de um Sistema");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_id_sistema = (EditText) findViewById(R.id.edt_id_sistema);
        btn_busca = (ImageButton) findViewById(R.id.btn_buscar);
        lista = (ListView) findViewById(R.id.lista_planetas);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it4 = new Intent(ActListarPlanetasDeSistema.this, ActPlaneta.class);
                it4.putExtra("planeta", planetas.get(position));
                it4.putExtra("operacao", "Consultar");
                startActivity(it4);
            }
        });

    }

    public void btnBusca(View view){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edt_id_sistema.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if(edt_id_sistema.getText().toString().length()>0 && Integer.parseInt(edt_id_sistema.getText().toString())>0){
            //Procurar galxia no banco
            ConsultaAstrosBackground consulta = new ConsultaAstrosBackground(this, "Sistema Planetário");
            consulta.setOnConsultaCompletedListener(this);
            consulta.execute(this.edt_id_sistema.getText().toString());

        }else{
            Toast.makeText(this, "ID inválido ou vazio!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConsultaCompleted(String result, ResultSet resultado) throws SQLException {

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
            dlg.setMessage("Sistema não encontrado!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            final Context act = this;
            dlg.setTitle("Sistema Planetário Encontrado!");
            dlg.setMessage("ID "+resultado.getInt("id_sistema")
                    +"\nNome: "+resultado.getString("nome_sistema"));

            dlg.setNeutralButton("Buscar Planetas", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ListarBackground listar = new ListarBackground(act);
                    listar.setOnListarCompletedListener((ListarBackground.OnListarCompletedListener) act);
                    listar.execute("Planetas de um Sistema", ((ActListarPlanetasDeSistema) act).edt_id_sistema.getText().toString());

                }
            });
            dlg.show();

        }

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
            while (true) {
                try {
                    if (!resultado.next()) break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Planeta planeta = null;
                String[] composicao = null;
                try {
                    Array comp = resultado.getArray("comp_planeta");
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
                this.planetas.add(planeta);
                i++;
            }
            if (i == 0) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Erro!");
                dlg.setMessage("Sistema Planetário não possui Planetas!");
                dlg.setNeutralButton("OK", null);
                dlg.show();

            } else {
                this.itens = new String[this.planetas.size()];
                for (int j = 0; j < itens.length; j++) {
                    itens[j] = "ID: " + planetas.get(j).getId() + "\nNome: " + planetas.get(j).getNome();
                }
                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
                this.lista.setAdapter(adapter4);

            }
        }

    }
}
