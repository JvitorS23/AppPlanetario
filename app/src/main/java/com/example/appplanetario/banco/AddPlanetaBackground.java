package com.example.appplanetario.banco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.appplanetario.Planeta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;

public class AddPlanetaBackground extends AsyncTask<Planeta, Void, String> {


    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    private AddPlanetaBackground.OnAddPlanetaCompletedListener onAddPlanetaCompletedListener;


    public AddPlanetaBackground(Context mContext) {
        this.mContext = mContext;
    }

    public interface OnAddPlanetaCompletedListener{
        void onAddPlanetaCompleted(String result);
    }

    public void setOnAddPlanetaCompletedListener(AddPlanetaBackground.OnAddPlanetaCompletedListener onAddPlanetaCompletedListener) {
        this.onAddPlanetaCompletedListener = onAddPlanetaCompletedListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Adicionando Planeta...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }


    @Override
    protected String doInBackground(Planeta ... planeta) {
        boolean conectou = false;
        conectou = connect();

        if(!conectou)
            return "ERRO-CONEXAO";


        String sql = "INSERT INTO astros.planeta VALUES(?, ?, ?, ?, ?, ?, ?)";

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
            ps.setInt(1, planeta[0].getId());
            ps.setString(2, planeta[0].getNome());
            ps.setFloat(3, planeta[0].getTamanho());
            ps.setFloat(4,planeta[0].getPeso() );
            ps.setFloat(5, planeta[0].getVel_rotacao());
            ps.setFloat(6, planeta[0].getGravidade());
            java.sql.Array comp = con.createArrayOf("VARCHAR", planeta[0].getComposicao());
            ps.setArray(7, comp);

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
        if(onAddPlanetaCompletedListener != null){
            //Chama o listener passando a string
            onAddPlanetaCompletedListener.onAddPlanetaCompleted(str);
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
