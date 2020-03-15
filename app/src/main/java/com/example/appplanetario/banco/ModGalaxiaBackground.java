package com.example.appplanetario.banco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.appplanetario.Galaxia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModGalaxiaBackground extends AsyncTask<Galaxia, Void, String> {

    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    private ModGalaxiaBackground.OnModGalaxiaCompletedListener onModGalaxiaCompletedListener;
    public int id;

    public ModGalaxiaBackground(Context mContext, int id) {
        this.id = id;
        this.mContext = mContext;
    }

    public interface OnModGalaxiaCompletedListener{
        void onModGalaxiaCompleted(String result);
    }

    public void setOnModGalaxiaCompletedListener(ModGalaxiaBackground.OnModGalaxiaCompletedListener onModGalaxiaCompletedListener) {
        this.onModGalaxiaCompletedListener = onModGalaxiaCompletedListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Modificando Galaxia...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }


    @Override
    protected String doInBackground(Galaxia ... galaxia) {
        boolean conectou = false;
        conectou = connect();

        if(!conectou)
            return "ERRO-CONEXAO";


        String sql = "UPDATE astros.galaxia SET id_galaxia=?, qtd_sistemas=?, dist_terra=?, nome_galaxia=? WHERE id_galaxia=?";

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
            ps.setInt(1, galaxia[0].getId());
            ps.setInt(2, galaxia[0].getQtd_sistemas());
            ps.setFloat(3, galaxia[0].getDist_terra());
            ps.setString(4, galaxia[0].getNome());
            ps.setInt(5, this.id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String result = "OK";
        try {
            ps.execute();
        } catch (SQLException e) {
            result = "ERRO-MODIFICAR";
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
        if(onModGalaxiaCompletedListener != null){
            //Chama o listener passando a string
            onModGalaxiaCompletedListener.onModGalaxiaCompleted(str);
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