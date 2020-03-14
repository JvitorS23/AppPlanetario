package com.example.appplanetario;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.autofill.AutofillId;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ActAdicionarEstrela extends AppCompatActivity implements EstrelaBanco.OnEstrelaCompletedListener {

    private String operacao;
    private Button btn;
    private EditText form_nome;
    private EditText form_id;
    private EditText form_tamanho;
    private EditText form_dist_terra;
    private EditText form_gravidade;
    private EditText form_massa;
    private EditText form_idade;
    private RadioGroup form_tipo;
    private RadioButton radio_escolhido;
    private RadioButton radio_aux;
    private Estrela estrela;
    private RadioButton tipoMorte;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adicionar_estrela);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);

        operacao = "Operação";
        operacao = (String) getIntent().getSerializableExtra("operacao");

        toolbar.setTitle(operacao + " Estrela");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        form_nome = findViewById(R.id.edtNome);
        form_id = findViewById(R.id.edtID);
        form_tamanho = findViewById(R.id.edtTamanho);
        form_dist_terra = findViewById(R.id.edtDistTerra);
        form_gravidade = findViewById(R.id.edtGravidade);
        form_massa = findViewById(R.id.edtMassa);
        form_idade = findViewById(R.id.edtIdade);
        form_tipo = findViewById(R.id.tipoEstrela);
        tipoMorte = (RadioButton) findViewById(R.id.tipoMorte);

        if(operacao.equals("Adicionar")){
            findViewById(R.id.btn_modificar).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_adicionar);
            btn.setVisibility(View.VISIBLE);
            tipoMorte.setVisibility(View.GONE);
        }
        if(operacao.equals("Modificar")){
            findViewById(R.id.btn_adicionar).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_modificar);
            btn.setVisibility(View.VISIBLE);

            estrela = (Estrela) getIntent().getExtras().getSerializable("estrela");
            form_nome.setText(estrela.getNome());
            form_id.setText(""+estrela.getId());
            form_massa.setText(""+estrela.getMassa());
            form_tamanho.setText(""+estrela.getTamanho());
            form_gravidade.setText(""+estrela.getGravidade());
            form_dist_terra.setText(""+estrela.getDist_terra());
            form_idade.setText(""+estrela.getIdade());

            if(estrela.getTipo_estrela().equals("Anã Vermelha")){
                radio_escolhido = findViewById(R.id.tipoAnaVermelha);
                radio_escolhido.setChecked(true);
                tipoMorte.setVisibility(View.INVISIBLE);
            }
            if(estrela.getTipo_estrela().equals("Anã Branca")){
                radio_escolhido = findViewById(R.id.tipoAnaBranca);
                radio_escolhido.setChecked(true);
                tipoMorte.setVisibility(View.INVISIBLE);
            }
            if(estrela.getTipo_estrela().equals("Estrela Binária")){
                radio_escolhido = findViewById(R.id.tipoBinaria);
                radio_escolhido.setChecked(true);
                tipoMorte.setVisibility(View.INVISIBLE);
            }
            if(estrela.getTipo_estrela().equals("Gigante Azul")){
                radio_escolhido = findViewById(R.id.tipoGiganteAzul);
                radio_escolhido.setChecked(true);
                tipoMorte.setVisibility(View.INVISIBLE);
            }
            if(estrela.getTipo_estrela().equals("Gigante Vermelha")){
                radio_escolhido = findViewById(R.id.tipoGiganteVermelha);
                radio_escolhido.setChecked(true);
            }
            System.out.println("BOTÃO ESCOLHIDO: " + ((RadioButton)findViewById(form_tipo.getCheckedRadioButtonId())).getText().toString());
        }

    }

    public void clickBtnAdicionarEstrela(View view){
        if(validaCampos()){

            //adicionar estrela no banco
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Estrela Adicionada");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarEstrela.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }

    }


    public void clickBtnModificarEstrela(View view){
        if(validaCampos()){
            //modificar estrela no banco
            String tipo = ((RadioButton)findViewById(form_tipo.getCheckedRadioButtonId())).getText().toString();
            System.out.println("BOTÃO ESCOLHIDO: " + tipo);

            estrela.setTipo_estrela(tipo);
            estrela.setMorreu(tipoMorte.isChecked());
            EstrelaBanco estrelaBanco = new EstrelaBanco(this, estrela, "Modificar");
            estrelaBanco.setOnEstrelaCompletedListener(ActAdicionarEstrela.this);
            estrelaBanco.execute();
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
                    if(isCampoVazio(form_dist_terra.getText().toString())){
                        form_dist_terra.requestFocus();
                        Toast.makeText(this, "Campo Distância da terra vazio", Toast.LENGTH_SHORT).show();
                        valid = false;
                    }else{
                        if(isCampoVazio(form_gravidade.getText().toString())){
                            form_gravidade.requestFocus();
                            Toast.makeText(this, "Campo gravidade vazio", Toast.LENGTH_SHORT).show();
                            valid = false;
                        }else{
                            if(isCampoVazio(form_massa.getText().toString())){
                                form_massa.requestFocus();
                                Toast.makeText(this, "Campo massa vazio", Toast.LENGTH_SHORT).show();
                                valid = false;
                            }else{
                                if(isCampoVazio(form_idade.getText().toString())){
                                    form_idade.requestFocus();
                                    Toast.makeText(this, "Campo idade vazio", Toast.LENGTH_SHORT).show();
                                    valid = false;

                                }else{
                                    int id_escolha = form_tipo.getCheckedRadioButtonId();
                                    if(id_escolha==-1){
                                        form_tipo.requestFocus();
                                        Toast.makeText(this, "Selecione o tipo da estrela", Toast.LENGTH_SHORT).show();
                                        valid = false;

                                    }
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
    public void onEstrelaCompleted(String result) {
        if(result.equals("ERRO-CONEXAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha na conexão!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("ERRO-ATUALIZACAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha ao atualizar os dados do astro!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("OK")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Parabéns!");
            dlg.setMessage("Operação realizada com sucesso!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAdicionarEstrela.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }
    }
}
