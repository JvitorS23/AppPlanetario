package com.example.appplanetario.banco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.appplanetario.SateliteNatural;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddSateliteBackground extends AsyncTask<SateliteNatural, Void, String> {
    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    private AddSateliteBackground.OnAddSateliteCompletedListener onAddSateliteCompletedListener;

    public AddSateliteBackground(Context mContext) {
        this.mContext = mContext;
    }

    public interface OnAddSateliteCompletedListener{
        void onAddSateliteCompleted(String result);
    }

    public void setOnAddSateliteCompletedListener(AddSateliteBackground.OnAddSateliteCompletedListener onAddSateliteCompletedListener) {
        this.onAddSateliteCompletedListener = onAddSateliteCompletedListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Adicionando Satélite Natural...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }


    @Override
    protected String doInBackground(SateliteNatural ... satelite) {
        boolean conectou = false;
        conectou = connect();

        if(!conectou)
            return "ERRO-CONEXAO";


        String sql = "INSERT INTO astros.satelite_natural VALUES(?, ?, ?, ?, ?)";

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
            ps.setInt(1, satelite[0].getId());
            ps.setString(2, satelite[0].getNome());
            java.sql.Array comp = con.createArrayOf("VARCHAR", satelite[0].getComposicao());
            ps.setArray(3, comp);
            ps.setFloat(4, satelite[0].getTamanho());
            ps.setFloat(5, satelite[0].getPeso());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String result = "OK";
        try {
            ps.execute();
        } catch (SQLException e) {
            result = "ERRO-INSERCAO";
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
        if(onAddSateliteCompletedListener != null){
            //Chama o listener passando a string
            onAddSateliteCompletedListener.onAddSateliteCompleted(str);
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
