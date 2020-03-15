package com.example.appplanetario;

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
import android.widget.Toast;

public class ActAdicionarSistema extends AppCompatActivity implements AddSistemaBackground.OnAddSistemaCompletedListener, ModSistemaBackground.OnModSistemaCompletedListener {

    private String operacao;
    private SistemaPlanetario sistema;
    private Button btn;
    private EditText form_nome;
    private EditText form_id;
    private EditText form_qtde_planetas;
    private EditText form_qtde_estrelas;
    private EditText form_idade;
    private EditText form_galaxia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adicionar_sistema);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        operacao = "";
        operacao = (String) getIntent().getSerializableExtra("operacao");
        toolbar.setTitle(operacao+" Sistema Planetário");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        form_nome = findViewById(R.id.edtNome);
        form_id = findViewById(R.id.edtID);
        form_qtde_estrelas = findViewById(R.id.edt_qtde_estrelas);
        form_qtde_planetas = findViewById(R.id.edt_qtde_planetas);
        form_idade = findViewById(R.id.edt_idade);
        form_galaxia = findViewById(R.id.edt_id_galaxia);

        if(operacao.equals("Adicionar")){
            findViewById(R.id.btn_modificar).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_remover_pertencimento);
            btn.setVisibility(View.VISIBLE);

        }
        if(operacao.equals("Modificar")) {
            findViewById(R.id.btn_remover_pertencimento).setVisibility(View.INVISIBLE);
            btn = (Button) findViewById(R.id.btn_modificar);
            btn.setVisibility(View.VISIBLE);
            sistema = (SistemaPlanetario) getIntent().getExtras().getSerializable("sistema");
            form_nome.setText(sistema.getNome());

            form_id.setText(sistema.getId()+"");
            form_qtde_planetas.setText(sistema.getQtde_planetas()+"");
            form_qtde_estrelas.setText(sistema.getQtde_estrelas()+"");
            form_idade.setText(sistema.getIdade()+"");
            form_galaxia.setText(sistema.getId_galaxia()+"");
        }
    }

    public void clickBtnAdicionarSistema(View view){
        if(validaCampos()){
            int id_sistema = Integer.parseInt(this.form_id.getText().toString());
            String nome = this.form_nome.getText().toString();
            int qtde_planetas = Integer.parseInt(this.form_qtde_planetas.getText().toString());
            int qtde_estrelas = Integer.parseInt(this.form_qtde_estrelas.getText().toString());
            int idade = Integer.parseInt(this.form_idade.getText().toString());
            int id_galaxia = Integer.parseInt(this.form_galaxia.getText().toString());

            this.sistema = new SistemaPlanetario(id_sistema, nome, qtde_planetas,qtde_estrelas, idade, id_galaxia);
            AddSistemaBackground add_sistema = new AddSistemaBackground(this);
            add_sistema.setOnAddSistemaCompletedListener(this);
            add_sistema.execute(this.sistema);
        }
    }

    public void clickBtnModificarSistema(View view){
        if(validaCampos()){
            int id_sistema = Integer.parseInt(this.form_id.getText().toString());
            String nome = this.form_nome.getText().toString();
            int qtde_planetas = Integer.parseInt(this.form_qtde_planetas.getText().toString());
            int qtde_estrelas = Integer.parseInt(this.form_qtde_estrelas.getText().toString());
            int idade = Integer.parseInt(this.form_idade.getText().toString());
            int id_galaxia = Integer.parseInt(this.form_galaxia.getText().toString());

            this.sistema = new SistemaPlanetario(id_sistema, nome, qtde_planetas,qtde_estrelas, idade, id_galaxia);
            ModSistemaBackground modificar = new ModSistemaBackground(this, (int)getIntent().getExtras().getSerializable("id"));
            modificar.setOnModSistemaCompletedListener(this);
            modificar.execute(this.sistema);
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
                if(isCampoVazio(form_qtde_planetas.getText().toString())){
                    form_qtde_planetas.requestFocus();
                    Toast.makeText(this, "Campo Quantidade de planetas vazio", Toast.LENGTH_SHORT).show();
                    valid = false;
                }else{
                    if(isCampoVazio(form_qtde_estrelas.getText().toString())){
                        form_qtde_estrelas.requestFocus();
                        Toast.makeText(this, "Campo Quantidade de estrelas vazio", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }else{
                        if(isCampoVazio(form_idade.getText().toString())){
                            form_idade.requestFocus();
                            Toast.makeText(this, "Campo idade vazio", Toast.LENGTH_SHORT).show();
                            valid = false;
                        }else{
                            if(isCampoVazio(form_galaxia.getText().toString())) {
                                form_galaxia.requestFocus();
                                Toast.makeText(this, "Campo id galáxia vazio", Toast.LENGTH_SHORT).show();
                                valid = false;
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
    public void onAddSistemaCompleted(String result) {
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
            dlg.setMessage("Falha ao inserir sistema!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        if(result.equals("ID_galaxia")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Galáxia não encontrada!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        if(result.equals("OK")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Sistema Planetário inserido!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarSistema.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }
    }

    @Override
    public void onModSistemaCompleted(String result) {
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
            dlg.setMessage("Falha ao modificar sistema planetário!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Sistema Planetário modificado!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarSistema.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }
    }
}
