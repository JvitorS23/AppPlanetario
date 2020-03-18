package com.example.appplanetario.ui.add;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appplanetario.SateliteNatural;
import com.example.appplanetario.banco.AddPlanetaBackground;
import com.example.appplanetario.banco.ModPlanetaBackground;
import com.example.appplanetario.Planeta;
import com.example.appplanetario.R;
import com.example.appplanetario.banco.OrbitaBackground;
import com.example.appplanetario.ui.Act_Inicio;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActAdicionarPlaneta extends AppCompatActivity implements AddPlanetaBackground.OnAddPlanetaCompletedListener, ModPlanetaBackground.OnModPlanetaCompletedListener, OrbitaBackground.OnOrbitaCompletedListener {

    private String operacao;
    private Button btn;
    private EditText form_nome;
    private EditText form_id;
    private EditText form_massa;
    private EditText form_tamanho;
    private EditText form_gravidade;
    private EditText form_composicao;
    private EditText form_velocidade;
    private Planeta planeta;
    private TextView txt_mensagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adicionarplaneta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);

        operacao = "Operação";
        operacao = (String) getIntent().getSerializableExtra("operacao");

        toolbar.setTitle(operacao +" Planeta");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        form_nome = findViewById(R.id.edt_nome);
        form_id = findViewById(R.id.edt_ID);
        form_massa = findViewById(R.id.edt_massa);
        form_tamanho = findViewById(R.id.edt_tamanho);
        form_gravidade = findViewById(R.id.edt_gravidade);
        form_composicao = findViewById(R.id.edt_composicao);
        form_velocidade = findViewById(R.id.edt_vel_rotacao);
        txt_mensagem = findViewById(R.id.txt_mensagem);

        if(operacao.equals("Adicionar")){
            findViewById(R.id.btn_modificar).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_remover_pertencimento);
            btn.setVisibility(View.VISIBLE);
            txt_mensagem.setVisibility(View.INVISIBLE);
        }
        if(operacao.equals("Modificar")){
            findViewById(R.id.btn_remover_pertencimento).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_modificar);
            btn.setVisibility(View.VISIBLE);
            planeta = (Planeta) getIntent().getExtras().getSerializable("planeta");
            form_nome.setText(planeta.getNome());
            form_id.setText(""+planeta.getId());
            form_massa.setText(""+planeta.getPeso());
            form_tamanho.setText(""+planeta.getTamanho());
            form_velocidade.setText(""+planeta.getVel_rotacao());
            form_gravidade.setText(""+planeta.getGravidade());
            String compos = "";
            for(int i=0; i<planeta.composicao.length; i++){

                compos = compos +planeta.composicao[i]+", ";
            }
            form_composicao.setText(compos);

            OrbitaBackground orbitaBackground = new OrbitaBackground(this, "Consultar-Possui-Satelite");
            orbitaBackground.setOnOrbitaCompletedListener(ActAdicionarPlaneta.this);
            orbitaBackground.execute(String.valueOf(planeta.getId()));
        }

    }
    public void clickBtnAdicionarPlaneta(View view){
        if(validaCampos()){
            String nome = form_nome.getText().toString();
            int id = Integer.parseInt(form_id.getText().toString());
            float tam = Float.parseFloat(form_tamanho.getText().toString());
            float peso = Float.parseFloat(form_massa.getText().toString());
            float gravidade = Float.parseFloat(form_gravidade.getText().toString());
            String[] comp = form_composicao.getText().toString().replaceAll(" ", "").split(",");
            float vel = Float.parseFloat(form_velocidade.getText().toString());
            this.planeta = new Planeta(id, nome, tam, peso, gravidade, vel, comp);
            AddPlanetaBackground add_planeta = new AddPlanetaBackground(this);
            add_planeta.setOnAddPlanetaCompletedListener(this);
            add_planeta.execute(this.planeta);
       }
    }

    public void clickBtnModificarPlaneta(View view){
        if(validaCampos()){
            //modificar o planeta no banco
            String nome = form_nome.getText().toString();
            int id = Integer.parseInt(form_id.getText().toString());
            float tam = Float.parseFloat(form_tamanho.getText().toString());
            float peso = Float.parseFloat(form_massa.getText().toString());
            float gravidade = Float.parseFloat(form_gravidade.getText().toString());
            String[] comp = form_composicao.getText().toString().replaceAll(" ", "").split(",");
            float vel = Float.parseFloat(form_velocidade.getText().toString());
            this.planeta = new Planeta(id, nome, tam, peso, gravidade, vel, comp);

            ModPlanetaBackground modificar = new ModPlanetaBackground(this, (int)getIntent().getExtras().getSerializable("id"));
            modificar.setOnModPlanetaCompletedListener(this);
            modificar.execute(this.planeta);
        }
    }

    public boolean validaCampos(){
        boolean valid=true;
        if(isCampoVazio(form_nome.getText().toString())){
            form_nome.requestFocus();
            Toast.makeText(this, "Campo nome vazio", Toast.LENGTH_SHORT).show();
            valid = false;
        }else {
            if (isCampoVazio(form_id.getText().toString())) {
                form_id.requestFocus();
                Toast.makeText(this, "Campo id vazio", Toast.LENGTH_SHORT).show();
                valid = false;
            }else{
                if(isCampoVazio(form_tamanho.getText().toString())){
                    form_tamanho.requestFocus();
                    Toast.makeText(this, "Campo tamanho vazio", Toast.LENGTH_SHORT).show();
                    valid = false;
                }else{
                    if(isCampoVazio(form_massa.getText().toString())){
                        form_massa.requestFocus();
                        Toast.makeText(this, "Campo massa vazio", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }else{
                        if(isCampoVazio(form_gravidade.getText().toString())){
                            form_gravidade.requestFocus();
                            Toast.makeText(this, "Campo gravidade vazio", Toast.LENGTH_SHORT).show();
                            valid = false;
                        }else{
                            if(isCampoVazio(form_composicao.getText().toString())){
                                form_composicao.requestFocus();
                                Toast.makeText(this, "Campo composição vazio", Toast.LENGTH_SHORT).show();
                                valid = false;
                            }else{
                                if(isCampoVazio(form_velocidade.getText().toString())){
                                    form_velocidade.requestFocus();
                                    Toast.makeText(this, "Campo Velocidade de Rotação vazio", Toast.LENGTH_SHORT).show();
                                    valid = false;

                                }
                            }
                        }

                    }
                }
            }
        }
        return valid;
    }
    public boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    @Override
    public void onAddPlanetaCompleted(String result) {

        if(result.equals("ERRO-CONEXAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha na conexão!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        if(result.equals("ERRO-INSERCAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha ao inserir planeta!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Planeta inserido!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarPlaneta.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }

    }

    @Override
    public void onModPlanetaCompleted(String result) {
        if(result.equals("ERRO-CONEXAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha na conexão!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("ERRO-MODIFICAR")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha ao modificar planeta!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Planeta modificado!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarPlaneta.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }
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
            dlg.setMessage("Falha ao consultar satélites desse planeta!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        if(result.equals("OK")) {
            try{
                while(true){
                    if(resultado.next()){
                        txt_mensagem.setText("Este planeta possui satélite natural");
                        break;
                    }else{
                        txt_mensagem.setText("Este planeta não possui satélite natural");
                        break;
                    }
                }
            }catch (SQLException e){
                System.out.println("EXCEPTION7");
                e.printStackTrace();
            }
        }
    }
}
