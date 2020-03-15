package com.example.appplanetario.banco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoverPertenceBackground extends AsyncTask<String, Void, String> {

    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    public String tipo;

    private RemoverPertenceBackground.OnRemoverPertenceCompletedListener onRemoverPertenceCompletedListener;

    public RemoverPertenceBackground(Context mContext, String tipo) {
        this.mContext = mContext;
        this.tipo = tipo;
    }

    public interface OnRemoverPertenceCompletedListener{
        void onRemoverPertenceCompleted(String result) ;
    }

    public void setOnRemoverPertenceCompletedListener(RemoverPertenceBackground.OnRemoverPertenceCompletedListener onRemoverPertenceCompletedListener) {
        this.onRemoverPertenceCompletedListener = onRemoverPertenceCompletedListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Removendo Pertencimento...");
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

        int id_sistema = Integer.parseInt(id[0]);
        int id_pertence = Integer.parseInt(id[1]);

      if(tipo.equals("Planeta")){
          sql = "DELETE FROM astros.sistema_planeta WHERE id_sistema = ? and id_planeta = ?";

      }else{
          sql = "DELETE FROM astros.sistema_estrela WHERE id_sistema = ? and id_estrela = ?";
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
            ps.setInt(1, id_sistema);
            ps.setInt(2, id_pertence);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String result = "OK";

        try {
            int a = ps.executeUpdate();

            if(a==0)
                result = "ERRO-REMOVER";

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
        if(onRemoverPertenceCompletedListener != null){
            //Chama o listener passando a string
            onRemoverPertenceCompletedListener.onRemoverPertenceCompleted(str);
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
