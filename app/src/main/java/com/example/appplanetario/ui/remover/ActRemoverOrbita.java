package com.example.appplanetario.ui.remover;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.ResultSet;

import com.example.appplanetario.banco.OrbitaBackground;
import com.example.appplanetario.R;
import com.example.appplanetario.ui.Act_Inicio;

public class ActRemoverOrbita extends AppCompatActivity implements OrbitaBackground.OnOrbitaCompletedListener {

    private EditText edt_id_remover_planeta_orb;
    private EditText edt_id_remover_satelite_natural_orb;
    private EditText edt_id_remover_estrela_orb;
    private Button btn_remover_orbita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_remover_orbita);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setTitle("Remover Órbita");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_id_remover_planeta_orb = (EditText) findViewById(R.id.edtIdRemoverPlanetaOrb);
        edt_id_remover_satelite_natural_orb = (EditText) findViewById(R.id.edtIdRemoverSateliteNaturalOrb);
        edt_id_remover_estrela_orb = (EditText) findViewById(R.id.edtIdRemoverEstrelaOrb);
        btn_remover_orbita = (Button) findViewById(R.id.btnRemoveOrbita);

    }

    public void removeOrbita(View view){
        int valid = validaCampos();

        if(valid == 0){
            String id_planeta = edt_id_remover_planeta_orb.getText().toString();
            String id_satelite_natural = edt_id_remover_satelite_natural_orb.getText().toString();
            String id_estrela = edt_id_remover_estrela_orb.getText().toString();

            OrbitaBackground orbitaBackground = new OrbitaBackground(this, "Remover");
            orbitaBackground.setOnOrbitaCompletedListener(ActRemoverOrbita.this);
            orbitaBackground.execute(id_planeta, id_satelite_natural, id_estrela);
        }else{
            if(valid==1){
                edt_id_remover_planeta_orb.requestFocus();
                Toast.makeText(this, "Campo planeta vazio", Toast.LENGTH_SHORT).show();
            }else{
                if(valid==2){
                    edt_id_remover_satelite_natural_orb.requestFocus();
                    Toast.makeText(this, "Campo satélite natural vazio", Toast.LENGTH_SHORT).show();
                }else{
                    if(valid==3){
                        edt_id_remover_estrela_orb.requestFocus();
                        Toast.makeText(this, "Campo estrela vazio", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public int validaCampos(){
        String planeta = edt_id_remover_planeta_orb.getText().toString();
        String satelite_natural = edt_id_remover_satelite_natural_orb.getText().toString();
        String estrela  = edt_id_remover_estrela_orb.getText().toString();

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
    public void onOrbitaCompleted(String result, ResultSet resultSet) {
        if(result.equals("ERRO-CONEXAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha na conexão!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        if(result.equals("ERRO-REMOCAO")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro!");
            dlg.setMessage("Falha ao remover órbita!");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        if(result.equals("OK")) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Operação concluída com sucesso!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActRemoverOrbita.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }
    }
}
