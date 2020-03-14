package com.example.appplanetario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActListarSistemasDeUmaGalaxia extends AppCompatActivity implements  ConsultaAstrosBackground.OnConsultaCompletedListener, ListarBackground.OnListarCompletedListener{
    public String tipo;
    public EditText edt_id_galaxia;
    public ImageButton btn_busca;
    public ArrayList<SistemaPlanetario> sistemas = new ArrayList<SistemaPlanetario>();
    public String[] itens;
    public ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_act_listar_sistemas_de_uma_galaxia);

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
        edt_id_galaxia = (EditText) findViewById(R.id.edt_id_galaxia);
        btn_busca = (ImageButton) findViewById(R.id.btn_buscar);
        lista = (ListView) findViewById(R.id.lista_sistemas);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it4 = new Intent(ActListarSistemasDeUmaGalaxia.this, ActSistema.class);
                it4.putExtra("sistema", sistemas.get(position));
                it4.putExtra("operacao", "Remover");
                startActivity(it4);
            }
        });
    }

    public void btnBusca(View view){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edt_id_galaxia.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if(edt_id_galaxia.getText().toString().length()>0 && Integer.parseInt(edt_id_galaxia.getText().toString())>0){
            //Procurar galxia no banco
            ConsultaAstrosBackground consulta = new ConsultaAstrosBackground(this, "Galáxia");
            consulta.setOnConsultaCompletedListener(this);
            consulta.execute(this.edt_id_galaxia.getText().toString());

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
            dlg.setMessage("Galáxia não encontrada!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Galáxia Encontrada!");
            dlg.setMessage("ID "+resultado.getInt("id_galaxia")
                    +"\nNome: "+resultado.getString("nome_galaxia"));
            final Context act = this;
            dlg.setNeutralButton("Buscar Sistemas", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ListarBackground listar = new ListarBackground(act);
                    listar.setOnListarCompletedListener((ListarBackground.OnListarCompletedListener) act);
                    listar.execute(tipo, ((ActListarSistemasDeUmaGalaxia) act).edt_id_galaxia.getText().toString());
                }
            });
            dlg.show();

        }
    }

    @Override
    public void onListarCompleted(ResultSet resultado, String result) {
        System.out.println(resultado);
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
            while(true) {
                try {
                    if (!resultado.next()) break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                SistemaPlanetario sistema = null;
                try {
                    System.out.println(resultado.getString("nome_sistema"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    sistema = new SistemaPlanetario(resultado.getInt("id_sistema"),
                            resultado.getString("nome_sistema"), resultado.getInt("qtd_planetas"),
                            resultado.getInt("qtd_estrelas"), resultado.getInt("idade_sistema"),
                            resultado.getInt("id_galaxia"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                this.sistemas.add(sistema);
                i++;
            }
            if(i==0){
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Erro!");
                dlg.setMessage("Galáxia não possui Sistemas Planetários!");
                dlg.setNeutralButton("OK", null);
                dlg.show();

            }else{

                this.itens = new String[this.sistemas.size()];
                for (int j=0; j<itens.length;j++){
                    itens[j] = "ID: "+sistemas.get(j).getId()+ "\nNome: "+sistemas.get(j).getNome();
                }
                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
                this.lista.setAdapter(adapter4);
                for (int j=0; j<itens.length;j++){
                    System.out.println(itens[j]);
                }
            }
        }

    }
}
