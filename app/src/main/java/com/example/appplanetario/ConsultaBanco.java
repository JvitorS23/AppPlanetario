package com.example.appplanetario;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaBanco extends AsyncTask<String, Void, String> {

    public interface OnConsultaCompletedListener{
        void onConsultaCompleted(String result, ResultSet resultado) throws SQLException;
    }

    public static Connection con;
    public Context mContext;
    public ProgressDialog mDialog;
    public String tipoAstro;
    public ResultSet resultado;
    public int id_astro;

    private OnConsultaCompletedListener onConsultaCompletedListener;

    public void setOnConsultaCompletedListener(OnConsultaCompletedListener onConsultaCompletedListener){
        this.onConsultaCompletedListener = onConsultaCompletedListener;
    }

    public ConsultaBanco(Context context, int id_astro, String tipoAstro){
        this.mContext = context;
        this.id_astro = id_astro;
        this.tipoAstro = tipoAstro;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Buscando...");
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
        switch (tipoAstro){
            case "Estrela":
                sql = "SELECT * FROM astros.estrela WHERE id_estrela = ?";
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
            switch (tipoAstro){
                case "Estrela":
                    ps.setInt(1, id_astro);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String result = "OK";
        ResultSet resultado;
        try {
            this.resultado = ps.executeQuery();
        } catch (SQLException e) {
            result = "ERRO-CONSULTA";
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
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        mDialog.dismiss();
        if(onConsultaCompletedListener != null){
            //Chama o listener passando a string
            try {
                onConsultaCompletedListener.onConsultaCompleted(result, this.resultado);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
