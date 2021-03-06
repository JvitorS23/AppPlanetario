package com.example.appplanetario.banco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.appplanetario.Estrela;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModEstrelaBackground extends AsyncTask<Estrela, Void, String> {

    public static Connection con;//conexão com o banco
    public Context mContext;//activity que chama
    public ProgressDialog mDialog;//load
    public int id;
    private ModEstrelaBackground.OnModEstrelaCompletedListener onModEstrelaCompletedListener;

    public ModEstrelaBackground(Context mContext, int id) {
        this.id = id;
        this.mContext = mContext;
    }

    public interface OnModEstrelaCompletedListener{
        void onModEstrelaCompleted(String result);
    }

    public void setOnModEstrelaCompletedListener(ModEstrelaBackground.OnModEstrelaCompletedListener onModEstrelaCompletedListener) {
        this.onModEstrelaCompletedListener = onModEstrelaCompletedListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Modificando Estrela...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }


    @Override
    protected String doInBackground(Estrela ... estrela) {
        boolean conectou = false;
        conectou = connect();

        if(!conectou)
            return "ERRO-CONEXAO";


        String sql = "UPDATE astros.estrela SET id_estrela=?, idade_estrela=?, dist_terra=?, nome_estrela=?, tam_estrela=?, gravidade_estrela=?, tipo_estrela=?, morte = ? WHERE id_estrela=?";

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
            ps.setInt(1, estrela[0].getId());
            ps.setInt(2, estrela[0].getIdade());
            ps.setFloat(3, estrela[0].getDist_terra());
            ps.setString(4, estrela[0].getNome());
            ps.setFloat(5, estrela[0].getTamanho());
            ps.setFloat(6, estrela[0].getGravidade());
            ps.setString(7, estrela[0].getTipo_estrela());
            ps.setBoolean(8, estrela[0].isMorte());
            ps.setInt(9, this.id);


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
        if(onModEstrelaCompletedListener != null){
            //Chama o listener passando a string
            onModEstrelaCompletedListener.onModEstrelaCompleted(str);
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
