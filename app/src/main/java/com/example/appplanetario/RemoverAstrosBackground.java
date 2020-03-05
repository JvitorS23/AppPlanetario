package com.example.appplanetario;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoverAstrosBackground  extends AsyncTask<String, Void, String> {

    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    public String tipo;

    private RemoverAstrosBackground.OnRemoverCompletedListener onRemoverCompletedListener;

    public RemoverAstrosBackground(Context mContext, String tipo) {
        this.mContext = mContext;
        this.tipo = tipo;
    }

    public interface OnRemoverCompletedListener{
        void onRemoverCompleted(String result) throws SQLException;
    }

    public void setOnRemoverCompletedListener(RemoverAstrosBackground.OnRemoverCompletedListener onRemoverCompletedListener) {
        this.onRemoverCompletedListener = onRemoverCompletedListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Removendo Astro...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }


    @Override
    protected String doInBackground(String ... id) {
        boolean conectou = false;
        conectou = connect();

        if(!conectou)
            return "ERRO-CONEXAO";

        String sql = "";

        int id_ = Integer.parseInt(id[0]);

        switch (tipo){
            case "Planeta":
                sql = "DELETE FROM astros.planeta WHERE id_planeta = ?";
                break;
            case "Estrela":
                sql = "DELETE FROM astros.estrela WHERE id_estrela = ?";
                break;
            case "Sistema Planetário":
                sql = "DELETE FROM astros.sistema_planetario WHERE id_sistema = ?";
                break;
            case "Satélite Natural":
                sql = "DELETE FROM astros.satelite_natural WHERE id_sn = ?";
                break;
            case "Galáxia":
                sql = "DELETE FROM astros.galaxia WHERE id_galaxia = ?";
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
            ps.setInt(1, id_);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String result = "OK";

        try {
            ps.execute();

        } catch (SQLException e) {
            result = "ERRO-REMOVER";
            e.printStackTrace();
        }

        //encerra conexão
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
        if(onRemoverCompletedListener != null){
            //Chama o listener passando a string
            try{
                onRemoverCompletedListener.onRemoverCompleted(str);
            }catch (SQLException e){
                e.printStackTrace();
            }
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
