package com.example.appplanetario.banco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrbitaBackground extends AsyncTask<String, Void, String> {

    public interface OnOrbitaCompletedListener{
        void onOrbitaCompleted(String result);
    }

    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    public String opcao;

    private OnOrbitaCompletedListener onOrbitaCompletedListener;

    public void setOnOrbitaCompletedListener(OnOrbitaCompletedListener onOrbitaCompletedListener){
        this.onOrbitaCompletedListener = onOrbitaCompletedListener;
    }

    public OrbitaBackground(Context context, String opcao){
        this.mContext = context;
        this.opcao = opcao;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Realizando relacionamento...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    protected String doInBackground(String... result) {
        boolean conectou = false;
        String sql = "";
        conectou = connect();

        if(!conectou)
            return "ERRO-CONEXAO";

        switch (opcao){
            case "Adicionar":
                sql = "INSERT INTO astros.orbita VALUES (?, ?, ?)";
                break;
            case "Remover":
                sql = "DELETE FROM astros.orbita WHERE id_planeta = ? AND id_sn = ? AND id_estrela = ?";
                break;
        }

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO-CONEXAO";
        }

        try {
            switch (opcao){
                case "Adicionar":
                    ps.setInt(1, Integer.parseInt(result[0]));//planeta
                    ps.setInt(2, Integer.parseInt(result[1]));//satélite natural
                    ps.setInt(3, Integer.parseInt(result[2]));//estrela
                    break;
                case "Remover":
                    ps.setInt(1, Integer.parseInt(result[0]));//planeta
                    ps.setInt(2, Integer.parseInt(result[1]));//satélite natural
                    ps.setInt(3, Integer.parseInt(result[2]));//estrela
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            switch (opcao){
                case "Adicionar":
                    return "ERRO-INSERCAO";
                case "Remover":
                    return "ERRO-REMOCAO";
            }

        }

        String resultado = "OK";
        try{
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
            switch (opcao){
                case "Adicionar":
                    return "ERRO-INSERCAO";
                case "Remover":
                    return "ERRO-REMOCAO";
            }
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
    protected void onPostExecute(String str){
        super.onPostExecute(str);
        mDialog.dismiss();
        if(onOrbitaCompletedListener != null){
            //Chama o listener passando a string
            onOrbitaCompletedListener.onOrbitaCompleted(str);
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
