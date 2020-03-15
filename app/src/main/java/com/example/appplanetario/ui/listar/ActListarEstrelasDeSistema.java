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

import com.example.appplanetario.Estrela;
import com.example.appplanetario.Planeta;
import com.example.appplanetario.R;
import com.example.appplanetario.banco.ConsultaAstrosBackground;
import com.example.appplanetario.banco.ListarBackground;
import com.example.appplanetario.ui.ActEstrela;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActListarEstrelasDeSistema extends AppCompatActivity implements ConsultaAstrosBackground.OnConsultaCompletedListener, ListarBackground.OnListarCompletedListener {

    public String tipo;
    public EditText edt_id_sistema;
    public ImageButton btn_busca;
    public ArrayList<Estrela> estrelas = new ArrayList<Estrela>();
    public String[] itens;
    public ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_estrelas_de_sistema);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setTitle("Listar Estrelas de um Sistema");
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
                Intent it4 = new Intent(com.example.appplanetario.ui.listar.ActListarEstrelasDeSistema.this, ActEstrela.class);
                it4.putExtra("estrela", estrelas.get(position));
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
            dlg.setMessage("Sistema Planetário não encontrado!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sistema Planetário Encontrado!");
            dlg.setMessage("ID: "+resultado.getInt("id_sistema")
                    +"\nNome: "+resultado.getString("nome_sistema"));
            final Context act = this;
            dlg.setNeutralButton("Buscar Estrelas", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ListarBackground listar = new ListarBackground(act);
                    listar.setOnListarCompletedListener((ListarBackground.OnListarCompletedListener) act);
                    listar.execute("Estrelas de um Sistema", ((ActListarEstrelasDeSistema) act).edt_id_sistema.getText().toString());
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
                Estrela estrela = null;
                try {
                    estrela = new Estrela(resultado.getInt("id_estrela"), resultado.getString("nome_estrela"),
                            resultado.getInt("idade_estrela"), resultado.getFloat("dist_terra"),
                            resultado.getFloat("gravidade_estrela"), resultado.getFloat("tam_estrela"),
                            resultado.getString("tipo_estrela"), resultado.getBoolean("morte"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                this.estrelas.add(estrela);
                i++;
            }
            if (i == 0) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Erro!");
                dlg.setMessage("Sistema Planetário não possui Estrelas!");
                dlg.setNeutralButton("OK", null);
                dlg.show();

            } else {
                this.itens = new String[this.estrelas.size()];
                for (int j = 0; j < itens.length; j++) {
                    itens[j] = "ID: " + estrelas.get(j).getId() + "\nNome: " + estrelas.get(j).getNome();
                }
                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
                this.lista.setAdapter(adapter4);

            }
        }
    }
}