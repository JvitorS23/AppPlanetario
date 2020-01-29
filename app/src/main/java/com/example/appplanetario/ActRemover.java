package com.example.appplanetario;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActRemover extends AppCompatActivity {

    private TextView txtID;
    private EditText edtId;
    public String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_remover);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        tipo = "Entidade";
        tipo = (String) getIntent().getSerializableExtra("tipo");
        toolbar.setTitle("Remover " + tipo);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtID = findViewById(R.id.txt_id);
        edtId = findViewById(R.id.edt_id);
        txtID.setText("ID "+tipo);

    }

    public void clickBtnRemover(View view){
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edtId.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        boolean achou = true;
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);

        switch (tipo) {
            case "Planeta":
                //consultar id na tabela planeta

                //achou = consultaBanco();
                if(achou){
                    dlg.setMessage("Tem certeza que deseja remover "+tipo + " ID: " + edtId.getText().toString());
                    dlg.setNegativeButton("Cancelar", null);
                    dlg.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which){
                            boolean removeu = true;
                            //removeu = removerPlaneta(id);
                            if(removeu){
                                Toast.makeText(getApplicationContext(), tipo+" removido!", Toast.LENGTH_LONG).show();
                                edtId.clearFocus();
                                edtId.setText(null);
                            }else{
                                Toast.makeText(getApplicationContext(), "Falha ao remover!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dlg.show();
                }else{
                    dlg.setMessage(tipo + " não encontrado!");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }
                break;

            case "Galáxia":
                //consultar id na tabela galáxia
                //achou = consultaBanco();
                if(achou){
                    dlg.setMessage("Tem certeza que deseja remover "+tipo + " ID: " + edtId.getText().toString());
                    dlg.setNegativeButton("Cancelar", null);
                    dlg.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which){
                            boolean removeu = true;
                            //removeu = removerPlaneta(id);
                            if(removeu){
                                Toast.makeText(getApplicationContext(), tipo+" removida!", Toast.LENGTH_LONG).show();
                                edtId.clearFocus();
                                edtId.setText(null);
                            }else{
                                Toast.makeText(getApplicationContext(), "Falha ao remover!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dlg.show();
                }else{
                    dlg.setMessage(tipo + " não encontrada!");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }
                break;
            case "Estrela":
                //consultar id na tabela Estrela
                                //achou = consultaBanco();
                if(achou){
                    dlg.setMessage("Tem certeza que deseja remover "+tipo + " ID: " + edtId.getText().toString());
                    dlg.setNegativeButton("Cancelar ", null);
                    dlg.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which){
                            boolean removeu = true;
                            //removeu = removerPlaneta(id);
                            if(removeu){
                                Toast.makeText(getApplicationContext(), tipo+" removida!", Toast.LENGTH_LONG).show();
                                edtId.clearFocus();
                                edtId.setText(null);
                            }else{
                                Toast.makeText(getApplicationContext(), "Falha ao remover!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dlg.show();
                }else{
                    dlg.setMessage(tipo + " não encontrada!");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }
                break;
            case "Sistema Planetário":
                //consultar id na tabela Sistema Planetário

                //achou = consultaBanco();
                if(achou){
                    dlg.setMessage("Tem certeza que deseja remover " + tipo + " ID: " + edtId.getText().toString());
                    dlg.setNegativeButton("Cancelar", null);
                    dlg.setPositiveButton("Remover ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which){
                            boolean removeu = true;
                            //removeu = removerPlaneta(id);
                            if(removeu){
                                Toast.makeText(getApplicationContext(), tipo+" removido!", Toast.LENGTH_LONG).show();
                                edtId.clearFocus();
                                edtId.setText(null);
                            }else{
                                Toast.makeText(getApplicationContext(), "Falha ao remover!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dlg.show();
                }else{
                    dlg.setMessage(tipo + " não encontrado!");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }

                break;
            case "Satélite Natural":
                //consultar id na tabela Satélite natural
                //achou = consultaBanco();
                if(achou){
                    dlg.setMessage("Tem certeza que deseja remover "+tipo + " ID: " + edtId.getText().toString());
                    dlg.setNegativeButton("Cancelar ", null);
                    dlg.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which){
                            boolean removeu = true;
                            //removeu = removerPlaneta(id);
                            if(removeu){
                                Toast.makeText(getApplicationContext(), tipo+" removido!", Toast.LENGTH_LONG).show();
                                edtId.clearFocus();
                                edtId.setText(null);
                            }else{
                                Toast.makeText(getApplicationContext(), "Falha ao remover!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dlg.show();
                }else{
                    dlg.setMessage(tipo + " não encontrado!");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }
                break;
        }
    }
}
