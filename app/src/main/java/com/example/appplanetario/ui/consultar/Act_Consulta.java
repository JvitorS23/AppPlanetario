package com.example.appplanetario.ui.consultar;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appplanetario.banco.OrbitaBackground;
import com.example.appplanetario.ui.ActEstrela;
import com.example.appplanetario.ui.ActGalaxia;
import com.example.appplanetario.ui.ActPlaneta;
import com.example.appplanetario.ui.ActSatelite;
import com.example.appplanetario.ui.ActSistema;
import com.example.appplanetario.banco.ConsultaAstrosBackground;
import com.example.appplanetario.Estrela;
import com.example.appplanetario.Galaxia;
import com.example.appplanetario.Planeta;
import com.example.appplanetario.R;
import com.example.appplanetario.SateliteNatural;
import com.example.appplanetario.SistemaPlanetario;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Act_Consulta extends AppCompatActivity implements ConsultaAstrosBackground.OnConsultaCompletedListener {
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
        edtId = findViewById(R.id.edt_id_pertencido);
        txtID.setText("ID "+tipo);

    }

    public void clickBtnConsultar(View view) throws SQLException {
        if(edtId.getText().toString().trim().length()<1) {
            edtId.requestFocus();
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }else{
            ConsultaAstrosBackground consulta = null;
            switch (tipo) {
                case "Planeta":
                    //Procurar planeta no banco
                     consulta = new ConsultaAstrosBackground(this, "Planeta");
                     consulta.setOnConsultaCompletedListener(this);
                     consulta.execute(this.edtId.getText().toString());
                    break;
                case "Galáxia":
                    //Procurar galxia no banco
                    consulta = new ConsultaAstrosBackground(this, "Galáxia");
                    consulta.setOnConsultaCompletedListener(this);
                    consulta.execute(this.edtId.getText().toString());
                    break;
                case "Estrela":
                    //Procurar estrela no banco
                    consulta = new ConsultaAstrosBackground(this, "Estrela");
                    consulta.setOnConsultaCompletedListener(this);
                    consulta.execute(this.edtId.getText().toString());
                    break;
                case "Sistema Planetário":
                    //Procurar sistema no banco
                    consulta = new ConsultaAstrosBackground(this, "Sistema Planetário");
                    consulta.setOnConsultaCompletedListener(this);
                    consulta.execute(this.edtId.getText().toString());
                    break;
                case "Satélite Natural":
                    //Procurar satélite no banco
                    consulta = new ConsultaAstrosBackground(this, "Satélite Natural");
                    consulta.setOnConsultaCompletedListener(this);
                    consulta.execute(this.edtId.getText().toString());
                    break;
            }
        }
    }

    @Override
    public void onConsultaCompleted(String result, ResultSet resultado)  {

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
            dlg.setMessage("Astro não encontrado!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        if(result.equals("OK")) {
            switch (tipo) {
                case "Planeta":
                    Intent it = new Intent(Act_Consulta.this, ActPlaneta.class);
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
                    it.putExtra("planeta", planeta);
                    it.putExtra("operacao", "Consultar");
                    startActivity(it);
                    break;
                case "Galáxia":
                    Intent it2 = new Intent(Act_Consulta.this, ActGalaxia.class);
                    Galaxia galaxia = null;
                    try {
                       galaxia = new Galaxia(resultado.getInt("id_galaxia"), resultado.getString("nome_galaxia"),
                               resultado.getInt("qtd_sistemas"), resultado.getFloat("dist_terra"));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    it2.putExtra("galaxia", galaxia);
                    it2.putExtra("operacao", "Consultar");
                    startActivity(it2);
                    break;
                case "Estrela":
                    //Procurar estrela no banco
                    // Se encontrar instanciar estrela e passar para a activity modificar estrela
                    Intent it3 = new Intent(Act_Consulta.this, ActEstrela.class);
                    Estrela estrela = null;
                    try {
                        estrela = new Estrela(resultado.getInt("id_estrela"), resultado.getString("nome_estrela"),
                                resultado.getInt("idade_estrela"), resultado.getFloat("dist_terra"),
                                resultado.getFloat("gravidade_estrela"), resultado.getFloat("tam_estrela"),
                                resultado.getString("tipo_estrela"), resultado.getBoolean("morte"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    it3.putExtra("estrela", estrela);
                    it3.putExtra("operacao", "Consultar");
                    startActivity(it3);
                    break;
                case "Sistema Planetário":
                    //Procurar sistema no banco
                    Intent it4 = new Intent(Act_Consulta.this, ActSistema.class);
                    SistemaPlanetario sistema = null;
                    try {
                        sistema = new SistemaPlanetario(resultado.getInt("id_sistema"),
                                resultado.getString("nome_sistema"), resultado.getInt("qtd_planetas"),
                                resultado.getInt("qtd_estrelas"), resultado.getInt("idade_sistema"),
                                resultado.getInt("id_galaxia"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    it4.putExtra("sistema", sistema);
                    it4.putExtra("operacao", "Consultar");
                    startActivity(it4);

                    break;
                case "Satélite Natural":
                    //Procurar satélite no banco

                    Intent it5 = new Intent(Act_Consulta.this, ActSatelite.class);

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

                    it5.putExtra("satelite", satelite);
                    it5.putExtra("operacao", "Consultar");
                    startActivity(it5);
                    break;
            }
        }
    }
}
