package com.example.appplanetario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActAddOrbita extends AppCompatActivity implements  OrbitaBackground.OnOrbitaCompletedListener{

    private EditText edt_id_planeta_orb;
    private EditText edt_id_satelite_natural_orb;
    private EditText edt_id_estrela_orb;
    private Button btn_add_orbita;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_add_orbita);

        edt_id_planeta_orb = (EditText) findViewById(R.id.edtIdPlanetaOrb);
        edt_id_satelite_natural_orb = (EditText) findViewById(R.id.edtIdSateliteNaturalOrb);
        edt_id_estrela_orb = (EditText) findViewById(R.id.edtIdEstrelaOrb);
        btn_add_orbita = (Button) findViewById(R.id.btnAddOrbita);
    }

    public void adicionaOrbita(View view){
        int valid = validaCampos();

        if(valid == 0){
            String id_planeta = edt_id_planeta_orb.getText().toString();
            String id_satelite_natural = edt_id_satelite_natural_orb.getText().toString();
            String id_estrela = edt_id_estrela_orb.getText().toString();

            OrbitaBackground orbitaBackground = new OrbitaBackground(this, "Adicionar");
            orbitaBackground.setOnOrbitaCompletedListener(ActAddOrbita.this);
            orbitaBackground.execute(id_planeta, id_satelite_natural, id_estrela);
        }else{
            if(valid==1){
                edt_id_planeta_orb.requestFocus();
                Toast.makeText(this, "Campo planeta vazio", Toast.LENGTH_SHORT).show();
            }else{
                if(valid==2){
                    edt_id_satelite_natural_orb.requestFocus();
                    Toast.makeText(this, "Campo satélite natural vazio", Toast.LENGTH_SHORT).show();
                }else{
                    if(valid==3){
                        edt_id_estrela_orb.requestFocus();
                        Toast.makeText(this, "Campo estrela vazio", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public int validaCampos(){
        String planeta = edt_id_planeta_orb.getText().toString();
        String satelite_natural = edt_id_satelite_natural_orb.getText().toString();
        String estrela  = edt_id_estrela_orb.getText().toString();
        if(isCampoVazio(planeta)){
            return 1;
        }else{
            if(isCampoVazio(satelite_natural)){
                return 2;
            }else{
                if(isCampoVazio(estrela)){
                    return 3;
                }
            }
        }
        return 0;
    }

    public boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    @Override
    public void onOrbitaCompleted(String result) {
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
            dlg.setMessage("Falha ao inserir órbita!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Operação concluída com sucesso!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActAddOrbita.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }
    }
}
