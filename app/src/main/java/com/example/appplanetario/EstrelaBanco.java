package com.example.appplanetario;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.StringBufferInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EstrelaBanco extends AsyncTask<String, Void, String> {

    public interface OnEstrelaCompletedListener{
        void onEstrelaCompleted(String result);
    }

    public static Connection con;
    public Context mContext;
    public ProgressDialog mDialog;
    public Estrela estrela;
    public String opcao;

    private OnEstrelaCompletedListener onEstrelaCompletedListener;

    public void setOnEstrelaCompletedListener(OnEstrelaCompletedListener onEstrelaCompletedListener){
        this.onEstrelaCompletedListener = onEstrelaCompletedListener;
    }

    public EstrelaBanco(Context context, Estrela estrela, String opcao){
        this.mContext = context;
        this.estrela = estrela;
        this.opcao = opcao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Realizando alteração...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    protected boolean connect() {
        try {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection("jdbc:postgresql://ec2-52-202-185-87.compute-1.amazonaws.com:5432/d3kpi243df7o13?sslmode=require", "zgashvtuvobqho", "c66b10ef01f1847512fee89609de964b73142d8f811661916ed17ad87df6868d");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Erro ao conectar o driver");
            cnfe.printStackTrace();
        } catch (SQLException e) {
            if(this.con==null)
                return false;
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected String doInBackground(String ... user) {
        String sql = "";
        if(!connect())
            return "ERRO-CONEXAO";
        switch (opcao){
            case "Modificar":
                sql = "UPDATE astros.estrela SET id_estrela = ?, idade_estrela = ?, dist_terra = ?, nome_estrela = ?, tam_estrela = ?, gravidade_estrela = ?, tipo_estrela = ?, morte = ? WHERE id_estrela = ?";
                break;
        }



        //esse método passa o sql ao banco mas n executa
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO-CONEXAO";
        }

        //Especifica aq os parâmetros da query na sequência das ?
        try {
            switch (opcao){
                case "Modificar":
                    ps.setInt(1, estrela.getId());
                    ps.setInt(2, estrela.getIdade());
                    ps.setFloat(3, estrela.getDist_terra());
                    ps.setString(4, estrela.getNome());
                    ps.setFloat(5, estrela.getTamanho());
                    ps.setFloat(6, estrela.getGravidade());
                    ps.setString(7, estrela.getTipo_estrela());
                    ps.setBoolean(8, estrela.isMorreu());
                    ps.setInt(9, estrela.getId());
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String result = "OK";
        try {
            ps.execute();
        } catch (SQLException e) {
            switch (opcao){
                case "Modificar":
                    result = "ERRO-ATUALIZACAO";
            }
            e.printStackTrace();
        }
        try {
            if(this.con!=null){
                this.con.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String str){
        super.onPostExecute(str);
        mDialog.dismiss();

        if(onEstrelaCompletedListener != null){
            //Chama o listener passando a string
            onEstrelaCompletedListener.onEstrelaCompleted(str);
        }
    }
}
