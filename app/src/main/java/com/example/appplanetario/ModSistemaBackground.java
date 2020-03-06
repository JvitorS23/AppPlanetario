package com.example.appplanetario;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModSistemaBackground extends AsyncTask<SistemaPlanetario, Void, String> {

    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    private ModSistemaBackground.OnModSistemaCompletedListener onModSistemaCompletedListener;
    public int id;
    public ModSistemaBackground(Context mContext, int id) {
        this.id = id;
        this.mContext = mContext;
    }

    public interface OnModSistemaCompletedListener{
        void onModSistemaCompleted(String result);
    }

    public void setOnModSistemaCompletedListener(ModSistemaBackground.OnModSistemaCompletedListener onModSistemaCompletedListener) {
        this.onModSistemaCompletedListener = onModSistemaCompletedListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Modificando Satélite Natural...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    protected String doInBackground(SistemaPlanetario... sistema) {
        boolean conectou = false;
        conectou = connect();

        if(!conectou)
            return "ERRO-CONEXAO";

        String sql = "SELECT id_galaxia FROM astros.galaxia WHERE id_galaxia = ?";

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
            ps.setInt(1, sistema[0].getId_galaxia());
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO-MODIFICAR";
        }

        String result = "";

        try {
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                result = "OK";

            }else{
                return "ERRO-MODIFICAR";          }

        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO-MODIFICAR";
        }

        sql = "UPDATE astros.sistema_planetario SET id_sistema=?, id_galaxia=?, qtd_planetas=?, qtd_estrelas=?, idade_sistema=?, nome_sistema=? WHERE id_sistema=?";

        //esse método passa o sql ao banco mas n executa
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO-CONEXAO";
        }

        //Especifica aq os parâmetros da query na sequência das ?
        try {
            ps.setInt(1, sistema[0].getId());
            ps.setInt(2, sistema[0].getId_galaxia());
            ps.setInt(3, sistema[0].getQtde_planetas());
            ps.setInt(4, sistema[0].getQtde_estrelas());
            ps.setInt(5, sistema[0].getIdade());
            ps.setString(6, sistema[0].getNome());
            ps.setInt(7, this.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = "OK";
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
        if(onModSistemaCompletedListener != null){
            //Chama o listener passando a string
            onModSistemaCompletedListener.onModSistemaCompleted(str);
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