package com.example.appplanetario;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ListarBackground extends AsyncTask<String, Void, ResultSet> {

    public String tipo;
    public String erro;
    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    private ListarBackground.OnListarCompletedListener onListarCompletedListener;

    public ListarBackground(Context mContext) {
        this.mContext = mContext;
    }

    public interface OnListarCompletedListener{
        void onListarCompleted(ResultSet resultado, String erro);
    }

    public void setOnListarCompletedListener(ListarBackground.OnListarCompletedListener onListarCompletedListener) {
        this.onListarCompletedListener = onListarCompletedListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Buscando astros...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    protected ResultSet doInBackground(String ... tipo) {
        boolean conectou = false;
        conectou = connect();
        this.erro = "OK";
        this.tipo = tipo[0];

        if(!conectou)
            this.erro =  "ERRO-CONEXAO";

        String sql = null;

        switch (tipo[0]) {
            case "Planeta":
                sql = "SELECT * FROM astros.planeta";
                break;
            case "Galáxia":
                sql = "SELECT * FROM astros.galaxia";
                break;
            case "Estrela":
                sql = "SELECT * FROM astros.estrela";
                break;
            case "Sistema Planetário":
                sql = "SELECT * FROM astros.sistema_planetario";
                break;
            case "Satélite Natural":
                sql = "SELECT * FROM astros.satelite_natural";
                break;
        }

        //esse método passa o sql ao banco mas n executa
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            this.erro = "ERRO-CONEXAO";        }


        ResultSet resultado = null;
        try {
             resultado = ps.executeQuery();

        }catch (SQLException e) {
            e.printStackTrace();
            this.erro = "ERRO-CONSULTA";
        }
        try {
            if(this.con!=null){
                this.con.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }


    @Override
    protected void onPostExecute(ResultSet resultado){
        super.onPostExecute(resultado);
        mDialog.dismiss();
        if(onListarCompletedListener != null){
            //Chama o listener passando a string
            onListarCompletedListener.onListarCompleted(resultado, this.erro);

        }
    }


    protected boolean connect() {

        try {
            /** Pasando o nome do Driver do PostgreSQL */
            Class.forName("org.postgresql.Driver");

            /** Obtendo a conexao com o banco de dados*/
            this.con = DriverManager.getConnection("jdbc:postgresql://ec2-52-202-185-87.compute-1.amazonaws.com:5432/d3kpi243df7o13?sslmode=require", "zgashvtuvobqho", "c66b10ef01f1847512fee89609de964b73142d8f811661916ed17ad87df6868d");

            /** Retorna um erro caso nao encontre o driver, ou alguma informacao sobre o mesmo esteja errada */
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
}
