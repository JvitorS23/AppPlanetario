package com.example.appplanetario.banco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginBackground extends AsyncTask<String, Void, String>{

    //interface para comunicação da tarefa com a activity
    public interface OnLoginCompletedListener{
        void onLoginCompleted(String result);
    }

    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load

    private LoginBackground.OnLoginCompletedListener onLoginCompletedListener;

    public void setOnLoginCompletedListener(LoginBackground.OnLoginCompletedListener onLoginCompletedListener){
        this.onLoginCompletedListener = onLoginCompletedListener;
    }

    public LoginBackground(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Validando login...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    protected String doInBackground(String ... user) {
        if(!connect())
            return "ERRO-CONEXAO";

        String sql = "SELECT username FROM astros.usuario WHERE username = ? AND password = md5(?)";

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
            ps.setString(1, user[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ps.setString(2, user[1]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String result = "";

        try {
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                result = "OK";


            }else{
                result = "ERRO-CONSULTA";

            }

        } catch (SQLException e) {
            result = "ERRO-CONSULTA";
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
        if(onLoginCompletedListener != null){
            //Chama o listener passando a string
            onLoginCompletedListener.onLoginCompleted(str);
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
