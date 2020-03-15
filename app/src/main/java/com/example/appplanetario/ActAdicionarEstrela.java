package com.example.appplanetario;

import android.annotation.SuppressLint;
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
import android.widget.RadioGroup;
import android.widget.Toast;

public class ActAdicionarEstrela extends AppCompatActivity implements AddEstrelaBackground.OnAddEstrelaCompletedListener, ModEstrelaBackground.OnModEstrelaCompletedListener{




    private String operacao;
    private Button btn;
    private EditText form_nome;
    private EditText form_id;
    private EditText form_tamanho;
    private EditText form_dist_terra;
    private EditText form_gravidade;

    private EditText form_idade;
    private RadioGroup form_tipo;
    private RadioButton radio_escolhido;
    private Estrela estrela;
    private RadioButton btn_radio_morte;


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

        form_idade = findViewById(R.id.edtIdade);
        form_tipo = findViewById(R.id.tipoEstrela);
        btn_radio_morte = findViewById(R.id.btnRadioMorte);

        if(operacao.equals("Adicionar")){
            findViewById(R.id.btn_modificar).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_remover_pertencimento);
            btn.setVisibility(View.VISIBLE);

        }
        if(operacao.equals("Modificar")){
            findViewById(R.id.btn_remover_pertencimento).setVisibility(View.INVISIBLE);
            btn = (Button)findViewById(R.id.btn_modificar);
            btn.setVisibility(View.VISIBLE);

            estrela = (Estrela) getIntent().getExtras().getSerializable("estrela");
            form_nome.setText(estrela.getNome());
            form_id.setText(""+estrela.getId());
            form_tamanho.setText(""+estrela.getTamanho());
            form_gravidade.setText(""+estrela.getGravidade());
            form_dist_terra.setText(""+estrela.getDist_terra());
            form_idade.setText(""+estrela.getIdade());

            if(estrela.getTipo_estrela().equals("Anã Vermelha")){
                radio_escolhido = findViewById(R.id.tipoAnaVermelha);
                radio_escolhido.setChecked(true);
                btn_radio_morte.setVisibility(View.INVISIBLE);
            }
            if(estrela.getTipo_estrela().equals("Anã Branca")){
                radio_escolhido = findViewById(R.id.tipoAnaBranca);
                radio_escolhido.setChecked(true);
                btn_radio_morte.setVisibility(View.INVISIBLE);
            }
            if(estrela.getTipo_estrela().equals("Estrela Binária")){
                radio_escolhido = findViewById(R.id.tipoBinaria);
                radio_escolhido.setChecked(true);
                btn_radio_morte.setVisibility(View.INVISIBLE);
            }
            if(estrela.getTipo_estrela().equals("Gigante Azul")){
                radio_escolhido = findViewById(R.id.tipoGiganteAzul);
                radio_escolhido.setChecked(true);
                btn_radio_morte.setVisibility(View.INVISIBLE);
            }
            if(estrela.getTipo_estrela().equals("Gigante Vermelha")){
                radio_escolhido = findViewById(R.id.tipoGiganteVermelha);
                radio_escolhido.setChecked(true);
            }
        }
    }

    public void clickBtnAdicionarEstrela(View view){
        if(validaCampos()){

            int id = Integer.parseInt(form_id.getText().toString());
            String nome = form_nome.getText().toString();
            int idade = Integer.parseInt(form_idade.getText().toString());
            float dist = Float.parseFloat(form_dist_terra.getText().toString());
            float gravidade = Float.parseFloat(form_gravidade.getText().toString());
            float tamanho = Float.parseFloat(form_tamanho.getText().toString());
            radio_escolhido = findViewById(form_tipo.getCheckedRadioButtonId());
            String tipo = radio_escolhido.getText().toString();
            boolean morte = btn_radio_morte.isChecked();
            this.estrela = new Estrela(id, nome, idade, dist, gravidade, tamanho, tipo, morte);
            AddEstrelaBackground add_estrela = new AddEstrelaBackground(this);
            add_estrela.setOnAddEstrelaCompletedListener(this);
            add_estrela.execute(this.estrela);
        }

    }


    public void clickBtnModificarEstrela(View view){
        if(validaCampos()){

            ModEstrelaBackground modificar = new ModEstrelaBackground(this, (int)getIntent().getExtras().getSerializable("id"));
            modificar.setOnModEstrelaCompletedListener(this);

            int id = Integer.parseInt(form_id.getText().toString());
            String nome = form_nome.getText().toString();
            int idade = Integer.parseInt(form_idade.getText().toString());
            float dist = Float.parseFloat(form_dist_terra.getText().toString());
            float gravidade = Float.parseFloat(form_gravidade.getText().toString());
            float tamanho = Float.parseFloat(form_tamanho.getText().toString());
            radio_escolhido = findViewById(form_tipo.getCheckedRadioButtonId());
            String tipo = radio_escolhido.getText().toString();
            boolean morte = btn_radio_morte.isChecked();
            this.estrela = new Estrela(id, nome, idade, dist, gravidade, tamanho, tipo, morte);

            modificar.execute(this.estrela);
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
        return valid;
    }
    public boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }


    @Override
    public void onAddEstrelaCompleted(String result) {
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
            dlg.setMessage("Falha ao inserir estrela!");
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Estrela inserida!");
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

    @Override
    public void onModEstrelaCompleted(String result) {
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
            dlg.setMessage("Falha ao modificar estrela!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Estrela modificada!");
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
