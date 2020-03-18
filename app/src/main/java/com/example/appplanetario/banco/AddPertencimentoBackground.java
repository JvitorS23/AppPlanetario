package com.example.appplanetario.banco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPertencimentoBackground extends AsyncTask<String, Void, String> {

    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    private AddPertencimentoBackground.OnAddPertencimentoCompletedListener onAddPertencimentoCompletedListener;

    public AddPertencimentoBackground(Context mContext) {
        this.mContext = mContext;
    }

    public interface OnAddPertencimentoCompletedListener {
        void onAddPertencimentoCompleted(String result);
    }

    public void setOnAddPertencimentoCompletedListener(AddPertencimentoBackground.OnAddPertencimentoCompletedListener onAddPertencimentoCompletedListener) {
        this.onAddPertencimentoCompletedListener = onAddPertencimentoCompletedListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Adicionando Pertencimento...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    protected String doInBackground(String... ids) {
        boolean conectou = false;
        conectou = connect();
        if (!conectou)
            return "ERRO-CONEXAO";

        String sql = "";
        PreparedStatement ps = null;
        String result = "";

        if(ids[0].equals("Planeta")){
            sql = "INSERT INTO astros.sistema_planeta VALUES(?, ?)";
        }else{
            sql = "INSERT INTO astros.sistema_estrela VALUES(?, ?)";
        }

        //esse método passa o sql ao banco mas n executa
        ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO-CONEXAO";
        }

        //Especifica aq os parâmetros da query na sequência das ?
        try {
            ps.setInt(1, Integer.parseInt(ids[1]));
            ps.setInt(2, Integer.parseInt(ids[2]));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        result = "OK";
        try {
            ps.execute();
        } catch (SQLException e) {
            result = "ERRO-INSERCAO";
            e.printStackTrace();
        }
        try {
            if (this.con != null) {
                this.con.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        mDialog.dismiss();
        if (onAddPertencimentoCompletedListener != null) {
            //Chama o listener passando a string
            onAddPertencimentoCompletedListener.onAddPertencimentoCompleted(str);
        }
    }


    protected boolean connect() {

        try {
            /** Pasando o nome do Driver do PostgreSQL */
            Class.forName("org.postgresql.Driver");

            /** Obtendo a conexao com o banco de dados*/
            this.con = DriverManager.getConnection("jdbc:postgresql://ec2-52-202-185-87.compute-1.amazonaws.com:5432/d3kpi243df7o13?sslmode=require", "zgashvtuvobqho", "c66b10ef01f1847512fee89609de964b73142d8f811661916ed17ad87df6868d");

            /** Retorna um erro caso nao encontre o driver, ou alguma informacao sobre o mesmo esteja errada */
        } catch (Exception e) {
            System.out.println("Erro ao conectar o driver");
            e.printStackTrace();
        }
        return true;
    }

}