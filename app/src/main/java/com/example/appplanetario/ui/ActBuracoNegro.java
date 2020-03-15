package com.example.appplanetario.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appplanetario.Estrela;
import com.example.appplanetario.R;
import com.example.appplanetario.banco.RemoverAstrosBackground;

import java.sql.SQLException;

public class ActBuracoNegro extends AppCompatActivity implements RemoverAstrosBackground.OnRemoverCompletedListener {

    private TextView txt_id_buraco;
    private TextView txt_nome_nuraco;
    private TextView txt_dist_terra_buraco;
    private TextView txt_gravidade_buraco;
    private TextView txt_tamanho_buraco;
    private Button btn_remove_buraco;
    private Intent it;
    private Estrela buraco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_buraco_negro);


        txt_id_buraco = findViewById(R.id.txtIdBuraco);
        txt_nome_nuraco = findViewById(R.id.txtNomeBuraco);
        txt_dist_terra_buraco = findViewById(R.id.txtDistTerraBuraco);
        txt_gravidade_buraco = findViewById(R.id.txtGravidadeBuraco);
        txt_tamanho_buraco = findViewById(R.id.txtTamanhoBuraco);
        btn_remove_buraco = findViewById(R.id.btnRemoverBuraco);

        buraco = (Estrela) getIntent().getSerializableExtra("buraco");
        txt_id_buraco.setText("ID: " + buraco.getId());
        txt_nome_nuraco.setText("Nome: " + buraco.getNome());
        txt_dist_terra_buraco.setText("Distância da Terra: " + buraco.getDist_terra());
        txt_tamanho_buraco.setText("Tamanho: " + buraco.getTamanho());
    }

    public void removeBuraco(View view){
        RemoverAstrosBackground removerAstrosBackground = new RemoverAstrosBackground(this, "Estrela");
        removerAstrosBackground.setOnRemoverCompletedListener(ActBuracoNegro.this);
        removerAstrosBackground.execute(String.valueOf(buraco.getId()));
    }

    @Override
    public void onRemoverCompleted(String result) throws SQLException {
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
        if(result.equals("OK")){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Sucesso!");
            dlg.setMessage("Buraco Negro Removido!");
            dlg.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent it = new Intent(ActBuracoNegro.this, Act_Inicio.class);
                    startActivity(it);
                    finish();
                }
            });
            dlg.show();
        }
    }
}
